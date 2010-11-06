package org.ftm.ui;

import org.bushe.swing.event.EventBus;
import org.bushe.swing.event.EventTopicSubscriber;
import org.ftm.api.Candidate;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * @author <font size=-1 color="#a3a3a3">Johnny Hujol</font>
 * @since Oct 30, 2010
 */
final class CandidateComponent extends JPanel implements EventTopicSubscriber {

    private final JTextField zipCode = new JTextField(25);
    private final JList canidatesFound = new JList();

    CandidateComponent() {
        zipCode.setPreferredSize(new Dimension(200, 25));
        canidatesFound.setPreferredSize(new Dimension(200, 150));

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
        p.add(zipCode);
        p.add(new JScrollPane(canidatesFound));
        final JButton jb = new JButton("Redraw");
        p.add(jb);
        p.add(Box.createVerticalGlue());

        add(p);
        canidatesFound.setCellRenderer(new MyListCellRenderer());

        // Some initial data
        canidatesFound.setListData(new String[]{
            "No candidates available"
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
        EventBus.subscribeStrongly("candidatesfound", this);
    }

    public void onEvent(String s, Object o) {
        if(o instanceof List) {
            List<Candidate> candidates = (List<Candidate>) o;
            canidatesFound.setListData(candidates.toArray(new Candidate[candidates.size()]));
        }
    }

    public void close() {
        EventBus.unsubscribe("candidatesfound", this);
    }

    private static class MyListCellRenderer extends DefaultListCellRenderer {

        public Component getListCellRendererComponent(JList jList, Object o, int i, boolean b, boolean b1) {
            final String text;
            if(o instanceof Candidate) {
                Candidate p = (Candidate) o;
                text = p.getFirstName() + ' ' + p.getLastName();
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
