/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicalClient;

/**
 *
 * @author totzhe
 */
public class SquareIsNotEmptyException extends Exception
{

    /**
     * Creates a new instance of
     * <code>SquareIsNotEmptyException</code> without detail message.
     */
    public SquareIsNotEmptyException()
    {
    }

    /**
     * Constructs an instance of
     * <code>SquareIsNotEmptyException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public SquareIsNotEmptyException(String msg)
    {
        super(msg);
    }
}
