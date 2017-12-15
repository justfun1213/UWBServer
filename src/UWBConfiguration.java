import javax.imageio.ImageIO;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by Rene Schouten on 12/12/2017.
 */
public class UWBConfiguration {
    public ArrayList<Anchor> anchors = new ArrayList<Anchor>();
    public ArrayList<VirtualRoom> rooms = new ArrayList<VirtualRoom>();
    public double scale;

    public void loadFromFile(String path) {
        String everything;
        try {

            BufferedReader br = new BufferedReader(new FileReader(path));
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

            for(int i=0;i<json.getJsonArray("anchors").size();i++)
            {
                String anchorName = json.getJsonArray("anchors").getJsonObject(i).get("name").toString().replaceAll("\"", "");
                double anchorX = Double.parseDouble(json.getJsonArray("anchors").getJsonObject(i).get("X").toString().replaceAll("\"", ""));
                double anchorY = Double.parseDouble(json.getJsonArray("anchors").getJsonObject(i).get("Y").toString().replaceAll("\"", ""));
                boolean fake;
                if(json.getJsonArray("anchors").getJsonObject(i).get("fake").toString()=="true")
                {
                    fake=true;
                }
                else{
                    fake=false;
                }
                boolean master;
                if(json.getJsonArray("anchors").getJsonObject(i).get("master").toString()=="true")
                {
                    master=true;
                }
                else{
                    master=false;
                }
                anchors.add(new Anchor(anchorName,new Point((int)anchorX,(int)anchorY),master,fake,false,"0"));
            }
            scale = Double.parseDouble(json.get("scale").toString().replaceAll("\"", ""));

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
    public BufferedImage map;
    public void loadMap(BufferedImage defaultImage) {

        String path = "./OurMapForUWB/maps/room1.png";

        try {
            ImageIO.setUseCache(false);
            map = ImageIO.read(new File(path));
            System.out.println("map loaded");
        } catch (IOException e) {
            map=defaultImage;
            e.printStackTrace();
        }
    }
}
