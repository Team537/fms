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

    public Robot() throws Exception 
    {
        try {
            sock = new DatagramSocket();
        } catch (SocketException ex) {
            System.err.println("DSCommand: " + ex);
            throw ex;
        }
        state = 'S';
        enabled = false;
        valid = false;
    }

    public Robot(byte[] data)
    {
        checksum.reset();
        checksum.update(data);
        packet = (data[0] << 8) + data[1];
        state = data[2];
        team = (data[4] * 100) + data[5];
        teamAddr = data[6] + "." + data[7] + "." + data[8] + "." + data[9];
        color = data[10];
        station = data[11];
        dsmac = data[12] + ":" + data[13] + ":" + data[14] + ":" + data[15] + ":" + data[16] + ":" + data[17];
        for (int i = 0; i < 8; i++)
            version[i] = data[i + 18];
        misscount = (data[26] << 8) + data[27];
        pktcount = (data[28] << 8) + data[29];
        avgrtt = ((data[30] << 8) + data[31]) / 1000;           // time in ms
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
        data[73] = crc[4];

        return new DatagramPacket(data, data.length, addr, 1120);
    }

    void update() throws Exception
    {
        if (!valid)
            return;
        sock.send( buildPacket() );
    }
}
