import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.StringReader;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class UWBSocketThread extends Thread {
	
	
	
	private SessionArranger sessionA;
	private Socket myClient;
	private DataInputStream  in;
	private DataOutputStream out;
	public UWBSocketThread(Socket client, SessionArranger session) {
		sessionA = session;
		myClient = client;
		System.out.println("Just connected to " + myClient.getRemoteSocketAddress());
		try {
			in = new DataInputStream(client.getInputStream());		
		} catch (Exception e) {
			System.out.println("dataStreams could not be created");
		}
	}
	
	
	public void run() {
		try {
			sleep(100);
			
			 Scanner sc = new Scanner(in);
				while(sc.hasNext())
				{
					sleep(100);
					String data2 = sc.nextLine();
					System.out.println("received from uwb  " + data2);
					sessionA.triangulation.calculateFromJson(data2);

					/*JsonReader jsonReader = Json.createReader(new StringReader(data2));
					JsonObject json = jsonReader.readObject();
					jsonReader.close();

					String aName = json.getString("anchor").toString().replaceAll("\"", "");
					String tName = json.getString("tag").toString().replaceAll("\"", "");
					double distance = Double.parseDouble(json.getString("distance").toString().replaceAll("\"", ""));
					String aName2 = json.getString("anchor2").toString().replaceAll("\"", "");
					String tName2 = json.getString("tag2").toString().replaceAll("\"", "");
					double distance2 = Double.parseDouble(json.getString("distance2").toString().replaceAll("\"", ""));
					
					String roomName = "room1";

					if(sessionA.doesRoomExist(roomName)) {
						sessionA.updateUWBData(aName,tName,distance,aName2,tName2,distance2,roomName);
					} else {
						sessionA.addUWB(new UWBData(aName,aName2,roomName));   //ToDo: change the static room1
						System.out.println("added a new room to the collection: " + aName + "  " + aName2 + "   "  + roomName); //ToDo: change static room1
					}*/
			
				}	

		 myClient.close();
         
		} catch(Exception e) {System.out.println("could not complete handeling the client"); e.printStackTrace();}
		
	}
}


