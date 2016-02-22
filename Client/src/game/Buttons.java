package game;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class Buttons extends JFrame {

	protected JButton[][] button;
	protected JPanel panel;
	protected ActionListener al;
	protected int getCoordinatesCounter;

	protected Buttons() {
		this.button = new JButton[10][10];
		this.panel = new JPanel(new GridLayout(10, 10));
		this.panel.setPreferredSize(new Dimension(500, 500));
		this.getCoordinatesCounter = 0;
	}

	protected void setButtons() {
		for (int i = 0; i < this.button.length; i++) {
			for (int j = 0; j < this.button.length; j++) {
				this.button[i][j] = new JButton();
				this.button[i][j].addActionListener(this.al);
				this.panel.add(button[i][j]);
			}
		}
	}

	protected int[] getCoordinatesOfClick(String source) {
		int coordinates[] = new int[2];
		coordinates[0] = -1;
		coordinates[1] = -1;

		// String kürzen, überprüfen ob Komma am Anfang steht und entsprechend
		// entfernen um x zu bestimmen, dann String erneut kürzen und Methode
		// rekursiv aufrufen um y Wert zu bestimmen
		if (source.substring(1).startsWith(",")) {
			// 1 stellige Ergebnisse gibt es bei dieser Größe nur bei 0
			coordinates[0] = 0;
			source = source.substring(2);
			//Counter sorgt dafür, dass ein String genau 2x durchlaufen wird um
			//x und y zu bestimmen
			if (this.getCoordinatesCounter == 0) {
				this.getCoordinatesCounter++;
				int[] tempCoordinates = this.getCoordinatesOfClick(source);
				coordinates[1] = tempCoordinates[0];
			}
		}
		else if (source.substring(2).startsWith(",")) {
			coordinates[0] = Integer.parseInt(source.substring(0, 2)) / 50;
			source = source.substring(3);
			if (this.getCoordinatesCounter == 0) {
				this.getCoordinatesCounter++;
				int[] tempCoordinates = this.getCoordinatesOfClick(source);
				coordinates[1] = tempCoordinates[0];
			}
		}
		else {
			coordinates[0] = Integer.parseInt(source.substring(0, 3)) / 50;
			source = source.substring(4);
			if (this.getCoordinatesCounter == 0) {
				this.getCoordinatesCounter++;
				int[] tempCoordinates = this.getCoordinatesOfClick(source);
				coordinates[1] = tempCoordinates[0];
			}
		}
		return coordinates;
	}

	public static int calculateLengthBetweenCoordinates(int[] coordinates) {
		int xOfStart = coordinates[0];
		int yOfStart = coordinates[1];
		int xOfEnd = coordinates[2];
		int yOfEnd = coordinates[3];

		if (xOfStart == xOfEnd) {
			if (yOfEnd > yOfStart) {
				return yOfEnd - yOfStart;
			}
			else {
				return yOfStart - yOfEnd;
			}
		}
		else if (yOfStart == yOfEnd) {
			if (xOfEnd > xOfStart) {
				return xOfEnd - xOfStart;
			}
			else {
				return xOfStart - xOfEnd;
			}
		}
		return -1;
	}

	protected void colorButton(int[] coordinates, Color color, boolean deactivateButton) {
		// TODO: Warum ist hier wieder x und y vertauscht?
		if(deactivateButton){
			this.button[coordinates[1]][coordinates[0]].setEnabled(false);
		}
		this.button[coordinates[1]][coordinates[0]].setBackground(color);
	}
	
	protected abstract void buttons(Container pane);
}
