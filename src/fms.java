package fms;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.team537.fms.Model;
import org.team537.fms.ui.MatchPanel;
import org.team537.fms.ui.TabPanel;
import org.team537.SwingUtil;

public class fms extends JFrame 
{
    private JPanel contentPane;
    private BorderLayout Layout1 = new BorderLayout();
    private MatchPanel match;
    private BufferedImage background;
    private TabPanel tabs;
    private Model model;

public fms() 
{
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
        fmsInit();
    } catch (Exception ex) {
        System.out.println("fmsInit: " + ex);
        ex.printStackTrace();
    }
}

private void fmsInit() throws Exception 
{
    background = ImageIO.read(getClass().getResource("/images/Background.png"));
    JLabel label = new JLabel(new ImageIcon(background));

    // contentPane = (JPanel) this.getContentPane();
    // contentPane.setLayout(Layout1);
    // this.setContentPane(label);
    this.setLayout(Layout1);

    // this.setSize(new Dimension(400, 300));
    this.setTitle("Team 537 FMS");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    // this.add(bluePane, BorderLayout.WEST);
    // this.add(redPane, BorderLayout.EAST);
    // this.pack();

    model = new Model();
    match = new MatchPanel(model);
    model.setMatchPanel(match);

    tabs = new TabPanel(model);
    model.setTabPanel(tabs);

    this.add(match, BorderLayout.NORTH);
    this.add(tabs, BorderLayout.SOUTH);
}

public static void main(String[] args)
{
    fms f = new fms();
    f.setSize(1024, 668);
    SwingUtil.center(f);
    f.setVisible(true);

    if (0 < args.length && args[0].equals("demo")) {
        int state = 0;
        while (true) {
            f.match.setState(state);
            state++;
            try {
            Thread.sleep(250);
            } catch (Exception ex) {
            }
        }
    }
    if (0 < args.length && args[0].equals("test")) {
        System.out.println("test mode");
    }
}
}
