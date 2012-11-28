/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import server.players.Player;

/**
 *
 * @author totzhe
 */
public class GameThread implements Runnable
{

    private Player player1;
    private Player player2;

    public GameThread(Player player1, Player player2)
    {
        this.player1 = player1;
        this.player2 = player2;
    }

    @Override
    public void run()
    {
        
        Game game = new Game(player1, player2);

        game.Start();

        System.out.println("Game over");
        
        player1.Disconnect();
        player2.Disconnect();

    }
}