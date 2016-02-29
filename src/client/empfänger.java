package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class empfänger extends Thread{
	
	public static Socket Sockets = null;
	private  PrintStream clientOutput = null;
	private BufferedReader serverInput = null;
	
	public void empfänger() throws UnknownHostException, IOException{
		Sockets = new Socket("localhost", 8080);
		
	}
	
	public void run(){	
	while(true){
		String serverResponse;
		try {
			try {
				serverInput = new BufferedReader(new InputStreamReader(Sockets.getInputStream()));
				clientOutput = new PrintStream(Sockets.getOutputStream(), true);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			while ((serverResponse = serverInput.readLine()) != null) {
				if (serverResponse.equals("end")) {
					System.out.println(serverResponse);
				}else{
					System.out.println("test");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
	}
	
}
