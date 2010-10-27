package org.ftm.ui;

import processing.core.PApplet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        final JButton button = new JButton("Visualize");
        add(button, BorderLayout.NORTH);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                embed.redraw();
            }
        });
        add(embed, BorderLayout.CENTER);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // important to call this whenever embedding a PApplet.
        // It ensures that the animation thread is started and
        // that other internal variables are properly set.
        embed.init();
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
