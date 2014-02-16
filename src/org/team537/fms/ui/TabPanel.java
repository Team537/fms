package org.team537.fms.ui;

import javax.swing.JComponent;
import javax.swing.JTabbedPane;

import org.team537.fms.Model;

public class TabPanel extends JTabbedPane 
{
    private JComponent tabs[] = new JComponent[6];

public TabPanel(Model model) throws Exception
{
    for (int i = 0; i < 6; i++) {
        switch (i) {
        case 0: tabs[i] = new tabFCUI(model);    break;
        case 1: tabs[i] = new tabConfig(model);  break;
        case 2: tabs[i] = new tabScore(model);   break;
        case 3: tabs[i] = new tabStatus(model);  break;
        case 4: tabs[i] = new tabTeam(model);  break;
        case 5: tabs[i] = new tabLog(model);  break;
        }
        addTab(tabs[i].getName(), tabs[i]);
    }

    setVisible(true);
}

public void setReady()
{
    ((tabFCUI) tabs[0]).setReady();
}

public void stopMatch()
{
    ((tabFCUI) tabs[0]).stopMatch();
}

public void Log(String msg)
{
    ((tabLog) tabs[5]).Log(msg);
}

}
