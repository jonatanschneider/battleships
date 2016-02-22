package serverGame;
import java.awt.*;
import javax.swing.JOptionPane;

public class ShootPhase extends game.ShootPhase {
	private ServerPlayer player;
	
	public ShootPhase(ServerPlayer player) {
		super();
		this.player = player;
	}

	protected void shoot(int[] coordinates) {
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