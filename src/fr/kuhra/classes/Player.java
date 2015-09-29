package fr.kuhra.classes;

import fr.kuhra.enumerations.PlayerType;
import fr.kuhra.enumerations.Position;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Dimitri on 28/09/2015.
 */
public class Player {
    private String name;
    private Map map;

    public Player(PlayerType playerType){
        if (playerType.equals(PlayerType.COMPUTER))
            initComputerPlayer();
        else
            initPlayer();
    }

    public Player (String name){
        this.name = name;
        this.map = new Map(10);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map getMap() {
        return map;
    }

    public boolean canShoot(Ship shooter, Location location){
        int range = shooter.getRange();

        for (Location loc : shooter.getLocations())
            if (((location.getX() - loc.getX() >= 1 && location.getX() - loc.getX() <= range)
                    || (loc.getX() - location.getX() >=1 && loc.getX() - location.getX() <= range))
                    && ((location.getY() - loc.getY() >= 1 && location.getY() - loc.getY() <= range)
                    || loc.getY() - location.getY() >=1 && loc.getY() - location.getY() <= range))
                return true;

        return false;
    }

    public int shoot(int shipNumber, Location location, Player player){
        Ship shooter = map.getShips().get(shipNumber);
        int range = shooter.getRange();

        if (!canShoot(shooter, location))
            return -1;

        Pattern pattern = Pattern.compile("[0-9]");
        Matcher matcher = pattern.matcher(player.getMap().getMatrice()[location.getX()][location.getY()]);

        if (matcher.find()){
            map.getMatrice()[location.getX()][location.getY()] = " o ";
            player.getMap().getMatrice()[location.getX()][location.getY()] = " o ";
            return 1;
        }
        else{
            map.getMatrice()[location.getX()][location.getY()] = " x ";
            player.getMap().getMatrice()[location.getX()][location.getY()] = " x ";
            return 0;
        }
    }

    public void setMap(int size) {
        map = new Map(size);
    }

    private void initPlayer(){
        boolean isValid = false;
        Scanner sc = new Scanner(System.in);

        System.out.println("Bienvenue sur Battleship+ !\n" +
                "Entrez votre nom, nouveau joueur.");
        name = sc.nextLine();

        System.out.println("\nBonjour " + name + ", nous allons pouvoir commencer.\n" +
                "Choisissez la taille de votre terrain de jeu (comprise entre 10 et 25).");

        int mapSize = 0;
        while (!isValid) {
            sc.reset();
            mapSize = Integer.parseInt(sc.nextLine());

            if (mapSize >= 10 && mapSize <= 25)
                isValid = true;
            else
                System.out.println("\nEntrez une taille comprise entre 10 et 25 !");
        }
        setMap(mapSize);

        System.out.println("\nParfait, voici votre terrain actuel :\n");
        map.printMap();

        System.out.println("Vous disposez de cinq bateaux (taille, portée), entrez leurs coordonnées (x, y) ainsi que leur position (verticale/horizontale)\n");
        for (Ship ship : map.getShips()){
            isValid = false;
            System.out.println(ship.getName() + " (" + ship.getSize() + ", " + ship.getRange() + ") :");

            while (!isValid) {
                try {
                    sc.reset();
                    System.out.print("\tChoisissez un point d'abscisse (x) : ");
                    int x = Integer.parseInt(sc.next());

                    sc.reset();
                    System.out.print("\tChoisissez un point d'ordonnée (y) : ");
                    int y = Integer.parseInt(sc.next());

                    sc.reset();
                    Position position = null;
                    System.out.print("\tChoisissez une position (v/h) : ");

                    String pos = sc.next();
                    if (pos.compareTo("v") == 0)
                        position = Position.VERTICAL;
                    else if (pos.compareTo("h") == 0)
                        position = Position.HORIZONTAL;

                    if (position != null) {
                        ship.setLocation(new Location(x, y), position);

                        if (map.isCorrectlyLocated(ship)){
                            isValid = true;
                            map.printMap();
                        }
                        else
                            System.out.println("\nVotre navire est mal positionné !\n");
                    }
                }
                catch(Exception e){
                    System.out.println("Oops, une erreur est survenue !");
                    isValid = false;
                }
            }
        }
    }

    public void initComputerPlayer(){
        name = "Computer";
        //int mapSize = ThreadLocalRandom.current().nextInt(10, 25);
        int mapSize = 10;
        setMap(mapSize);

        boolean isValid = false;
        for (Ship ship : map.getShips()){
            isValid = false;

            while (!isValid) {
                int x = ThreadLocalRandom.current().nextInt(0, mapSize);
                int y = ThreadLocalRandom.current().nextInt(0, mapSize);
                int pos = ThreadLocalRandom.current().nextInt(0, 1);

                Position position = (pos == 0) ? Position.HORIZONTAL : Position.VERTICAL;

                ship.setLocation(new Location(x, y), position);

                if (map.isCorrectlyLocated(ship)){
                    isValid = true;
                }
            }
        }
        map.printMap();
    }
}
