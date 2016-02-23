package server;

import java.net.UnknownHostException;
import javax.swing.JOptionPane;

public class Server {
	public static ServerPlayer player = new ServerPlayer();
	public static ServerPlayer player2 = new ServerPlayer();

	public static void main(String[] args) throws UnknownHostException {

		try {
			new Network().start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		ServerSetPhase setShipFrame = new ServerSetPhase(player);
		setShipFrame.setResizable(false);
		setShipFrame.buttons(setShipFrame.getContentPane());
		setShipFrame.pack();
		setShipFrame.setTitle("Spieler A: Schiffe setzen");
		setShipFrame.setVisible(true);
		setShipFrame.setLocationRelativeTo(null);
		JOptionPane.showMessageDialog(setShipFrame,
				"Du kannst jetzt deine Schiffe setzen\n"
						+ "Klick dazu immer auf das Anfangs- und Endfeld");
	}

	public static void initiateShootPhase() {
		if (player2.getStatus() == 1) {
			Network.sendReady();
		} else {
			player.setStatus(1);
			JOptionPane.showMessageDialog(null, "Warte auf Spieler 2");
		}

		while (player2.getStatus() == 0) {

		}
		ServerShootPhase shootFrame = new ServerShootPhase(player2);
		shootFrame.setResizable(false);
		shootFrame.buttons(shootFrame.getContentPane());
		shootFrame.pack();
		shootFrame.setName("Spieler A: Felder beschieﬂen");
		shootFrame.setVisible(true);
		shootFrame.setLocationRelativeTo(null);
		JOptionPane.showMessageDialog(shootFrame,
				"Du kannst jetzt auf das Feld deines Gegners schieﬂen");
	}
}
