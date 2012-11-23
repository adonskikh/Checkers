/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author Alina
 */
public class SquareIsEmptyException extends Exception
{
    
    public SquareIsEmptyException(){}
    
    /**
     *Если сервер получил координаты, которым не соответствует шашка на доске
     * @param message
     */
    public SquareIsEmptyException(String message)
    {
        super(message);
    }
}
