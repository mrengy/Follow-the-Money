package org.ftm.ui;

import org.bushe.swing.event.EventBus;
import org.bushe.swing.event.EventTopicSubscriber;

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
final class NavigationComponent extends JPanel implements EventTopicSubscriber {

    private final JButton politicianB = new JButton("Candidate");
    private final JButton issueB = new JButton("Issue");
    private final JButton contributionB = new JButton("Contribution");
    private final JButton visuB = new JButton("Visualize");

    NavigationComponent() {
        final Dimension dim = new Dimension(300, 25);
        politicianB.setPreferredSize(dim);
        issueB.setPreferredSize(dim);
        contributionB.setPreferredSize(dim);

        // Layout
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));

        p.add(Box.createVerticalStrut(5));
        p.add(politicianB);
        p.add(Box.createVerticalStrut(10));
        p.add(issueB);
        p.add(Box.createVerticalStrut(10));
        p.add(contributionB);
        p.add(Box.createVerticalStrut(10));
        p.add(visuB);
        p.add(Box.createVerticalStrut(10));
        p.add(Box.createVerticalGlue());
        add(p);

        // Events
        politicianB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                EventBus.publish("politicianSearch", null);
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

    public void onEvent(String s, Object o) {
        throw new IllegalStateException("org.ftm.ui.NavigationComponent.onEvent Not implemented");
    }
}
