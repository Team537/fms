package org.team537.fms;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.util.zip.CRC32;

public class Robot
{
    int packet = 0;
    int team = 0;
    InetAddress addr;
    static DatagramSocket sock;
    CRC32 checksum = new CRC32();
    byte[] version;
    boolean enabled, valid;
    byte state;
    byte color;
    byte station;
    String teamAddr, dsmac;
    int misscount, pktcount, avgrtt;
    float volts;

    static {
        try {
            sock = new DatagramSocket(1120);
        } catch (SocketException ex) {
            System.err.println("Robot socket: " + ex);
        }
    }

    public Robot() throws Exception 
    {
        reset();
    }

    public void reset() 
    {
        packet = 0;
        state = 'S';
        enabled = false;
        valid = false;
    }

    // state:
    //  64: 0x40
    //  94: 0x5e

    public Robot(byte[] data)
    {
        checksum.reset();
        checksum.update(data);
        packet = 0xffff & ((data[0] << 8) + data[1]);
        state = (byte) (0xff & data[2]);
        team = ((0xff & data[4]) * 100) + (0xff & data[5]);
        teamAddr = Byte.valueOf(data[6]) + "." + (0xff & data[7]) + "." + (0xff & data[8]) + "." + (0xff & data[9]);
        color = data[10];
        station = data[11];
        dsmac = (0xff & data[12]) + ":" 
                + (0xff & data[13]) + ":" 
                + (0xff & data[14]) + ":" 
                + (0xff & data[15]) + ":" 
                + (0xff & data[16]) + ":" 
                + (0xff & data[17]);
        version = new byte[8];
        for (int i = 0; i < 8; i++)
            version[i] = data[i + 18];
        misscount = 0xffff & ((data[26] << 8) + data[27]);
        pktcount = 0xffff & ((data[28] << 8) + data[29]);
        avgrtt = 0xffff & ((data[30] << 8) + data[31]);           // time in ms
        volts = b2v(data[40], data[41]);
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
        if (0 == team)
            return;
        valid = true;
    }

    public void setState(boolean tele, boolean enable)
    {
        state = 'S';            // auto
    }

    public void setAuto()
    {   
        state = 'S';            // auto   0x53
        if (enabled) state += 0x20;  //   0x73
    }

    public void setTeleop()
    {
        state = 'C';            // teleop  0x43
        if (enabled) state += 0x20;    //  0x63
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
        byte[] data = new byte[74];

        for (int i = 0; i < data.length; i++)
            data[i] = 0;

        packet++;                       // Packet number
        data[0] = (byte) (0xff &  packet);
        data[1] = (byte) (0xff & (packet >> 8));

        data[2] = state;                // Robot state

        data[3] = color;                // Alliance color
        data[4] = station;              // Alliance station

        for (int j = 0; j < version.length; j++)
            data[18 + j] = version[j];

        checksum.reset();
        checksum.update(data);

        byte[] crc = ByteBuffer.allocate(4).putInt((int)checksum.getValue()).array();

        data[70] = crc[0];
        data[71] = crc[1];
        data[72] = crc[2];
        data[73] = crc[3];

        return new DatagramPacket(data, data.length, addr, 1120);
    }

    public void update() throws Exception
    {
        if (!valid) {
            // clear previous state
            return;
        }
        DatagramPacket pkt = buildPacket();
        sock.send( pkt );
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

    public int getRTT()
    {
        return avgrtt;
    }

    public float getVolts()
    {
        return volts;
    }

    public String getStatus() 
    {
        char cs = (char) state;
        StringBuffer sb = new StringBuffer();
        sb.append(state).append(" 0x").append(Integer.toHexString(cs));
        return sb.toString();
    }

    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("Robot: packet: ").append(packet);
        sb.append(" state: ").append(state);
        sb.append(" team: ").append(team);
        sb.append(" Addr: ").append(teamAddr);
        sb.append(" color: ").append(Integer.toHexString(Byte.valueOf(color).intValue()));
        sb.append(" station: ").append(station);
        sb.append(" mac: ").append(dsmac);
        sb.append(" version: ").append(new String(version));
        sb.append(" miscnt: ").append(misscount);
        sb.append(" pkts: ").append(pktcount);
        sb.append(" avgrtt: ").append(avgrtt);
        sb.append(" volts: ").append(volts);
        return sb.toString();
    }
}
