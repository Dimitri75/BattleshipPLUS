package fr.kuhra;

import fr.kuhra.classes.Game;
import fr.kuhra.classes.Location;
import fr.kuhra.enumerations.ConsoleSentence;
import fr.kuhra.enumerations.Direction;

import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Game game = new Game();

        Scanner sc = new Scanner(System.in);
        int x, y, result = -1;
        while (result != 2) {
            while (result < 0) {
                // Affichage de votre carte et de celle de l'adversaire
                game.getPlayer1().getMap().printMap(ConsoleSentence.MAP_OF + game.getPlayer1().getName());
                game.getPlayer1().getOpponentMap().printMap(ConsoleSentence.MAP_OF + game.getPlayer2().getName());

                // Récupération des coordonnées du tir
                sc.reset();
                System.out.print(ConsoleSentence.X_SHOOT_OF);
                x = Integer.parseInt(sc.next());

                sc.reset();
                System.out.print(ConsoleSentence.Y_SHOOT_OF);
                y = Integer.parseInt(sc.next());

                // Tir
                result = game.getPlayer1().shoot(new Location(x, y), game.getPlayer2());

                System.out.println("\n");
                game.getPlayer1().getOpponentMap().printMap(ConsoleSentence.MAP_OF + game.getPlayer2().getName());


                // Affichage du résultat
                String res = ConsoleSentence.TOUCHED.toString();
                if (result < 0)
                    res = ConsoleSentence.CANT_SHOOT_HERE.toString();
                else if (result == 0)
                    res = ConsoleSentence.MISSED.toString();

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
                    while (shipNumber < 0 || shipNumber >= game.getPlayer1().getMap().getShips().size()) {
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

                    moved = game.getPlayer1().moveShip(shipNumber, directionDictionary.get(directionChar), nbTiles);
                }

                if (moved)
                    game.getPlayer1().getMap().printMap(ConsoleSentence.MAP_AFTER_MOVEMENT.toString());
                else
                    System.out.println(ConsoleSentence.INCORRECT_MOVEMENT + "\n");
            }
        }

        // Tir de l'ordinateur
        if (game.getPlayer2().shootRandomLocation(game.getPlayer1()) == 0) {
            System.out.println(ConsoleSentence.MISSED);

            // Déplacement d'un bateau de l'ordinateur
            game.getPlayer2().randomShipMovement();
        } else
            System.out.println(ConsoleSentence.TOUCHED);
    }
}

