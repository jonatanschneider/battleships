package serverShips;

public class Ship {
	protected int length;
	protected int[][] coordinates;
	public int[][] hits;
	protected boolean isSunken;

	protected Ship() {
		this.isSunken = false;
		this.hits = new int[10][10];
	}

	public int[][] getCoordinates() {
		return coordinates;
	}	
	public void setSunken(boolean isSunken) {
		this.isSunken = isSunken;
	}
	public boolean getSunken(){
		return this.isSunken;
	}

	public void setCoordinates(int[] coordinates) {
		int x1, x2, y1, y2;
		// Den kleinsten x bzw. y Wert an den Beginn, falls die erste
		// Koordinate des Schiffes sich rechts oder unten befindet
		if (coordinates[0] > coordinates[2] || coordinates[1] > coordinates[3]) {
			x1 = coordinates[2];
			y1 = coordinates[3];
			x2 = coordinates[0];
			y2 = coordinates[1];
		}
		else {
			x1 = coordinates[0];
			y1 = coordinates[1];
			x2 = coordinates[2];
			y2 = coordinates[3];
		}
		int[][] results = new int[10][10];
		results[x1][y1] = 1;
		results[x2][y2] = 1;
		// Die Zwischen-Koordinaten ermitteln dabei entweder X oder Y Wert
		// ignorieren,
		// da einer von beiden Übereinstimmen muss
		if (x1 == x2) {
			for (int i = y1 + 1; i < y2; i++) {
				results[x1][i] = 1;
			}
		}
		else if (y1 == y2) {
			for (int i = x1 + 1; i < x2; i++) {
				results[i][y1] = 1;
			}
		}
		this.coordinates = results;
	}

	public boolean isSunken() {
		int counter = 0;
		for (int i = 0; i < hits.length; i++) {
			for (int j = 0; j < hits.length; j++) {
				if (hits[i][j] == 1) {
					counter++;
				}
			}
		}
		if (counter == this.length) {
			this.isSunken = true;
			return true;
		}
		return false;
	}

	public boolean isHit(int x, int y) {
		if (coordinates[x][y] == 1) {
			hits[x][y] = 1;
			return true;
		}
		else {
			return false;
		}
	}

}
