package jonatanschneider;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Ship {
    private ShipTypes shipType;
    private boolean isSunken = false;
    private boolean[] hits;
    private List<Point> coordinates;

    /**
     * Gets the ship type by calculating it's length
     * @param start Start Point of the ship
     * @param end End Point of the ship
     * @return enum ShipType
     */
    public static ShipTypes getShipTypeByPoints(Point start, Point end) {
        int length;
        if (start.x == end.x) {
            length = Math.abs(start.y - end.y);
        } else {
            length = Math.abs(start.x - end.x);
        }
        switch (length) {
            case 2:
                return ShipTypes.SUBMARINE;
            case 3:
                return ShipTypes.DESTROYER;
            case 4:
                return ShipTypes.CRUISER;
            case 5:
                return ShipTypes.BATTLESHIP;
            default:
                System.out.println("Invalid coordinates!");
                return ShipTypes.INVALID;
        }
    }
    public Ship(ShipTypes type, Point start, Point end) {

    }

    public Ship(Point start, Point end) {
        shipType = getShipTypeByPoints(start, end);
        hits = new boolean[shipType.length];
        coordinates = new ArrayList<>(this.shipType.length);
        setShip(start, end);
    }

    public ShipTypes getShipType(){
        return shipType;
    }

    /**
     * Sets the coordinates of the ship in the field based on their start and end points
     *
     * @param start Coordinates of the start point of the ship
     * @param end   Coordinates of the end point of the ship
     * @throws IllegalArgumentException If start and end coordinates are the same
     */
    private void setShip(Point start, Point end) throws IllegalArgumentException {
        if (start.equals(end)) throw new IllegalArgumentException("Coordinates are not possible!");

        //correct start and end if they're reversed
        if (start.x > end.x) {
            int temp = start.x;
            start.x = end.x;
            end.x = temp;
        } else if (start.y > end.y) {
            int temp = start.y;
            start.y = end.y;
            end.y = temp;
        }

        coordinates.add(start);
        coordinates.add(end);

        //get coordinates in between the start and end points
        //X or Y gets ignored because one of them has to be equal in start Point and end Point
        if (start.x == end.x) {
            for (int i = start.y + 1; i < end.y; i++) {
                coordinates.add(new Point(start.x, i));
            }
        } else if (start.y == end.y) {
            for (int i = start.x + 1; i < end.y; i++) {
                coordinates.add(new Point(i, start.y));
            }
        }
    }

    /**
     * Checks if the shot has hit the ship
     *
     * @param shot Coordinate of the shot
     * @return Is ship hit
     */
    public boolean isHit(Point shot) {
        for (Point p : coordinates) {
            if (p.equals(shot)) {
                setHit(p);
                return true;
            }
        }
        return false;
    }

    /**
     * Sets hits[] to true at the position of the related coordinate
     *
     * @param p Point where the ship got hit
     */
    private void setHit(Point p) {
        hits[coordinates.indexOf(p)] = true;
    }

    /**
     * Checks whether or not the ship is already sunken
     *
     * @return boolean isSunken
     */
    public boolean isSunken() {
        for (boolean b : hits) {
            if (! b) return false;
        }
        return (isSunken = true);
    }

}
