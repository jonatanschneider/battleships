package clientGame;
import javax.swing.*;
import ships.*;
import java.awt.Color;
import java.io.IOException;

public class Player extends game.Player {
	
	public Player(){
		super();
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
//						JOptionPane.showMessageDialog(null, "U-Boot erstellt!","Schiff erstellt",JOptionPane.INFORMATION_MESSAGE);
						main.sendToServer(coordinates, 0);
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
//						JOptionPane.showMessageDialog(null, "Zerstörer erstellt!","Schiff erstellt", JOptionPane.INFORMATION_MESSAGE);
						main.sendToServer(coordinates, 0);
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
//						JOptionPane.showMessageDialog(null, "Kreuzer erstellt!","Schiff erstellt", JOptionPane.INFORMATION_MESSAGE);
						 main.sendToServer(coordinates, 0);
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
//						JOptionPane.showMessageDialog(null, "Schlachtschiff erstellt!","Schiff erstellt",JOptionPane.INFORMATION_MESSAGE);
						main.sendToServer(coordinates, 0);
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
		
	/*public void shipIsSunken(JButton[][] button, Ship ship){
		int[][] coordinates = ship.getCoordinates();
		for (int i = 0; i < coordinates.length; i++) {
			for (int j = 0; j < coordinates.length; j++) {
				if(coordinates[i][j] == 1){
					//TODO: x und y vertauscht
					button[j][i].setEnabled(false);
					button[j][i].setBackground(Color.green);
					//TODO Fehlt .setSunken(true) hier??
				}
			}
		}
	}*/
	
	public void findOther(JButton[][] button , int[] coords){
		int x = coords[0];
		int y = coords[1];
		for(Ship ship : ships ){
			int[][] coordinates = ship.getCoordinates();
			if(coordinates[x][y] == 1){
				for(int j = 0; j < coordinates.length; j++){
					for (int h = 0; h < coordinates.length; h++) {
						if(coordinates[j][h] == 1){
							button[h][j].setEnabled(false);
							button[h][j].setBackground(Color.green);
						}
					}
				}
			}
		}
	}
}