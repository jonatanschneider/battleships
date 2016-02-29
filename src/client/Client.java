package client;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Client extends Thread {
	public static Player player = new Player();
	public static Player player2 = new Player();
	public static Socket Socket = null;
	public static PrintStream clientOutput = null;
	public static BufferedReader serverInput = null;

	public static void main(String[] args) {
		init();
		initiateSetPhase();
	}

	private static void init() {
		try {
			Socket = new Socket("localhost", 8080);
			clientOutput = new PrintStream(Socket.getOutputStream(), true);
			serverInput = new BufferedReader(new InputStreamReader(
					Socket.getInputStream()));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"Es konnte keine Verbindung hergestellt werden");
			System.exit(0);
		}
	}

	private static void initiateSetPhase() {
		Player player = new Player();
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

	public static void initiateShootPhase(Player player)
			throws IOException {
		int warte = sendReady();
		System.out.println(warte);
		ShootPhase shootFrame = new ShootPhase(player);
		shootFrame.setResizable(false);
		shootFrame.buttons(shootFrame.getContentPane());
		shootFrame.pack();
		shootFrame.setName("Spieler B: Felder beschießen");
		shootFrame.setVisible(true);
		shootFrame.setLocationRelativeTo(null);
		shootFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
		JOptionPane.showMessageDialog(shootFrame,
				"Du kannst jetzt auf das Feld deines Gegners schießen");
		shootFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                    closeApp();
            }
        }
     );     
	}

	public static int sendToServer(int[] coordinates, int isShot)
			throws IOException {
		try {
			// _Socket.setSoTimeout(5000);
			int xOfStart = coordinates[0];
			int yOfStart = coordinates[1];
			int xOfEnd = 0;
			int yOfEnd = 0;
			if (isShot == 0) {
				xOfEnd = coordinates[2];
				yOfEnd = coordinates[3];
			}

			clientOutput.print(xOfStart + "" + yOfStart + "" + xOfEnd + ""
					+ yOfEnd + "" + isShot + "\n");
			clientOutput.flush();
			String serverResponse = null;
			while ((serverResponse = serverInput.readLine()) != null) {
				try{
					if (Integer.parseInt(serverResponse) >= 0) {
						System.out.println(serverResponse);
						return Integer.parseInt(serverResponse);
					}
				}catch(NumberFormatException e){
					if(serverResponse.equals("end")){
						JOptionPane.showMessageDialog(null,
								"Spieler 1 hat das Spiel verlassen");
						System.exit(0);
					}
				}
			}
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null,
					"Zeitüberschreiung bei Serveranfrage");
		} catch (IOException e){
		}
		return -1;

	}

	public static int sendReady() throws IOException {
		try {
			// _Socket.setSoTimeout(5000);
			clientOutput.print("ready\n");
			clientOutput.flush();
			String serverResponse = null;
			JOptionPane.showMessageDialog(null, "Warte auf Spieler1");
			while ((serverResponse = serverInput.readLine()) != null) {
				if (serverResponse.equals("ready")) {
					System.out.println(serverResponse);
					return 1;
				}else if(serverResponse.equals("end")){
					JOptionPane.showMessageDialog(null,
							"Spieler 1 hat das Spiel verlassen");
				}
			}
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null,
					"Zeitüberschreiung bei Serveranfrage");
		} catch (IOException e) {
		}
		return 0;

	}
	
	public static void quit(){
		clientOutput.print("end\n");
		clientOutput.flush();
	}
	
	private static void closeApp(){
		int end = JOptionPane.showConfirmDialog(null, 
				"Soll das Programm wirklich beendet werden?", 
				"Beenden", 
				JOptionPane.YES_NO_OPTION, 
				JOptionPane.QUESTION_MESSAGE); 
		if(end == 0){
			quit();
			System.exit(0);
		}
	}
}
