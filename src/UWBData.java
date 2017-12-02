import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class UWBData {
	
	private String name;
	private BufferedImage map;
	private LinkedList<Point> Masters=new LinkedList<Point>();
	private Utrangulation uTrang;
	
	private double masterX;
	private double masterY;
	private double slaveX;
	private double slaveY;

	private String master;
	private String slave;
	
	private Boolean isReady = false;

	private LinkedList<VirtualRoom> rooms = new LinkedList<VirtualRoom>();

	public UWBData(String Master, String slave, String roomName) {

		this.master = Master;
		this.slave = slave;
		this.name = roomName;


		

		
		loadMap();

		loadRooms();
		uTrang = new Utrangulation(masterX, masterY, slaveX, slaveY, 110); // static scale
		
		isReady = true;

		

	}
	
	private void loadRooms() {
	
		String pathRooms = "./OurMapForUWB/coordinates/"+name+".txt"; 
		String everything;
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(pathRooms));
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		    everything = sb.toString();
		    br.close();
		    System.out.println(everything);
		    
		    
			JsonReader jsonReader = Json.createReader(new StringReader(everything));
			JsonObject json = jsonReader.readObject();
			jsonReader.close();
			
			
			
			masterX = Double.parseDouble(json.getJsonArray("anchors").getJsonObject(0).get("X").toString().replaceAll("\"", ""));
			masterY = Double.parseDouble(json.getJsonArray("anchors").getJsonObject(0).get("Y").toString().replaceAll("\"", ""));
			slaveX = Double.parseDouble(json.getJsonArray("anchors").getJsonObject(1).get("X").toString().replaceAll("\"", ""));
			slaveY = Double.parseDouble(json.getJsonArray("anchors").getJsonObject(1).get("Y").toString().replaceAll("\"", ""));
			
			 for(int i = 0; i< json.getJsonArray("rooms").size();i++) {				
				 String name = json.getJsonArray("rooms").getJsonObject(i).get("name").toString();
				 double x1 = Double.parseDouble(json.getJsonArray("rooms").getJsonObject(i).get("x1").toString().replaceAll("\"", ""));
				 double y1 = Double.parseDouble(json.getJsonArray("rooms").getJsonObject(i).get("y1").toString().replaceAll("\"", ""));
				 double x2 = Double.parseDouble(json.getJsonArray("rooms").getJsonObject(i).get("x2").toString().replaceAll("\"", ""));
				 double y2 = Double.parseDouble(json.getJsonArray("rooms").getJsonObject(i).get("y2").toString().replaceAll("\"", ""));
				 System.out.println(name + " " + x1 + " " + y1 + " " + x2 + " " + y2 ); 
				 rooms.add(new VirtualRoom(name,x1,y1,x2,y2));
			}
			
		} catch(Exception e) {System.out.println("cant read room locations"); e.printStackTrace(); } 
		
		
		
	}

	public void loadMap() {
		
		String path = "./OurMapForUWB/maps/"+name+".png";

		try {
			map = ImageIO.read(new File(path));
			System.out.println("map loaded");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getName() {
		return name;
	}

	public BufferedImage getMap() {
		return map;
	}

	public String getCoordinates() {
		int width = map.getWidth();
		int height = map.getHeight();
		 
		
		JsonBuilderFactory factory = Json.createBuilderFactory(null);
		 JsonObject value = factory.createObjectBuilder()
		     .add("masterName", "ABCDEF")
		    
		     .add("coordinates", factory.createArrayBuilder()
		         .add(factory.createObjectBuilder()
		             .add("name", "jantje")
		             .add("X", (int)(Math.random() * width))
		             .add("Y", (int)(Math.random() * height) ))
		         .add(factory.createObjectBuilder()
	        		 .add("name", "pietje")
		             .add("X", (int)(Math.random() * width))
		             .add("Y", (int)(Math.random() * height)))
		         .add(factory.createObjectBuilder()
		             .add("name", "koosje")
		             .add("X", (int)(Math.random() * width))
		             .add("Y", (int)(Math.random() * height)))
			      .add(factory.createObjectBuilder()
	        		 .add("name", "hansje")
		             .add("X", (int)(Math.random() * width))
		             .add("Y", (int)(Math.random() * height))))
		     .build();
		System.out.println("WE GOT THE COORDINATES: "+value.toString());
		return value.toString();
	}
	
	public void updateCoordinates(double distance, double distance2) {

		
		uTrang.createIntersectionPoints(distance, distance2);
		System.out.println("I GOT UPDATED:::::::::::::::::::::::::::::: "+ distance +  "  "  + distance2 + "   " + uTrang.getUserLocationPoint1().getX()+ "  " + uTrang.getUserLocationPoint1().getY());


	}
	
	public void getPointMaster() {

//		uTrang.getUserLocationPoint1();
//		uTrang.getUserLocationPoint1();

	}

	public void getPointSlave(){

	}

	public Boolean doesExist() {
		return isReady;
	}	
	
}