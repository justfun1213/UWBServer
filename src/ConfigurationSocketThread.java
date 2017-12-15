
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class ConfigurationSocketThread extends Thread {
    private SessionArranger sessionA;
    private Socket myClient;
    private DataInputStream in;
    private DataOutputStream out;

    public ConfigurationSocketThread(Socket client, SessionArranger session) {
        sessionA = session;
        myClient = client;
        System.out.println("confSocket Just connected to " + myClient.getRemoteSocketAddress() + " on:" + new SimpleDateFormat("yyyy.MM.dd  HH:mm").format(new Date()));
        try {
            in = new DataInputStream(client.getInputStream());
            out = new DataOutputStream(client.getOutputStream());
        } catch (Exception e) {
            System.out.println("confSocket dataStreams could not be created");
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

            if(command.equalsIgnoreCase("setConfiguration")) {

               System.out.println("new config upload ");
               String config = in.readUTF();
                System.out.println(config);

                BufferedWriter writer = null;
                try {
                    writer = new BufferedWriter( new FileWriter( sessionA.configPath));
                    writer.write(config);
                } catch ( IOException e2) {
                    e2.printStackTrace();
                } finally {
                    try {
                        if ( writer != null)
                            writer.close( );
                    } catch ( IOException e3) {
                        e3.printStackTrace();
                    }
                }
                sessionA.reload();
            }
            else if(command.equalsIgnoreCase("setImageFast"))
            {
                int FILE_SIZE = 6022386;
                String FILE_TO_RECEIVED = "./OurMapForUWB/maps/room1.png";
                int bytesRead;
                int current = 0;
                FileOutputStream fos = null;
                BufferedOutputStream bos = null;
                try {
                    InputStream inFromServer = myClient.getInputStream();
                    byte [] mybytearray  = new byte [FILE_SIZE];
                    fos = new FileOutputStream(FILE_TO_RECEIVED);
                    bos = new BufferedOutputStream(fos);
                    bytesRead = inFromServer.read(mybytearray,0,mybytearray.length);
                    current = bytesRead;

                    do {
                        bytesRead =
                                inFromServer.read(mybytearray, current, (mybytearray.length-current));
                        if(bytesRead >= 0) current += bytesRead;
                    } while(bytesRead > -1);

                    bos.write(mybytearray, 0 , current);
                    bos.flush();
                    System.out.println("File " + FILE_TO_RECEIVED
                            + " downloaded (" + current + " bytes read)");

                    myClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }





            myClient.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
