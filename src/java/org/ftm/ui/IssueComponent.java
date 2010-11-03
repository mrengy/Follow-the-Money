package org.ftm.ui;

import org.bushe.swing.event.EventTopicSubscriber;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author <font size=-1 color="#a3a3a3">Johnny Hujol</font>
 * @since Oct 30, 2010
 */
final class IssueComponent extends JPanel implements EventTopicSubscriber {

    IssueComponent() {

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
        p.add(new JLabel("Issues"));
        p.add(Box.createVerticalGlue());

        add(p);
    }

    public void onEvent(String s, Object o) {
        throw new IllegalStateException("org.ftm.ui.CandidateComponent.onEvent Not implemented");
    }
}
