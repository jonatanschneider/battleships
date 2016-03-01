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

	static ServerSocket serverSocket = null;
	Socket clientSocket = null;
	static PrintWriter serverOutput = null;
	static BufferedReader clientInput = null;
	static int status = -1;
	static int connectet = -1;
	
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
				connectet = 1;
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
					}else if(incoming.equals("end")){
						clientInput.close();
						serverOutput.close();
						serverSocket.close();
						JOptionPane.showMessageDialog(null,
								"Spieler 2 hat das Spiel verlassen");
						System.exit(0);
					}else if(status == 0){
						serverOutput.write("end\n");
						serverOutput.flush();
						clientInput.close();
						serverOutput.close();
						serverSocket.close();
						System.exit(0);
						
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
			try {
				clientInput.close();
				serverSocket.close();
				serverOutput.close();
				System.exit(0);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public static void quit(){
			try {
				//serverOutput.write("end/n");
				//serverOutput.flush();
				serverSocket.close();
				System.exit(0);
			} catch (IOException e) {
				e.printStackTrace();
			}		
	}

	public static void sendReady() {
		serverOutput.write("ready\n");
		serverOutput.flush();
	}

}
