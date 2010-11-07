package org.ftm.ui;

import org.bushe.swing.event.EventBus;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author <font size=-1 color="#a3a3a3">Johnny Hujol</font>
 * @since Oct 31, 2010
 */
final class NavigationComponent extends JPanel {

    private final JButton candidateB = new JButton("Candidate");
    private final JButton issueB = new JButton("Issue");
    private final JButton contributionB = new JButton("Contribution");
    private final JButton visuB = new JButton("Visualize");
    private static final Dimension DIM = new Dimension(120, 25);

    NavigationComponent() {
        ComponentUtils.setSizes(candidateB, DIM);
        candidateB.setAlignmentX(CENTER_ALIGNMENT);

        ComponentUtils.setSizes(issueB, DIM);
        issueB.setAlignmentX(CENTER_ALIGNMENT);

        ComponentUtils.setSizes(contributionB, DIM);
        contributionB.setAlignmentX(CENTER_ALIGNMENT);

        ComponentUtils.setSizes(visuB, DIM);
        visuB.setAlignmentX(CENTER_ALIGNMENT);

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
        add(candidateB);
        add(Box.createVerticalStrut(20));
        add(issueB);
        add(Box.createVerticalStrut(20));
        add(contributionB);
        add(Box.createVerticalStrut(20));
        add(visuB);
        add(Box.createVerticalGlue());

        setBackground(Color.MAGENTA);

        // Events
        candidateB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                EventBus.publish("candidateSearch", null);
            }
        });
        issueB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                EventBus.publish("issueSearch", null);
            }
        });
        contributionB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                EventBus.publish("contributionSearch", null);
            }
        });
        visuB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                EventBus.publish("visualization", null);
            }
        });
    }

    // Overriding the following methods allows to keep a fixed width whenever the component is re-sized.

    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension((int) DIM.getWidth(), super.getPreferredSize().height);
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }
}
