package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;
import game.Player;

public class Network extends Thread {

	static ServerSocket serverSocket = null;
	Socket clientSocket = null;
	static ObjectOutputStream serverOutput = null;
	static ObjectInputStream clientInput = null;
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
				serverOutput = new ObjectOutputStream(clientSocket.getOutputStream());
				clientInput = new ObjectInputStream(clientSocket.getInputStream());
				JOptionPane.showMessageDialog(null,"Spieler Zwei ist Verbunden");
				int send = 0;
				while (true) {
						Server.player2 = (Player)clientInput.readObject();
					while(send == 0){
						if(Server.player.getStatus() == 1){
						serverOutput.writeObject(Server.player);
						serverOutput.flush();
						send = 1;
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
