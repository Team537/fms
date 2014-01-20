package org.team537;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.Window;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class SwingUtil {
    public static Frame frameOf(Component c) {
        while (c != null && !(c instanceof Frame)) {
            c = c.getParent();
        }
        return (Frame) c;
    }

    public static void center(Window frame) {
        center(frame, 0, 0, 0, 0);
    }
    public static void center(Window frame, int left, int right, int top, int bottom) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        if (frameSize.height > (screenSize.height - top - bottom)) {
            frameSize.height = screenSize.height - top - bottom;
        }
        if (frameSize.width > screenSize.width - left - right) {
            frameSize.width = screenSize.width - left - right;
        }
        if (!frameSize.equals(frame.getSize())) {
            frame.setSize(frameSize);
        }
        frame.setLocation( left + (screenSize.width - frameSize.width) / 2, top + (screenSize.height - frameSize.height) / 2);
    }
}
