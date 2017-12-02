import java.awt.Point;

public class VirtualRoom {
	private Point p1 = new Point(0,0);
	private Point p2 = new Point(0,0);
	private String name ="unknown";
     public VirtualRoom(String name,double x, double y,double x2,double y2) {
    	 p1.setLocation(x, y);
    	 p2.setLocation(x2, y2);
    	 this.name = name;
     }
     
     public double getX1() {
    	 return p1.getX();
     }
     public double getY1() {
    	 return p1.getY();
     }
     public double getX2() {
    	 return p2.getX();
     }
     public double getY2() {
    	 return p2.getY();
     }
}
