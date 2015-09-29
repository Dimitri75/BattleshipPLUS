package fr.kuhra.classes;

import fr.kuhra.enumerations.Position;

import java.util.ArrayList;

/**
 * Created by Dimitri on 28/09/2015.
 */
public class Ship {
    private String name;
    private int size;
    private int range;
    private ArrayList<Location> locations;
    private Position position;

    public Ship (String name, int size, int range){
        this.name = name;
        this.size = size;
        this.range = range;
        this.locations = new ArrayList<>();
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

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public void setLocation(Location location, Position position) {
        this.position = position;
        locations.clear();
        locations.add(location);

        for (int i = 1; i < size; i++) {
            if (position.compareTo(Position.HORIZONTAL) == 0)
                locations.add(new Location(location.getX() + i, location.getY()));
            else
                locations.add(new Location(location.getX(), location.getY() + i));
        }
    }
}
