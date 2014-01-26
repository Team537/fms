package org.team537.fms;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.awt.Insets;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MatchPanel extends JPanel
{
    JLabel matchNumber;
    JLabel matchTime;
    private AlliancePanel blue, red;
    private BufferedImage background;

public MatchPanel() throws Exception
{
    super(new BorderLayout());

    setOpaque(false);
    GridBagConstraints bag = new GridBagConstraints();

    background = ImageIO.read(getClass().getResource("/images/Background.png"));
    JPanel top = new JPanel(new GridBagLayout());
    matchNumber = new JLabel("MATCH:  0 ");
    matchNumber.setFont(new Font("SansSerif", Font.PLAIN, 24));
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.insets = new Insets(0, 100, 0, 0);
    bag.gridx = 0;
    bag.gridy = 0;
    top.add(matchNumber, bag);

    matchTime = new JLabel("TIME:  0   SECS");
    matchTime.setFont(new Font("SansSerif", Font.PLAIN, 24));
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.insets = new Insets(0, 0, 0, 100);
    bag.gridx = 1;
    bag.gridy = 0;
    top.add(matchTime, bag);
    add(top, BorderLayout.NORTH);

    JPanel middle = new JPanel(new GridBagLayout());
    blue = new AlliancePanel(true);
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.insets = new Insets(0, 20, 0, 0);
    bag.gridx = 0;
    bag.gridy = 0;
    middle.add(blue, bag);
    
    red = new AlliancePanel(false);
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.insets = new Insets(0, 0, 0, 20);
    bag.gridx = 1;
    bag.gridy = 0;
    middle.add(red, bag);

    add(middle, BorderLayout.CENTER);
    setVisible(true);
}

@Override
protected void paintComponent(Graphics g) 
{
    super.paintComponent(g);
    g.drawImage(background, 0, 0, null);
}

public void setState(int st) 
{
    for (int i = 0; i < 3; i++) {
        blue.setDSLink(i,       1 == (st & 0x8));
        blue.setEStop(i,        1 == (st & 0x4));
        blue.setRobotLink(i,    1 == (st & 0x2));
        blue.setRobotEnabled(i, 1 == (st & 0x1));
        red.setDSLink(i,       1 == (st & 0x8));
        red.setEStop(i,        1 == (st & 0x4));
        red.setRobotLink(i,    1 == (st & 0x2));
        red.setRobotEnabled(i, 1 == (st & 0x1));
        st++;
    }
}

}
