package org.team537.fms.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import java.awt.Insets;

import javax.swing.JPanel;

import org.team537.fms.Model;

public class tabScore extends JPanel 
{
    Model model;
    AllianceScore blue, red;

public tabScore(Model model) throws Exception
{
    super(new BorderLayout());
    this.model = model;

    GridBagConstraints bag = new GridBagConstraints();

    JPanel middle = new JPanel(new GridBagLayout());
    blue = new AllianceScore(model, true);
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.insets = new Insets(0, 0, 0, 0);
    bag.gridx = 0;
    bag.gridy = 0;
    middle.add(blue, bag);
    
    red = new AllianceScore(model, false);
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.insets = new Insets(0, 0, 0, 0);
    bag.gridx = 0;
    bag.gridy = 1;
    middle.add(red, bag);

    add(middle, BorderLayout.WEST);
    setVisible(true);
}

public String getName()
{
    return "Score";
}

}
