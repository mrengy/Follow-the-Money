package org.ftm.ui;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Dimension;

/**
 * @author <font size=-1 color="#a3a3a3">Johnny Hujol</font>
 * @since Nov 7, 2010
 */
final class ComponentUtils {

    public static JPanel createLineBoxLaidOutPanel() {
        final JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.LINE_AXIS));
        return p;
    }

    public static JPanel createPageBoxLaidOutPanel() {
        final JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
        return p;
    }

    public static void setSizes(Component comp, Dimension dim) {
        comp.setMinimumSize(dim);
        comp.setPreferredSize(dim);
        comp.setMaximumSize(new Dimension(Short.MAX_VALUE, (int) comp.getPreferredSize().getHeight()));
    }

    public static JPanel createPanelWithFixedSize(final int preferredWidth) {
        final JPanel p = new JPanel() {
            @Override
            public Dimension getMinimumSize() {
                return getPreferredSize();
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(preferredWidth, super.getPreferredSize().height);
            }

            @Override
            public Dimension getMaximumSize() {
                return getPreferredSize();
            }
        };
        return p;
    }
}
