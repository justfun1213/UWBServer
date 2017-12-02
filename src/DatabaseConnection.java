import java.sql.*;

public class DatabaseConnection
{
    private Connection con = null;
    protected Authentication atc;

    public DatabaseConnection()
    {
        connect();
    }

    public void connect()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Ufinder","root","Ufinder");
            System.out.println("Connection to the Database Established.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Connection to the Database Failed !");
        }
    }

    public void closeCon()
    {
        try
        {
            con.close();
            System.out.println("Connection to the Database Closed.");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println("Failed to Close Connection to the Database !");
        }
    }
    
    public Connection getCon()
    {
    	return con;
    }

}
