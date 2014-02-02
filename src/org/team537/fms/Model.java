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

    boolean running = false;

    public Model() throws Exception 
    {
        blue = new Alliance(true);
        red = new Alliance(false);
        matchTimer = null;
        start();
    }

    public AllianceTeam getTeam(boolean isBlue, int slot)
    {
        return isBlue ? blue.getTeam(slot) : red.getTeam(slot);
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
            try {
                sock.receive(pkt);
                goodData = true;
            } catch (Exception ex) {
                System.err.println("Model: listen thread: unknown: " + ex);
            }
            // System.out.println("good: " + goodData + " recieved bytes: " + pkt.getLength());
            if (goodData) {
                Robot robot = new Robot(data);
                System.out.println(robot);
                pkt.setLength(data.length);
                if (robot.isBlue())
                    blue.update(robot);
                else
                    red.update(robot);
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
    }

    public void setMatchNumber(Integer mNum)
    {
        mPanel.setMatch(mNum);
    }

    public void startMatch()
    {
        blue.startMatch(true);
        red.startMatch(true);
        mPanel.setTime(matchTime);
        matchTimer = new Timer();
        matchTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    matchTime--;
                    mPanel.setTime(matchTime);
                    if (0 == matchTime) {
                        stopMatch();
                        matchTimer.cancel();
                        matchTimer = null;
                    }
                    if (teleTime == matchTime) {
                        blue.startMatch(false);
                        red.startMatch(false);
                    }
                }
                }, 1000, 1000);
    }

    public void stopMatch()
    {
        blue.stopMatch();
        red.stopMatch();
        tPanel.stopMatch();
        matchTimer.cancel();
    }

}
