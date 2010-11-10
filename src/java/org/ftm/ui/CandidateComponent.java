package org.ftm.ui;

import org.bushe.swing.event.EventBus;
import org.bushe.swing.event.EventTopicSubscriber;
import org.ftm.api.Candidate;
import org.ftm.util.Debug;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Collection;
import java.util.List;

import static org.ftm.ui.ComponentUtils.createPanelWithFixedSize;

/**
 * @author <font size=-1 color="#a3a3a3">Johnny Hujol</font>
 * @since Oct 30, 2010
 */
final class CandidateComponent extends JPanel implements EventTopicSubscriber {

    private final JTextField zipCode = new JTextField(15);
    private final JLabel zipCodeLabel = new JLabel("Zip Code");

    private final JTextField candidateName = new JTextField(15);
    private final JLabel candidateNameLabel = new JLabel("Candidate Name");

    private final JLabel orLabel = new JLabel("Or");
    private final JList candidatesFound = new JList();
    private static final Dimension DIMENSION = new Dimension(150, 25);

    CandidateComponent() {
        JComponent zipCodeSearchPanel = createZipCodePanel();
        JComponent candidateNameSearchPanel = createCandidateNameSearchPanel();
        JComponent candidatesFoundPanel = createCandidatesFoundPanel();

        zipCodeSearchPanel.setAlignmentX(LEFT_ALIGNMENT);
        orLabel.setAlignmentX(LEFT_ALIGNMENT);
        candidateNameSearchPanel.setAlignmentX(LEFT_ALIGNMENT);

        orLabel.setPreferredSize(DIMENSION);
        candidatesFound.setPreferredSize(new Dimension(200, 150));

        final JPanel panel1 = ComponentUtils.createLineBoxLaidOutPanel();
        panel1.add(zipCodeSearchPanel);
        panel1.add(Box.createHorizontalGlue());

        final JPanel panel2 = ComponentUtils.createLineBoxLaidOutPanel();
        panel2.add(Box.createRigidArea(new Dimension((int) ((candidateNameSearchPanel.getPreferredSize().getWidth() /*- orLabel.getPreferredSize().getWidth()*/) / 2), 10)));
        panel2.add(orLabel);
        panel2.add(Box.createHorizontalGlue());

        final JPanel panel3 = ComponentUtils.createLineBoxLaidOutPanel();
        panel3.add(candidateNameSearchPanel);
        panel3.add(Box.createHorizontalGlue());

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        add(panel1);
        add(panel2);
        add(panel3);
        add(Box.createVerticalStrut(10));
        add(candidatesFoundPanel);
        add(Box.createVerticalGlue());

        if(Debug.isDebug()) {
            setBackground(Color.green);
        }

        candidatesFound.setCellRenderer(new MyListCellRenderer());

        // Some initial data
        candidatesFound.setListData(new String[]{
            "No candidates available"
        });
        candidatesFound.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                EventBus.publish("candidateselected", candidatesFound.getSelectedValue());
            }
        });
        // Add events
        zipCode.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if(KeyEvent.VK_ENTER == keyEvent.getKeyCode()) {
                    EventBus.publish("zipcode", zipCode.getText());
                }
            }
        });
        candidateName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if(KeyEvent.VK_ENTER == keyEvent.getKeyCode()) {
                    EventBus.publish("candidateNameSearch", candidateName.getText());
                }
            }
        });
        EventBus.subscribeStrongly("candidatesfound", this);
    }

    private JComponent createCandidatesFoundPanel() {
        final JPanel p = new JPanel();
        p.add(new JScrollPane(candidatesFound));
        return p;
    }

    private JComponent createCandidateNameSearchPanel() {
        final JPanel p = createPanelWithFixedSize(DIMENSION.width * 2 + 20);
        p.setLayout(new BoxLayout(p, BoxLayout.LINE_AXIS));

        ComponentUtils.setSizes(candidateName, DIMENSION);
        ComponentUtils.setSizes(candidateNameLabel, DIMENSION);

        p.add(Box.createHorizontalStrut(20));
        p.add(candidateNameLabel);
        p.add(Box.createHorizontalStrut(5));
        p.add(candidateName);
        return p;
    }

    private JComponent createZipCodePanel() {
        // This to make things aligned nicely
        final JPanel p = createPanelWithFixedSize(DIMENSION.width * 2 + 20);
        p.setLayout(new BoxLayout(p, BoxLayout.LINE_AXIS));

        ComponentUtils.setSizes(zipCodeLabel, DIMENSION);
        ComponentUtils.setSizes(zipCode, DIMENSION);

        p.add(Box.createHorizontalStrut(20));
        p.add(zipCodeLabel);
        p.add(Box.createHorizontalStrut(5));
        p.add(zipCode);
        return p;
    }

    /*
        @Override
        public Dimension getMinimumSize() {
            return getPreferredSize();
        }

        @Override
        public Dimension getMaximumSize() {
            return getPreferredSize();
        }

        @Override
        public Dimension getPreferredSize() {
            final Container container = getParent();
            if(null != container) {
                return container.getSize();
            }
            else {
                return super.getPreferredSize();
            }
        }
    */

    public void onEvent(String s, Object o) {
        if(o instanceof List) {
            Collection<Candidate> candidates = (List<Candidate>) o;
            candidatesFound.setListData(candidates.toArray(new Candidate[candidates.size()]));
        }
    }

    public void close() {
        EventBus.unsubscribe("candidatesfound", this);
    }

    private static class MyListCellRenderer extends DefaultListCellRenderer {

        public Component getListCellRendererComponent(JList jList, Object o, int i, boolean b, boolean b1) {
            final String text;
            if(o instanceof Candidate) {
                Candidate p = (Candidate) o;
                text = p.getFirstName() + ' ' + p.getLastName();
            }
            else {
                text = o.toString();
            }
            final Component cp = super.getListCellRendererComponent(jList, b, i, b, b1);
            if(cp instanceof JLabel) {
                ((JLabel) cp).setText(text);
            }
            return cp;
        }
    }
}
