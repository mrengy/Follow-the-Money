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
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author <font size=-1 color="#a3a3a3">Johnny Hujol</font>
 * @since Oct 31, 2010
 */
final class NavigationComponent extends JPanel {

    private final JButton candidateB = new JButton("Candidate");
    private final JButton issueB = new JButton("Issue");
    private final JButton contributionB = new JButton("Contribution");
    private final JButton visuB = new JButton("Visualize");
    private static final Dimension DIM = new Dimension(115, 25);

    private final EventTopicSubscriber eventSubscriber;

    private Image bgImage = null;
    private String message = "";

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
        setBorder(BorderFactory.createEmptyBorder(40, 20, 0, 20));
        add(candidateB);
        add(Box.createVerticalStrut(33));
        add(issueB);
        add(Box.createVerticalStrut(33));
        add(contributionB);
        add(Box.createVerticalStrut(80));
        add(visuB);
        add(Box.createVerticalGlue());

        if(Debug.isDebug()) {
            setBackground(Color.MAGENTA);
        }

        try {
            //            getFileImage(new FileInputStream("/Users/hujol/Projects/followthemoney/sf/resources/images/bkg.png"));
            // The PNG logo throws a CRC corruption
            // seems to be a bug: http://bugs.sun.com/view_bug.do?bug_id=4530546
            getFileImage(getClass().getResourceAsStream("/resources/images/ftmLogo.jpg"));
            //            getFileImage( getClass().getResourceAsStream("/resources/images/bkg.png"));
        }
        catch(Exception ex) {
            message = "File load failed: " + ex.getMessage();
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

    @Override
    public void paintComponent(Graphics g) {
        if(bgImage != null) {
            g.drawImage(bgImage, 18, 20, this);
        }
        else {
            g.drawString(message, 40, 40);
        }
    }

    private void getFileImage(final InputStream in) throws InterruptedException, IOException {
        byte[] b = new byte[in.available()];
        in.read(b);
        in.close();
        bgImage = Toolkit.getDefaultToolkit().createImage(b);
        MediaTracker mt = new MediaTracker(this);
        mt.addImage(bgImage, 0);
        mt.waitForAll();
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
