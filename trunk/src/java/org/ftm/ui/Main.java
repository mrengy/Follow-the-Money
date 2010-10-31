package org.ftm.ui;

import org.bushe.swing.event.EventBus;
import org.bushe.swing.event.EventTopicSubscriber;
import org.ftm.api.DataAccessObject;
import org.ftm.api.Politician;
import org.ftm.api.ZipCode;
import org.ftm.impl.SimpleDataAccessObject;
import processing.core.PApplet;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the starting class for the application.
 *
 * @author <font size=-1 color="#a3a3a3">Johnny Hujol</font>
 * @since Oct 26, 2010
 */
public final class Main extends JFrame {

    public Main() {
        super("VisualizationPanel PApplet");

        setLayout(new BorderLayout());
        final PApplet embed = new VisualizationPanel();
        MainPanel mp = new MainPanel();
        add(mp, BorderLayout.NORTH);

        add(embed, BorderLayout.CENTER);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // important to call this whenever embedding a PApplet.
        // It ensures that the animation thread is started and
        // that other internal variables are properly set.
        embed.init();

        //
        final DataAccessObject dao = new SimpleDataAccessObject();

        // Event management
        EventBus.subscribe("redraw", new EventTopicSubscriber() {
            public void onEvent(String s, Object o) {
                embed.redraw();
            }
        });
        //        final List<Object> subscribers = EventBus.getSubscribers("redraw");
        EventBus.subscribe("zipcode", new EventTopicSubscriber() {
            public void onEvent(String s, Object o) {
                if(o instanceof String) {
                    String zipCode = (String) o;
                    final List<Politician> politicians = new ArrayList<Politician>();
                    Politician p = null;
                    try {
                        politicians.addAll(dao.getPoliticians(new ZipCode(zipCode)));
                    }
                    catch(Exception e) {
                        e.printStackTrace();
                        EventBus.publish("politiciansfound", null);
                        return;
                    }
                    EventBus.publish("politiciansfound", politicians);
                }
            }
        });
    }

    public static void main(String[] args) {
        final Main f = new Main();
        f.setResizable(true);
        f.setSize(800, 600);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        f.setLocation((int) (screen.getWidth() - f.getWidth()) / 2, (int) (screen.getHeight() - f.getHeight()) / 2);
        f.setVisible(true);
    }
}
