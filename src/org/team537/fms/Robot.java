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
    int lastPacket;
    int team = 0;
    InetAddress addr;
    static DatagramSocket sock;
    CRC32 checksum = new CRC32();
    byte[] version;
    boolean enabled, valid;
    byte state, rstate;
    byte color;
    byte station;
    String teamAddr, dsmac;
    int misscount, pktcount, avgrtt;
    float volts;
    int dsMissCount;
    long tstamp;
    Stats davgrtt;

    static {
        try {
            sock = new DatagramSocket(1120);
        } catch (SocketException ex) {
            System.err.println("Robot socket: " + ex);
        }
    }

    public Robot() throws Exception 
    {
        davgrtt = new Stats();
        reset();
    }

    public void reset() 
    {
        packet = 0;
        state = 'W';
        enabled = false;
        valid = false;
        dsMissCount = 0;
        davgrtt.clear();
    }

    // state:   when using 'S'/'C' as auto/tele-op  set-state
    //  64: 0x40        no radio / no crio / ds
    //  68: 0x44           radio / no crio / ds
    //  78: 0x4e           radio /    crio / ds   tele-op disabled
    //  94: 0x5e           radio /    crio / ds   auto disabled
    // 110: 0x6e           radio /    crio / ds   tele-op enabled
    // 126: 0x7e           radio /    crio / ds   auto enabled

    // state:   when using 'W'/'G' as auto/tele-op  set-state
    //  64: 0x40        no radio / no crio / ds
    //  68: 0x44           radio / no crio / ds
    //  78: 0x4e           radio /    crio / ds   tele-op disabled
    // 206: 0xce           recieved after auto-disable sent
    // 222: 0xde           radio /    crio / ds   auto disabled
    // 238: 0xee           radio /    crio / ds   tele-op enabled
    // 254: 0xfe           radio /    crio / ds   auto enabled

    public Robot(byte[] data)
    {
        checksum.reset();
        checksum.update(data);
        packet = b2i(data[0], data[1]);
        state = (byte) (0x0ff & data[2]);
        team = ((0x0ff & data[4]) * 100) + (0x0ff & data[5]);
        teamAddr = (0x0ff & data[6]) + "." + (0x0ff & data[7]) + "." + (0x0ff & data[8]) + "." + (0x0ff & data[9]);
        color = data[10];
        station = data[11];
        dsmac = toHex(data[12]) + ":" + toHex(data[13]) + ":" + toHex(data[14]) + ":" 
                + toHex(data[15]) + ":" + toHex(data[16]) + ":" + toHex(data[17]);
        version = new byte[8];
        for (int i = 0; i < 8; i++)
            version[i] = data[i + 18];
        misscount = b2i(data[26], data[27]);
        pktcount = b2i(data[28], data[29]);
        avgrtt = b2i(data[30], data[31]);                  // time in ms
        volts = b2v(data[40], data[41]);
    }

    private int b2i(byte b1, byte b2)
    {
        return 0xffff & (((0x0ff & b1) << 8) + (0x0ff & b2));
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
        state = 'W';            // auto
    }

    public void setReturnState(byte st) 
    {
        rstate = st;
    }

    public void setAuto()
    {   
        state = 'W';            // auto   0x57
        if (enabled) state += 0x20;  //   0x77
    }

    public void setTeleop()
    {
        state = 'G';            // teleop  0x47
        if (enabled) state += 0x20;    //  0x67
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

    public void setLastPacket(int number)
    {
        lastPacket = number;
    }

    public void setTimeStamp(long ts)
    {
        tstamp = ts;
    }

    public void setRoundTrip(long ts)
    {
        davgrtt.add(ts - tstamp);
    }

    public void incrementMissCount()
    {
        dsMissCount++;
    }

    private DatagramPacket buildPacket()
    {
        byte[] data = new byte[74];

        for (int i = 0; i < data.length; i++)
            data[i] = 0;

        packet = (packet + 1) % 65535;  // Packet number
        data[0] = (byte) (0xff & (packet >> 8));
        data[1] = (byte) (0xff &  packet);

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
            return;
        }
        DatagramPacket pkt = buildPacket();
        sock.send( pkt );
        tstamp = System.currentTimeMillis();
    }

    public boolean isBlue()
    {
        return 'B' == color;
    }

    public int getPacketNumber()
    {
        return packet;
    }

    public int getLastPacket()
    {
        return lastPacket;
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

    public int getDSMisses()
    {
        return dsMissCount;
    }

    public int getRTT()
    {
        return avgrtt;
    }

    public double getDSRTT()
    {
        return davgrtt.Mean();
    }

    public float getVolts()
    {
        return volts;
    }

    public boolean doesDSseeRadio()
    {
        return 0x04 == (0x04 & state);
    }

    public boolean doesDSseeCRio()
    {
        return 0x0a == (0x0a & state);
    }

    public boolean isEnabled()
    {
        return 0x20 == (0x20 & state);
    }

    public boolean isAuto()
    {
        return 0x10 == (0x10 & state);
    }

    public long getTimeStamp()
    {
        return tstamp;
    }

    public boolean isValid()
    {
        return valid;
    }

    public String getStatus() 
    {
        return String.format("0x%1$2x", 0x0ff & state);
    }

    public byte getState()
    {
        return state;
    }

    public boolean getRobotAckDisable()
    {
        return 0x20 != (0x20 & rstate);
    }

    public boolean getRobotAckTele()
    {
        return 0x10 == (0x10 & rstate);
    }

    private String toHex(byte val) {
        return Integer.toHexString(0x00ff & val);
    }

    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("Robot: packet: ").append(packet);
        sb.append(" state: ").append(toHex(state));
        sb.append(" team: ").append(team);
        sb.append(" Addr: ").append(teamAddr);
        sb.append(" color: ").append(toHex(state));    // Integer.toHexString(Byte.valueOf(color).intValue()));
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
