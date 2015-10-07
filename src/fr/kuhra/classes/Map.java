package fr.kuhra.classes;

import fr.kuhra.enumerations.Position;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Dimitri on 28/09/2015.
 */
public class Map {
    private int size;
    private String[][] matrice;
    private ArrayList<Ship> ships;
    private ArrayList<Location> impacts;

    public Map(int size){
        this.size = size;

        matrice = new String[size][size];

        for (int y = 0; y < size; y++){
            for (int x = 0; x < size; x++)
                matrice[x][y] = " . ";
        }

        ships = new ArrayList<>();
        impacts = new ArrayList<>();

        ships.add(new Ship("Porte-avion", 5, 2));
        ships.add(new Ship("Croiseur", 4, 2));
        ships.add(new Ship("Contre-torpilleur", 3, 2));
        ships.add(new Ship("Sous-marin", 3, 4));
        ships.add(new Ship("Torpilleur", 2, 5));
    }

    public int getSize() {
        return size;
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public String[][] getMatrice() {
        return matrice;
    }

    public void setImpacts(ArrayList<Location> impacts) {
        this.impacts = impacts;
    }

    public ArrayList<Location> getShipLocations(){
        ArrayList<Location> shipLocations = new ArrayList<>();
        for (Ship ship : ships)
            for (Location location : ship.getLocations().keySet())
                shipLocations.add(location);

        return shipLocations;
    }

    public ArrayList<Location> getOtherShipLocations(Ship ship){
        ArrayList<Location> otherShipLocations = new ArrayList<>();
        for (Ship otherShip : ships)
            if (!ship.equals(otherShip))
                for (Location location : otherShip.getLocations().keySet())
                    otherShipLocations.add(location);

        return otherShipLocations;
    }

    public void refreshShipLocations(){
        for (int y = 0; y < size; y++)
            for (int x = 0; x < size; x ++)
                matrice[x][y] = " . ";

        for (Ship ship : ships){
            if (!ship.getLocations().isEmpty()) {
                for (Location location : ship.getLocations().keySet()) {

                    if (ship.getLocations().get(location))
                        matrice[location.getX()][location.getY()] = " X ";
                    else
                        matrice[location.getX()][location.getY()] = " " + String.valueOf(ships.indexOf(ship)) + " ";
                }
            }
        }
    }

    public boolean isShipCorrectlyLocated(Ship ship){
        try {
            int sizeLeft = ship.getSize();
            TreeSet<Location> sortedShipLocations = new TreeSet<>(ship.getLocations().keySet());
            for (Location location : sortedShipLocations) {
                if (getOtherShipLocations(ship).contains(location) ||
                        (location.getX() + sizeLeft > size && location.getY() + sizeLeft > size) ||
                        location.getX() < 0 ||
                        location.getY() < 0)
                    return false;
                sizeLeft--;
            }
        }
        catch (Exception e){
            return false;
        }

        return true;
    }

    public boolean hasShipCorrectlyMoved(Ship newShip, Ship oldShip){
        try {
            for (Location location : newShip.getLocations().keySet())
                if (getOtherShipLocations(oldShip).contains(location) ||
                        location.getX() >= size ||
                        location.getY() >= size ||
                        location.getX() < 0 ||
                        location.getY() < 0)
                    return false;
        }
        catch (Exception e){
            return false;
        }

        return true;
    }

    public void printMap(String name){
        //refreshShipLocations();
        System.out.println(name);
        for (int y = 0; y < size; y++){
            for (int x = 0; x < size; x++)
                System.out.print(matrice[x][y]);
            System.out.println();
        }
        System.out.println();
    }
}
