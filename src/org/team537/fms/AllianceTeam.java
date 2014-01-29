package org.team537.fms;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

public class AllianceTeam {
    JFormattedTextField teamNum;
    JCheckBox bypass;
    JCheckBox dq;
    // DS
    JLabel sum_dslink;
    JLabel sum_es;
    // Robot
    JLabel sum_rlink;
    JLabel sum_renabled;

    JLabel dslink;
    JLabel incomp;
    JLabel pc;
    JLabel scc_es;
    boolean scc_estop = false;;
    JLabel ds_es;
    boolean ds_estop = false;;
    JLabel ds_enabled;

    JLabel rlink;
    JLabel auto;
    JLabel renabled;
    JLabel unused;

    private ImageIcon redIcon;
    private ImageIcon greenIcon;

    public AllianceTeam() throws Exception
    {
        try {
            BufferedImage red = ImageIO.read(getClass().getResource("/images/red-icon.png"));
            BufferedImage green = ImageIO.read(getClass().getResource("/images/green-icon.png"));
            redIcon = new ImageIcon(red, "red");
            greenIcon = new ImageIcon(green, "green");
        } catch (Exception ex) {
            System.err.println("AlliancePanel: " + ex);
            throw(ex);
        }
        teamNum = new JFormattedTextField(new MaskFormatter("####"));
        teamNum.setEditable(true);
        teamNum.setText("0000");

        bypass = new JCheckBox("Bypass");
        dq = new JCheckBox("DQ");

        sum_dslink = new JLabel(redIcon);
        sum_dslink.setToolTipText("DS Link");
        dslink = new JLabel(redIcon);
        dslink.setToolTipText("DS Link");

        sum_es = new JLabel(greenIcon);
        sum_es.setToolTipText("E-Stop");
        scc_es = new JLabel(greenIcon);
        scc_es.setToolTipText("SCC E-Stop");
        ds_es = new JLabel(greenIcon);
        ds_es.setToolTipText("DS E-Stop");

        sum_rlink = new JLabel(redIcon);
        sum_rlink.setToolTipText("Robot Link");
        rlink = new JLabel(redIcon);
        rlink.setToolTipText("Robot Link");

        sum_renabled = new JLabel(redIcon);
        sum_renabled.setToolTipText("Enabled");
        ds_enabled = new JLabel(redIcon);
        ds_enabled.setToolTipText("Enabled");
        renabled = new JLabel(redIcon);
        renabled.setToolTipText("Enabled");

        auto = new JLabel(redIcon);
        auto.setToolTipText("Autonomous");

        unused = new JLabel(greenIcon);
        unused.setToolTipText("Unused");

        incomp = new JLabel(redIcon);
        incomp.setToolTipText("In Competition Mode");

        pc = new JLabel(redIcon);
        pc.setToolTipText("PC Connection State");
    }

    public void setDSlink(boolean link)
    {
        sum_dslink.setIcon(link ? greenIcon : redIcon);
        dslink.setIcon(link ? greenIcon : redIcon);
    }

    public void setSCCestop(boolean estop)
    {
        scc_estop = estop;
        scc_es.setIcon(scc_estop ? redIcon : greenIcon);
        sum_es.setIcon(scc_estop || ds_estop ? redIcon : greenIcon);
    }

    public void setDSestop(boolean estop)
    {
        ds_estop = estop;
        ds_es.setIcon(ds_estop ? redIcon : greenIcon);
        sum_es.setIcon(scc_estop || ds_estop ? redIcon : greenIcon);
    }

    public void setRobotLink(boolean link)
    {
        sum_rlink.setIcon(link ? greenIcon : redIcon);
        rlink.setIcon(link ? greenIcon : redIcon);
    }

    public void setRobotEnabled(boolean en)
    {
        sum_renabled.setIcon(en ? greenIcon : redIcon);
        renabled.setIcon(en ? greenIcon : redIcon);
    }

    public void setFMSip(String ip)
    {
    }

    public void setDSip(String ip)
    {
    }

    public void setDSmac(String mac)
    {
    }

    public void setCompetition(boolean comp)
    {
        incomp.setIcon(comp ? greenIcon : redIcon);
    }

    public void setPCstate(boolean state)
    {
        pc.setIcon(state ? greenIcon : redIcon);
    }

    public void setRobotAuto(boolean state)
    {
        auto.setIcon(state ? greenIcon : redIcon);
    }

};

