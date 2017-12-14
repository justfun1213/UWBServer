import java.awt.*;
import static java.lang.Math.*;
import static java.lang.StrictMath.toDegrees;

public class Utrangulation
{
    private double xUWB1, yUWB1;
    private double xUWB2, yUWB2;
    private double deltaX;
    private double deltaY;
    private double c;

    private double scale;

    private Point userLocationPoint1;
    private Point userLocationPoint2;

    public Utrangulation(double UWBmasterX, double UWBmasterY, double UWBslaveX, double UWBslaveY, double s)
    {
        xUWB1 = UWBmasterX;
        yUWB1 = UWBmasterY;
        xUWB2 = UWBslaveX;
        yUWB2 = UWBslaveY;

        scale = s;

        deltaX = (xUWB2-xUWB1);
        deltaY = (yUWB2-yUWB1);

        System.out.println("UTriangulation made..... coordinates of masters & slaves: ("+ UWBmasterX + ", " + UWBmasterY +  ");  (" + UWBslaveX + ", " + UWBslaveY+") ");
    }

    public int convertFromDistanceToPixels(double data)
    {
        double tempData = data * scale;
        return (int)tempData;
    }

    public void createIntersectionPoints(double rUWB1, double rUWB2)
        {
        /*rUWB1 = convertFromDistanceToPixels(rUWB1);
        rUWB2 = convertFromDistanceToPixels(rUWB2);
        double alphaAngle = toDegrees(acos((pow(rUWB1,2) + pow(getC(),2) - pow(rUWB2,2)) / (2*rUWB1*getC())));

        //get (x3,y3) coordinates
        double correctionAngle = toDegrees(atan(((double)deltaY)/((double)deltaX)));
        double calculationAngle = alphaAngle+correctionAngle;
        double userLocationPointX1 = rUWB1 * cos(toRadians(calculationAngle)) + xUWB1;
        double userLocationPointY1 = rUWB1 * sin(toRadians(calculationAngle)) + yUWB1;

        //get (x4,x4) coordinates
        double calculationAngle2 = 90 - calculationAngle + 2*alphaAngle;
        double userLocationPointX2 = rUWB1 * sin(toRadians(calculationAngle2)) + xUWB1;
        double userLocationPointY2 = rUWB1 * cos(toRadians(calculationAngle2)) + yUWB1;

        //convert length(meter) data    ==>    length(pixels) data
        int locationPointX1 = (int)userLocationPointX1;
        int locationPointY1 = (int)userLocationPointY1;
        int locationPointX2 = (int)userLocationPointX2;
        int locationPointY2 = (int)userLocationPointY2;

        //create Points with pixel coordinates for the App
        userLocationPoint1 = new Point(locationPointX1, locationPointY1);
        userLocationPoint2 = new Point(locationPointX2, locationPointY2);

        System.out.println("In meters: "+userLocationPointX1+", "+userLocationPointY1);
        System.out.println("In meters: "+userLocationPointX2+", "+userLocationPointY2);
        System.out.println("P3 in pixels("+userLocationPoint1.getX()+", "+userLocationPoint1.getY()+")");
        System.out.println("P4 in pixels("+userLocationPoint2.getX()+", "+userLocationPoint2.getY()+")"); */

            rUWB1 = convertFromDistanceToPixels(rUWB1);
            rUWB2 = convertFromDistanceToPixels(rUWB2);

            double aDeltaX=Math.abs(xUWB1-xUWB2);
            double aDeltaY=Math.abs(yUWB1-yUWB2);
            double a=Math.sqrt(Math.pow(aDeltaX,2)+Math.pow(aDeltaY,2));
            double b=rUWB1;
            double c=rUWB2;
            System.out.println("a: "+a+" b: "+b+" c: "+c);


            double cAngle= 2*Math.PI - Math.acos((b*b+a*a-c*c)/(2*a*b));
            System.out.println("cangle"+cAngle);
            System.out.println("alfa:"+Math.toDegrees(cAngle));
            double d= Math.cos(cAngle)*b;
            double e=Math.sin(cAngle)*b;
            System.out.println("d: "+d+" e: "+e);
            double anchorsAngle=Math.atan2(aDeltaY,aDeltaX);
            double distanceFrom0 = Math.sqrt(d*d+e*e);

            double tagAngleFrom0=Math.atan2(d,e);
            double destinationtagAngle=0;
            if(yUWB2>yUWB1) {
                if(xUWB2>xUWB1) {
                    destinationtagAngle = tagAngleFrom0 + (Math.PI-anchorsAngle);
                }
                else {
                    destinationtagAngle = tagAngleFrom0 + anchorsAngle;
                }
            }
            else {
                if(xUWB2>xUWB1) {
                    destinationtagAngle = tagAngleFrom0 - (Math.PI-anchorsAngle);
                }
                else {
                    destinationtagAngle = tagAngleFrom0 - anchorsAngle;
                }
            }
            double tagx= Math.sin(destinationtagAngle)*distanceFrom0+xUWB2;
            double tagy= Math.cos(destinationtagAngle)*distanceFrom0+yUWB2;

            System.out.println("angle:"+anchorsAngle+" distance from 0 "+distanceFrom0+" angle:"+Math.toDegrees(cAngle)  +" destination angle:"+Math.toDegrees(destinationtagAngle));
           // tag.setPotentialLocation(new Point((int)(tagx),(int)(tagy)));
            userLocationPoint1 = new Point((int)tagx,(int)tagy);


    }

    public double getC()
    {
        if((yUWB2-yUWB1)==0)
        {
            c = deltaX;
            System.out.println(c);
        }
        else
        {
            c = sqrt(pow((deltaX),2) + pow((deltaY),2));
        }
        return c;
    }

    public Point getUserLocationPoint1()
    {
        return userLocationPoint1;
    }

    public Point getUserLocationPoint2()
    {
        return userLocationPoint2;
    }
}