package client;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.JOptionPane;
import game.*;

public class SetPhase extends game.SetPhase {
	private Player player;
	
	public SetPhase(Player player) {
		super();
		this.player = player;
	}

	public void buttons(Container pane) {
		this.al = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String source = e.getSource().toString().substring(21);
				int[] coordinates = getCoordinatesOfClick(source);
				getCoordinatesCounter = 0;
				colorButton(coordinates, Color.red, false);

				if (clickCounter < 2) {
					if (clickCounter == 0) {
						clickCoordinates[0] = coordinates[0];
						clickCoordinates[1] = coordinates[1];
						clickCounter++;
					}
					else {
						clickCoordinates[2] = coordinates[0];
						clickCoordinates[3] = coordinates[1];
						clickCounter++;
						try {
							setShipByClickCoordinates();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
				if(player.allShipsSet()){
					setVisible(false);
					JOptionPane.showMessageDialog(null, "Alle Schiffe erstellt!");
					try {
						Client.initiateShootPhase();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		};
		this.setButtons();
		pane.add(this.panel, BorderLayout.NORTH);
	}

	private void setShipByClickCoordinates() throws IOException {
		this.player.setShip(this.clickCoordinates);
		this.clickCounter = 0;
		this.player.showShips(this.button);
	}
}