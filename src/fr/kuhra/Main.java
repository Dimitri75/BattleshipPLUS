package fr.kuhra;

import fr.kuhra.classes.Game;
import fr.kuhra.classes.Location;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Game game = new Game();

        Scanner sc = new Scanner(System.in);
        int x, y, result = 0;

        while (result != 1) {
            sc.reset();
            System.out.print("Tir de Player1 en x : ");
            x = Integer.parseInt(sc.next());

            sc.reset();
            System.out.print("Tir de Player1 en y : ");
            y = Integer.parseInt(sc.next());

            result = game.getPlayer1().shoot(1, new Location(x, y), game.getPlayer2());

            System.out.println("\nPlayer1");
            game.getPlayer1().getMap().printMap();

            System.out.println("\nPlayer2");
            game.getPlayer2().getMap().printMap();

            String res = "Touché.\n";
            if (result < 0)
                res = "Vous ne pouvez pas tirer ici.\n";
            else if (result == 0)
                res = "Raté.\n";

            System.out.println(res);
        }
    }
}
