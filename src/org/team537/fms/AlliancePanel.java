package org.team537.fms;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AlliancePanel extends JPanel
{
    JLabel alliance_ready;
    JLabel fms_label;
    JLabel team_label;
    JLabel robot_label;
    private BufferedImage background;

public AlliancePanel(boolean isBlueAlliance) throws Exception
{
    super(new GridBagLayout());

    try {
    background = isBlueAlliance 
        ? ImageIO.read(getClass().getResource("/images/blue-background.png"))
        : ImageIO.read(getClass().getResource("/images/red-background.png"));
    } catch (Exception ex) {
        System.err.println("AlliancePanel: " + ex);
        throw(ex);
    }

    GridBagConstraints bag = new GridBagConstraints();

    String ready = isBlueAlliance ? "BLUE ALLIANCE READY" : "RED ALLIANCE READY";
    alliance_ready = new JLabel(ready);
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.weightx = 0.0;
    bag.gridx = 0;
    bag.gridy = 0;
    bag.gridwidth = 3;
    this.add(alliance_ready, bag);

    fms_label = new JLabel("FMS");
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.weightx = 0.70;
    bag.gridx = 0;
    bag.gridy = 1;
    this.add(fms_label, bag);

    team_label = new JLabel("Team");
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.weightx = 0.15;
    bag.gridx = 1;
    bag.gridy = 1;
    this.add(team_label, bag);

    robot_label = new JLabel("Robot");
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.weightx = 0.15;
    bag.gridx = 2;
    bag.gridy = 1;
    this.add(robot_label, bag);

    for (int i = 1; i < 4; i++) {
        // team slot
        JLabel team = new JLabel(Integer.toString(i));
        bag.fill = GridBagConstraints.HORIZONTAL;
        bag.weightx = 0;
        bag.gridx = 0;
        bag.gridy = 1 + i;
        this.add(team, bag);

        // team number
        JLabel teamNum = new JLabel("0000");
        bag.fill = GridBagConstraints.HORIZONTAL;
        bag.weightx = 0;
        bag.gridx = 1;
        bag.gridy = 1 + i;
        this.add(team, bag);

        // button bypass

        // button dq
        
        // Team DS link status

        // Team E-stop

        // Robot Link

        // Robot Enabled
    }
    setVisible(true);
    setMinimumSize(new Dimension(480, 160));
    setPreferredSize(new Dimension(480, 160));
    setOpaque(true);
}

@Override
protected void paintComponent(Graphics g) 
{
    super.paintComponent(g);
    g.drawImage(background, 0, 0, null);
}

}
