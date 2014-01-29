package org.team537.fms;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.awt.Insets;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class tabStatus extends JPanel 
{
    private ImageIcon redIcon;
    private ImageIcon greenIcon;
    private Model model;

public tabStatus(Model model) throws Exception {
    super(new BorderLayout());
    this.model = model;

    try {
        BufferedImage red = ImageIO.read(getClass().getResource("/images/red-icon.png"));
        BufferedImage green = ImageIO.read(getClass().getResource("/images/green-icon.png"));
        redIcon = new ImageIcon(red, "red");
        greenIcon = new ImageIcon(green, "green");
    } catch (Exception ex) {
        System.err.println("tabStatus: " + ex);
        throw(ex);
    }

    GridBagConstraints bag = new GridBagConstraints();

    JPanel top = new JPanel(new GridBagLayout());

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 0;
    bag.gridy = 0;
    bag.insets = new Insets(0, 0, 0, 20);
    top.add(new JLabel("Station"), bag);

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 1;
    bag.gridy = 0;
    bag.insets = new Insets(0, 0, 0, 20);
    top.add(new JLabel("FMS IP"), bag);

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 2;
    bag.gridy = 0;
    bag.insets = new Insets(0, 0, 0, 20);
    top.add(new JLabel("DS IP"), bag);

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 3;
    bag.gridy = 0;
    bag.insets = new Insets(0, 0, 0, 20);
    top.add(new JLabel("FMS"), bag);

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 4;
    bag.gridy = 0;
    bag.insets = new Insets(0, 0, 0, 20);
    top.add(new JLabel("DS"), bag);

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 5;
    bag.gridy = 0;
    bag.insets = new Insets(0, 0, 0, 20);
    top.add(new JLabel("DS MAC"), bag);

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 6;
    bag.gridy = 0;
    bag.insets = new Insets(0, 0, 0, 20);
    top.add(new JLabel("Comm Vers"), bag);

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 7;
    bag.gridy = 0;
    bag.gridwidth = 6;
    bag.insets = new Insets(0, 0, 0, 20);
    top.add(new JLabel("DS Status"), bag);

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 13;
    bag.gridy = 0;
    bag.gridwidth = 5;
    bag.insets = new Insets(0, 0, 0, 20);
    top.add(new JLabel("Robot Status"), bag);

    JPanel bot = new JPanel(new GridBagLayout());

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 0;
    bag.gridy = 0;
    bag.insets = new Insets(0, 0, 0, 20);
    bot.add(new JLabel("Station"), bag);

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 1;
    bag.gridy = 0;
    bag.insets = new Insets(0, 0, 0, 20);
    bot.add(new JLabel("DS Id"), bag);

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 2;
    bag.gridy = 0;
    bag.insets = new Insets(0, 0, 0, 20);
    bot.add(new JLabel("DS Version"), bag);

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 3;
    bag.gridy = 0;
    bag.insets = new Insets(0, 0, 0, 20);
    bot.add(new JLabel("FPGA"), bag);

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 4;
    bag.gridy = 0;
    bag.insets = new Insets(0, 0, 0, 20);
    bot.add(new JLabel("CRIO"), bag);

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 5;
    bag.gridy = 0;
    bag.insets = new Insets(0, 0, 0, 40);
    bot.add(new JLabel("Avg Trip"), bag);

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 6;
    bag.gridy = 0;
    bag.insets = new Insets(0, 0, 0, 40);
    bot.add(new JLabel("Missed Count"), bag);

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 7;
    bag.gridy = 0;
    bag.insets = new Insets(0, 0, 0, 40);
    bot.add(new JLabel("Total Count"), bag);

    bag.insets = new Insets(0, 0, 0, 0);
    for (int i = 0; i < 3; i++) {
        teamTopInit(top, bag, true, i);      // blue
        teamTopInit(top, bag, false, i);     // red
        teamBotInit(bot, bag, true, i);      // blue
        teamBotInit(bot, bag, false, i);     // red
    }

    add(top, BorderLayout.NORTH);
    add(bot, BorderLayout.CENTER);
}

private void teamTopInit(JPanel pane, GridBagConstraints bag, boolean isBlue, int ident)
{
    int slot = isBlue ? 1 + ident : 3 - ident;
    int gridy = isBlue ? 1 + ident : 4 + ident;

    AllianceTeam team = model.getTeam(isBlue, ident);

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 0;
    bag.gridy = gridy;
    bag.gridwidth = 1;
    pane.add(new JLabel((isBlue ? "Blue " : "Red ") + slot), bag);

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 1;
    bag.gridy = gridy;
    bag.gridwidth = 1;
    bag.insets = new Insets(0, 0, 0, 20);
    pane.add(new JLabel("10.0.0.2"), bag);

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 2;
    bag.gridy = gridy;
    bag.gridwidth = 1;
    bag.insets = new Insets(0, 0, 0, 20);
    pane.add(new JLabel("10.XX.XX.5"), bag);

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 3;
    bag.gridy = gridy;
    bag.gridwidth = 1;
    bag.insets = new Insets(0, 0, 0, 20);
    pane.add(new JLabel("0000"), bag);

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 4;
    bag.gridy = gridy;
    bag.gridwidth = 1;
    bag.insets = new Insets(0, 0, 0, 20);
    pane.add(new JLabel("0000"), bag);

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 5;
    bag.gridy = gridy;
    bag.gridwidth = 1;
    bag.insets = new Insets(0, 0, 0, 20);
    pane.add(new JLabel("ff:xx:aa:bb:cc:dd"), bag);

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 6;
    bag.gridy = gridy;
    bag.gridwidth = 1;
    bag.insets = new Insets(0, 0, 0, 20);
    pane.add(new JLabel("090210a3"), bag);

    bag.insets = new Insets(0, 0, 0, 0);
    for (int i = 0; i < 6; i++) {
        JLabel demo = null;
        switch (i) {
        case 0: demo = team.dslink;  break;
        case 1: demo = team.incomp;  break;
        case 2: demo = team.pc;  break;
        case 3: demo = team.scc_es;  break;
        case 4: demo = team.ds_es;  break;
        case 5: demo = team.ds_enabled;
            bag.insets = new Insets(0, 0, 0, 20);
            break;
        }
        bag.gridx = 7 + i;
        bag.gridy = gridy;
        bag.gridwidth = 1;
        pane.add(demo, bag);
    }

    bag.insets = new Insets(0, 0, 0, 0);
    for (int j = 0; j < 4; j++) {
        JLabel demo = null;
        switch (j) {
        case 0: demo = team.rlink;  break;
        case 1: demo = team.auto;  break;
        case 2: demo = team.renabled;  break;
        case 3: demo = team.unused;  break;
        }
        bag.gridx = 13 + j;
        bag.gridy = gridy;
        bag.gridwidth = 1;
        pane.add(demo, bag);
    }
}

private void teamBotInit(JPanel pane, GridBagConstraints bag, boolean isBlue, int ident)
{
    int slot = isBlue ? 1 + ident : 3 - ident;
    int gridy = isBlue ? 1 + ident : 4 + ident;

    AllianceTeam team = model.getTeam(isBlue, ident);

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 0;
    bag.gridy = gridy;
    bag.gridwidth = 1;
    pane.add(new JLabel((isBlue ? "Blue " : "Red ") + slot), bag);

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 1;
    bag.gridy = gridy;
    bag.gridwidth = 1;
    bag.insets = new Insets(0, 0, 0, 20);
    pane.add(new JLabel("0000"), bag);

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 2;
    bag.gridy = gridy;
    bag.gridwidth = 1;
    bag.insets = new Insets(0, 0, 0, 20);
    pane.add(new JLabel("140108n1"), bag);

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 3;
    bag.gridy = gridy;
    bag.gridwidth = 1;
    bag.insets = new Insets(0, 0, 0, 20);
    pane.add(new JLabel("1ab2c3d"), bag);

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 4;
    bag.gridy = gridy;
    bag.gridwidth = 1;
    bag.insets = new Insets(0, 0, 0, 20);
    pane.add(new JLabel("1ab2c3d"), bag);

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 5;
    bag.gridy = gridy;
    bag.gridwidth = 1;
    bag.insets = new Insets(0, 0, 0, 40);
    pane.add(new JLabel("0"), bag);

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 6;
    bag.gridy = gridy;
    bag.gridwidth = 1;
    bag.insets = new Insets(0, 0, 0, 40);
    pane.add(new JLabel("0"), bag);

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 7;
    bag.gridy = gridy;
    bag.gridwidth = 1;
    bag.insets = new Insets(0, 0, 0, 40);
    pane.add(new JLabel("0"), bag);
}

public String getName()
{
    return "Status";
}

}
