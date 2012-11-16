/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicalClient;

import graphicalClient.BoardElements.SquareIsNotEmptyException;
import graphicalClient.BoardElements.Board;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author totzhe
 */
public class Game
{
    private Player player;
    
    private Board board;
    
    private static int boardWidth = 400;
    
    public Game() throws SquareIsNotEmptyException
    {
        player = new Player(Color.RED);//TODO: получение цвета от сервера (0 - черный, 1 - белый)
        board = new Board(boardWidth, player);
    }
    
    public void ClickAction(int mouse_x, int mouse_y)
    {
        board.ClickAction(ConvertMouseCoordToBoardCoord(mouse_x), ConvertMouseCoordToBoardCoord(mouse_y));
    }
    
    public int ConvertMouseCoordToBoardCoord(int mouse_coord)
    {
        return mouse_coord / board.getSquareWidth();
    }
    
    public void Draw(Graphics g, int border)
    {        
        Graphics gr = g.create(border, border, board.getBoardWidth(), board.getBoardWidth());
        board.Draw(gr);
    }
}
