package org.team537.fms;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MatchPanel extends JPanel
{
    JLabel matchNumber;
    JLabel matchTime;
    private BufferedImage background;

public MatchPanel()
{
    matchNumber = new JLabel("MATCH:  0 ");
    matchTime = new JLabel("TIME:  0   SECS");
}

}
