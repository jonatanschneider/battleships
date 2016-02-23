package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;

public class Client {
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
		JOptionPane.showMessageDialog(setShipFrame,
				"Du kannst jetzt deine Schiffe setzen\n"
						+ "Klick dazu immer auf das Anfangs- und Endfeld");
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
		JOptionPane.showMessageDialog(shootFrame,
				"Du kannst jetzt auf das Feld deines Gegners schießen");
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
				if (Integer.parseInt(serverResponse) >= 0) {
					System.out.println(serverResponse);
					return Integer.parseInt(serverResponse);
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
				}
			}
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null,
					"Zeitüberschreiung bei Serveranfrage");
		} catch (IOException e) {
		}
		return 0;

	}
}
