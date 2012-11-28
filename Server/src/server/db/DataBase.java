/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.db;

import java.security.MessageDigest;
import java.sql.*;
import java.util.Properties;
import server.players.Player;

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
    
    public int IsPlayerLikeThisInDB(Connection connection, String name, String password)
    {
        try
        {
            PreparedStatement statement = connection.prepareStatement("SELECT id FROM PLAYER WHERE name = ? and password = ?");
            statement.setString(1, name);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next())
            {
                return resultSet.getInt("id");
            }
            statement.close();
            
            return -1;
        }
        catch (SQLException exception)
        {
            System.err.println("IsPlayerLikeThisInDB");
        }
        
        return -1;
    }
    
    public void GetPlayersInfo(Connection connection, Player player)
    {
        try
        {
            PreparedStatement statement = connection.prepareStatement("SELECT won_games_count, lost_games_count FROM PLAYER WHERE id = ?");
            statement.setInt(1, player.getID());
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next())
            {
                player.setWonGamesCount(resultSet.getInt("won_games_count"));
                player.setLostGamesCount(resultSet.getInt("lost_games_count"));
            }
            statement.close();
        }
        catch(SQLException exception)
        {
            System.err.println("GetPlayersInfo");
        }
    }
    
    public void UpdatePlayerInfo(Connection connection, Player player)
    {
        try
        {
            PreparedStatement statement = connection.prepareStatement("UPDATE PLAYER SET won_games_count = ?, lost_games_count = ? WHERE id = ?");
            statement.setInt(1, player.getWonGamesCount());
            statement.setInt(2, player.getLostGamesCount());
            statement.setInt(3, player.getID());
            statement.executeUpdate();
            statement.close();
        }
        catch(SQLException exception)
        {
            System.err.println("UpdatePlayersInfo");
        }
    }
}
