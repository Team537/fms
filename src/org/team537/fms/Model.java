package org.team537.fms;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Timer;
import java.util.TimerTask;

import org.team537.fms.ui.MatchPanel;
import org.team537.fms.ui.TabPanel;

public class Model extends Thread
{
    private Alliance blue, red;
    private Timer matchTimer;
    private int matchTime, teleTime;
    private MatchPanel mPanel;
    private TabPanel tPanel;
    private Audience aud;

    boolean running = false;
    int matchState = 0, counterTime;

    public Model() throws Exception 
    {
        blue = new Alliance(true);
        red = new Alliance(false);
        matchTimer = null;
        aud = new Audience(this);
        start();
    }

    public AllianceTeam getTeam(boolean isBlue, int slot)
    {
        return isBlue ? blue.getTeam(slot) : red.getTeam(slot);
    }
    
    public Alliance getAlliance(boolean isBlue)
    {
        return isBlue ? blue : red;
    }

    public void run()
    {
        DatagramSocket sock;
        try {
            sock = new DatagramSocket(1160);
        } catch (SocketException ex) {
            System.err.println("Model: listenSocket: " + ex);
            return;
        }

        byte[] data = new byte[50];
        DatagramPacket pkt = new DatagramPacket(data, data.length);
        while (true) {
            boolean goodData = false;
            long tstamp = 0;
            try {
                sock.receive(pkt);
                tstamp = System.currentTimeMillis();
                goodData = true;
            } catch (Exception ex) {
                System.err.println("Model: listen thread: unknown: " + ex);
            }
            // System.out.println("good: " + goodData + " recieved bytes: " + pkt.getLength());
            if (goodData) {
                Robot robot = new Robot(data);
                robot.setTimeStamp(tstamp);
                // System.out.println(robot);
                pkt.setLength(data.length);
                if (robot.isBlue())
                    blue.update(robot, aud);
                else
                    red.update(robot, aud);
            }
        }
    }

    public void setMatchPanel(MatchPanel mp)
    {
        mPanel = mp;
    }

    public void setTabPanel(TabPanel tp)
    {
        tPanel = tp;
    }

    public void setMatchTime(Integer aTime, Integer tTime)
    {
        matchTime = aTime + tTime;
        teleTime = tTime;
        mPanel.setTime(matchTime);
        aud.setTime(matchTime);
    }

    public void setMatchNumber(Integer mNum)
    {
        mPanel.setMatch(mNum);
        aud.setMatchNumber(mNum);
    }

    // start:       auto disabled
    // startMatch:  auto enabled
    // auto-end:    auto disabled
    // switch:      tele disabled
    // tele-op:     tele enabled
    // stopMatch:

    private void runMatch()
    {
        System.out.println(String.format("match: %1$d  count: %2$5d  match: %3$5d", 
                    matchState, counterTime, matchTime));
        try {
        switch (matchState) {
        case 0:                 // Auto Enabled
            if (0 == counterTime-- % 4) {
                matchTime--;
                mPanel.setTime(matchTime);
                if (teleTime == matchTime) {
                    blue.stopMatch();
                    red.stopMatch();
                    aud.stopMatch(true);
                    matchState = 1;
                }
            }
            break;
        case 1:                 // Auto Complete
            // wait for all robots to ack disable state
            // and set teleop 
            if (blue.readyForEndAuto() && red.readyForEndAuto()) {
                blue.setTeleop();
                red.setTeleop();
                matchState = 2;
            }
            break;
        case 2:                 // Tele Op Wait
            // wait for all robots to ack teleop state
            // and set enabled 
            if (blue.readyForTeleop() && red.readyForTeleop()) {
                blue.startMatch(false);
                red.startMatch(false);
                aud.startMatch(false);
                matchState = 3;
            }
            break;
        case 3:                 // Tele Enabled
            if (0 == counterTime-- % 4) {
                matchTime--;
                mPanel.setTime(matchTime);
                if (0 == matchTime) {
                    stopMatch();
                    aud.stopMatch(false);
                    matchTimer.cancel();
                    matchTimer = null;
                    matchState = 0;
                }
            }
            break;
        }
        } catch (Exception ex) {
            System.err.println("runMatch: caught " + ex);;
            ex.printStackTrace();
        }
    }

    public void startMatch()
    {
        blue.startMatch(true);
        red.startMatch(true);
        aud.startMatch(true);
        mPanel.setTime(matchTime);
        matchTimer = new Timer();
        counterTime = 4 * matchTime;
        matchTimer.schedule(new TimerTask() {
            @Override
            public void run() { runMatch(); } }, 250, 250);
    }

    public void stopMatch()
    {
        matchTimer.cancel();
        blue.stopMatch();
        red.stopMatch();
        tPanel.stopMatch();
    }

    public void reset()
    {
        matchTimer.cancel();
        matchState = 0;
        blue.reset();
        red.reset();
    }

    public void Log(String message)
    {
        tPanel.Log(message);
    }

    public Audience getAudience()
    {
        return aud;
    }
}
