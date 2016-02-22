package game;
import javax.swing.*;

import network.Client;
import ships.*;

import java.awt.Color;
import java.io.IOException;

public class clientPlayer {
	private int battleshipsToCreate;
	private int cruisersToCreate;
	private int destoryersToCreate;
	private int submarinesToCreate;
	private Ship[] ships;
	
	public clientPlayer(){
		this.battleshipsToCreate = 1;
		this.cruisersToCreate = 2;
		this.destoryersToCreate = 3;
		this.submarinesToCreate = 4;
		this.ships = new Ship[10];
	}
	
	public void setShip(int[] coordinates) throws IOException{
		for (int i = 0; i < this.ships.length; i++) {
			if(this.ships[i] == null){		
				int shipLength = SetPhase.calculateLengthBetweenCoordinates(coordinates);
				switch(shipLength){
				case 1:
					if(this.submarinesToCreate > 0){
						
						this.ships[i] = new Submarine();
						this.ships[i].setCoordinates(coordinates);
						int rueckgabe = Client.sendToServer(coordinates, 0);
						System.out.println(rueckgabe + "ausgabe");
						this.submarinesToCreate--;
						}
					else{
						JOptionPane.showMessageDialog(null, "Maximale Anzahl an U-Booten erreicht (4 Stück)!","Maximale Anzahl erreicht!",JOptionPane.ERROR_MESSAGE);
					}
						break;
				case 2:
					if(this.destoryersToCreate > 0){
						this.ships[i] = new Destroyer();
						this.ships[i].setCoordinates(coordinates);
						JOptionPane.showMessageDialog(null, "Zerstörer erstellt!","Schiff erstellt", JOptionPane.INFORMATION_MESSAGE);
						this.destoryersToCreate--;
					}
					else{
						JOptionPane.showMessageDialog(null, "Maximale Anzahl an Zerstörern erreicht (3 Stück)!","Maximale Anzahl erreicht!",JOptionPane.ERROR_MESSAGE);
					}
					break;
				case 3:
					if(this.cruisersToCreate > 0){
						this.ships[i] = new Cruiser();
						this.ships[i].setCoordinates(coordinates);
						JOptionPane.showMessageDialog(null, "Kreuzer erstellt!","Schiff erstellt", JOptionPane.INFORMATION_MESSAGE);
						this.cruisersToCreate--;
					}
					else{
						JOptionPane.showMessageDialog(null, "Maximale Anzahl an Kreuzern erreicht (2 Stück)!", "Maximale Anzahl erreicht",JOptionPane.ERROR_MESSAGE);
					}
					break;
				case 4:
					if(this.battleshipsToCreate > 0){
						this.ships[i] = new Battleship();
						this.ships[i].setCoordinates(coordinates);
						JOptionPane.showMessageDialog(null, "Schlachtschiff erstellt!","Schiff erstellt",JOptionPane.INFORMATION_MESSAGE);
						this.battleshipsToCreate--;
					}
					else{
						JOptionPane.showMessageDialog(null, "Maximale Anzahl an Schlachtschiffen erreicht (1 Stück)!","Maximale Anzahl erreicht",JOptionPane.ERROR_MESSAGE);
					}
					break;
				default:
					JOptionPane.showMessageDialog(null, "Deine Auswahl hat keine erlaubte Größe (min. 2, max. 5 Kästchen)!","Falsche Größe!",JOptionPane.ERROR_MESSAGE);
					break;
				}
				break;
			}
		}
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
	public JButton[][] deactiveSurroundingButtons(JButton[][] button, int x, int y){
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