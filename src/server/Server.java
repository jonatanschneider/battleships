package server;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Server {
	public static Player player = new Player();
	public static Player player2 = new Player();

	public static void main(String[] args) throws UnknownHostException {

		try {
			new Network().start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		SetPhase setShipFrame = new SetPhase(player);
		setShipFrame.setResizable(false);
		setShipFrame.buttons(setShipFrame.getContentPane());
		setShipFrame.pack();
		setShipFrame.setTitle("Spieler A: Schiffe setzen");
		setShipFrame.setVisible(true);
		setShipFrame.setLocationRelativeTo(null);
		setShipFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		JOptionPane.showMessageDialog(setShipFrame,
				"Du kannst jetzt deine Schiffe setzen\n"
						+ "Klick dazu immer auf das Anfangs- und Endfeld");
		setShipFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                    try {
						closeApp();
					} catch (IOException e) {
						e.printStackTrace();
					}
            }
        }
     );     
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
		ShootPhase shootFrame = new ShootPhase(player2);
		shootFrame.setResizable(false);
		shootFrame.buttons(shootFrame.getContentPane());
		shootFrame.pack();
		shootFrame.setName("Spieler A: Felder beschieﬂen");
		shootFrame.setVisible(true);
		shootFrame.setLocationRelativeTo(null);
		shootFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		JOptionPane.showMessageDialog(shootFrame,
				"Du kannst jetzt auf das Feld deines Gegners schieﬂen");
		
		shootFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                    try {
						closeApp();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            }
        }
     );     
	}
	
	private static void closeApp() throws IOException{
		int end = JOptionPane.showConfirmDialog(null, 
				"Soll das Programm wirklich beendet werden?", 
				"Beenden", 
				JOptionPane.YES_NO_OPTION, 
				JOptionPane.QUESTION_MESSAGE); 
		if(end == 0){
			Network.status = 0;
		}
	}
}
