package jonatanschneider;

public enum ShipTypes {
    SUBMARINE(2, 4),
    DESTROYER(3, 3),
    CRUISER(4, 2),
    BATTLESHIP(5, 1),
    INVALID(-1, -1);

    final int length;
    final int maximumNumber;

    private ShipTypes(int length, int maximumNumber) {
        this.length = length;
        this.maximumNumber = maximumNumber;
    }

}
