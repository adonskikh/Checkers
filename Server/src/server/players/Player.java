/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.players;

import java.awt.*;
import java.io.*;
import server.checkers.BlackChecker;
import server.checkers.IChecker;
import server.checkers.WhiteChecker;

/**
 *
 * @author alina
 */
public class Player
{
    private int id;
    private ObjectInputStream reader; 
    private ObjectOutputStream writer;
    private boolean connected;
    private int score;
    private Color color;
    private IChecker current_checker;
    private IChecker[][] board;
    private int wongamescount;
    private int lostgamescount;
    private String name;
    
    public Player(int id, Color color, ObjectInputStream reader, ObjectOutputStream writer)
    {
        this.id = id;
        score = 0;
        this.color = color;
        this.reader = reader;
        this.writer = writer;
        connected = true;
        current_checker = null;
        name = "TODO: get this from database";
    }
    
    public int getWonGamesCount()
    {
        return wongamescount;
    }
    
    public void setWonGamesCount(int count)
    {
        wongamescount = count;
    }
    
    public int getLostGamesCount()
    {
        return lostgamescount;
    }
    
    public void setLostGamesCount(int count)
    {
        lostgamescount = count;
    }
    
    public int getID()
    {
        return  id;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    public void setBoard(IChecker[][] gameBoard)
    {
        board = gameBoard;
    }
  
    public Color getColor()
    {
        return color;
    }
    
    public IChecker getCurrentChecker()
    {
        return current_checker;
    }
    
    public void setCurrentChecker(IChecker checker)
    {
        current_checker = checker;
    }
            

    /**
     * @return the score
     */
    public int getScore()
    {
        return score;
    }
    
    public void IncScore()
    {
        score++;
    }

    /**
     * @param score the score to set
     */
    public void setScore(int score)
    {
        this.score = score;
    }
    
    public boolean isConnected()
    {
        return connected;
    }
    
    //Расстановка фигур на доске в начале игры
    public void SetCheckersInStartPosition()
    {
        for(int i = 0; i < board.length; i++) 
        {
            for(int j = 0; j < board.length; j++) 
            {
                if((i + j) % 2 == 1)
                {
                    if(j < 3 && color == Color.WHITE)
                    {
                        board[i][j] = new WhiteChecker(color, i, j, board);
                    }
                    if(j >= board.length - 3  && color == Color.BLACK)
                    {
                        board[i][j] = new BlackChecker(Color.BLACK, i, j, board);
                    }
                }
            }
        }    
    }
    
    //Проверка является ли шашка, шашкой игрока (по цвету)
    private boolean IsMineChecker(Point checker_coord)
    {
        if(board[checker_coord.x][checker_coord.y] != null && board[checker_coord.x][checker_coord.y].getColor() == color)
        {
            current_checker = board[checker_coord.x][checker_coord.y];
            return true;
        }
        else
        {
            return false;
        }
    }
    
    //Считывает из сокета запрос на перемещение шашки от клиента
    public Point ReadMoveRequest()
    {
        Point startPoint = null;
        Point finishPoint = null;
        try
        {
            try
            {
                startPoint = (Point) reader.readObject();
                finishPoint = (Point) reader.readObject();
                System.out.println("1. [" + startPoint.x + ", " + startPoint.y + "] -> [" + finishPoint.x + ", " + finishPoint.y + "]");
                /*String s = (String) reader.readObject();
                System.out.println(s);*/
            }
            catch (ClassNotFoundException ex)
            {
                System.out.println("ClassNotFoundException: " + ex.getMessage());
                connected = false;
                return null;
            }
        }
        catch (IOException e)
        {
            System.out.println("Stream " + id + " reading error: " + e.getMessage());
            connected = false;
            return null;
        }
        if(startPoint != null && finishPoint != null && IsMineChecker(startPoint))
        {
            current_checker = board[startPoint.x][startPoint.y];
            return finishPoint;
        }
        else
            return null;
    }

    public void SendNewGameCommand(int color, String name, String opponentName)// + currentPoint
    {
        try
        {
            System.out.println("Player: " + name + ", Opponent: " + opponentName + ", color: " + color);
            writer.writeObject(color);
            writer.writeObject(name);
            writer.writeObject(opponentName);
            writer.flush();
        }
        catch (IOException e)
        {
            connected = false;
            System.out.println("Stream " + id + " writing error: " + e.getMessage());
        }
    }

    public void SendMoveCommand(Point startPoint, Point finishPoint, Point killedCheckerPoint, boolean turnToQueen, boolean endTurn)// + currentPoint
    {
        try
        {
            System.out.println("2. [" + startPoint.x + ", " + startPoint.y + "] -> [" + finishPoint.x + ", " + finishPoint.y + "]");
            writer.writeObject("Move");
            writer.writeObject(startPoint);
            writer.writeObject(finishPoint);
            writer.writeObject(killedCheckerPoint);
            writer.writeObject(turnToQueen);
            writer.writeObject(endTurn);
            writer.flush();
        }
        catch (IOException e)
        {
            connected = false;
            System.out.println("Stream " + id + " writing error: " + e.getMessage());
        }
    }

    public void SendGameOverCommand(String message)
    {
        try
        {
            writer.writeObject("Game over");
            writer.writeObject(message);
            writer.flush();
        }
        catch (IOException e)
        {
            connected = false;
            System.out.println("Stream " + id + " writing error: " + e.getMessage());
        }
    }
    
    public void SendEndGameCommand(String message)
    {
        try
        {
            writer.writeObject(message);
            writer.flush();
        }
        catch(IOException exception)
        {
            connected = false;
            System.out.println("Stream " + id + " writing error");
        }
    }
    
}
