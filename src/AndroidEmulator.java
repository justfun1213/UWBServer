
import java.net.*;


import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.image.BufferedImage;
import java.io.*;

public class AndroidEmulator {

	public static void main(String[] args) {
		String serverName = "77.172.10.240";
		//serverName = "127.0.0.1";
		int port = 8379;

		String antw = send("hallo", serverName, port);
		System.out.println(antw);
		
		
		BufferedImage reply = getImage("room1", serverName, port);
		System.out.println("SERVER REPLIED: " + reply.getWidth() + " " + reply.getHeight());
		System.out.println("displaying image");
		
		
		JFrame f = new JFrame("Downloaded Image");
		f.setSize(reply.getWidth(),reply.getHeight());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().add(new JLabel(new ImageIcon(reply)));
		f.setVisible(true);
		
		
		
		

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

	private static BufferedImage getImage(String data, String serverName, int port) {

		try {
			Socket client = new Socket(serverName, port);
			OutputStream outToServer = client.getOutputStream();
			DataOutputStream out = new DataOutputStream(outToServer);
			InputStream inFromServer = client.getInputStream();
			

			out.writeUTF(data);


			BufferedImage responce = ImageIO.read(ImageIO.createImageInputStream(inFromServer));
			client.close();
			return responce;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

}
