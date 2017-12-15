

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class  ConfigurationSocket extends Thread {
    private ServerSocket serverSocket;
    private SessionArranger sessionA;

    public  ConfigurationSocket(int port,SessionArranger session){

        this.sessionA = session;
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(0); // infinite waiting for clients.
        } catch (Exception e) {
            System.out.println("configuration socket could not be set up!");
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            try {

                Socket client = serverSocket.accept();
                ConfigurationSocketThread t = new ConfigurationSocketThread(client, sessionA);
                t.start();


            } catch (SocketTimeoutException s) {
                System.out.println("Socket timed out!");
                break;
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
