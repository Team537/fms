package org.team537.fms.ui;

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

import org.team537.fms.AllianceTeam;
import org.team537.fms.Model;

public class MatchPanel extends JPanel
{
    JLabel matchNumber;
    JLabel matchTime;
    private AlliancePanel blue, red;
    private BufferedImage background;
    Model model;

public MatchPanel(Model model) throws Exception
{
    super(new BorderLayout());
    this.model = model;

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
    blue = new AlliancePanel(model, true);
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.insets = new Insets(0, 20, 0, 0);
    bag.gridx = 0;
    bag.gridy = 0;
    middle.add(blue, bag);
    
    red = new AlliancePanel(model, false);
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
        AllianceTeam b = model.getTeam(true, i);
        AllianceTeam r = model.getTeam(false, i);

        b.setRobotAuto( 128 == (st & 0x80));
        b.setPCstate(    64 == (st & 0x40));
        b.setCompetition(32 == (st & 0x20));
        b.setDSlink(     16 == (st & 0x10));
        b.setSCCestop(    8 == (st & 0x8));
        b.setDSestop(     4 == (st & 0x4));
        b.setRobotLink(   2 == (st & 0x2));
        b.setRobotEnabled(1 == (st & 0x1), false);

        b.setRobotAuto( 128 == (st & 0x80));
        r.setPCstate(    64 == (st & 0x40));
        r.setCompetition(32 == (st & 0x20));
        r.setDSlink(     16 == (st & 0x10));
        r.setSCCestop(    8 == (st & 0x8));
        r.setDSestop(     4 == (st & 0x4));
        r.setRobotLink(   2 == (st & 0x2));
        r.setRobotEnabled(1 == (st & 0x1), false);
        st++;
    }
}

public void setTime(int currTime)
{
    matchTime.setText("TIME:  " + currTime + " SECS");
}

public void setMatch(int mMatch)
{
    matchNumber.setText("MATCH:  " + mMatch + " ");
}

}
