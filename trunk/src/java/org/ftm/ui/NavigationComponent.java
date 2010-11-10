package org.ftm.ui;

import org.bushe.swing.event.EventBus;
import org.bushe.swing.event.EventTopicSubscriber;
import org.ftm.api.Candidate;
import org.ftm.api.Contributor;
import org.ftm.api.Issue;
import org.ftm.util.Debug;

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
    private static final Dimension DIM = new Dimension(140, 25);

    private final EventTopicSubscriber eventSubscriber;

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

        if(Debug.isDebug()) {
            setBackground(Color.MAGENTA);
        }

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

        eventSubscriber = new EventTopicSubscriber() {
            public void onEvent(String s, Object o) {
                if("candidateselected".equals(s)) {
                    if(o instanceof Candidate) {
                        final Candidate candidate = (Candidate) o;
                        final String text = candidate.getFirstName() + " " + candidate.getLastName();
                        candidateB.setText(text);
                        candidateB.setToolTipText(text);
                    }
                }
                else if("issueselected".equals(s)) {
                    if(o instanceof Issue) {
                        final Issue issue = (Issue) o;
                        final String text = issue.getDescription();
                        issueB.setText(text);
                        issueB.setToolTipText(text);
                    }
                }
                else if("contributorselected".equals(s)) {
                    if(o instanceof Contributor) {
                        final Contributor contributor = (Contributor) o;
                        final String text = contributor.getIndustryCategory();
                        contributionB.setText(text);
                        contributionB.setToolTipText(text);
                    }
                }
            }
        };
        EventBus.subscribeStrongly("candidateselected", eventSubscriber);
        EventBus.subscribeStrongly("issueselected", eventSubscriber);
        EventBus.subscribeStrongly("contributorselected", eventSubscriber);
    }

    public void close() {
        EventBus.unsubscribe("candidateselected", eventSubscriber);
        EventBus.unsubscribe("issueselected", eventSubscriber);
        EventBus.unsubscribe("contributorselected", eventSubscriber);
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
