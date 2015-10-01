package fr.kuhra.classes;

import fr.kuhra.enumerations.Position;

import java.util.ArrayList;
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

    public void setShipLocations(){
        for (Ship ship : ships){
            if (!ship.getLocations().isEmpty()) {
                for (Location location : ship.getLocations().keySet()) {
                    if (ship.getLocations().get(location))
                        matrice[location.getX()][location.getY()] = " o ";
                    else
                        matrice[location.getX()][location.getY()] = " " + String.valueOf(ships.indexOf(ship)) + " ";
                }
            }
        }
    }

    public boolean isCorrectlyLocated(Ship ship){
        Pattern pattern = Pattern.compile("[0-9]");
        Matcher matcher;

        boolean isCorrect = true;
        try {
            for (Location location : ship.getLocations().keySet()) {
                matcher = pattern.matcher(matrice[location.getX()][location.getY()]);

                if (matcher.find())
                    isCorrect = false;
            }
        }
        catch (Exception e){
            return false;
        }
        return isCorrect;
    }

    public void printMap(String name){
        //setShipLocations();
        System.out.println(name);
        for (int y = 0; y < size; y++){
            for (int x = 0; x < size; x++)
                System.out.print(matrice[x][y]);
            System.out.println();
        }
        System.out.println();
    }
}
