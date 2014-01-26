package org.team537.fms;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

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

public tabFCUI() {

    super(new GridBagLayout());
    GridBagConstraints bag = new GridBagConstraints();

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 0;
    bag.gridy = 0;
    this.add(new JLabel("Match Number:"), bag);

    SpinnerModel model = new SpinnerNumberModel(1, 1, 200, 1);
    matchNumber = new JSpinner(model);
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 1;
    bag.gridy = 0;
    this.add(matchNumber, bag);

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 2;
    bag.gridy = 0;
    this.add(new JLabel("Auto Time:"), bag);

    model = new SpinnerNumberModel(15, 5, 30, 1);
    autoTime = new JSpinner(model);
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 3;
    bag.gridy = 0;
    this.add(autoTime, bag);

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 4;
    bag.gridy = 0;
    this.add(new JLabel("TeleOp Time:"), bag);

    model = new SpinnerNumberModel(120, 5, 200, 1);
    teleTime = new JSpinner(model);
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 5;
    bag.gridy = 0;
    this.add(teleTime, bag);

    setVisible(true);
}

public String getName()
{
    return "FCUI";
}

}
