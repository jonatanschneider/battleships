package client;
import java.awt.*;
import java.io.IOException;

import javax.swing.JOptionPane;

public class ClientShootPhase extends game.ShootPhase {
	private ClientPlayer player;
	
	public ClientShootPhase(ClientPlayer player) {
		super();
		this.player = player;
	}

	protected void shoot(int[] coordinates) {
		int shot = -1;
		try {
			shot = Client.sendToServer(coordinates, 1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (shot == 1) {
			colorButton(coordinates, Color.red, true);
			JOptionPane.showMessageDialog(null, "Treffer!");
		}
		else if (shot == 2) {
			player.findOther(this.button, coordinates);
			colorButton(coordinates, Color.green, true);
			JOptionPane.showMessageDialog(null, "Treffer und versenkt!");
		}
		else if (shot == 0) {
			colorButton(coordinates, Color.gray, true);
			JOptionPane.showMessageDialog(null, "Kein Treffer!");
		}
		
		if(player.allShipsSunken()){
			int n = JOptionPane.showConfirmDialog(null, "Alle Schiffe versenkt, Spiel wird beendet","Spiel beenden?", JOptionPane.YES_NO_OPTION);
			if(n == 0){
				System.exit(0);
			}
		}
	}
}