package jonatanschneider;

import java.awt.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Player {
    List<Ship> ships = new ArrayList<>(10);
    private List<Point> possibleTargets = new ArrayList<>(100);

    public Player(){

        //Initialize possible targets
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                possibleTargets.add(new Point(i, j));
            }
        }
    }

    /**
     * Adds a new ship to this player
     * @param type Type of the ship
     * @param start Start point coordinate
     * @param end End point coordinate
     * @return true if adding was successful
     * @throws IllegalArgumentException if the ship can't be added
     * because there maximum is reached
     */
    public boolean addShip(Point start, Point end) throws IllegalArgumentException {
        if (! isCapacityReached(type)) {
            ships.add(new Ship(start, end));
            return true;
        }
        throw new IllegalArgumentException("Maximum number of " + type.name()
                + " for this playe reached");
    }

    /**
     * Checks if the maximum of a type for this player is reached
     * @param type Type of the ship
     * @return true if the capacity is reached and adding a new ship isn't allowed
     */
    private boolean isCapacityReached(ShipTypes type) {
        int counter = 0;
        for (Ship ship : ships) {
            if (ship.getShipType() == type) {
                counter++;
            }
        }
        if (counter >= type.maximumNumber) return true;
        return false;
    }

    /**
     * Checks if the shot hit a target and removes the point from the possible targets
     * @param other The target player
     * @param p The target point
     * @return true if the shot was a hit
     * @throws IllegalArgumentException if that point already got shot
     */
    public boolean shoot(Player other, Point p) throws IllegalArgumentException {
        if (! possibleTargets.contains(p)) throw new IllegalArgumentException("You already shot that point!");
        possibleTargets.remove(p);
        return other.isHit(p);
    }

    /**
     * Checks if the shot was a hit
     * @param shot Point of the shot
     * @return true if the shot was a hit
     */
    private boolean isHit(Point shot) {
        for (Ship ship : ships) {
            if (ship.isHit(shot)) {
                if (ship.isSunken()) {
                    System.out.println("The ship sinks!");
                }
                return true;
            }
        }
        return false;
    }

}
