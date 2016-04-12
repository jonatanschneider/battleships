package client;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import game.Player;

public class Client extends Thread {
	public static Player player = new Player();
	public static Player player2 = new Player();
	public static Socket Socket = null;
	public static ObjectOutputStream clientOutput = null;
	public static ObjectInputStream serverInput = null;
	public static ShootPhase shootFrame;
	public static int win = 0;

	public static void main(String[] args) {
		init();
		initiateSetPhase();
	}

	private static void init() {
		try {
			Socket = new Socket("localhost", 8080);
			clientOutput = new ObjectOutputStream(Socket.getOutputStream());
			serverInput = new ObjectInputStream(Socket.getInputStream());
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"Es konnte keine Verbindung hergestellt werden");
			System.exit(0);
		}
	}

	private static void initiateSetPhase() {
		SetPhase setShipFrame = new SetPhase(player);
		setShipFrame.setResizable(false);
		setShipFrame.buttons(setShipFrame.getContentPane());
		setShipFrame.pack();
		setShipFrame.setTitle("Spieler B: Schiffe setzen");
		setShipFrame.setVisible(true);
		setShipFrame.setLocationRelativeTo(null);
		setShipFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		JOptionPane.showMessageDialog(setShipFrame,
				"Du kannst jetzt deine Schiffe setzen\n"
						+ "Klick dazu immer auf das Anfangs- und Endfeld");
		
		 setShipFrame.addWindowListener(new WindowAdapter() {
             public void windowClosing(WindowEvent event) {
                     closeApp();
             }
         }
      );     
	}

	public static void initiateShootPhase() throws IOException {
		waitForPlayer();
		while(player2.getStatus() == 0){
			System.out.println(player2.getStatus());
		}
		
		shootFrame = new ShootPhase(player2);
		shootFrame.setResizable(false);
		shootFrame.buttons(shootFrame.getContentPane());
		shootFrame.pack();
		shootFrame.setTitle("Spieler B: Felder beschieﬂen");
		shootFrame.setVisible(true);
		shootFrame.setLocationRelativeTo(null);
		shootFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
		JOptionPane.showMessageDialog(shootFrame,
				"Du kannst jetzt auf das Feld deines Gegners schieﬂen");
		shootFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
            		disableField();
                    //closeApp();
            }
        }
     );     
	}
	
	private static void disableField(){
		shootFrame.setVisible(false);
	}
	
	private static void enableField(){
		shootFrame.setVisible(true);
	}

	public static void waitForPlayer(){
		player.setStatus(1);
		try {
			clientOutput.writeObject(player);
			clientOutput.flush();
			while ((player2 = (Player)serverInput.readObject()) != null) {
//				System.out.println("Erfolgreich");
				break;
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void closeApp(){
		int end = JOptionPane.showConfirmDialog(null, 
				"Soll das Programm wirklich beendet werden?", 
				"Beenden", 
				JOptionPane.YES_NO_OPTION, 
				JOptionPane.QUESTION_MESSAGE); 
		if(end == 0){
			System.exit(0);
		}
	}
	
	public static void sendShoot(){
		disableField();
		try {
			
			if(win == 0){
				clientOutput.write(1);
			}else if(win == 1){
				clientOutput.write(2);
			}
			clientOutput.flush();
			int response;
			while ((response = serverInput.read()) != 0) {
				if(response == 1){
					enableField();
				}
				else if( response == 2){
					JOptionPane.showMessageDialog(null, "Spieler A, gewinnt das Spiel!");
				}
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
