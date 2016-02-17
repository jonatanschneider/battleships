package game;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;

public class ShootPhase extends Buttons {

	public ShootPhase(Player player) {
		super();
		this.player = player;
		
	}

	public void buttons(Container pane) {
		this.panel.setPreferredSize(new Dimension(500, 500));
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
		int shot = this.player.isHit(this.button, coordinates[0], coordinates[1]);
		if (shot == 1) {
			colorButton(coordinates, Color.red, true);
			JOptionPane.showMessageDialog(null, "Treffer!");
		}
		else if (shot == 2) {
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