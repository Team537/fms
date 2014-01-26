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

import org.team537.fms.AlliancePanel;
import org.team537.fms.MatchPanel;
import org.team537.SwingUtil;

public class fms extends JFrame 
{
    private JPanel contentPane;
    private BorderLayout Layout1 = new BorderLayout();
    private MatchPanel match;
    private BufferedImage background;

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
    
    // bluePane = new AlliancePanel(true);
    // redPane = new AlliancePanel(false);

    // this.add(bluePane, BorderLayout.WEST);
    // this.add(redPane, BorderLayout.EAST);
    // this.pack();

    match = new MatchPanel();
    this.add(match, BorderLayout.NORTH);
}

public static void main(String[] args)
{
    fms f = new fms();
    f.setSize(1024, 668);
    SwingUtil.center(f);
    f.setVisible(true);

    int state = 0;
    while (true) {
        f.match.setState(state);
        state++;
    }
}
}
