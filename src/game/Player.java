package game;
import java.awt.Color;
import javax.swing.JButton;
import ships.Ship;

public class Player {
	protected int battleshipsToCreate;
	protected int cruisersToCreate;
	protected int destoryersToCreate;
	protected int submarinesToCreate;
	protected Ship[] ships;
	
	protected Player(){
		this.battleshipsToCreate = 1;
		this.cruisersToCreate = 2;
		this.destoryersToCreate = 3;
		this.submarinesToCreate = 4;
		this.ships = new Ship[10];
	}
	
	public JButton[][] showShips(JButton[][] button){
		for (Ship ship : this.ships) {
			if(ship != null){
				int[][] coordinates = new int[10][10];
				coordinates = ship.getCoordinates();
				for (int i = 0; i < coordinates.length; i++) {
					for (int j = 0; j < coordinates.length; j++) {
						if(coordinates[i][j] == 1){
							//TODO: X und y vertauscht
							button[j][i].setEnabled(false);
							button[j][i].setBackground(Color.blue);
							button = deactiveSurroundingButtons(button, i, j);
						}
					}
				}
			}
		}
		return button;
	}
	
	/** 
	 * Deaktiviert die umliegenden Buttons, an welchen keine weitere Schiffe platziert werden dürfen
	 * @param button Alle Buttons
	 * @param x Die X-Koordinate des Buttons von welchem die umliegenden Buttons deaktiviert werden sollen
	 * @param y Die Y-Koordinate des Buttons von welchem die umliegenden Buttons deaktiviert werden sollen
	 * @return Gibt alle Buttons, inklusive der deaktivierten, zurück
	 */
	protected JButton[][] deactiveSurroundingButtons(JButton[][] button, int x, int y){
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				try{
					button[y+j][x+i].setEnabled(false);
				}
					catch(ArrayIndexOutOfBoundsException e){
				}
			}
		}
		return button;
	}
	
	/**
	 * Übeprüft ob ein Schiff des Spielers getroffen wurde
	 * @param button Alle Buttons
	 * @param x X-Koordinate des Schusses
	 * @param y Y-Koordinate des Schusses
	 * @return 0 = Kein Treffer, 1 = Treffer, 2 = Treffer und versenkt
	 */
	public int isHit(JButton[][] button, int x, int y){
		for (Ship ship : ships) {
			if(ship != null){
				if(ship.isHit(x, y)){
					if(ship.isSunken()){
						shipIsSunken(button, ship);
						return 2;
					}
					return 1;
				}
			}
		}
		return 0;
	}
	
	public int serverisHit(int x, int y){
		for (Ship ship : ships) {
			if(ship != null){
				if(ship.isHit(x, y)){
					if(ship.isSunken()){		
						return 2;
					}
					return 1;
				}
			}
		}
		return 0;
	}
	
	public int serverisHit(JButton[][] button, int x, int y){
		for (Ship ship : ships) {
			if(ship != null){
				if(ship.isHit(x, y)){
					if(ship.isSunken()){
						return 2;
					}
					return 1;
				}
			}
		}
		return 0;
	}

	private void shipIsSunken(JButton[][] button, Ship ship){
		int[][] coordinates = ship.getCoordinates();
		for (int i = 0; i < coordinates.length; i++) {
			for (int j = 0; j < coordinates.length; j++) {
				if(coordinates[i][j] == 1){
					//TODO: x und y vertauscht
					button[j][i].setEnabled(false);
					button[j][i].setBackground(Color.green);
					ship.setSunken(true);
				}
			}
		}
	}

	public boolean allShipsSet(){
		for (Ship ship : ships) {
			if (ship == null){
				return false;
			}
		}
		return true;
	}
	
	public boolean allShipsSunken(){
		for (Ship ship : ships) {
			if(ship != null){
				if (ship.isSunken() == false){
					return false;
				}
			}
			else{
				return false;
			}
		}
		return true;
	}

}
