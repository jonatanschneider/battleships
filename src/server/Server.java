package server;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import server.ShootPhase;
import game.*;

public class Server {
	public static Player player = new Player();
	public static Player player2 = new Player();
	public static ShootPhase shootFrame;
	public static int win = 0;
	public static boolean connectet = false;
	
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
		player.setStatus(1);
		while (player2.getStatus() == 0) {
			
		}
		
		shootFrame = new ShootPhase(player2);
		shootFrame.setResizable(false);
		shootFrame.buttons(shootFrame.getContentPane());
		shootFrame.pack();
		shootFrame.setTitle("Spieler A: Felder beschieﬂen");
		shootFrame.setVisible(false);
		shootFrame.setLocationRelativeTo(null);
		shootFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
		JOptionPane.showMessageDialog(shootFrame,
				"Du kannst jetzt auf das Feld deines Gegners schieﬂen");
		
		shootFrame.addWindowListener(new WindowAdapter() {
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

	
	public static void disableField(){
		shootFrame.setVisible(false);
	}
	
	public static void enableField(){
		shootFrame.setVisible(true);
	}
	
	private static void closeApp() throws IOException{
		if(connectet){
		Network.serverOutput.close();
		Network.clientInput.close();
		Network.clientSocket.close();
		System.exit(0);
		}else{
			System.exit(0);
		}
	}
	
}
