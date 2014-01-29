package org.team537.fms;

import org.team537.fms.AllianceTeam;

public class Model
{
    private AllianceTeam[] blue = new AllianceTeam[3];
    private AllianceTeam[] red = new AllianceTeam[3];

    public Model()
    {
        for (int i = 0; i < 3; i++) {
            blue[i] = new AllianceTeam();
            red[i] = new AllianceTeam();
        }
    }

    public AllianceTeam getTeam(boolean isBlue, int slot)
    {
        return isBlue ? blue[slot] : red[slot];
    }
}
