package org.team537.fms;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AlliancePanel extends JPanel
{
    public class AllianceTeam {
        JLabel teamNum;
        JCheckBox bypass;
        JCheckBox dq;
        JButton ds;
        JButton es;
        JButton rlink;
        JButton renabled;
    };

    JLabel alliance_ready;
    JLabel fms_label;
    JLabel team_label;
    JLabel robot_label;
    private BufferedImage background;
    private ImageIcon redIcon;
    private ImageIcon greenIcon;
    private AllianceTeam[] team = new AllianceTeam[3];

public AlliancePanel(boolean isBlueAlliance) throws Exception
{
    super(new GridBagLayout());

    try {
        background = isBlueAlliance 
            ? ImageIO.read(getClass().getResource("/images/blue-background.png"))
            : ImageIO.read(getClass().getResource("/images/red-background.png"));
        BufferedImage red = ImageIO.read(getClass().getResource("/images/red-icon.png"));
        BufferedImage green = ImageIO.read(getClass().getResource("/images/green-icon.png"));
        redIcon = new ImageIcon(red, "red");
        greenIcon = new ImageIcon(green, "green");
    } catch (Exception ex) {
        System.err.println("AlliancePanel: " + ex);
        throw(ex);
    }

    GridBagConstraints bag = new GridBagConstraints();

    String ready = isBlueAlliance ? "BLUE ALLIANCE READY" : "RED ALLIANCE READY";
    alliance_ready = new JLabel(ready);
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.weightx = 0.0;
    bag.gridx = 3;
    bag.gridy = 0;
    bag.gridwidth = 3;
    this.add(alliance_ready, bag);

    fms_label = new JLabel("FMS");
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.weightx = 0.70;
    bag.gridx = 0;
    bag.gridy = 1;
    bag.gridwidth = 1;
    this.add(fms_label, bag);

    team_label = new JLabel("Team");
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.weightx = 0.15;
    bag.gridx = 4;
    bag.gridy = 1;
    bag.gridwidth = 1;
    this.add(team_label, bag);

    robot_label = new JLabel("Robot");
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.weightx = 0.15;
    bag.gridx = 6;
    bag.gridy = 1;
    bag.gridwidth = 1;
    this.add(robot_label, bag);

    for (int i = 0; i < 3; i++) {
        int slot = isBlueAlliance ? i : 2 - i;
        team[slot] = new AllianceTeam();
        // team slot
        JLabel lslot = new JLabel(Integer.toString(1 + i));
        bag.fill = GridBagConstraints.HORIZONTAL;
        bag.weightx = 0.15;
        bag.gridx = 0;
        bag.gridy = 2 + slot;
        bag.gridwidth = 1;
        this.add(lslot, bag);

        // team number
        team[slot].teamNum = new JLabel("0000");
        bag.fill = GridBagConstraints.HORIZONTAL;
        bag.weightx = 0.15;
        bag.gridx = 1;
        bag.gridy = 2 + slot;
        bag.gridwidth = 1;
        this.add(team[slot].teamNum, bag);

        // button bypass
        team[slot].bypass = new JCheckBox("Bypass");
        bag.weightx = 0.15;
        bag.gridx = 2;
        bag.gridy = 2 + slot;
        bag.gridwidth = 1;
        team[slot].bypass.setContentAreaFilled(false);
        this.add(team[slot].bypass, bag);

        // button dq
        team[slot].dq = new JCheckBox("DQ");
        bag.weightx = 0.15;
        bag.gridx = 3;
        bag.gridy = 2 + slot;
        bag.gridwidth = 1;
        team[slot].dq.setContentAreaFilled(false);
        this.add(team[slot].dq, bag);
        
        // Team DS link status
        team[slot].ds = new JButton(redIcon);
        team[slot].ds.setMaximumSize(new Dimension(24,24));
        bag.weightx = 0.15;
        bag.gridx = 4;
        bag.gridy = 2 + slot;
        bag.gridwidth = 1;
        this.add(team[slot].ds, bag);

        // Team E-stop
        team[slot].es = new JButton(greenIcon);
        team[slot].es.setMaximumSize(new Dimension(24,24));
        bag.weightx = 0.15;
        bag.gridx = 5;
        bag.gridy = 2 + slot;
        bag.gridwidth = 1;
        this.add(team[slot].es, bag);

        // Robot Link
        team[slot].rlink = new JButton(redIcon);
        bag.weightx = 0.15;
        bag.gridx = 6;
        bag.gridy = 2 + slot;
        bag.gridwidth = 1;
        this.add(team[slot].rlink, bag);

        // Robot Enabled
        team[slot].renabled = new JButton(redIcon);
        bag.weightx = 0.15;
        bag.gridx = 7;
        bag.gridy = 2 + slot;
        bag.gridwidth = 1;
        this.add(team[slot].renabled, bag);
    }
    setMinimumSize(new Dimension(480, 160));
    setPreferredSize(new Dimension(480, 160));
// setOpaque(true);
    setVisible(true);
}

@Override
protected void paintComponent(Graphics g) 
{
    super.paintComponent(g);
    g.drawImage(background, 0, 0, null);
}

}
