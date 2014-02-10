package org.team537.fms.ui;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;

import org.team537.fms.Model;

public class TabPanel extends JTabbedPane implements ChangeListener
{
    private JComponent tabs[] = new JComponent[4];
    private boolean init = false;

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

    addChangeListener(this);
    setVisible(true);
}

public void stateChanged(ChangeEvent ev)
{
    if (!init) {
        init = true;
        try {
            ((tabScore) tabs[2]).finishConfig();
        } catch (Exception ex) {
            System.err.println("Error initializing Score tab");
            ex.printStackTrace();
        }
    }
}

public void setReady()
{
    ((tabFCUI) tabs[0]).setReady();
}

public void stopMatch()
{
    ((tabFCUI) tabs[0]).stopMatch();
}

}
