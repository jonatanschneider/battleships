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
	static Socket clientSocket = null;
	static ObjectOutputStream serverOutput = null;
	static ObjectInputStream clientInput = null;
	static int status = -1;
	static int connectet = -1;
	public static int shoot = 1;
	
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
				Server.connectet = true;
				serverOutput = new ObjectOutputStream(clientSocket.getOutputStream());
				clientInput = new ObjectInputStream(clientSocket.getInputStream());
				JOptionPane.showMessageDialog(null,"Spieler Zwei ist Verbunden");
				int send = 0;
				while (true) {
					if(Server.player2.getStatus() == 1){
						shoot = 1;
						int response = clientInput.read();
						
						if(response == 2){
							JOptionPane.showMessageDialog(null, "Spieler B, gewinnt das Spiel!");
							clientSocket.close();
							serverOutput.close();
							clientInput.close();
							System.exit(0);
						}else{
						Server.enableField();
						while(shoot != 0){
							System.out.println("test");
						}
						}
						if(Server.win == 0){
							serverOutput.write(1);
						}else if(Server.win == 1){
							serverOutput.write(2);
						}
						serverOutput.flush();
						Server.disableField();
						shoot = 0;
					}else{
						Server.player2 = (Player)clientInput.readObject();
						while(send == 0){
							if(Server.player.getStatus() == 1){
							serverOutput.writeObject(Server.player);
							serverOutput.flush();
							send = 1;
							}
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
