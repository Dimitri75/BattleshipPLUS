package fr.kuhra.classes;

/**
 * Created by Dimitri on 28/09/2015.
 */
public class Location {
    private int x;
    private int y;

    public Location (int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void change(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return x == ((Location) obj).getX() && y == ((Location) obj).getY();
    }
}
