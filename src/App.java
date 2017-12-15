


public class App{   
   
   
   
   public static void main(String [] args) {
      SessionArranger s = new SessionArranger();
	 
      System.out.println("prototype,, ,this is not a official buildt!");
      try {
         Thread t = new AndroidSocket(8379,s); // makes a listener for the android devices
         t.start();
         Thread i = new UWBSocket(8378,s); // makes a listener for the UWB modules devices
         i.start();
         Thread configurationConenction = new ConfigurationSocket(8377,s);
         configurationConenction.start();
         
         
         
      }catch(Exception e) {
         System.out.println("the threads could not be started");
      }
      System.out.println("server threads have been started.... server is listening");
   }
}