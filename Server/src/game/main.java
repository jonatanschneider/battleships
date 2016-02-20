package game;

import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import network.Client;
import network.server;

public class main {
	public static Player player = new Player();
	public static Player player2 = new Player();
	
	public static void main(String[] args) throws UnknownHostException {	
		
		try{
			 new server().start();
		}catch (Exception e){
			e.printStackTrace();
		}
		
	SetPhase setShipFrame = new SetPhase(player);		
	        setShipFrame.setResizable(false);  
	        setShipFrame.buttons(setShipFrame.getContentPane());
	        setShipFrame.pack();
	        setShipFrame.setTitle("Spieler A: Schiffe setzen");
	        setShipFrame.setVisible(true);
	        setShipFrame.setLocationRelativeTo(null);
	        JOptionPane.showMessageDialog(setShipFrame, "Du kannst jetzt deine Schiffe setzen\n"
	        		+ "Klick dazu immer auf das Anfangs- und Endfeld");
	  	}
	
	public static void initiateShootPhase(){
		if(player2.getStatus() == 1){
			server.sendReady();
		}else{
			player.setStatus(1);
			JOptionPane.showMessageDialog(null, "Warte auf Spieler 2");
		}
		
		while(player2.getStatus() == 0){
			System.out.println("warte");
		}
		ShootPhase shootFrame = new ShootPhase(player2);
		shootFrame.setResizable(false);  
        shootFrame.buttons(shootFrame.getContentPane());
        shootFrame.pack();
        shootFrame.setName("Spieler A: Felder beschieﬂen");
        shootFrame.setVisible(true);
        shootFrame.setLocationRelativeTo(null);
        JOptionPane.showMessageDialog(shootFrame, "Du kannst jetzt auf das Feld deines Gegners schieﬂen");
	}
}
