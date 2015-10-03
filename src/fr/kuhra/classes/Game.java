package fr.kuhra.classes;

import fr.kuhra.enumerations.PlayerType;

/**
 * Created by Dimitri on 28/09/2015.
 */
public class Game {
    private Player player1;
    private Player player2;

    public Game(){
        player1 = new Player(PlayerType.HUMAN);
        player2 = new Player(PlayerType.COMPUTER);

        player1.setOpponentMap(player2);
        player2.setOpponentMap(player1);
    }

    public Game(PlayerType playerType){
        player1 = new Player(PlayerType.HUMAN);
        player2 = new Player(playerType);

        player1.setOpponentMap(player2);
        player2.setOpponentMap(player1);
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }
}
