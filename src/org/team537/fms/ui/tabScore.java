package org.team537.fms.ui;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import org.team537.fms.Model;

public class tabScore extends JPanel 
{
    Model model;
    AllianceScore blue, red;
    JSpinner matchNumber;

public tabScore(Model model) throws Exception
{
    super();
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    this.model = model;

    JPanel top = new JPanel();
    top.setLayout(new BoxLayout(top, BoxLayout.X_AXIS));

    top.add(new JLabel("Match "));

    SpinnerModel smodel = new SpinnerNumberModel(1, 1, 200, 1);
    matchNumber = new JSpinner(smodel);
    matchNumber.setMaximumSize(new Dimension(60, 24));
    top.add(matchNumber);
    add(top);

    blue = new AllianceScore(model, true);
    add(new JScrollPane(blue, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));
    
    red = new AllianceScore(model, false);
    add(new JScrollPane(red, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER));

    setVisible(true);
}

public String getName()
{
    return "Score";
}

}
