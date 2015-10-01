package fr.kuhra.classes;

import fr.kuhra.enumerations.Position;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Dimitri on 28/09/2015.
 */
public class Ship {
    private String name;
    private int size;
    private int range;
    private HashMap<Location, Boolean> locations;
    private Position position;

    public Ship (String name, int size, int range){
        this.name = name;
        this.size = size;
        this.range = range;
        this.locations = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public int getRange() {
        return range;
    }

    public Position getPosition() {
        return position;
    }

    public HashMap<Location, Boolean> getLocations() {
        return locations;
    }

    public void setLocation(Location location, Position position) {
        this.position = position;
        locations.clear();
        locations.put(location, false);

        for (int i = 1; i < size; i++) {
            if (position.compareTo(Position.HORIZONTAL) == 0)
                locations.put(new Location(location.getX() + i, location.getY()), false);
            else
                locations.put(new Location(location.getX(), location.getY() + i), false);
        }
    }

    public void move(int x, int y){
        for(Location l : locations.keySet()){
            l.setX(l.getX() + x);
            l.setY(l.getY() + y);
        }
    }

    public boolean canMove(int x, int y){
        int xTmp, yTmp
                ;
        if(x + y < 3){
            for(Location l : locations.keySet()){
                xTmp = l.getX() + x;
                yTmp = l.getY() + y;
                if(xTmp > 9 || xTmp < 0 || yTmp > 9 || yTmp < 0){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
