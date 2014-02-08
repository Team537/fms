package org.team537.fms.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.DefaultFormatter;

import org.team537.fms.Model;

public class tabFCUI extends JPanel implements ActionListener, ChangeListener
{
    private JSpinner matchNumber;
    private JSpinner autoTime;
    private JSpinner teleTime;
    private JButton start, reset, restore;

    private Model model;
    private boolean isRunning = false;
    private boolean expired = true;

public tabFCUI(Model model)
{
    super(new BorderLayout());
    this.model = model;
    setPreferredSize(new Dimension(400, 400));
    GridBagConstraints bag = new GridBagConstraints();

    JPanel top = new JPanel(new GridBagLayout());

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 0;
    bag.gridy = 0;
    top.add(new JLabel("Match Number:"), bag);

    SpinnerModel smodel = new SpinnerNumberModel(1, 1, 200, 1);
    matchNumber = new JSpinner(smodel);
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 1;
    bag.gridy = 0;
    top.add(matchNumber, bag);
    matchNumber.addChangeListener(this);
    model.setMatchNumber(1);
    JComponent comp = matchNumber.getEditor();
    JFormattedTextField field = (JFormattedTextField) comp.getComponent(0);
    DefaultFormatter formatter = (DefaultFormatter) field.getFormatter();
    formatter.setCommitsOnValidEdit(true);

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 3;
    bag.gridy = 1;
    top.add(new JLabel("Auto Time:"), bag);

    smodel = new SpinnerNumberModel(15, 5, 30, 1);
    autoTime = new JSpinner(smodel);
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 4;
    bag.gridy = 1;
    top.add(autoTime, bag);
    autoTime.addChangeListener(this);

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 5;
    bag.gridy = 1;
    top.add(new JLabel("TeleOp Time:"), bag);

    smodel = new SpinnerNumberModel(120, 5, 200, 1);
    teleTime = new JSpinner(smodel);
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 6;
    bag.gridy = 1;
    top.add(teleTime, bag);
    teleTime.addChangeListener(this);

    model.setMatchTime(15, 120);

    start = new JButton("start");
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 0;
    bag.gridy = 3;
    bag.gridwidth = 2;
    top.add(start, bag);
    start.setActionCommand("start");
    start.addActionListener(this);
    start.setEnabled(true);

    reset = new JButton("reset");
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 0;
    bag.gridy = 4;
    bag.gridwidth = 2;
    top.add(reset, bag);
    reset.setActionCommand("reset");
    reset.addActionListener(this);

    restore = new JButton("restore");
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = 4;
    bag.gridy = 4;
    bag.gridwidth = 2;
    top.add(restore, bag);
    restore.setActionCommand("restore");
    restore.addActionListener(this);

    this.add(top, BorderLayout.NORTH);

    setVisible(true);
}

public String getName()
{
    return "FCUI";
}

public void stateChanged(ChangeEvent ev)
{
    JSpinner src = (JSpinner) ev.getSource();
    if (matchNumber == src) 
        model.setMatchNumber((Integer) matchNumber.getValue());
    else if (autoTime == src || teleTime == src)
        model.setMatchTime((Integer) autoTime.getValue(), (Integer) teleTime.getValue());
}

public void actionPerformed(ActionEvent ev)
{
    String cmd = ev.getActionCommand();
    if (!isRunning && "start".equals(cmd)) {
        isRunning = true;
        start.setText("Stop");
        if (expired)
            model.setMatchTime((Integer) autoTime.getValue(), (Integer) teleTime.getValue());
        model.startMatch();
        reset.setEnabled(false);
        restore.setEnabled(false);
    } else if (isRunning && "start".equals(cmd)) {
        expired = false;
        model.stopMatch();
    } else if ("reset".equals(cmd)) {
        model.setMatchTime((Integer) autoTime.getValue(), (Integer) teleTime.getValue());
        expired = true;
    } else if ("restore".equals(cmd)) {
        autoTime.setValue(new Integer(15));
        teleTime.setValue(new Integer(120));
        matchNumber.setValue(new Integer(1)); 
    }
}

public void stopMatch()
{
    isRunning = false;
    start.setText("Start");
    reset.setEnabled(true);
    restore.setEnabled(true);
    if (expired) {
        model.setMatchTime((Integer) autoTime.getValue(), (Integer) teleTime.getValue());
        Integer mnum = (Integer) matchNumber.getValue();
        matchNumber.setValue(1 + mnum);
    }
}

public void setReady()
{
    start.setEnabled(true);
}

}
