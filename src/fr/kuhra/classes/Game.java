package fr.kuhra.classes;

import fr.kuhra.enumerations.ConsoleSentence;
import fr.kuhra.enumerations.Direction;
import fr.kuhra.enumerations.PlayerType;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by Dimitri on 28/09/2015.
 */
public class Game {
    private Player player1;
    private Player player2;

    public Game() {
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

    public void start(){
        Scanner sc = new Scanner(System.in);
        int x, y, result;
        boolean gameDone = false;

        while (!gameDone) {
            result = -1;
            while (result < 0) {
                // Affichage de votre carte et de celle de l'adversaire
                player1.getMap().printMap(ConsoleSentence.MAP_OF + player1.getName());
                player1.getOpponentMap().printMap(ConsoleSentence.MAP_OF + player1.getName());

                // Récupération des coordonnées du tir

                try {
                    sc.reset();
                    System.out.print(ConsoleSentence.X_SHOOT_OF);
                    x = Integer.parseInt(sc.next());

                    sc.reset();
                    System.out.print(ConsoleSentence.Y_SHOOT_OF);
                    y = Integer.parseInt(sc.next());
                }catch(NumberFormatException e){
                    x = -1;
                    y = -1;
                }

                // Tir
                result = player1.shoot(new Location(x, y), player2);

                System.out.println("\n");
                player1.getOpponentMap().printMap(ConsoleSentence.MAP_OF + player2.getName());


                // Affichage du résultat
                String res = ConsoleSentence.CANT_SHOOT_HERE.toString();
                if (result == 0)
                    res = ConsoleSentence.MISSED.toString();
                else if (result == 1) {
                    res = ConsoleSentence.TOUCHED.toString();

                    if (player1.getMap().hasNoShipsLeft()) {
                        result = 2;
                        System.out.println(ConsoleSentence.WIN);
                        gameDone = true;
                        return;
                    }
                }

                System.out.println("\n" + res);
            }

            // Déplacement d'un bateau
            sc.reset();
            System.out.println(ConsoleSentence.ASK_SHIP_MOVEMENT + " (o/n)");
            if (sc.next().equals("o")) {
                HashMap<String, Direction> directionDictionary = new HashMap<>();
                directionDictionary.put("z", Direction.UP);
                directionDictionary.put("s", Direction.DOWN);
                directionDictionary.put("q", Direction.LEFT);
                directionDictionary.put("d", Direction.RIGHT);

                boolean moved = false;
                while (!moved) {
                    int shipNumber = -1;
                    String directionChar = "";
                    int nbTiles = -1;
                    while (shipNumber < 0 || shipNumber >= getPlayer1().getMap().getShips().size()) {
                        sc.reset();
                        System.out.println(ConsoleSentence.WHAT_SHIP_TO_MOVE);
                        shipNumber = Integer.parseInt(sc.next());
                    }

                    while (directionChar == null || !"zqsd".contains(directionChar) || directionChar.length() != 1) {
                        sc.reset();
                        System.out.println(ConsoleSentence.WHICH_DIRECTION + " (z, q, s, d)");
                        directionChar = sc.next();
                    }

                    while (nbTiles < 0 || nbTiles > 2) {
                        sc.reset();
                        System.out.println(ConsoleSentence.HOW_MANY_TILES + " (1 ou 2)");
                        nbTiles = Integer.parseInt(sc.next());
                    }

                    moved = player1.moveShip(shipNumber, directionDictionary.get(directionChar), nbTiles);
                }

                if (moved)
                    player1.getMap().printMap(ConsoleSentence.MAP_AFTER_MOVEMENT.toString());
                else
                    System.out.println(ConsoleSentence.INCORRECT_MOVEMENT + "\n");
            }

            // Tir de l'ordinateur
            if (player2.shootRandomLocation(player1) == 0) {
                System.out.println(ConsoleSentence.MISSED);

                // Déplacement d'un bateau de l'ordinateur
                player2.randomShipMovement();
            } else {
                System.out.println(ConsoleSentence.TOUCHED);

                if (player2.getMap().hasNoShipsLeft()) {
                    gameDone = true;
                    System.out.println(ConsoleSentence.LOOSE);
                    return;
                }
            }
        }
    }
}
