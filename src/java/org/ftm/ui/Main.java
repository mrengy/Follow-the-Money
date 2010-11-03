package org.ftm.ui;

import org.bushe.swing.event.EventBus;
import org.ftm.api.DataAccessObject;
import org.ftm.impl.SimpleDataAccessObject;
import processing.core.PApplet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the starting class for the application.
 *
 * @author <font size=-1 color="#a3a3a3">Johnny Hujol</font>
 * @since Oct 26, 2010
 */
public final class Main extends JFrame {

    private final JPanel placeHolder = new JPanel();
    private final CandidateComponent candidateCp = new CandidateComponent();
    private final IssueComponent issueCp = new IssueComponent();
    private final ContributionComponent contributionCp = new ContributionComponent();
    private final PApplet visu = new VisualizationComponent();

    public Main() {
        super("VisualizationComponent");

        // DAO
        final DataAccessObject dao = new SimpleDataAccessObject();
        final Controller controller = new Controller(this, dao, new Model());

        final NavigationComponent mp = new NavigationComponent();

        placeHolder.setLayout(new BorderLayout());

        setLayout(new BorderLayout());
        add(mp, BorderLayout.WEST);

        add(this.placeHolder, BorderLayout.CENTER);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        EventBus.publish("politicianSearch", null);
    }

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                    MetalLookAndFeel.setCurrentTheme(new OceanTheme());
                }
                catch(Exception e) {
                    Logger.getLogger("org.ftm.ui.Main").log(Level.WARNING, null, e);
                }

                final Main f = new Main();
                f.setResizable(true);
                f.setSize(800, 600);
                Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
                f.setLocation((int) (screen.getWidth() - f.getWidth()) / 2, (int) (screen.getHeight() - f.getHeight()) / 2);
                f.setVisible(true);
            }
        };
        EventQueue.invokeLater(runnable);
    }

    public void setPoliticianSearch() {
        switchComponent(candidateCp);
    }

    private void switchComponent(Component component) {
        placeHolder.removeAll();
        placeHolder.add(component, BorderLayout.CENTER);
        placeHolder.validate();
        this.validate();
        this.invalidate();
        this.repaint();
    }

    public void setIssueSearch() {
        switchComponent(issueCp);
    }

    public void setContributionSearch() {
        switchComponent(contributionCp);
    }

    public void setVisualization() {
        // important to call this whenever embedding a PApplet.
        // It ensures that the animation thread is started and
        // that other internal variables are properly set.
        visu.init();
        switchComponent(visu);
    }
}
