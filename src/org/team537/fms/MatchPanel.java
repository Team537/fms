package org.team537.fms;

import java.awt.Dimension;
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

    GridBagConstraints bag = new GridBagConstraints();

    background = ImageIO.read(getClass().getResource("/images/Background.png"));
    JPanel top = new JPanel(new GridBagLayout());
    matchNumber = new JLabel("MATCH:  0 ");
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.insets = new Insets(0, 100, 0, 0);
    bag.gridx = 0;
    bag.gridy = 0;
    top.add(matchNumber, bag);

    matchTime = new JLabel("TIME:  0   SECS");
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.insets = new Insets(0, 0, 0, 100);
    bag.gridx = 1;
    bag.gridy = 0;
    top.add(matchTime, bag);
    add(top, BorderLayout.NORTH);

    JPanel middle = new JPanel(new BorderLayout());
    blue = new AlliancePanel(true);
    red = new AlliancePanel(false);
    middle.add(blue, BorderLayout.WEST);
    middle.add(red, BorderLayout.EAST);
    add(middle, BorderLayout.CENTER);
    setVisible(true);
}

@Override
protected void paintComponent(Graphics g) 
{
    super.paintComponent(g);
    g.drawImage(background, 0, 0, null);
}

}
