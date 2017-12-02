import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Authentication
{
    private Connection conn;    
    private ResultSet rs;
    public Authentication(Connection con)
    {
        conn = con;
    }
    public boolean loginCheck(String username, String password)
    {
    	 boolean loginSucces = false;
        try
        {
            String resultUser = null;
            String resultPass = null;
            PreparedStatement ps = conn.prepareStatement("SELECT username, password FROM users WHERE users.username = ? AND users.password = ?");
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            rs.next();
            try
            {
                resultUser = rs.getString("username");
                resultPass = rs.getString("password");
            }
            catch (SQLException e)
            {
                System.out.println("No results in the query.");
            }
            System.out.println("Query execution Complete.");

            //autheticate the login
            if(username.equals(resultUser) && password.equals(resultPass))
            {
                loginSucces = true;
                System.out.println("Login Succesful. "+loginSucces);
            } else {
            	loginSucces = false;;
            	System.out.println("Login failed. "+loginSucces);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println("Something went wrong while executing Query !");
        }
        return loginSucces;
    }
}
