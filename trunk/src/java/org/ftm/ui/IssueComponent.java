package org.ftm.ui;

import org.bushe.swing.event.EventBus;
import org.bushe.swing.event.EventTopicSubscriber;
import org.ftm.api.Issue;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

/**
 * @author <font size=-1 color="#a3a3a3">Johnny Hujol</font>
 * @since Oct 30, 2010
 */
final class IssueComponent extends JPanel implements EventTopicSubscriber {
    private JComboBox issuesCombo;

    IssueComponent() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        issuesCombo = new JComboBox();
        issuesCombo.setPreferredSize(new Dimension(300, 25));
        p.add(new JLabel("Please select an issue:"));
        p.add(Box.createVerticalStrut(10));
        p.add(issuesCombo);
        p.add(Box.createVerticalGlue());

        add(p);
        issuesCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList jList, Object o, int i, boolean b, boolean b1) {
                final JLabel label = (JLabel) super.getListCellRendererComponent(jList, o, i, b, b1);
                final Issue issue = (Issue) o;
                label.setText(issue.getDescription());
                label.setToolTipText(issue.getDescription());
                return label;
            }
        });

        EventBus.subscribeStrongly("issuesfound", this);

        issuesCombo.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                EventBus.publish("issueselected", itemEvent.getItem());
            }
        });
    }

    public void onEvent(String s, Object o) {
        if("issuesfound".equalsIgnoreCase(s)) {
            if(null != o && o instanceof List) {
                final List<Issue> issues = (List<Issue>) o;
                issuesCombo.setModel(new DefaultComboBoxModel(issues.toArray(new Issue[issues.size()])));
            }
        }
    }
}
