package org.team537.fms;

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

public class AlliancePanel extends JPanel
{
    public class AllianceTeam {
        JFormattedTextField teamNum;
        JCheckBox bypass;
        JCheckBox dq;
        JButton ds;
        JButton es;
        JButton rlink;
        JButton renabled;
    };

    public class TeamStatus extends JButton {
        public TeamStatus() {
        }

        @Override
        protected void paintComponent(Graphics g) {
        }

        @Override
        public void setContentAreaFilled(boolean b) {
        }
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
    // setFocusTraversalPolicy(new APFocusPolicy());

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
        team[slot] = new AllianceTeam();
        // team slot
        JLabel lslot = new JLabel(Integer.toString(1 + i));
        bag.fill = GridBagConstraints.HORIZONTAL;
        bag.weightx = 0.15;
        bag.insets = new Insets(0, 10, 0, 0);
        bag.gridx = 0;
        bag.gridy = 2 + slot;
        bag.gridwidth = 1;
        this.add(lslot, bag);

        // team number
        team[slot].teamNum = new JFormattedTextField(new MaskFormatter("####"));
        team[slot].teamNum.setEditable(true);
        team[slot].teamNum.setText("0000");
        bag.fill = GridBagConstraints.HORIZONTAL;
        bag.insets = new Insets(0, 10, 0, 10);
        bag.weightx = 0.15;
        bag.gridx = 1;
        bag.gridy = 2 + slot;
        bag.gridwidth = 1;
        this.add(team[slot].teamNum, bag);

        // button bypass
        team[slot].bypass = new JCheckBox("Bypass");
        bag.weightx = 0.15;
        bag.insets = new Insets(0, 0, 0, 0);
        bag.gridx = 2;
        bag.gridy = 2 + slot;
        bag.gridwidth = 1;
        team[slot].bypass.setContentAreaFilled(false);
        this.add(team[slot].bypass, bag);

        // button dq
        team[slot].dq = new JCheckBox("DQ");
        bag.weightx = 0.15;
        bag.insets = new Insets(0, 0, 0, 10);
        bag.gridx = 3;
        bag.gridy = 2 + slot;
        bag.gridwidth = 1;
        team[slot].dq.setContentAreaFilled(false);
        this.add(team[slot].dq, bag);
        
        // Team DS link status
        team[slot].ds = new JButton(redIcon);
        team[slot].ds.setPressedIcon(greenIcon);
        team[slot].ds.setDisabledSelectedIcon(redIcon);
        team[slot].ds.setSelected(false);
        team[slot].ds.setMaximumSize(new Dimension(24,24));
        team[slot].ds.setMaximumSize(new Dimension(24,24));
        team[slot].ds.setToolTipText("DS Link");
        bag.weightx = 0.15;
        bag.insets = new Insets(0, 0, 0, 0);
        bag.gridx = 4;
        bag.gridy = 2 + slot;
        bag.gridwidth = 1;
        this.add(team[slot].ds, bag);

        // Team E-stop
        team[slot].es = new JButton(greenIcon);
        team[slot].es.setPressedIcon(greenIcon);
        team[slot].es.setDisabledSelectedIcon(redIcon);
        team[slot].es.setSelected(true);
        team[slot].es.setMaximumSize(new Dimension(24,24));
        team[slot].es.setToolTipText("E-Stop");
        bag.weightx = 0.15;
        bag.gridx = 5;
        bag.gridy = 2 + slot;
        bag.gridwidth = 1;
        this.add(team[slot].es, bag);

        // Robot Link
        team[slot].rlink = new JButton(redIcon);
        team[slot].rlink.setPressedIcon(greenIcon);
        team[slot].rlink.setDisabledSelectedIcon(redIcon);
        team[slot].rlink.setSelected(false);
        team[slot].rlink.setToolTipText("Robot Link");
        bag.weightx = 0.15;
        bag.gridx = 6;
        bag.gridy = 2 + slot;
        bag.gridwidth = 1;
        this.add(team[slot].rlink, bag);

        // Robot Enabled
        team[slot].renabled = new JButton(redIcon);
        team[slot].renabled.setPressedIcon(greenIcon);
        team[slot].renabled.setDisabledSelectedIcon(redIcon);
        team[slot].renabled.setSelected(false);
        team[slot].renabled.setToolTipText("Enabled");
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
    return Integer.valueOf((String) team[teamSlot].teamNum.getValue());
}

boolean getBypass(int teamSlot)
{
    return team[teamSlot].bypass.isSelected();
}

boolean getDQ(int teamSlot)
{
    return team[teamSlot].dq.isSelected();
}

void setDSLink(int teamSlot, boolean status)
{
    team[teamSlot].ds.setSelected(status);
}

void setEStop(int teamSlot, boolean status)
{
    team[teamSlot].es.setSelected(status);
}

void setRobotLink(int teamSlot, boolean status)
{
    team[teamSlot].rlink.setSelected(status);
}

void setRobotEnabled(int teamSlot, boolean status)
{
    team[teamSlot].renabled.setSelected(status);
}

}
