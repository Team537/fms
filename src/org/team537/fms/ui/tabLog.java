package org.team537.fms.ui;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import org.team537.fms.Model;

public class tabLog extends JPanel 
{
    JList logtext;
    DefaultListModel data;

public tabLog(Model model) 
{
    super();
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    data = new DefaultListModel();
    data.addElement("Event Log");

    logtext = new JList(data);

    JScrollPane scroll = new JScrollPane(logtext, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    add(scroll);
}

public String getName()
{
    return "Log";
}

}
