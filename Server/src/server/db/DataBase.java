/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.db;

import java.security.MessageDigest;
import java.sql.*;
import java.util.Properties;

/**
 *
 * @author Алина
 */
public final class DataBase
{
    public DataBase()
    { 
        //Class.forName("com.mysql.jdbc.Driver");
    }
    
    public Connection CreateConnection()
    {
        try 
        {
            Properties connInfo = new Properties();
            connInfo.put("characterEncoding", "UTF8");
            connInfo.put("user", "checkers");
            connInfo.put("password", "checkers");
            
            
            Connection connection = DriverManager.getConnection("jdbc:mysql://vragov.com:3306/checkers", connInfo);
            
            return connection;
        }
        catch(SQLException e)
        {
            System.err.println("Cannot connect to this db.");
            System.err.println(e.getStackTrace().toString());
        }
        
        return null;
    }
    
    public void CloseConnection(Connection connection)
    {
        try
        {
            if(connection != null)
            {
                connection.close();
            }
        }
        catch(SQLException ex)
        {
            System.err.println("On close: " + ex.toString());
        }
    }
    
    public boolean IsPlayerLikeThisInDB(Connection connection, String name, String password)
    {
        try
        {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id FROM PLAYER WHERE name = '"+ name + "' and password = '" + password +"';");
            if(resultSet.wasNull())
            {
                return false;
            }
            return true;
        }
        catch (SQLException exception)
        {
            System.err.println("IsPlayerLikeThisInDB");
        }
        
        return false;
    }
}
