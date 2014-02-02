package org.team537.fms;

public class Alliance extends Thread {
    private AllianceTeam[] team = new AllianceTeam[3];

    public Alliance(boolean isBlue) throws Exception
    {
        for (int i = 0; i < 3; i++) {
            team[i] = new AllianceTeam(isBlue);
        }
        start();
    }

    public AllianceTeam getTeam(int slot)
    {
        return team[slot];
    }

    public void run()
    {
        while (true) {
            int i = 0;
            try {
                for ( ; i < 3; i++) {
                    team[i].update(i);
                }
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
}
