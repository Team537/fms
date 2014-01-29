package org.team537.fms;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class tabFCUI extends JPanel 
{
    private JSpinner matchNumber;
    private JSpinner autoTime;
    private JSpinner teleTime;
    private JButton start, reset, restore;

public tabFCUI(Model model) {

    super(new GridBagLayout());
    setPreferredSize(new Dimension(400, 400));
    GridBagConstraints bag = new GridBagConstraints();

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 0;
    bag.gridy = 0;
    this.add(new JLabel("Match Number:"), bag);

    SpinnerModel smodel = new SpinnerNumberModel(1, 1, 200, 1);
    matchNumber = new JSpinner(smodel);
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 1;
    bag.gridy = 0;
    this.add(matchNumber, bag);

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 3;
    bag.gridy = 0;
    this.add(new JLabel("Auto Time:"), bag);

    smodel = new SpinnerNumberModel(15, 5, 30, 1);
    autoTime = new JSpinner(smodel);
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 4;
    bag.gridy = 0;
    this.add(autoTime, bag);

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 5;
    bag.gridy = 0;
    this.add(new JLabel("TeleOp Time:"), bag);

    smodel = new SpinnerNumberModel(120, 5, 200, 1);
    teleTime = new JSpinner(smodel);
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 6;
    bag.gridy = 0;
    this.add(teleTime, bag);

    start = new JButton("start");
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 0;
    bag.gridy = 2;
    bag.gridwidth = 2;
    this.add(start, bag);

    reset = new JButton("reset");
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 0;
    bag.gridy = 3;
    bag.gridwidth = 2;
    this.add(reset, bag);

    restore = new JButton("restore");
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 4;
    bag.gridy = 3;
    bag.gridwidth = 2;
    this.add(restore, bag);

    setVisible(true);
}

public String getName()
{
    return "FCUI";
}

}
