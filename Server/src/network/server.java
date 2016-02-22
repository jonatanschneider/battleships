package network;

import game.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JOptionPane;

public class server extends Thread {
	
	ServerSocket 	serverSocket	= null;
	Socket 			clientSocket 	= null;
	static PrintWriter 	out 			= null;
	BufferedReader 	in 				= null;
	
	public server(){	
			
			try {
				serverSocket = new ServerSocket(8080);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("gestartet");
	}
	
	public void run(){
		while(true){
			try {
			clientSocket = serverSocket.accept();
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out = new PrintWriter(clientSocket.getOutputStream());
			JOptionPane.showMessageDialog(null, "Spieler Zwei ist Verbunden");
			while(true){	
				String incomming = null;
				incomming = in.readLine();
				if(incomming.equals("ready")){
					if(main.player.getStatus() == 1){
						out.write("ready\n");
						out.flush();
						main.player2.setStatus(1);
					}else{
						main.player2.setStatus(1);
					}
				}else{
					int x = Integer.parseInt(incomming.substring(0,1));
					int y = Integer.parseInt(incomming.substring(1,2));
					int xend = Integer.parseInt(incomming.substring(2,3));
					int yend = Integer.parseInt(incomming.substring(3,4));
					int meth = Integer.parseInt(incomming.substring(4,5));
					System.out.println(x +"" + y + ""+ xend + ""+ yend + "" + meth );
					
					if(meth == 0){
						int coords[]= { x , y , xend , yend};
						for (int i = 0; i < coords.length; i++) {
							System.out.println(coords[i]);
						}
						main.player2.setShip(coords);
						System.out.println("test");
						out.write("1\n");
						out.flush();
					}else{
						int ergebniss = main.player.serverisHit(x,y);
						System.out.println(ergebniss);
						out.write(ergebniss+"\n");
						out.flush();
					}	
				}
			}
			} catch (IOException e) {
				try {
					in.close();
					out.close();
					serverSocket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}
		
	}// run
	
	public static void sendReady(){
			out.write("ready\n");
			out.flush();
	}
	
}

