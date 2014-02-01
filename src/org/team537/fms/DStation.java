package org.team537.fms;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.zip.CRC32;

public class DStation
{
    int packet = 0;
    int team = 0;
    InetAddress addr;
    DatagramSocket sock;
    CRC32 checksum = new CRC32();
    byte[] version;
    boolean enabled, valid;
    byte state;
    byte color;
    byte station;
    String teamAddr, dsmac;
    int misscount, pktcount;
    float avgrtt;
    float volts;

    public DStation() throws Exception 
    {
        try {
            sock = new DatagramSocket();
        } catch (SocketException ex) {
            System.err.println("DSCommand: " + ex);
            throw ex;
        }
        reset();
    }

    public void reset() 
    {
        packet = 0;
        state = 'S';
        enabled = false;
        valid = false;
    }

    public DStation(byte[] data)
    {
        checksum.reset();
        checksum.update(data);
        packet = (data[1] << 8) + data[0];
        state = data[2];
        color = data[3];
        station = data[4];
        
        for (int i = 0; i < version.length; i++)
            version[i] = data[i + 18];

        checksum.reset();
        checksum.update(data);
    }

    private float b2v(byte b1, byte b2)
    {
        int ix;
        int dec;
        ix = ((b1 >> 4) * 10) + (b1 & 0x0f);
        dec = ((b2 >> 4) * 10) + (b2 & 0x0f);
        return ix + ((float) dec) / 100;
    }

    public void setTeam(int iteam) throws Exception
    {
        if ((valid && team == iteam) || (!valid && 0 == iteam))
            // already have set valid team number
            // iteam is not a valid  
            return;
        reset();
        team = iteam;
        int upper = team / 100;
        int lower = team % 100;
        teamAddr = "10." + upper + "." + lower + ".5";
        valid = false;
        try {
            addr = InetAddress.getByName(teamAddr);
        } catch (Exception ex) {
            System.err.println("Robot.setTeam: " + iteam + ": " + ex);
            throw ex;
        }
        valid = true;
    }

    public void setState(boolean tele, boolean enable)
    {
        state = 'S';            // auto
    }

    public void setAuto()
    {
        state = 'S';            // auto
        if (enabled) state += 0x20;
    }

    public void setTeleop()
    {
        state = 'C';            // teleop
        if (enabled) state += 0x20;
    }

    public void setEnabled()
    {
        if (!enabled) {
            enabled = !enabled;
            state += 0x20;
        }
    }

    public void setDisabled()
    {
        if (enabled) {
            enabled = !enabled;
            state -= 0x20;
        }
    }

    public void setStation(boolean isBlue, int pos)
    {
        color = (byte) (isBlue ? 'B' : 'R');
        station = (byte) ('1' + pos);
    }

    public void setVersion(String vers) throws Exception
    {
        byte[] temp = vers.getBytes("US-ASCII");
        version = new byte[8];
        for (int i = 0; i < 8; i++)
            version[i] = (i < temp.length) ? temp[i] : 0;
    }

    private DatagramPacket buildPacket()
    {
        byte[] data = new byte[50];

        for (int i = 0; i < data.length; i++)
            data[i] = 0;

        packet++;                       // Packet number
        data[0] = (byte) (0xff & (packet >> 8));
        data[1] = (byte) (0xff &  packet);

        data[2] = state;                // Robot state

        data[4] = (byte) (team / 100);
        data[5] = (byte) (team % 100);

        // ip-addr
        data[6] = 10;
        data[7] = (byte) (team / 100);
        data[8] = (byte) (team % 100);
        data[9] = 5;

        data[10] = color;
        data[11] = station;

        // mac addr
        for (int i = 0; i < 6; i++)
            data[i + 12] = (byte) 0xff;

        for (int i = 0; i < 8; i++)
            data[i + 18] = version[i];

        data[26] = (byte) (0xff & (misscount >> 8));
        data[27] = (byte) (0xff &  misscount);
        data[28] = (byte) (0xff & (pktcount >> 8));
        data[29] = (byte) (0xff &  pktcount);

        int avgms = (int) (1000 * avgrtt);
        data[30] = (byte) (0xff & (avgms >> 8));
        data[31] = (byte) (0xff &  avgms);

        for (int i = 0; i < 6; i++)
            data[i + 34] = (byte) 0xff;

        int ivolt = (int) (100 * volts);
        data[41] = (byte) (ivolt % 10);
        ivolt = ivolt / 10;
        data[41] |= (byte) (ivolt % 10) << 4;
        ivolt = ivolt / 10;
        data[40] = (byte) (ivolt % 10);
        ivolt = ivolt / 10;
        data[40] |= (byte) (ivolt % 10) << 4;

        checksum.reset();
        checksum.update(data);

        byte[] crc = ByteBuffer.allocate(4).putInt((int)checksum.getValue()).array();

        data[70] = crc[0];
        data[71] = crc[1];
        data[72] = crc[2];
        data[73] = crc[4];

        return new DatagramPacket(data, data.length, addr, 1120);
    }

    public void update() throws Exception
    {
        if (!valid)
            return;
        sock.send( buildPacket() );
    }

    public boolean isBlue()
    {
        return 'B' == color;
    }

    public int getTeam()
    {
        return team;
    }

    public String getTeamAddr()
    {
        return teamAddr;
    }

    public String getTeamMac()
    {
        return dsmac;
    }

    public String getVersion()
    {
        return new String(version);
    }

    public int getPacketCount()
    {
        return pktcount;
    }

    public int getMisses()
    {
        return misscount;
    }

    public float getRTT()
    {
        return avgrtt;
    }

    public float getVolts()
    {
        return volts;
    }
}
