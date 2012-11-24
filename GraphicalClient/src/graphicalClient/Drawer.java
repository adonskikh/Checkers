/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicalClient;

import java.awt.*;

/**
 *
 * @author totzhe
 */
public class Drawer
{
    private static int squareWidth = 50;
    private static int border = 3;
    private static Graphics graphics;

    /**
     * @return the squareWidth
     */
    public static int getSquareWidth()
    {
        return squareWidth;
    }

    public static void setGraphics(Graphics g)
    {
        graphics = g;
    }
    
    public static void DrawSquare(int x, int y, Color color)
    {
        graphics.setColor(color);
        graphics.fillRect(x * getSquareWidth(), y * getSquareWidth(), getSquareWidth(), getSquareWidth());
    }

    public static void DrawChecker(int x, int y, Color color)
    {
        graphics.setColor(color);
        graphics.fillOval(x * squareWidth + border, y * squareWidth + border, squareWidth - 2 * border, squareWidth - 2 * border);
    }

    public static void DrawCrownedChecker(int x, int y, Color color)
    {
        graphics.setColor(color);
        graphics.fillOval(x * squareWidth + border, y * squareWidth + border, squareWidth - 2 * border, squareWidth - 2 * border);
        graphics.setColor(Color.YELLOW);
        graphics.fillOval(x * squareWidth + 5 * border, y * squareWidth + 5 * border, squareWidth - 10 * border, squareWidth - 10 * border);
    }

    public static void DrawSelection(int x, int y)
    {
        graphics.setColor(Color.RED);
        graphics.drawRect(x * getSquareWidth(), y * getSquareWidth(), getSquareWidth() - 1, getSquareWidth() - 1);
    }
}
