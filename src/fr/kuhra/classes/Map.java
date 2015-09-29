package fr.kuhra.classes;

import fr.kuhra.enumerations.Position;

import java.util.ArrayList;

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
            for (Location location : ship.getLocations())
                shipLocations.add(location);

        return shipLocations;
    }

    public void setShipLocations(){
        for (Ship ship : ships){
            for (Location location : ship.getLocations()){
                matrice[location.getX()][location.getY()] = " " + String.valueOf(ships.indexOf(ship)) + " ";
            }
        }
    }

    public boolean isCorrectlyLocated(Ship ship){
        boolean isCorrect = true;
        try {
            for (Location location : ship.getLocations()) {
                if (matrice[location.getX()][location.getY()] != " . ") {
                    isCorrect = false;
                }
            }
        }
        catch (Exception e){
            return false;
        }
        return isCorrect;
    }

    public void printMap(){
        setShipLocations();
        for (int y = 0; y < size; y++){
            for (int x = 0; x < size; x++)
                System.out.print(matrice[x][y]);
            System.out.println();
        }
        System.out.println();
    }
}
