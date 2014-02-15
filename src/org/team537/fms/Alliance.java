package org.team537.fms;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class Alliance extends Thread {
    private AllianceTeam[] team = new AllianceTeam[3];

    public JLabel alliance_ready;
    private ImageIcon readyRed, readyGreen;


    public Alliance(boolean isBlue) throws Exception
    {
        try {
            BufferedImage red, green;
            if (isBlue) {
                red = ImageIO.read(getClass().getResource("/images/blueTeam-red.png"));
                green = ImageIO.read(getClass().getResource("/images/blueTeam-green.png"));
            } else {
                red = ImageIO.read(getClass().getResource("/images/redTeam-red.png"));
                green = ImageIO.read(getClass().getResource("/images/redTeam-green.png"));
            }
            readyRed = new ImageIcon(red, "NotReady");
            readyGreen = new ImageIcon(green, "Ready");
        } catch (Exception ex) {
            System.err.println("Alliance: " + ex);
            throw(new Exception("Alliance: constuction during load", ex));
        }

        alliance_ready = new JLabel( readyRed );

        for (int i = 0; i < 3; i++) {
            team[i] = new AllianceTeam(isBlue);
        }
        start();
    }

    public AllianceTeam getTeam(int slot)
    {
        return team[slot];
    }

    public void checkForReady()
    {
        boolean ready = true;
        for (int i = 0; i < 3; i++) {
            ready = ready & team[i].getRobotEnabled();
        }
        alliance_ready.setIcon( ready ? readyGreen : readyRed );
    }

    public boolean readyForEndAuto()
    {
        boolean ready = true;
        for (int i = 0; i < 3; i++) {
            ready = ready & team[i].getRobotAckDisable();
        }
        return ready;
    }

    public boolean readyForTeleop()
    {
        boolean ready = true;
        for (int i = 0; i < 3; i++) {
            ready = ready & team[i].getRobotAckTele();
        }
        return ready;
    }

    public void setTeleop()
    {
        for (int i = 0; i < 3; i++) {
            team[i].setTeleop();
        }
    }

    public void run()
    {
        while (true) {
            int i = 0;
            try {
                for ( ; i < 3; i++) {
                    team[i].update(i);
                }
                checkForReady();
                Thread.sleep(500);
            } catch (InterruptedException iex) {
                System.err.println("AllianceTeam: [" + i + "] " + team[i].getTeam() + ": timeout: " + iex);
            } catch (Exception ex) {
                System.err.println("Robot: [" + i + "] " + team[i].getTeam() + ": exception: " + ex);
                ex.printStackTrace();
            }
        }
    }

    public void update(Robot robot)
    {
        for (int i = 0; i < 3; i++) {
            if (robot.getTeam() == team[i].getTeam()) {
                team[i].update(robot);
                break;
            }
        }
        checkForReady();
    }

    public void startMatch(boolean auto)
    {
        // block update
        for (int i = 0; i < 3; i++) {
            team[i].setEnabled(auto);
        }
        // enabled update
    }

    public void stopMatch()
    {
        // block update
        for (int i = 0; i < 3; i++) {
            team[i].setDisabled();
        }
        // enabled update
    }

    public void reset()
    {
        for (int i = 0; i < 3; i++) {
            team[i].reset();
        }
    }
}
