package org.team537.fms.ui;

import java.awt.Dimension;
import java.awt.FocusTraversalPolicy;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.awt.Insets;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

import org.team537.fms.AllianceTeam;
import org.team537.fms.Model;

public class AlliancePanel extends JPanel
{
    JLabel alliance_ready;
    JLabel fms_label;
    JLabel team_label;
    JLabel robot_label;
    private boolean blue;
    private Model model;
    private BufferedImage background;
    private ImageIcon redIcon;
    private ImageIcon greenIcon;

public AlliancePanel(Model model, boolean isBlueAlliance) throws Exception
{
    super(new GridBagLayout());

    this.model = model;
    this.blue = isBlueAlliance;
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
        throw(new Exception("AlliancePanel ", ex));
    }

    GridBagConstraints bag = new GridBagConstraints();
    // setFocusTraversalPolicy(new APFocusPolicy());

    // String ready = isBlueAlliance ? "BLUE ALLIANCE READY" : "RED ALLIANCE READY";
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.weightx = 0.0;
    bag.gridx = 0;
    bag.gridy = 0;
    bag.gridwidth = 8;
    this.add(model.getAlliance(isBlueAlliance).alliance_ready, bag);

    fms_label = new JLabel("FMS");
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.insets = new Insets(0, 40, 0, 0);
    bag.weightx = 0.70;
    bag.gridx = 2;
    bag.gridy = 1;
    bag.gridwidth = 1;
    this.add(fms_label, bag);

    team_label = new JLabel("Team");
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.weightx = 0.15;
    bag.insets = new Insets(0, 30, 0, 0);
    bag.gridx = 4;
    bag.gridy = 1;
    bag.gridwidth = 1;
    this.add(team_label, bag);

    robot_label = new JLabel("Robot");
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.weightx = 0.15;
    bag.insets = new Insets(0, 30, 0, 0);
    bag.gridx = 6;
    bag.gridy = 1;
    bag.gridwidth = 1;
    this.add(robot_label, bag);

    for (int i = 0; i < 3; i++) {
        int slot = isBlueAlliance ? i : 2 - i;
        AllianceTeam team = model.getTeam(isBlueAlliance, slot);
        // team slot
        JLabel lslot = new JLabel(Integer.toString(1 + slot));
        bag.fill = GridBagConstraints.HORIZONTAL;
        bag.weightx = 0.15;
        bag.insets = new Insets(0, 10, 0, 0);
        bag.gridx = 0;
        bag.gridy = 2 + i;
        bag.gridwidth = 1;
        this.add(lslot, bag);

        // team number
        bag.insets = new Insets(0, 10, 0, 10);
        bag.fill = GridBagConstraints.HORIZONTAL;
        bag.weightx = 0.15;
        bag.gridx = 1;
        bag.gridy = 2 + i;
        bag.gridwidth = 1;
        this.add(team.teamNum, bag);

        // button bypass
        bag.weightx = 0.15;
        bag.insets = new Insets(0, 0, 0, 0);
        bag.gridx = 2;
        bag.gridy = 2 + i;
        bag.gridwidth = 1;
        team.bypass.setContentAreaFilled(false);
        this.add(team.bypass, bag);

        // button dq
        bag.weightx = 0.15;
        bag.insets = new Insets(0, 0, 0, 10);
        bag.gridx = 3;
        bag.gridy = 2 + i;
        bag.gridwidth = 1;
        team.dq.setContentAreaFilled(false);
        this.add(team.dq, bag);
        
        // Team DS link status
        bag.weightx = 0.15;
        bag.insets = new Insets(0, 0, 0, 0);
        bag.gridx = 4;
        bag.gridy = 2 + i;
        bag.gridwidth = 1;
        this.add(team.sum_dslink, bag);

        // Team E-stop
        bag.weightx = 0.15;
        bag.gridx = 5;
        bag.gridy = 2 + i;
        bag.gridwidth = 1;
        this.add(team.sum_es, bag);

        // Robot Link
        bag.weightx = 0.15;
        bag.gridx = 6;
        bag.gridy = 2 + i;
        bag.gridwidth = 1;
        this.add(team.sum_rlink, bag);

        // Robot Enabled
        bag.weightx = 0.15;
        bag.gridx = 7;
        bag.gridy = 2 + i;
        bag.gridwidth = 1;
        this.add(team.sum_renabled, bag);
    }
    setMinimumSize(new Dimension(480, 160));
    setPreferredSize(new Dimension(480, 160));
    setOpaque(false);
    setVisible(true);
}

public boolean isBlue()
{
    return blue;
}

@Override
protected void paintComponent(Graphics g) 
{
    super.paintComponent(g);
    g.drawImage(background, 0, 0, null);
}

// public class APFocusPolicy extends FocusTraversalPolicy 
// {
// public Component getComponentAfter(Container focusCycleRoot, Component aComponent) {
//    if(aComponent.equals(tf1)) return tf2;
//    else if(aComponent.equals(tf2)) return tf3;
//    else if(aComponent.equals(tf3)) return panel.getFocusTraversalPolicy().getDefaultComponent(panel);
//    else if(panel.isAncestorOf(aComponent)){
//        if(aComponent.equals(panel.getFocusTraversalPolicy().getLastComponent(panel))) return tf4;
//        else return panel.getFocusTraversalPolicy().getComponentAfter(panel, aComponent);
//    }
//    else if(aComponent.equals(tf4)) return tf5;
//    else if(aComponent.equals(tf5)) return tf6;
//    else return tf1;
// }
//       
// public Component getComponentBefore(Container focusCycleRoot, Component aComponent) {
//    if(aComponent.equals(tf6)) return tf5;
//    else if(aComponent.equals(tf5)) return tf4;
//    else if(aComponent.equals(tf4)) return panel.getFocusTraversalPolicy().getLastComponent(panel);
//    else if(panel.isAncestorOf(aComponent)){
//        if(aComponent.equals(panel.getFocusTraversalPolicy().getFirstComponent(panel))) return tf3;
//        else return panel.getFocusTraversalPolicy().getComponentBefore(panel, aComponent);
//    }
//    else if(aComponent.equals(tf3)) return tf2;
//    else if(aComponent.equals(tf2)) return tf1;
//    else return tf6;
// }
//       
// public Component getDefaultComponent(Container focusCycleRoot) {
//    return tf1;
// }
//       
// public Component getFirstComponent(Container focusCycleRoot) {
//    return tf1;
// }
//       
// public Component getLastComponent(Container focusCycleRoot) {
//    return tf6;
// }
// }

int getTeam(int teamSlot)
{
    return Integer.valueOf((String) model.getTeam(isBlue(), teamSlot).teamNum.getValue());
}

boolean getBypass(int teamSlot)
{
    return model.getTeam(isBlue(), teamSlot).bypass.isSelected();
}

boolean getDQ(int teamSlot)
{
    return model.getTeam(isBlue(), teamSlot).dq.isSelected();
}

void setDSLink(int teamSlot, boolean status)
{
    // team[teamSlot].ds.setSelected(status);
    model.getTeam(isBlue(), teamSlot).sum_dslink.setIcon(status ? greenIcon : redIcon);
}

void setEStop(int teamSlot, boolean status)
{
    // team[teamSlot].es.setSelected(status);
    model.getTeam(isBlue(), teamSlot).sum_es.setIcon(status ? greenIcon : redIcon);
}

void setRobotLink(int teamSlot, boolean status)
{
    // team[teamSlot].rlink.setSelected(status);
    model.getTeam(isBlue(), teamSlot).sum_rlink.setIcon(status ? greenIcon : redIcon);
}

void setRobotEnabled(int teamSlot, boolean status)
{
    // team[teamSlot].renabled.setSelected(status);
    model.getTeam(isBlue(), teamSlot).sum_renabled.setIcon(status ? greenIcon : redIcon);
}

}
