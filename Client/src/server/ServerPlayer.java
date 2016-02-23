package server;
import javax.swing.*;
import ships.Battleship;
import ships.*;
import java.awt.Color;

public class ServerPlayer extends game.Player{
	private int status;
	
	public ServerPlayer(){
		super();
		this.status = 0;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	public int getStatus() {
		return status;
	}
	
	public void setShip(int[] coordinates){
		for (int i = 0; i < this.ships.length; i++) {
			if(this.ships[i] == null){		
				int shipLength = ServerSetPhase.calculateLengthBetweenCoordinates(coordinates);
				switch(shipLength){
				case 1:
					if(this.submarinesToCreate > 0){
						
						this.ships[i] = new Submarine();
						this.ships[i].setCoordinates(coordinates);
						//JOptionPane.showMessageDialog(null, "U-Boot erstellt!","Schiff erstellt",JOptionPane.INFORMATION_MESSAGE);
						this.submarinesToCreate--;
						}
					else{
						JOptionPane.showMessageDialog(null, "Maximale Anzahl an U-Booten erreicht (4 St�ck)!","Maximale Anzahl erreicht!",JOptionPane.ERROR_MESSAGE);
					}
						break;
				case 2:
					if(this.destoryersToCreate > 0){
						this.ships[i] = new Destroyer();
						this.ships[i].setCoordinates(coordinates);
						//JOptionPane.showMessageDialog(null, "Zerst�rer erstellt!","Schiff erstellt", JOptionPane.INFORMATION_MESSAGE);
						this.destoryersToCreate--;
					}
					else{
						JOptionPane.showMessageDialog(null, "Maximale Anzahl an Zerst�rern erreicht (3 St�ck)!","Maximale Anzahl erreicht!",JOptionPane.ERROR_MESSAGE);
					}
					break;
				case 3:
					if(this.cruisersToCreate > 0){
						this.ships[i] = new Cruiser();
						this.ships[i].setCoordinates(coordinates);
						//JOptionPane.showMessageDialog(null, "Kreuzer erstellt!","Schiff erstellt", JOptionPane.INFORMATION_MESSAGE);
						this.cruisersToCreate--;
					}
					else{
						JOptionPane.showMessageDialog(null, "Maximale Anzahl an Kreuzern erreicht (2 St�ck)!", "Maximale Anzahl erreicht",JOptionPane.ERROR_MESSAGE);
					}
					break;
				case 4:
					if(this.battleshipsToCreate > 0){
						this.ships[i] = new Battleship();
						this.ships[i].setCoordinates(coordinates);
						//JOptionPane.showMessageDialog(null, "Schlachtschiff erstellt!","Schiff erstellt",JOptionPane.INFORMATION_MESSAGE);
						this.battleshipsToCreate--;
					}
					else{
						JOptionPane.showMessageDialog(null, "Maximale Anzahl an Schlachtschiffen erreicht (1 St�ck)!","Maximale Anzahl erreicht",JOptionPane.ERROR_MESSAGE);
					}
					break;
				default:
					JOptionPane.showMessageDialog(null, "Deine Auswahl hat keine erlaubte Gr��e (min. 2, max. 5 K�stchen)!","Falsche Gr��e!",JOptionPane.ERROR_MESSAGE);
					break;
				}
				break;
			}
		}
	}
		

}