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
        try {
            for (int i = 0; i < 3; i++) {
                team[i].update();
            }
            Thread.sleep(500);
        } catch (InterruptedException iex) {
            System.err.println("AllianceTeam: " + team + ": timeout: " + iex);
        } catch (Exception ex) {
            System.err.println("Robot: " + team + ": exception: " + ex);
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
}
