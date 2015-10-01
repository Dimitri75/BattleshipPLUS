package fr.kuhra.classes;

import fr.kuhra.enumerations.PlayerType;
import fr.kuhra.enumerations.Position;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Dimitri on 28/09/2015.
 */
public class Player {
    private String name;
    private Map map;
    private Map adversaryMap;

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

    public Map getMap() {
        return map;
    }

    public Map getAdversaryMap() {
        return adversaryMap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAdversaryMap(Player opponent) {
        this.adversaryMap = new Map(opponent.getMap().getSize());
    }

    public boolean canShoot(Location location){
        for (Ship ship : map.getShips()) {
            for (Location shipLocation : ship.getLocations().keySet()){
                double distance = Math.sqrt(Math.pow(shipLocation.getX() - location.getX(), 2) + Math.pow(shipLocation.getY() - location.getY(), 2));
                if (distance <= ship.getRange())
                    return true;
            }
        }
        return false;
    }

    public int shoot (Location location, Player opponent){
        if (!canShoot(location)) return -1;

        for (Ship opponentShip : opponent.getMap().getShips())
            for (Location shipLocation : opponentShip.getLocations().keySet())
                if (location.equals(shipLocation)){

                    adversaryMap.getMatrice()[location.getX()][location.getY()] = " X ";
                    opponent.getMap().getMatrice()[location.getX()][location.getY()] = " X ";
                    opponentShip.getLocations().put(location, true);

                    return 1;
                }

        adversaryMap.getMatrice()[location.getX()][location.getY()] = " - ";
        opponent.getMap().getMatrice()[location.getX()][location.getY()] = " - ";

        return 0;
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
        map.printMap(name);

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
                            map.refreshShipLocations();
                            map.printMap(name);
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

                int x = new Random().nextInt(map.getSize());
                int y = new Random().nextInt(map.getSize());
                int pos = new Random().nextInt(1);

                Position position = (pos == 0) ? Position.HORIZONTAL : Position.VERTICAL;

                ship.setLocation(new Location(x, y), position);

                if (map.isCorrectlyLocated(ship)){
                    isValid = true;
                }
            }
        }
        map.refreshShipLocations();
    }
}
