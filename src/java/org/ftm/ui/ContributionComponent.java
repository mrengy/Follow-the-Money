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
import javax.swing.SwingWorker;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Collections;
import java.util.Comparator;
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
                final String text;
                if(null == contributor) {
                    text = "No Industry Found";
                }
                else {
                    text = contributor.getIndustryCategory();
                }
                label.setText(text);
                label.setToolTipText(text);
                return label;
            }
        });

        EventBus.subscribeStrongly("contributorsfound", this);

        contributorsCombo.addItemListener(new ItemListener() {
            public void itemStateChanged(final ItemEvent itemEvent) {
                new SwingWorker() {
                    @Override
                    protected Object doInBackground() throws Exception {
                        return null;
                    }

                    @Override
                    protected void done() {
                        EventBus.publish("contributorselected", itemEvent.getItem());
                    }
                }.execute();

            }
        });
    }

    public void onEvent(final String s, final Object o) {
        new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                return null;
            }

            @Override
            protected void done() {
                if("contributorsfound".equalsIgnoreCase(s)) {
                    if(null != o && o instanceof List) {
                        final List<Contributor> contributors = (List<Contributor>) o;
                        Collections.sort(contributors, new Comparator<Contributor>() {
                            public int compare(Contributor contributor, Contributor contributor1) {
                                return contributor.getIndustryCategory().compareTo(contributor1.getIndustryCategory());
                            }
                        });
                        contributorsCombo.setModel(new DefaultComboBoxModel(contributors.toArray(new Contributor[contributors.size()])));
                    }
                }
            }
        }.execute();

    }
}
