/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.checkers;

import java.awt.Color;
import java.awt.Point;
/**
 *
 * @author Alina
 */
public interface IChecker 
{
    
    public  Color getColor();
    
    public IChecker Crown();
    
    public int getX();
    public void setX(int x);
    
    public int getY();
    public void setY(int y); 
    
    public boolean CheckMotion(Point targetPoint);
   
    public Point KillEnemysCheckers(Point point);
    
    public boolean CanKillSmb();
    
    public boolean IfThisOneKillSmb(Point targetPoint); 
    
    public Point ChangeCoords(Point targetPoint);
    
    public boolean IfCanTurnToQueen();
}
