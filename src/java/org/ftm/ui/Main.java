package org.ftm.ui;

import org.bushe.swing.event.EventBus;
import org.ftm.api.Bill;
import org.ftm.api.Candidate;
import org.ftm.api.Contribution;
import org.ftm.api.DataAccessObject;
import org.ftm.impl.SimpleDataAccessObject;
import org.ftm.util.Debug;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the starting class for the application.
 *
 * @author <font size=-1 color="#a3a3a3">Johnny Hujol</font>
 * @since Oct 26, 2010
 */
public final class Main {

    //Specify the look and feel to use.  Valid values:
    //null (use the default), "Metal", "System", "Motif", "GTK+"
    private final static String LOOKANDFEEL = "Metal";

    private final JPanel placeHolder = new JPanel();
    private final Component candidateCp = new CandidateComponent();
    private final Component issueCp = new IssueComponent();
    private final Component contributionCp = new ContributionComponent();
    private final Applet visu;
    private final JSplitPane mainPane;
    private final NavigationComponent navigation;

    private final Model model;
    private final DataAccessObject dao;
    private final Controller controller;

    public Main() {
        model = Model.getSingleton();
        dao = new SimpleDataAccessObject();
        controller = new Controller(this, this.dao, model);

        final NavigationComponent mp = new NavigationComponent();

        placeHolder.setLayout(new BoxLayout(placeHolder, BoxLayout.LINE_AXIS));
        if(Debug.isDebug()) {
            placeHolder.setBackground(Color.cyan);
        }

        navigation = mp;
        mainPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, navigation, placeHolder);
        final int dividerLocation = (int) mp.getPreferredSize().getWidth() + mp.getInsets().left + mp.getInsets().right;
        mainPane.setDividerLocation(dividerLocation);
        mainPane.setEnabled(false);
        mainPane.setDividerSize(0);
        if(Debug.isDebug()) {
            mainPane.setBackground(Color.yellow);
        }
        visu = new VisualizationComponent2();
    }

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            public void run() {
                createAndShowGui();
            }
        };
        EventQueue.invokeLater(runnable);
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGui() {
        initLookAndFeel();

        final JFrame f = new JFrame("Follow The Money");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final Main m = new Main();
        f.setContentPane(m.mainPane);

        f.setResizable(true);
        final Dimension dim = new Dimension(920, 760);
        f.setMinimumSize(dim);
        f.setSize(dim);
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setLocation((int) (screen.getWidth() - f.getWidth()) / 2, (int) (screen.getHeight() - f.getHeight()) / 2);

        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent windowEvent) {
                m.controller.close();
                m.dao.shutdown();
            }
        });

        f.pack();
        f.setVisible(true);

        EventBus.publish("candidateSearch", null);
        EventBus.publish("getissues", null);
    }

    private static void initLookAndFeel() {
        if(null != LOOKANDFEEL) {
            String lookAndFeel;
            if("Metal".equals(LOOKANDFEEL)) {
                lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
            }
            else if("System".equals(LOOKANDFEEL)) {
                lookAndFeel = UIManager.getSystemLookAndFeelClassName();
            }
            else if("Motif".equals(LOOKANDFEEL)) {
                lookAndFeel = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
            }
            else if("GTK+".equals(LOOKANDFEEL)) { //new in 1.4.2
                lookAndFeel = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
            }
            else {
                System.err.println("Unexpected value of LOOKANDFEEL specified: "
                    + LOOKANDFEEL);
                lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();
            }

            try {
                UIManager.setLookAndFeel(lookAndFeel);
            }
            catch(ClassNotFoundException e) {
                System.err.println("Couldn't find class for specified look and feel:"
                    + lookAndFeel);
                System.err.println("Did you include the L&F library in the class path?");
                System.err.println("Using the default look and feel.");
                Logger.getLogger("org.ftm.ui.Main").log(Level.WARNING, null, e);
            }
            catch(UnsupportedLookAndFeelException e) {
                System.err.println("Can't use the specified look and feel ("
                    + lookAndFeel
                    + ") on this platform.");
                System.err.println("Using the default look and feel.");
                Logger.getLogger("org.ftm.ui.Main").log(Level.WARNING, null, e);
            }
            catch(Exception e) {
                System.err.println("Couldn't get specified look and feel ("
                    + lookAndFeel
                    + "), for some reason.");
                System.err.println("Using the default look and feel.");
                Logger.getLogger("org.ftm.ui.Main").log(Level.WARNING, null, e);
            }
        }
    }

    private void switchComponent(Component component) {
        placeHolder.removeAll();
        placeHolder.add(component);
        placeHolder.add(Box.createHorizontalGlue());
        placeHolder.invalidate();
        placeHolder.validate();
        mainPane.validate();
        mainPane.invalidate();
        mainPane.repaint();
    }

    public void setCandidateSearch() {
        visu.stop();
        switchComponent(candidateCp);
    }

    public void setIssueSearch() {
        visu.stop();
        switchComponent(issueCp);
    }

    public void setContributionSearch() {
        visu.stop();
        switchComponent(contributionCp);
    }

    public void setVisualization() throws Exception {
        // Set information in model so visu can draw using the model.
        Model model = Model.getSingleton();
        Candidate candidate = model.getCandidateSelected();
        List<Bill> bills = dao.getBills(candidate, 2008);
        model.setBills(bills);

        final Collection<Contribution> contributions = dao.getContributions(candidate, 2008);
        model.setContributions(new ArrayList<Contribution>(contributions));


        // important to call this whenever embedding a PApplet.
        // It ensures that the animation thread is started and
        // that other internal variables are properly set.
        visu.init();
        visu.start();
        switchComponent(visu);
    }
}
