package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JOptionPane;

public class Network extends Thread {

	ServerSocket serverSocket = null;
	Socket clientSocket = null;
	static PrintWriter serverOutput = null;
	BufferedReader clientInput = null;

	public Network() {

		try {
			serverSocket = new ServerSocket(8080);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("gestartet");
	}

	public void run() {
		while (true) {
			try {
				clientSocket = serverSocket.accept();
				clientInput = new BufferedReader(new InputStreamReader(
						clientSocket.getInputStream()));
				serverOutput = new PrintWriter(clientSocket.getOutputStream());
				JOptionPane.showMessageDialog(null,
						"Spieler Zwei ist Verbunden");
				while (true) {
					String incoming = null;
					incoming = clientInput.readLine();
					if (incoming.equals("ready")) {
						if (Server.player.getStatus() == 1) {
							serverOutput.write("ready\n");
							serverOutput.flush();
							Server.player2.setStatus(1);
						} else {
							Server.player2.setStatus(1);
						}
					} else {
						int xOfStart = Integer.parseInt(incoming
								.substring(0, 1));
						int yOfStart = Integer.parseInt(incoming
								.substring(1, 2));
						int xOfEnd = Integer.parseInt(incoming.substring(2, 3));
						int yOfEnd = Integer.parseInt(incoming.substring(3, 4));
						int isShot = Integer.parseInt(incoming.substring(4, 5));
						System.out.println(xOfStart + "" + yOfStart + ""
								+ xOfEnd + "" + yOfEnd + "" + isShot);

						if (isShot == 0) {
							int coords[] = { xOfStart, yOfStart, xOfEnd, yOfEnd };
							for (int i = 0; i < coords.length; i++) {
								System.out.println(coords[i]);
							}
							Server.player2.setShip(coords);
							System.out.println("test");
							serverOutput.write("1\n");
							serverOutput.flush();
						} else {
							int ergebniss = Server.player.serverisHit(xOfStart,
									yOfStart);
							System.out.println(ergebniss);
							serverOutput.write(ergebniss + "\n");
							serverOutput.flush();
						}
					}
				}
			} catch (IOException e) {
				try {
					clientInput.close();
					serverOutput.close();
					serverSocket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}
	}

	public static void sendReady() {
		serverOutput.write("ready\n");
		serverOutput.flush();
	}

}
