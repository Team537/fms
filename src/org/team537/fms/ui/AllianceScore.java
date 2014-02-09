package org.team537.fms.ui;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import org.team537.fms.Model;

public class AllianceScore extends JPanel
{
    class AutoScore {
        JCheckBox mobile;
        JCheckBox lowgoal;
        JCheckBox highgoal;
        JCheckBox hotgoal;
        JLabel total;
    }

    class TeleScore {
        JLabel cycle;
        JCheckBox zone1;
        JCheckBox zone2;
        JCheckBox zone3;
        JCheckBox truss;
        JCheckBox rcatch;
        JCheckBox lowgoal;
        JCheckBox highgoal;
        JLabel total;
        JButton plus;
    }

    ArrayList<AutoScore> ascore = new ArrayList<AutoScore>();
    ArrayList<TeleScore> tscore = new ArrayList<TeleScore>();
    JSpinner fouls;
    JSpinner techFouls;
    JLabel foulTotal;
    JLabel allTotal;
    int cycleCount = 0;
    int cycleGridy;

    BufferedImage background;

public AllianceScore(Model model, boolean isBlue) throws Exception
{
    super(new GridBagLayout());

    try {
        background = isBlue 
           ? ImageIO.read(getClass().getResource("/images/blue-background.png"))
           : ImageIO.read(getClass().getResource("/images/red-background.png"));
    } catch (Exception ex) {
        System.err.println("AllianceScore: " + ex);
        throw(new Exception("AllianceScore ", ex));
    }

    GridBagConstraints bag = new GridBagConstraints();
    int gridx = 0, gridy;

    // Header  Auto
    JLabel zchange = new JLabel("Mobile");
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.weightx = 0.0;
    bag.gridx = gridx++;
    bag.gridy = 0;
    bag.gridwidth = 1;
    add(zchange, bag);

    JLabel alowgoal = new JLabel("low-goal");
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.weightx = 0.0;
    bag.gridx = gridx++;
    bag.gridy = 0;
    bag.insets = new Insets(0, 0, 0, 10);
    bag.gridwidth = 1;
    add(alowgoal, bag);

    JLabel ahighgoal = new JLabel("high-goal");
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.weightx = 0.0;
    bag.gridx = gridx++;
    bag.gridy = 0;
    bag.gridwidth = 1;
    bag.insets = new Insets(0, 0, 0, 10);
    add(ahighgoal, bag);

    JLabel hotgoal = new JLabel("hot-goal");
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.weightx = 0.0;
    bag.gridx = gridx++;
    bag.gridy = 0;
    bag.insets = new Insets(0, 0, 0, 00);
    bag.gridwidth = 1;
    add(hotgoal, bag);

    JLabel atotal = new JLabel("Total");
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.weightx = 0.0;
    bag.gridx = 8;
    bag.gridy = 0;
    bag.gridwidth = 1;
    add(atotal, bag);

    JLabel alltot = new JLabel("Alliance Total");
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.weightx = 0.0;
    bag.gridx = 9;
    bag.gridy = 0;
    bag.gridwidth = 1;
    bag.insets = new Insets(0, 30, 0, 0);
    add(alltot, bag);

    bag.gridx = 9;
    bag.gridy = 1;
    bag.gridwidth = 1;
    allTotal = new JLabel(String.format("%1$4d", 0));
    add(allTotal, bag);

    bag.insets = new Insets(0, 0, 0, 0);
    gridy = 0;
    for (int i = 0; i < 3; i++) {
        AutoScore aentry = new AutoScore();
        gridx = 0;
        gridy++;

        bag.insets = new Insets(0, 0, 0, 0);
        bag.gridx = gridx++;
        bag.gridy = gridy;
        bag.gridwidth = 1;
        aentry.mobile = new JCheckBox(" +5 ");
        aentry.mobile.setContentAreaFilled(false);
        add(aentry.mobile, bag);

        bag.gridx = gridx++;
        bag.gridy = gridy;
        bag.gridwidth = 1;
        aentry.lowgoal = new JCheckBox(" 1 ");
        aentry.lowgoal.setContentAreaFilled(false);
        add(aentry.lowgoal, bag);

        bag.gridx = gridx++;
        bag.gridy = gridy;
        bag.gridwidth = 1;
        aentry.highgoal = new JCheckBox(" 10 ");
        aentry.highgoal.setContentAreaFilled(false);
        add(aentry.highgoal, bag);

        bag.gridx = gridx++;
        bag.gridy = gridy;
        bag.gridwidth = 1;
        aentry.hotgoal = new JCheckBox(" +5 ");
        aentry.hotgoal.setContentAreaFilled(false);
        add(aentry.hotgoal, bag);

        bag.gridx = 8;
        bag.gridy = gridy;
        bag.gridwidth = 1;
        aentry.total = new JLabel(String.format("%1$4d", 0));
        add(aentry.total, bag);

        ascore.add(aentry);
    }

    gridx = 0;
    gridy++;

    JLabel foul = new JLabel("Fouls");
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.weightx = 0.0;
    bag.gridx = gridx++;
    bag.gridy = gridy;
    bag.gridwidth = 1;
    add(foul, bag);

    SpinnerModel smodel = new SpinnerNumberModel(1, 1, 20, 1);
    fouls = new JSpinner(smodel);
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.weightx = 0.0;
    bag.gridx = gridx++;
    bag.gridy = gridy;
    bag.gridwidth = 1;
    add(fouls, bag);

    JLabel techFoul = new JLabel("Tech Fouls");
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.weightx = 0.0;
    bag.gridx = gridx++;
    bag.gridy = gridy;
    bag.gridwidth = 1;
    add(techFoul, bag);

    smodel = new SpinnerNumberModel(1, 1, 20, 1);
    techFouls = new JSpinner(smodel);
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.weightx = 0.0;
    bag.gridx = gridx++;
    bag.gridy = gridy;
    bag.gridwidth = 1;
    add(techFouls, bag);

    foulTotal = new JLabel(String.format("%1$4d", 0));
    // bag.insets = new Insets(0, 0, 0, 10);
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.weightx = 0.0;
    bag.gridx = 8;
    bag.gridy = gridy;
    bag.gridwidth = 1;
    add(foulTotal, bag);

    gridx = 0;
    gridy++;

    // Header  Tele-op
    JLabel cycle = new JLabel("Cycle");
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.weightx = 0.0;
    bag.gridx = gridx++;
    bag.gridy = gridy;
    bag.gridwidth = 1;
    add(cycle, bag);

    JLabel zone1 = new JLabel("zone-1");
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.weightx = 0.0;
    bag.gridx = gridx++;
    bag.gridy = gridy;
    bag.gridwidth = 1;
    add(zone1, bag);

    JLabel zone2 = new JLabel("zone-2");
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.weightx = 0.0;
    bag.gridx = gridx++;
    bag.gridy = gridy;
    bag.gridwidth = 1;
    add(zone2, bag);

    JLabel truss = new JLabel("truss");
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.weightx = 0.0;
    bag.gridx = gridx++;
    bag.gridy = gridy;
    bag.gridwidth = 1;
    add(truss, bag);

    JLabel rcatch = new JLabel("catch");
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.weightx = 0.0;
    bag.gridx = gridx++;
    bag.gridy = gridy;
    bag.gridwidth = 1;
    add(rcatch, bag);

    JLabel zone3 = new JLabel("zone-3");
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.weightx = 0.0;
    bag.gridx = gridx++;
    bag.gridy = gridy;
    bag.gridwidth = 1;
    add(zone3, bag);

    JLabel lowgoal = new JLabel("low-goal");
    bag.insets = new Insets(0, 10, 0, 10);
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.weightx = 0.0;
    bag.gridx = gridx++;
    bag.gridy = gridy;
    bag.gridwidth = 1;
    add(lowgoal, bag);

    JLabel highgoal = new JLabel("high-goal");
    bag.insets = new Insets(0, 0, 0, 10);
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.weightx = 0.0;
    bag.gridx = gridx++;
    bag.gridy = gridy;
    bag.gridwidth = 1;
    add(highgoal, bag);

    JLabel total = new JLabel("Total");
    bag.insets = new Insets(0, 0, 0, 10);
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.weightx = 0.0;
    bag.gridx = gridx++;
    bag.gridy = gridy;
    bag.gridwidth = 1;
    add(total, bag);

    cycleGridy = gridy + 1;
    addCycle();
}

private void addCycle()
{
    TeleScore tentry = new TeleScore();
    int gridx = 0;
    int prev = tscore.size();
    
    if (0 != prev) {
        TeleScore pentry = (TeleScore) tscore.get(prev - 1);
        pentry.plus.setEnabled(false);
    }
    GridBagConstraints bag = new GridBagConstraints();

    bag.insets = new Insets(0, 0, 0, 0);
    bag.gridx = gridx++;
    bag.gridy = cycleGridy;
    bag.gridwidth = 1;
    tentry.cycle = new JLabel(String.format("%1$3d", cycleCount++));
    add(tentry.cycle, bag);

    bag.gridx = gridx++;
    bag.gridwidth = 1;
    tentry.zone1 = new JCheckBox(" 0 ");
    tentry.zone1.setContentAreaFilled(false);
    add(tentry.zone1, bag);

    bag.gridx = gridx++;
    bag.gridwidth = 1;
    tentry.zone2 = new JCheckBox(" 10 ");
    tentry.zone2.setContentAreaFilled(false);
    add(tentry.zone2, bag);

    bag.gridx = gridx++;
    bag.gridwidth = 1;
    tentry.truss = new JCheckBox(" 10 ");
    tentry.truss.setContentAreaFilled(false);
    add(tentry.truss, bag);

    bag.gridx = gridx++;
    bag.gridwidth = 1;
    tentry.rcatch = new JCheckBox(" 10 ");
    tentry.rcatch.setContentAreaFilled(false);
    add(tentry.rcatch, bag);

    bag.gridx = gridx++;
    bag.gridwidth = 1;
    tentry.zone3 = new JCheckBox(" 30 ");
    tentry.zone3.setContentAreaFilled(false);
    add(tentry.zone3, bag);

    bag.gridx = gridx++;
    bag.gridwidth = 1;
    tentry.lowgoal = new JCheckBox(" 1 ");
    tentry.lowgoal.setContentAreaFilled(false);
    add(tentry.lowgoal, bag);

    bag.gridx = gridx++;
    bag.gridwidth = 1;
    tentry.highgoal = new JCheckBox(" 10 ");
    tentry.highgoal.setContentAreaFilled(false);
    add(tentry.highgoal, bag);

    bag.gridx = gridx++;
    bag.gridwidth = 1;
    tentry.total = new JLabel(String.format("%1$4d", 0));
    add(tentry.total, bag);

    bag.gridx = gridx++;
    bag.gridwidth = 1;
    tentry.plus = new JButton("plus");
    add(tentry.total, bag);

    tscore.add(tentry);
}

@Override
protected void paintComponent(Graphics g)
{
    super.paintComponent(g);
    g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
}

}
