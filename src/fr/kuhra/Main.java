package fr.kuhra;

import fr.kuhra.classes.Game;
import fr.kuhra.classes.Location;

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
        while (sc.nextLine() != "exit") {
            game.getPlayer1().getMap().printMap("PLAYER1 MAP ");
            game.getPlayer1().getAdversaryMap().printMap("PLAYER1 ADVERSARY MAP ");

            sc.reset();
            System.out.print("Tir de Player1 en x : ");
            x = Integer.parseInt(sc.next());

            sc.reset();
            System.out.print("Tir de Player1 en y : ");
            y = Integer.parseInt(sc.next());

            result = game.getPlayer1().shoot(new Location(x, y), game.getPlayer2());

            System.out.println("\n");
            game.getPlayer2().getMap().printMap("PLAYER2 MAP ");

            System.out.println("Player2");

            String res = "Touché.\n";
            if (result < 0)
                res = "Vous ne pouvez pas tirer ici.\n";
            else if (result == 0)
                res = "Raté.\n";

            System.out.println(res);
        }

    }
}
