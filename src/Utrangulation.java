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

    private int scale;

    private Point userLocationPoint1;
    private Point userLocationPoint2;

    public Utrangulation(double UWBmasterX, double UWBmasterY, double UWBslaveX, double UWBslaveY, int s)
    {
        xUWB1 = UWBmasterX;
        yUWB1 = UWBmasterY;
        xUWB2 = UWBslaveX;
        yUWB2 = UWBslaveY;

        scale = s;

        deltaX = (xUWB2-xUWB1);
        deltaY = (yUWB2-yUWB1);
    }

    public int convertFromDistanceToPixels(double data)
    {
        double tempData = data * scale;
        return (int)tempData;
    }

    public void createIntersectionPoints(double rUWB1, double rUWB2)
    {
        double alphaAngleDegree = toDegrees(acos((pow(rUWB1,2) + pow(getC(),2) - pow(rUWB2,2)) / (2*rUWB1*getC())));

        //get (x3,y3) coordinates
        double correctionAngle = toDegrees(atan(((double)deltaY)/((double)deltaX)));
        double calculationAngle = alphaAngleDegree+correctionAngle;
        double userLocationPointX1 = rUWB1 * cos(toRadians(calculationAngle)) + xUWB1;
        double userLocationPointY1 = rUWB1 * sin(toRadians(calculationAngle)) + yUWB1;

        //get (x4,x4) coordinates
        double calculationAngle2 = 90 - calculationAngle + 2*alphaAngleDegree;
        double userLocationPointX2 = rUWB1 * sin(toRadians(calculationAngle2)) + xUWB1;
        double userLocationPointY2 = rUWB1 * cos(toRadians(calculationAngle2)) + yUWB1;

        //convert length(meter) data    ==>    length(pixels) data
        int locationPointX1 = convertFromDistanceToPixels(userLocationPointX1);
        int locationPointY1 = convertFromDistanceToPixels(userLocationPointY1);
        int locationPointX2 = convertFromDistanceToPixels(userLocationPointX2);
        int locationPointY2 = convertFromDistanceToPixels(userLocationPointY2);

        //create Points with pixel coordinates for the App
        userLocationPoint1 = new Point(locationPointX1, locationPointY1);
        userLocationPoint2 = new Point(locationPointX2, locationPointY2);

        //System.out.println("In meters: "+userLocationPointX1+", "+userLocationPointY1);
        //System.out.println("In meters: "+userLocationPointX2+", "+userLocationPointY2);
        //System.out.println("P3 in pixels("+userLocationPoint1.getX()+", "+userLocationPoint1.getY()+")");
        //System.out.println("P4 in pixels("+userLocationPoint2.getX()+", "+userLocationPoint2.getY()+")");
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