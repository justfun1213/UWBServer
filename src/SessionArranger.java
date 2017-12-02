import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class SessionArranger {
	
	private DatabaseConnection dbc;
	private Authentication aut;
	private static BufferedImage DefaultImage;
	
	public SessionArranger() {
		perpareRecources();
		dbc = new DatabaseConnection();
		aut = new Authentication(dbc.getCon());
		
		
		try {

			DefaultImage = ImageIO.read(new File("./OurMapForUWB/maps/Default.png"));
			System.out.println("default image is loaded = "+	DefaultImage.getWidth());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	private void perpareRecources() {
		 System.out.println("preparing recources");

		 Path path = Paths.get("./OurMapForUWB/maps");
	        if (!Files.exists(path)) {
	            try {
	       		    Files.createDirectories(path);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        } 
	        path = Paths.get("./OurMapForUWB/coordinates");
	        if (!Files.exists(path)) {
	            try {
	       		    Files.createDirectories(path);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        } 
	        path = Paths.get("./OurMapForUWB/utils");
	        if (!Files.exists(path)) {
	            try {
	       		    Files.createDirectories(path);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        } 
		
	}


	private static LinkedList<UWBData> UWBs = new LinkedList<UWBData>();
	public static LinkedList getUWBs() {
		return UWBs;
	}
	
	public void addUWB(UWBData i) {
		System.out.println(i.getName());
		UWBs.add(i);
	}

	public static BufferedImage getImage(String name) {
		for(UWBData i : UWBs) {
			if(i.getName().equalsIgnoreCase(name)) {
				return i.getMap();
			}
		}
		return DefaultImage;
	}



	public boolean doesRoomExist(String name) {
		for(UWBData i : UWBs) {
			if(i.doesExist() && i.getName().equalsIgnoreCase(name)) {
				return true;
			}
			
		}
		return false;
	}


	public String getLocation(String roomName) {
		for(UWBData i : UWBs) {
			if(i.getName().equalsIgnoreCase(roomName)) {
				return i.getCoordinates();
			}
		}
		
		return "no data";
	}
	
	public String login(String user, String password)
	{
		Boolean r = aut.loginCheck(user, password);
		if(r) {
			return "true";
		}
		return "false";
	}
	
	public void updateUWBData(String aName, String TagName, double distance,String aName2, String TagName2, double distance2,String roomName) {
		
			
				for(UWBData i: UWBs) {
					if(i.getName().equalsIgnoreCase(roomName)) {
					i.updateCoordinates(distance,distance2);
					}
				}
			
		}
		
	


	
	
}
