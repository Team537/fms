package org.team537.fms.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import org.team537.fms.Model;
import org.team537.ScrollUtil;

public class AllianceScore extends JPanel implements ActionListener, ItemListener, ChangeListener
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
    JScrollPane scrollBar;

    BufferedImage background;
    ImageIcon plus;
    boolean isBlue;

public AllianceScore(Model model, JLabel ltotal, boolean isBlue) throws Exception
{
    super(new GridBagLayout());

    allTotal = ltotal;
    this.isBlue = isBlue;

    try {
        background = isBlue 
           ? ImageIO.read(getClass().getResource("/images/blue-background.png"))
           : ImageIO.read(getClass().getResource("/images/red-background.png"));
        BufferedImage iplus = ImageIO.read(getClass().getResource("/images/plus.png"));
        plus = new ImageIcon(iplus, "plus");
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

    bag.insets = new Insets(0, 0, 0, 0);
    gridy = 1;
    for (int i = 0; i < 3; i++) {
        AutoScore aentry = new AutoScore();
        gridx = 0;
        bag.gridy = gridy++;

        bag.insets = new Insets(0, 0, 0, 0);
        bag.gridx = gridx++;
        bag.gridwidth = 1;
        aentry.mobile = new JCheckBox(" +5 ");
        aentry.mobile.setContentAreaFilled(false);
        add(aentry.mobile, bag);
        aentry.mobile.addItemListener(this);

        bag.gridx = gridx++;
        bag.gridwidth = 1;
        aentry.lowgoal = new JCheckBox(" 1 ");
        aentry.lowgoal.setContentAreaFilled(false);
        add(aentry.lowgoal, bag);
        aentry.lowgoal.addItemListener(this);

        bag.gridx = gridx++;
        bag.gridwidth = 1;
        aentry.highgoal = new JCheckBox(" 10 ");
        aentry.highgoal.setContentAreaFilled(false);
        add(aentry.highgoal, bag);
        aentry.highgoal.addItemListener(this);

        bag.gridx = gridx++;
        bag.gridwidth = 1;
        aentry.hotgoal = new JCheckBox(" +5 ");
        aentry.hotgoal.setContentAreaFilled(false);
        add(aentry.hotgoal, bag);
        aentry.hotgoal.addItemListener(this);

        bag.gridx = 8;
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

    SpinnerModel smodel = new SpinnerNumberModel(0, 0, 20, 1);
    fouls = new JSpinner(smodel);
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.weightx = 0.0;
    bag.gridx = gridx++;
    bag.gridy = gridy;
    bag.gridwidth = 1;
    add(fouls, bag);
    fouls.addChangeListener(this);

    JLabel techFoul = new JLabel("Tech Fouls");
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.weightx = 0.0;
    bag.gridx = gridx++;
    bag.gridy = gridy;
    bag.gridwidth = 1;
    add(techFoul, bag);

    smodel = new SpinnerNumberModel(0, 0, 20, 1);
    techFouls = new JSpinner(smodel);
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.weightx = 0.0;
    bag.gridx = gridx++;
    bag.gridy = gridy;
    bag.gridwidth = 1;
    add(techFouls, bag);
    techFouls.addChangeListener(this);

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

    cycleGridy = gridy;
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
    cycleGridy++;

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
    tentry.zone1.addItemListener(this);

    bag.gridx = gridx++;
    bag.gridwidth = 1;
    tentry.zone2 = new JCheckBox(" 10 ");
    tentry.zone2.setContentAreaFilled(false);
    add(tentry.zone2, bag);
    tentry.zone2.addItemListener(this);

    bag.gridx = gridx++;
    bag.gridwidth = 1;
    tentry.truss = new JCheckBox(" 10 ");
    tentry.truss.setContentAreaFilled(false);
    add(tentry.truss, bag);
    tentry.truss.addItemListener(this);

    bag.gridx = gridx++;
    bag.gridwidth = 1;
    tentry.rcatch = new JCheckBox(" 10 ");
    tentry.rcatch.setContentAreaFilled(false);
    add(tentry.rcatch, bag);
    tentry.rcatch.addItemListener(this);

    bag.gridx = gridx++;
    bag.gridwidth = 1;
    tentry.zone3 = new JCheckBox(" 30 ");
    tentry.zone3.setContentAreaFilled(false);
    add(tentry.zone3, bag);
    tentry.zone3.addItemListener(this);

    bag.gridx = gridx++;
    bag.gridwidth = 1;
    tentry.lowgoal = new JCheckBox(" 1 ");
    tentry.lowgoal.setContentAreaFilled(false);
    add(tentry.lowgoal, bag);
    tentry.lowgoal.addItemListener(this);

    bag.gridx = gridx++;
    bag.gridwidth = 1;
    tentry.highgoal = new JCheckBox(" 10 ");
    tentry.highgoal.setContentAreaFilled(false);
    add(tentry.highgoal, bag);
    tentry.highgoal.addItemListener(this);

    bag.gridx = gridx++;
    bag.gridwidth = 1;
    tentry.total = new JLabel(String.format("%1$4d", 0));
    add(tentry.total, bag);

    bag.gridx = gridx++;
    bag.gridwidth = 1;
    tentry.plus = new JButton(plus);
    tentry.plus.setPreferredSize(new Dimension(24, 24));
    tentry.plus.setMaximumSize(new Dimension(24, 24));
    add(tentry.plus, bag);
    tentry.plus.setActionCommand("plus");
    tentry.plus.addActionListener(this);

    tscore.add(tentry);
}

public void actionPerformed(ActionEvent ev)
{
    String cmd = ev.getActionCommand();
    if ("plus".equals(cmd)) {
        addCycle();
        revalidate();
        ScrollUtil.scroll(scrollBar, ScrollUtil.NONE, ScrollUtil.BOTTOM);
    }
}

public void itemStateChanged(ItemEvent ev)
{
    sumAll();
    revalidate();
}

public void stateChanged(ChangeEvent ev) 
{
    sumAll();
    revalidate();
}

private void sumAll()
{
    int sum = 0;
    for (AutoScore ae : ascore) {
        int lsum = 0;
        lsum += ae.mobile.isSelected() ? 5 : 0;
        lsum += ae.lowgoal.isSelected() ? 1 : 0;
        lsum += ae.highgoal.isSelected() ? 10 : 0;
        lsum += ae.hotgoal.isSelected() ? 5 : 0;
        ae.total.setText(String.format("%1$4d", lsum));
        sum += lsum;
    }
    for (TeleScore te : tscore) {
        int lsum = 0;
        lsum += te.zone2.isSelected() ? 10 : 0;
        lsum += te.zone3.isSelected() ? 30 : 0;
        lsum += te.truss.isSelected() ? 10 : 0;
        lsum += te.rcatch.isSelected() ? 10 : 0;
        lsum += te.lowgoal.isSelected() ? 1 : 0;
        lsum += te.highgoal.isSelected() ? 10 : 0;
        te.total.setText(String.format("%1$4d", lsum));
        sum += lsum;
    }

    int tot = ((Integer) fouls.getValue() * -20) + ((Integer) techFouls.getValue() * -50);
    foulTotal.setText(String.format("%1$4d", tot));
    sum += tot;
    allTotal.setText(String.format("  %1$s Total:  %2$4d  ", isBlue ? "Blue" : "Red ", sum));
}

public void setScrollHandle(JScrollPane handle)
{
    scrollBar = handle;
}

@Override
protected void paintComponent(Graphics g)
{
    super.paintComponent(g);
    g.drawImage(background, 0, 0, getWidth(), getHeight(), null);
}

}
