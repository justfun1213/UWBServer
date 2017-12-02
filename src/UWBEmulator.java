
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public class UWBEmulator {
 public static void main(String[] args) {
	 String serverName = "77.172.10.240";
		//serverName = "127.0.0.1";
		int port = 8378;
		
		
		String name = "room1";
		String data = "randomXYdata";

		String antw = send(name+":"+data, serverName, port);
		System.out.println(antw);
		
		
		
 }
 
 
 private static String send(String data, String serverName, int port) {
		String responce = "";
		try {
			Socket client = new Socket(serverName, port);
			OutputStream outToServer = client.getOutputStream();
			DataOutputStream out = new DataOutputStream(outToServer);
			InputStream inFromServer = client.getInputStream();
			DataInputStream in = new DataInputStream(inFromServer);

			out.writeUTF(data);

			responce = in.readUTF();
			client.close();
		} catch (IOException e) {
			return "no connection";
		}

		return responce;
	}
}
