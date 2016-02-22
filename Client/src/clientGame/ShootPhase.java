package clientGame;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.JOptionPane;

public class ShootPhase extends game.Buttons {
	private ClientPlayer player;
	
	public ShootPhase(ClientPlayer player) {
		super();
		this.player = player;
	}

	public void buttons(Container pane) {
		this.al = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String source = e.getSource().toString().substring(21);
				getCoordinatesCounter = 0;
				int[] coordinates = getCoordinatesOfClick(source);
				shoot(coordinates);
			}
		};
		this.setButtons();
		pane.add(this.panel, BorderLayout.NORTH);
	}

	private void shoot(int[] coordinates) {
		int shot = -1;
		try {
			shot = main.sendToServer(coordinates, 1);
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