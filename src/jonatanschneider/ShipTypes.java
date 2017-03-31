package jonatanschneider;

public enum ShipTypes {
    SUBMARINE(2),
    DESTROYER(3),
    CRUISER(4),
    BATTLESHIP(5);

    final int length;

    ShipTypes(int length) {
        this.length = length;
    }
}
