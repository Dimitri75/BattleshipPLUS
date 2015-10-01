package fr.kuhra;

import fr.kuhra.classes.Game;
import fr.kuhra.classes.Location;
import fr.kuhra.enumerations.Direction;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Game game = new Game();

        /*
        game.getPlayer1().getMap().printMap(game.getPlayer1().getName() + " MAP ");
        for (int a = 0; a < 10; a++)
            for (int b = 0; b < 10; b++)
                game.getPlayer1().shoot(new Location(b, a), game.getPlayer2());

        game.getPlayer1().getAdversaryMap().printMap(game.getPlayer1().getName() + " ADVERSARY MAP ");
        */


        Scanner sc = new Scanner(System.in);
        int x, y, result = 0;
        while (result != 2) {
            game.getPlayer1().getMap().printMap("PLAYER1 MAP ");
            /*game.getPlayer1().getAdversaryMap().printMap("PLAYER1 ADVERSARY MAP ");

            sc.reset();
            System.out.print("Tir de Player1 en x : ");
            x = Integer.parseInt(sc.next());

            sc.reset();
            System.out.print("Tir de Player1 en y : ");
            y = Integer.parseInt(sc.next());

            result = game.getPlayer1().shoot(new Location(x, y), game.getPlayer2());

            System.out.println("\n");
            game.getPlayer2().getMap().printMap("PLAYER2 MAP ");


            String res = "Touché.\n";
            if (result < 0)
                res = "Vous ne pouvez pas tirer ici.\n";
            else if (result == 0)
                res = "Raté.\n";

            System.out.println(res);
            */

            sc.reset();
            System.out.println("Voulez-vous déplacer un navire ? (o/n)");
            if (sc.next().equals("o")) {
                boolean moved = false;
                while (!moved) {
                    int shipNumber = -1;
                    String directionChar = "";
                    int nbTiles = -1;
                    while (shipNumber < 0 || shipNumber >= game.getPlayer1().getMap().getShips().size()) {
                        sc.reset();
                        System.out.println("Quel navire souhaitez-vous déplacer ?");
                        shipNumber = Integer.parseInt(sc.next());
                    }

                    while ( directionChar == null || !"zqsd".contains(directionChar) || directionChar.length() != 1) {
                        sc.reset();
                        System.out.println("Dans quelle direction souhaitez-vous le déplacer ? (z, q, s, d)");
                        directionChar = sc.next();
                    }

                    while (nbTiles < 0 || nbTiles > 2) {
                        sc.reset();
                        System.out.println("De combien de cases souhaitez-vous déplacer le navire ? (1 ou 2)");
                        nbTiles = Integer.parseInt(sc.next());
                    }

                    switch (directionChar) {
                        case "z":
                            moved = game.getPlayer1().moveShip(shipNumber, Direction.UP, nbTiles);
                            break;
                        case "q":
                            moved = game.getPlayer1().moveShip(shipNumber, Direction.LEFT, nbTiles);
                            break;
                        case "s":
                            moved = game.getPlayer1().moveShip(shipNumber, Direction.DOWN, nbTiles);
                            break;
                        case "d":
                            moved = game.getPlayer1().moveShip(shipNumber, Direction.RIGHT, nbTiles);
                            break;
                    }

                    if (moved)
                        game.getPlayer1().getMap().printMap("MAP AFTER MOVE");
                    else
                        System.out.println("Déplacement incorrect.\n");
                }
            }
        }
    }
}
