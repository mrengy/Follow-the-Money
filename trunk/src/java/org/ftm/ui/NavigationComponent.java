package org.ftm.ui;

import org.bushe.swing.event.EventBus;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
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

    NavigationComponent() {
        final Dimension dim = new Dimension(150, 25);
        candidateB.setPreferredSize(dim);
        issueB.setPreferredSize(dim);
        contributionB.setPreferredSize(dim);

        // Layout
        //        JPanel p = new JPanel();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        add(Box.createVerticalStrut(20));
        add(candidateB);
        add(Box.createVerticalStrut(20));
        add(issueB);
        add(Box.createVerticalStrut(20));
        add(contributionB);
        add(Box.createVerticalStrut(20));
        add(visuB);
        add(Box.createVerticalStrut(15));
        add(Box.createVerticalGlue());

        //        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        //        add(Box.createHorizontalStrut(20));
        //        add(p);
        //        add(Box.createHorizontalGlue());

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
}
