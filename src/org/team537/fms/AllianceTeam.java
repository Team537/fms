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
    public JFormattedTextField teamNum;
    public JCheckBox bypass;
    public JCheckBox dq;
    // DS
    public JLabel sum_dslink;
    public JLabel sum_es;
    // Robot
    public JLabel sum_rlink;
    public JLabel sum_renabled;

    // DS
    public JLabel dslink;
    public JLabel incomp;
    public JLabel pc;
    public JLabel scc_es;
    boolean scc_estop = false;;
    public JLabel ds_es;
    boolean ds_estop = false;;
    public JLabel ds_enabled;
    public JLabel dmissCount;
    public JLabel davgrtt;

    // Robot
    public JLabel rlink;
    public JLabel auto;
    public JLabel renabled;
    public JLabel unused;

    public JLabel ipaddr;
    public JLabel macaddr;
    public JLabel version;
    public JLabel missCount;
    public JLabel pktCount;
    public JLabel avgrtt;
    public JLabel volts;
    public JLabel status;

    private ImageIcon redIcon;
    private ImageIcon greenIcon;

    private Robot robot;

    boolean enabled;
    boolean isBlue = false;
    boolean first = true;
    int thisNum = 0;

    public AllianceTeam(boolean blue) throws Exception
    {
        isBlue = blue;
        try {
            BufferedImage red = ImageIO.read(getClass().getResource("/images/red-icon.png"));
            BufferedImage green = ImageIO.read(getClass().getResource("/images/green-icon.png"));
            redIcon = new ImageIcon(red, "red");
            greenIcon = new ImageIcon(green, "green");
        } catch (Exception ex) {
            System.err.println("AllianceTeam: " + ex);
            throw(new Exception("AllianceTeam constructor load", ex));
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

        incomp = new JLabel(greenIcon);
        incomp.setToolTipText("In Competition Mode");

        pc = new JLabel(redIcon);
        pc.setToolTipText("PC Connection State");

        ipaddr = new JLabel("10.x.x.5");
        macaddr = new JLabel("ff:ff:ff:ff:ff:xx");
        version = new JLabel("xxxxxxxx");
        missCount = new JLabel("0");
        pktCount = new JLabel("0000");
        avgrtt = new JLabel("000");
        volts = new JLabel("00.00");
        status = new JLabel("-?-");
        dmissCount = new JLabel("0");
        davgrtt = new JLabel("000");

        robot = new Robot();
        robot.setVersion("xxxxxxxx");
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
        enabled = link;
    }

    public void setRobotEnabled(boolean en, boolean auto)
    {
        sum_renabled.setIcon(en ? greenIcon : redIcon);
        ds_enabled.setIcon(en ? greenIcon : redIcon);
        renabled.setIcon(en ? greenIcon : redIcon);
    }

    public boolean getRobotEnabled()
    {
        return enabled || bypass.isSelected();
    }

    public void setFMSip(String ip)
    {
    }

    public void setDSip(String ip)
    {
        ipaddr.setText(ip);
    }

    public void setDSmac(String mac)
    {
        macaddr.setText(mac);
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

    public void setVersion(String vers) 
    {
        version.setText(vers);
    }

    public void setMisses(int cnt)
    {
        missCount.setText(String.format("%1$5d", cnt));
    }

    public void setDSMisses(int cnt)
    {
        dmissCount.setText(String.format("%1$5d", cnt));
    }

    public void setPacketCount(int cnt)
    {
        pktCount.setText(String.format("%1$5d", cnt));
    }

    public void setRTT(int rtt)
    {
        avgrtt.setText(String.format("%1$5d", rtt));
    }

    public void setDSRTT(double rtt)
    {
        davgrtt.setText(String.format("%1$8.3f", rtt));
    }

    public void setVolts(float val)
    {
        volts.setText(String.format("%1$8.3f", val));
    }

    public void setStatus(String stat) 
    {
        status.setText(stat);
    }
    
    public void setEnabled(boolean auto)
    {
        if (auto)
            robot.setAuto();
        else
            robot.setTeleop();
        robot.setEnabled();
    }

    public void setDisabled()
    {
        robot.setDisabled();
    }

    public int getTeam()
    {
        int num = 0;
        try {
            num = Integer.parseInt( teamNum.getText() );
            // System.out.println("getTeam " + num);
        } catch (Exception ex) {
            System.err.println("ATeam: getTeam: " + ex);
        }
        return num;
    }

    public void update(int i) throws Exception
    {
        int num = getTeam();
        if (false && ((0 == i) || (thisNum != num))) {
            System.out.println("updating " + (isBlue ? "Blue Team [" : "Red  Team [")
                        + i + "]: " + num);
            first = false;
            thisNum = num;
        }
        robot.setTeam( num );
        int lastSent = robot.getPacketNumber();
        int lastRecv = robot.getLastPacket();
        if (robot.isValid() && (lastSent != lastRecv)) {
            System.out.println("got pkt " + lastRecv + " looking for " + lastSent);
            setDSlink(false);
            setPCstate(false);
            robot.incrementMissCount();
            setDSMisses( robot.getDSMisses() );
        }
        if (!robot.isValid()) {
            setDSip( "10.x.x.5" );
            setDSmac( "ff:ff:ff:ff:ff:xx" );
            setVersion( "xxxxxxxx" );
            setMisses( 0 );
            setPacketCount( 0 );
            setRTT( 0 );
            setVolts((float) 0.0 );
            setStatus( "-?-" );
            setDSlink( false );
            setRobotLink( false );
            setPCstate( false );
            setRobotAuto( false );
            setRobotEnabled( false, false );
            setDSRTT( 0.0 );
        }
        robot.setStation(isBlue, i);
        robot.update();
    }

    public void update(Robot iRobot) 
    {
        // System.out.println("recieved packet: " + iRobot.toString());
        setDSip( iRobot.getTeamAddr() );
        setDSmac( iRobot.getTeamMac() );
        setVersion( iRobot.getVersion() );
        setMisses( iRobot.getMisses() );
        setPacketCount( iRobot.getPacketCount() );
        setRTT( iRobot.getRTT() );
        setVolts( iRobot.getVolts() );
        setStatus( iRobot.getStatus() );
        setDSlink( true );                              // i got this packet - ds alive
        setRobotLink( iRobot.doesDSseeCRio() );
        setPCstate( iRobot.doesDSseeRadio() );
        setRobotAuto( iRobot.isAuto() );
        setRobotEnabled( iRobot.isEnabled(), iRobot.isAuto() );
        robot.setLastPacket( iRobot.getPacketNumber() );
        robot.setRoundTrip( iRobot.getTimeStamp() );
        setDSRTT( robot.getDSRTT() );
    }
};

