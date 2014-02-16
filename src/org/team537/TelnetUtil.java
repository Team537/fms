import java.io.InputStream;
import java.io.OutputStream;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

import org.apache.commons.net.telnet.TelnetClient;
import org.apache.commons.net.telnet.TelnetNotificationHandler;
import org.apache.commons.net.telnet.SimpleOptionHandler;
import org.apache.commons.net.telnet.EchoOptionHandler;
import org.apache.commons.net.telnet.TerminalTypeOptionHandler;
import org.apache.commons.net.telnet.SuppressGAOptionHandler;


public class TelnetUtil 
{
    String ipaddr;
    int portNum;
    TelnetClient client;

    class reader extends Thread
    {
        InputStream stream;
        ByteBuffer inputBuffer;

    public reader(InputStream str) 
    {
        stream = str;
        inputBuffer = ByteBuffer.allocate(10240);
        start();
    }

    synchronized public byte[] get()
    {
        inputBuffer.flip();
        byte[] out = new byte[inputBuffer.limit()];
        inputBuffer.get(out);
        notify();
        return out;
    }

    synchronized private void put(byte[] ibuf) throws Exception
    {
        inputBuffer.put(ibuf);
    }

    public void run()
    {
        try {
            byte[] ibuffer = new byte[1024];
            int rc = 0;

            while (0 <= rc) {
                rc = stream.read(ibuffer);
                while (0 < rc) {
                    try { 
                        put(ibuffer);
                        rc = 0;
                    } catch (BufferOverflowException boe) {
                        wait();
                    }
                }
            }

        } catch (Exception ex) {
        }
    }

    }

    reader readthr;

public TelnetUtil(String ipAddr)
{
    ipaddr = ipAddr;
    portNum = 23;
}

public TelnetUtil(String ipAddr, String port)
{
    ipaddr = ipAddr;
    portNum = (new Integer(port)).intValue();
}

public void connect() 
{
    TerminalTypeOptionHandler ttopt = new TerminalTypeOptionHandler("VT100", false, false, true, false);
    EchoOptionHandler echoopt = new EchoOptionHandler(true, false, true, false);
    SuppressGAOptionHandler gaopt = new SuppressGAOptionHandler(true, true, true, true);

    client = new TelnetClient();

    try {
        client.addOptionHandler(ttopt);
        client.addOptionHandler(echoopt);
        client.addOptionHandler(gaopt);
    } catch (Exception ex) {
        System.err.println("TelnetUtil: Error registering option handlers: " + ex);
        ex.printStackTrace();
    }

    try {
        client.connect(ipaddr, portNum);
        readthr = new reader(client.getInputStream());
    } catch (Exception ex) {
        System.err.println("TelnetUtil: connect: " + ex);
        ex.printStackTrace();
    }
}

}
