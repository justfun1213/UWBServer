
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.StringReader;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class AndroidSocketThread extends Thread {
	private ClassLoader classLoader;
	private SessionArranger sessionA;
	private Socket myClient;
	private DataInputStream in;
	private DataOutputStream out;

	public AndroidSocketThread(Socket client, SessionArranger session) {
		sessionA = session;
		myClient = client;
		System.out.println("Just connected to " + myClient.getRemoteSocketAddress() + " on:" + new SimpleDateFormat("yyyy.MM.dd  HH:mm").format(new Date()));
		try {
			in = new DataInputStream(client.getInputStream());
			out = new DataOutputStream(client.getOutputStream());
		} catch (Exception e) {
			System.out.println("dataStreams could not be created");
		}
	}

	public void run() {
		try {
			String input = in.readUTF();
			System.out.println(input);
			
			
			
			JsonReader jsonReader = Json.createReader(new StringReader(input));
			JsonObject json = jsonReader.readObject();
			jsonReader.close();
			
			String command = json.get("command").toString().replaceAll("\"", "");
			
			
			
			
			
			if(command.equalsIgnoreCase("getImage")) {
				BufferedImage image = sessionA.getImage(json.get("name").toString().replaceAll("\"", ""));
				ImageIO.write(image, "PNG", out);
				
			} else if(command.equalsIgnoreCase("getCoordinates")) {
				String roomName = json.get("name").toString().replaceAll("\"", "");
				
				 String toSend = sessionA.getLocation(roomName);
				 System.out.println("sending coordinates to client:" + toSend);
				 out.writeUTF(toSend);
				
			} else if(command.equalsIgnoreCase("login")) {
				
				String uName = json.get("uName").toString().replaceAll("\"", "");
				String PW = json.get("PW").toString().replaceAll("\"", ""); 
				
				String responce =sessionA.login(uName,PW);
				
				JsonBuilderFactory factory = Json.createBuilderFactory(null);
				JsonObject value = factory.createObjectBuilder()
				     .add("loginReply", responce).build();
				    
				   
				
				 System.out.println("logingreply: " + value.toString());
				 out.writeUTF(value.toString());
			}
			
			
			

		
			myClient.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
