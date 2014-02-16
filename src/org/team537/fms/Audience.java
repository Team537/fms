package org.team537.fms;

import java.net.InetAddress;
import java.net.Socket;

import javax.swing.DefaultListModel;

// audience proxy
// The FRC FMS audience display uses MS Web Subscription Service for receiving match updates.
// This protocol is implemented over tcp/ip but is heavily MicroSoft specific and there currently
// exists no java implementation to interface with it.
// At https://github.com/Team537/fms-audience-proxy, exists code which implements the audience protocol
// and receives updates from this fms to broadcast to all audience display listeners.

public class Audience extends Thread
{
    boolean enabled;
    InetAddress addr;
    int port;
    boolean isAuto = false;;
    Model model;

    int matchTime, matchNumber;
    int blue[] = new int[3], red[] = new int[3];

    DefaultListModel queue;

    static final int MATCHTIME = 0;
    static final int MATCHNUMBER = 1;
    static final int STARTMATCH = 2;
    static final int STOPMATCH = 3;
    static final int ENABLED = 4;
    static final int DISABLED = 5;
    static final int B1 = 6;
    static final int B2 = 7;
    static final int B3 = 8;
    static final int R1 = 9;
    static final int R2 = 10;
    static final int R3 = 11;

    public Audience(Model model)
    {
        queue = new DefaultListModel();
        enabled = false;
        this.model = model;
    }

    public void setProxy(InetAddress addr, String port)
    {
        this.addr = addr;
        this.port = Integer.valueOf(port);
    }

    public void setEnabled(boolean en)
    {
        enabled = en;
        enq(en ? ENABLED : DISABLED);
    }

    public void setTime(int mTime)
    {
        matchTime = mTime;
        enq(MATCHTIME);
    }

    public void setMatchNumber(int mNum)
    {
        matchNumber = mNum;
        enq(MATCHNUMBER);
    }

    public void startMatch(boolean isAuto)
    {
        this.isAuto = isAuto;
        enq(STARTMATCH);
    }

    public void stopMatch(boolean isAuto)
    {
        this.isAuto = isAuto;
        enq(STOPMATCH);
    }

    public void setTeam(boolean isBlue, int slot, int teamNum)
    {
        if (isBlue)
            blue[slot] = teamNum;
        else
            red[slot] = teamNum;

        switch (slot) {
        case 0: enq(isBlue ? B1 : R1); break;
        case 1: enq(isBlue ? B2 : R2); break;
        case 2: enq(isBlue ? B3 : R3); break;
        }
    }

    private synchronized void enq(int req)
    {
        queue.addElement(new Integer(req));
    }

    private synchronized int deq()
    {
        return (Integer) queue.remove(0);
    }

    public void run()
    {
        boolean once = false;
        Socket sock = null;
        while (true) {
            if (0 == queue.getSize()) {
                try {
                    wait();
                } catch (Exception ex) {
                    // don't care
                }
            }
            int msg = deq();
            if (enabled) {
                if (null == sock) { 
                    try { 
                        sock = new Socket(addr, port);
                        once = false;
                    } catch (Exception ex) {
                        if (!once) 
                            model.Log("Audience: cannot re-connect to socket" + ex);
                        once = true;
                    }
                }
                String amsg = null;
                switch (msg) {
                case ENABLED: break;
                case MATCHTIME:     amsg = "time " + matchTime; break;
                case MATCHNUMBER:   amsg = "match " + matchNumber; break;
                case STARTMATCH:    amsg = (isAuto ? "auto-start" : "tele-start"); break;
                case STOPMATCH:     amsg = (isAuto ? "auto-end" : "tele-end"); break;
                case B1:            amsg = "b1 " + blue[0]; break;
                case B2:            amsg = "b2 " + blue[1]; break;
                case B3:            amsg = "b3 " + blue[2]; break;
                case R1:            amsg = "r1 " + red[0]; break;
                case R2:            amsg = "r2 " + red[1]; break;
                case R3:            amsg = "r3 " + red[2]; break;
                }
                if (null != amsg && null != sock) {
                    try { 
                        sock.getOutputStream().write(amsg.getBytes());
                    } catch (Exception ex) {
                        model.Log("Audience: cannot write to socket " + ex);
                        sock = null;
                    }
                }
            }
        }
    }
}
