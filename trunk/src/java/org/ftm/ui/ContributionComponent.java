package org.ftm.ui;

import org.bushe.swing.event.EventBus;
import org.bushe.swing.event.EventTopicSubscriber;
import org.ftm.api.Contributor;

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
final class ContributionComponent extends JPanel implements EventTopicSubscriber {

    private JComboBox contributorsCombo;

    ContributionComponent() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        contributorsCombo = new JComboBox();
        contributorsCombo.setPreferredSize(new Dimension(300, 25));
        p.add(new JLabel("Please select a contributor:"));
        p.add(Box.createVerticalStrut(10));
        p.add(contributorsCombo);
        p.add(Box.createVerticalGlue());

        add(p);
        contributorsCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList jList, Object o, int i, boolean b, boolean b1) {
                final JLabel label = (JLabel) super.getListCellRendererComponent(jList, o, i, b, b1);
                final Contributor contributor = (Contributor) o;
                label.setText(contributor.getIndustryCategory());
                label.setToolTipText(contributor.getIndustryCategory());
                return label;
            }
        });

        EventBus.subscribeStrongly("contributorsfound", this);

        contributorsCombo.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent itemEvent) {
                EventBus.publish("contributorselected", itemEvent.getItem());
            }
        });
    }

    public void onEvent(String s, Object o) {
        if("contributorsfound".equalsIgnoreCase(s)) {
            if(null != o && o instanceof List) {
                final List<Contributor> contributors = (List<Contributor>) o;
                contributorsCombo.setModel(new DefaultComboBoxModel(contributors.toArray(new Contributor[contributors.size()])));
            }
        }
    }
}
