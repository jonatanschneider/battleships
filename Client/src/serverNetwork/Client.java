package serverNetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import serverGame.*;

public class Client extends JFrame {

	public static Socket _Socket = null;
	public static PrintStream _out = null;
	public static BufferedReader _in = null;

	public Client() throws UnknownHostException {
		super();
		init();
		initiateSetPhase();

	}

	public static void init() throws UnknownHostException {
		try {
			_Socket = new Socket("localhost", 8080);
			_out = new PrintStream(_Socket.getOutputStream(), true);
			_in = new BufferedReader(new InputStreamReader(_Socket.getInputStream()));
		}
		catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Es konnte keine Verbindung hergestellt werden");
		}
	}

	public void initiateSetPhase() {
		ServerPlayer player = new ServerPlayer();
		ServerSetPhase setShipFrame = new ServerSetPhase(player);
		setShipFrame.setResizable(false);
		setShipFrame.buttons(setShipFrame.getContentPane());
		setShipFrame.pack();
		setShipFrame.setTitle("Spieler B: Schiffe setzen");
		setShipFrame.setVisible(true);
		setShipFrame.setLocationRelativeTo(null);
		JOptionPane.showMessageDialog(setShipFrame,
				"Du kannst jetzt deine Schiffe setzen\n" + "Klick dazu immer auf das Anfangs- und Endfeld");
	}

	public static void initiateShootPhase(ServerPlayer player) {
		ServerShootPhase shootFrame = new ServerShootPhase(player);
		shootFrame.setResizable(false);
		shootFrame.buttons(shootFrame.getContentPane());
		shootFrame.pack();
		shootFrame.setName("Spieler A: Felder beschießen");
		shootFrame.setVisible(true);
		shootFrame.setLocationRelativeTo(null);
		JOptionPane.showMessageDialog(shootFrame, "Du kannst jetzt auf das Feld deines Gegners schießen");
	}

	public static int sendToServer(int[] coords, int meth) throws IOException {
		try {
			_Socket.setSoTimeout(500);
			int x = coords[0];
			int y = coords[1];
			int endx = coords[2];
			int endy = coords[3];

			_out.print(x + "" + y + "" + endx + "" + endy + "" + meth + "\n");
			_out.flush();
			String serverResponse = null;
			while ((serverResponse = _in.readLine()) != null) {
				if (Integer.parseInt(serverResponse) > 0) {
					System.out.println(serverResponse);
					return Integer.parseInt(serverResponse);
				}
			}
		}
		catch (UnknownHostException e) {
		}
		catch (IOException e) {
		}
		return -1;

	} // run()*/
}
