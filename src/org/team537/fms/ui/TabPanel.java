package org.team537.fms.ui;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;

import org.team537.fms.Model;

public class TabPanel extends JTabbedPane
{
    private JComponent tabs[] = new JComponent[4];

public TabPanel(Model model) throws Exception
{
    for (int i = 0; i < 4; i++) {
        switch (i) {
        case 0: tabs[i] = new tabFCUI(model);    break;
        case 1: tabs[i] = new tabConfig(model);  break;
        case 2: tabs[i] = new tabScore(model);   break;
        case 3: tabs[i] = new tabStatus(model);  break;
        }
        addTab(tabs[i].getName(), tabs[i]);
    }
    setVisible(true);
}

}
