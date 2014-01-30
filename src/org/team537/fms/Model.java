package org.team537.fms;

public class Model extends Thread
{
    private Alliance blue, red;

    boolean running = false;

    public Model() throws Exception 
    {
        blue = new Alliance(true);
        red = new Alliance(false);
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
                sock.receive(p);
                goodData = true;
            } catch (InterruptedException iex) {
                System.err.println("Model: listen thread interrupted: " + iex);
            } catch (Exception ex) {
                System.err.println("Model: listen thread: unknown: " + ex);
            }
            if (goodData) {
                Robot robot = new Robot(data);
                p.setLength(data.length);
                if (robot.isBlue())
                    blue.update(robot);
                else
                    red.update(robot);
            }
        }
    }

}
