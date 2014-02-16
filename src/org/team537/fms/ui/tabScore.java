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
    JLabel  btotal, rtotal;
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

    btotal = new JLabel(String.format("  Blue Total:  %1$4d  ", 0));
    top.add(btotal);

    rtotal = new JLabel(String.format("  Red Total:  %1$4d  ", 0));
    top.add(rtotal);

    add(top);

    blue = new AllianceScore(model, btotal, true);
    JScrollPane bs = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    bs.setViewportView(blue);
    add(bs);

    red = new AllianceScore(model, rtotal, false);
    JScrollPane rs = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    rs.setViewportView(red);
    add(rs);

    setVisible(true);
}

public String getName()
{
    return "Score";
}

}
