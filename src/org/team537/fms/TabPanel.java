package org.team537.fms;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;

public class TabPanel extends JTabbedPane
{
    private JComponent tabs[] = new JComponent[4];

public TabPanel() throws Exception
{
    for (int i = 0; i < 4; i++) {
        switch (i) {
        case 0: tabs[i] = new tabFCUI();    break;
        case 1: tabs[i] = new tabConfig();  break;
        case 2: tabs[i] = new tabScore();   break;
        case 3: tabs[i] = new tabStatus();  break;
        }
        addTab(tabs[i].getName(), tabs[i]);
    }
    setVisible(true);
}

}
