package org.team537.fms.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.net.InetAddress;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.team537.fms.Model;

public class tabConfig extends JPanel implements ItemListener, ActionListener
{
    private Model model;

    private JCheckBox eCisco;
    private JTextField ciscoIPaddr;

    private JCheckBox eTeamMgmt;
    private JTextField TeamMgmtURL;

    private JCheckBox eAudProxy;
    private JTextField AudProxyIPaddr;
    private JTextField AudProxyPort;

public tabConfig(Model model) 
{
    super(new GridBagLayout());
    this.model = model;
    GridBagConstraints bag = new GridBagConstraints();
    int gridx = 0;

    // configure access-point connectivity
    eCisco = new JCheckBox("Enable Cisco");
    eCisco.setContentAreaFilled(false);
    eCisco.addItemListener(this);
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = gridx++;
    bag.gridy = 0;
    add(eCisco, bag);

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = gridx++;
    bag.gridy = 0;
    add(new JLabel("Cisco ip-addr:"), bag);

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = gridx++;
    bag.gridy = 0;
    ciscoIPaddr = new JTextField("10.0.0.1");
    ciscoIPaddr.setEditable(false);
    ciscoIPaddr.addActionListener(this);
    add(ciscoIPaddr, bag);

    gridx = 0;

    // configure team management url
    eTeamMgmt = new JCheckBox("Enable Team Management");
    eTeamMgmt.setContentAreaFilled(false);
    eTeamMgmt.addItemListener(this);
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = gridx++;
    bag.gridy = 1;
    add(eTeamMgmt, bag);

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.insets = new Insets(0, 10, 0, 10);
    bag.gridx = gridx++;
    bag.gridy = 1;
    add(new JLabel("URL:"), bag);

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.insets = new Insets(0, 0, 0, 0);
    bag.gridx = gridx++;
    bag.gridy = 1;
    TeamMgmtURL = new JTextField(40);
    TeamMgmtURL.setEditable(false);
    TeamMgmtURL.addActionListener(this);
    add(TeamMgmtURL, bag);

    gridx = 0;
    // Audience Proxy 
    eAudProxy = new JCheckBox("Enable Audience Proxy");
    eAudProxy.setContentAreaFilled(false);
    eAudProxy.addItemListener(this);
    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = gridx++;
    bag.gridy = 2;
    add(eAudProxy, bag);

    bag.gridx = gridx++;
    bag.gridy = 2;
    add(new JLabel("Aud ip-addr:"), bag);

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = gridx++;
    bag.gridy = 2;
    AudProxyIPaddr = new JTextField("10.0.0.10");
    AudProxyIPaddr.setEditable(false);
    AudProxyIPaddr.addActionListener(this);
    add(AudProxyIPaddr, bag);

    bag.gridx = gridx++;
    bag.gridy = 2;
    bag.insets = new Insets(0, 10, 0, 10);
    add(new JLabel("Port:"), bag);

    bag.fill = GridBagConstraints.HORIZONTAL;
    bag.gridx = gridx++;
    bag.gridy = 2;
    bag.insets = new Insets(0, 0, 0, 0);
    AudProxyPort = new JTextField("8005");
    AudProxyPort.setEditable(false);
    AudProxyPort.addActionListener(this);
    add(AudProxyPort, bag);

    // export scoring

    setVisible(true);
}

public String getName()
{
    return "Config";
}

public void itemStateChanged(ItemEvent ev) 
{
    Object source = ev.getItemSelectable();
    try {
    if (source == eCisco) {
        if (eCisco.isSelected())
            ciscoIPaddr.setEditable(true);
        else
            ciscoIPaddr.setEditable(false);
    } else if (source == eTeamMgmt) {
        if (eTeamMgmt.isSelected())
            TeamMgmtURL.setEditable(true);
        else
            TeamMgmtURL.setEditable(false);
    } else if (source == eAudProxy) {
        if (eAudProxy.isSelected()) {
            AudProxyIPaddr.setEditable(true);
            AudProxyPort.setEditable(true);
            model.getAudience().setProxy(InetAddress.getByName(AudProxyIPaddr.getText()), AudProxyPort.getText());
            model.getAudience().setEnabled(true);
        } else {
            AudProxyIPaddr.setEditable(false);
            AudProxyPort.setEditable(false);
            model.getAudience().setEnabled(false);
        }
    }
    } catch (Exception ex) {
        // don't care
    }
    revalidate();
}

public void actionPerformed(ActionEvent evt)
{
    Object source = evt.getSource();

    try {
    if (source == ciscoIPaddr) {
        System.out.println("config: cisco " + ciscoIPaddr.getText());
    } else if (source == TeamMgmtURL) {
        System.out.println("config: team mgmt " + TeamMgmtURL.getText());
    } else if (source == AudProxyIPaddr) {
        model.getAudience().setProxy(InetAddress.getByName(AudProxyIPaddr.getText()), AudProxyPort.getText());
        System.out.println("config: aud proxy " + AudProxyIPaddr.getText());
    } else if (source == AudProxyPort) {
        model.getAudience().setProxy(InetAddress.getByName(AudProxyIPaddr.getText()), AudProxyPort.getText());
        System.out.println("config: aud port " + AudProxyPort.getText());
    }
    } catch (Exception ex) {
        // don't care
    }

}

}
