package serverNetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JOptionPane;
import serverGame.main;

public class server extends Thread {
	
	ServerSocket serverSocket = null;
	Socket clientSocket = null;
	/*static PrintWriter out = null;
	BufferedReader in = null;*/
	static PrintWriter serverOutput = null;
	BufferedReader clientInput = null;
	
	public server(){	
			
			try {
				serverSocket = new ServerSocket(8080);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("gestartet");
	}
	
	public void run(){
		while(true){
			try {
			clientSocket = serverSocket.accept();
			clientInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			serverOutput = new PrintWriter(clientSocket.getOutputStream());
			JOptionPane.showMessageDialog(null, "Spieler Zwei ist Verbunden");
			while(true){	
				String incoming = null;
				incoming = clientInput.readLine();
				if(incoming.equals("ready")){
					if(main.player.getStatus() == 1){
						serverOutput.write("ready\n");
						serverOutput.flush();
						main.player2.setStatus(1);
					}else{
						main.player2.setStatus(1);
					}
				}else{
					int xStart = Integer.parseInt(incoming.substring(0,1));
					int y = Integer.parseInt(incoming.substring(1,2));
					int xend = Integer.parseInt(incoming.substring(2,3));
					int yend = Integer.parseInt(incoming.substring(3,4));
					int meth = Integer.parseInt(incoming.substring(4,5));
					System.out.println(xStart +"" + y + ""+ xend + ""+ yend + "" + meth );
					
					if(meth == 0){
						int coords[]= { xStart , y , xend , yend};
						for (int i = 0; i < coords.length; i++) {
							System.out.println(coords[i]);
						}
						main.player2.setShip(coords);
						System.out.println("test");
						serverOutput.write("1\n");
						serverOutput.flush();
					}else{
						int ergebniss = main.player.serverisHit(xStart,y);
						System.out.println(ergebniss);
						serverOutput.write(ergebniss+"\n");
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
	
	public static void sendReady(){
			serverOutput.write("ready\n");
			serverOutput.flush();
	}
	
}

