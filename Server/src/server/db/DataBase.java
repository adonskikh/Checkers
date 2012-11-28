/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.db;

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
    
    private Connection connection;
    
    public void CreateConnection()
    {
        try 
        {
            Properties connInfo = new Properties();
            connInfo.put("characterEncoding", "UTF8");
            connInfo.put("user", "checkers");
            connInfo.put("password", "checkers");
            
            
            connection = DriverManager.getConnection("jdbc:mysql://vragov.com:3306/checkers", connInfo);
        }
        catch(SQLException e)
        {
            System.err.println("Cannot connect to this db.");
            System.err.println(e.getStackTrace().toString());
        }
    }
    
    public void CloseConnection()
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
    
    public int IsPlayerLikeThisInDB(String name, String password)
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
    
    public void GetPlayersInfo(Player player)
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
    
    public void UpdatePlayerInfo(Player player)
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
    
    public Player GetPlayerByLoginAndPass(String login, String password)
    {
        try
        {
            MD5 md5 = new MD5();
            String pass = md5.getHash(password);
            PreparedStatement statement = connection.prepareStatement("SELECT id FROM PLAYER WHERE name = ? and password = ?");
            statement.setString(1, login);
            statement.setString(2, pass);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next())
            {
                return new Player(resultSet.getInt("id"), login) ;
            }
            System.out.println("Not resul in DB");
            statement.close();
            
            return null;
        }
        catch (SQLException exception)
        {
            System.err.println("GetPlayerByLoginAndPass");
        }
        
        return null;
    }
}
