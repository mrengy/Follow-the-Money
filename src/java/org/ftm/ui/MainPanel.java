package org.ftm.ui;

import org.bushe.swing.event.EventBus;
import org.bushe.swing.event.EventTopicSubscriber;
import org.ftm.api.Politician;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * @author <font size=-1 color="#a3a3a3">Johnny Hujol</font>
 * @since Oct 30, 2010
 */
final class MainPanel extends JPanel {

    private final JTextField zipCode = new JTextField(25);
    private final JList politiciansFound = new JList();

    MainPanel() {
        setLayout(new BorderLayout());
        add(zipCode, BorderLayout.NORTH);
        add(politiciansFound, BorderLayout.CENTER);
        final JButton jb = new JButton("Redraw");
        add(jb, BorderLayout.SOUTH);

        politiciansFound.setCellRenderer(new MyListCellRenderer());

        // Some initial data
        politiciansFound.setListData(new String[]{
            "No politicians available"
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
        jb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                EventBus.publish("redraw", null);
            }
        });
        EventBus.subscribe("politiciansfound", new EventTopicSubscriber() {
            public void onEvent(String s, Object o) {
                if(o instanceof List) {
                    List<Politician> politicians = (List<Politician>) o;
                    politiciansFound.setListData(politicians.toArray(new Politician[politicians.size()]));
                }
            }
        });
    }

    private static class MyListCellRenderer extends DefaultListCellRenderer {

        public Component getListCellRendererComponent(JList jList, Object o, int i, boolean b, boolean b1) {
            final String text;
            if(o instanceof Politician) {
                Politician p = (Politician) o;
                text = p.getFirstName() + " " + p.getLastName();
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
