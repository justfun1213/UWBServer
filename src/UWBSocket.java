

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class UWBSocket extends Thread {
	 private ServerSocket serverSocket;
	 private SessionArranger sessionA;
	
	public UWBSocket(int port, SessionArranger session){
		this.sessionA = session;
	      try {
			serverSocket = new ServerSocket(port);
			serverSocket.setSoTimeout(0); // infinite waiting for clients.
		} catch (Exception e) {
			System.out.println("socket could not be set up!");
		}

	   }
	public void run() {
	      while(true) {
	         try {
	            
	            Socket client = serverSocket.accept();
	            UWBSocketThread t = new UWBSocketThread(client, sessionA);
	            t.start();
	            
	         }catch(SocketTimeoutException s) {
	            System.out.println("Socket timed out!");
	            break;
	         }catch(IOException e) {
	            e.printStackTrace();
	            break;
	         }
	      }
	   }
	
}


