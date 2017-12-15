

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class AndroidSocket extends Thread {
	 private ServerSocket serverSocket;
	 private SessionArranger sessionA;
	 
	public AndroidSocket(int port,SessionArranger session){

		this.sessionA = session;
	      try {
			serverSocket = new ServerSocket(port);
			serverSocket.setSoTimeout(0); // infinite waiting for clients.
		} catch (Exception e) {
			System.out.println("socket could not be set up!");
			e.printStackTrace();
		}
	   }
	
	public void run() {
		while (true) {
			try {

				Socket client = serverSocket.accept();
				AndroidSocketThread t = new AndroidSocketThread(client, sessionA);
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
