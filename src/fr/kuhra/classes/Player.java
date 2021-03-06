package fr.kuhra.classes;

import fr.kuhra.enumerations.ConsoleSentence;
import fr.kuhra.enumerations.Direction;
import fr.kuhra.enumerations.PlayerType;
import fr.kuhra.enumerations.Position;

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Dimitri on 28/09/2015.
 */
public class Player {
    private String name;
    private Map map;
    private Map opponentMap;

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

    public Map getOpponentMap() {
        return opponentMap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOpponentMap(Player opponent) {
        this.opponentMap = new Map(opponent.getMap().getSize());
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

                    opponentMap.getMatrice()[location.getX()][location.getY()] = " X ";
                    opponent.getMap().getMatrice()[location.getX()][location.getY()] = " X ";
                    opponentShip.getLocations().put(location, true);

                    return 1;
                }

        opponentMap.getMatrice()[location.getX()][location.getY()] = " - ";
        opponent.getMap().getMatrice()[location.getX()][location.getY()] = " - ";

        return 0;
    }

    public int shootRandomLocation (Player opponent){
        int xIA = 0, yIA = 0, result = -1;
        while (result < 0) {
            xIA = new Random().nextInt(opponent.getMap().getSize());
            yIA = new Random().nextInt(opponent.getMap().getSize());
            result = shoot(new Location(xIA, yIA), opponent);
        }
        return result;
    }

    public boolean moveShip(int shipNumber, int x, int y){
        Ship movingShip = map.getShips().get(shipNumber);

        Ship tmpShip = new Ship(movingShip.getName(), movingShip.getSize(), movingShip.getRange());
        for (Location location : movingShip.getLocations().keySet()){
            tmpShip.getLocations().put(new Location(location.getX() + x, location.getY() + y), movingShip.getLocations().get(location));
        }

        if (map.hasShipCorrectlyMoved(tmpShip, movingShip)){
            map.getShips().remove(movingShip);
            map.getShips().add(shipNumber, tmpShip);
            return true;
        }
        return false;
    }

    public boolean moveShip(int shipNumber, Direction direction, int nbTiles){
        Ship movingShip = map.getShips().get(shipNumber);

        Ship tmpShip = new Ship(movingShip.getName(), movingShip.getSize(), movingShip.getRange());
        for (Location location : movingShip.getLocations().keySet()){
            switch (direction){
                case UP :
                    tmpShip.getLocations().put(new Location(location.getX(), location.getY() - nbTiles), movingShip.getLocations().get(location));
                    break;
                case DOWN :
                    tmpShip.getLocations().put(new Location(location.getX(), location.getY() + nbTiles), movingShip.getLocations().get(location));
                    break;
                case LEFT :
                    tmpShip.getLocations().put(new Location(location.getX() - nbTiles, location.getY()), movingShip.getLocations().get(location));
                    break;
                case RIGHT :
                    tmpShip.getLocations().put(new Location(location.getX() + nbTiles, location.getY()), movingShip.getLocations().get(location));
                    break;
            }
        }

        if (map.hasShipCorrectlyMoved(tmpShip, movingShip)){
            map.getShips().remove(movingShip);
            map.getShips().add(shipNumber, tmpShip);
            map.refreshShipLocations();
            return true;
        }
        return false;
    }

    public void setMap(int size) {
        map = new Map(size);
    }

    private void initPlayer(){
        boolean isValid = false;
        Scanner sc = new Scanner(System.in);

        System.out.println(ConsoleSentence.WELCOME);
        System.out.print(ConsoleSentence.ENTER_NAME);
        name = sc.nextLine();

        System.out.println(ConsoleSentence.CHOOSE_MAP_SIZE + " (10 à 25)");

        int mapSize = 0;
        while (!isValid) {
            sc.reset();
            mapSize = Integer.parseInt(sc.nextLine());

            if (mapSize >= 10 && mapSize <= 25)
                isValid = true;
            else
                System.out.println(ConsoleSentence.ERROR);
        }
        setMap(mapSize);

        map.printMap(ConsoleSentence.HERE_IS_YOUR_MAP.toString());

        System.out.println(ConsoleSentence.WHAT_YOU_DISPOSE);
        for (Ship ship : map.getShips()){
            isValid = false;
            System.out.println(ship.getName() + " (" + ship.getSize() + ", " + ship.getRange() + ") :");

            while (!isValid) {
                try {
                    sc.reset();
                    System.out.print("\t" + ConsoleSentence.CHOOSE_X);
                    int x = Integer.parseInt(sc.next());

                    sc.reset();
                    System.out.print("\t" + ConsoleSentence.CHOOSE_Y);
                    int y = Integer.parseInt(sc.next());

                    sc.reset();
                    Position position = null;
                    System.out.print("\t" + ConsoleSentence.CHOOSE_POSITION + "(v/h)");

                    String pos = sc.next();
                    if (pos.compareTo("v") == 0)
                        position = Position.VERTICAL;
                    else if (pos.compareTo("h") == 0)
                        position = Position.HORIZONTAL;

                    if (position != null) {
                        ship.setLocation(new Location(x, y), position);

                        if (map.isShipCorrectlyLocated(ship)){
                            isValid = true;
                            map.refreshShipLocations();
                            map.printMap(name);
                        }
                        else
                            System.out.println(ConsoleSentence.BAD_SHIP_LOCATION);
                    }
                }
                catch(Exception e){
                    System.out.println(ConsoleSentence.ERROR);
                    isValid = false;
                }
            }
        }
    }

    public void initComputerPlayer(){
        name = "Computer";
        //int mapSize = new Random().nextInt(15) + 10;
        int mapSize = 10;
        setMap(mapSize);

        boolean isValid;
        for (Ship ship : map.getShips()){
            isValid = false;
            while (!isValid) {

                int x = new Random().nextInt(map.getSize());
                int y = new Random().nextInt(map.getSize());
                int pos = new Random().nextInt(2);

                Position position = (pos == 0) ? Position.HORIZONTAL : Position.VERTICAL;

                ship.setLocation(new Location(x, y), position);

                if (map.isShipCorrectlyLocated(ship)) {
                    isValid = true;
                    map.refreshShipLocations();
                }
            }
        }
    }

    public boolean randomShipMovement() {
        HashMap<Integer, Direction> directionDictionary = new HashMap<>();
        directionDictionary.put(0, Direction.UP);
        directionDictionary.put(1, Direction.DOWN);
        directionDictionary.put(2, Direction.LEFT);
        directionDictionary.put(3, Direction.RIGHT);

        int randomDirection = new Random().nextInt(4);
        int nbTiles = new Random().nextInt(2) + 1;
        int randomShip = new Random().nextInt(5);

        return moveShip(randomShip, directionDictionary.get(randomDirection), nbTiles);
    }
}
