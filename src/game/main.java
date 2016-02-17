package game;

import javax.swing.JOptionPane;

public class main {

	public static void main(String[] args) {
		Player player = new Player();
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
	public static void initiateShootPhase(Player player){
		ShootPhase shootFrame = new ShootPhase(player);
		shootFrame.setResizable(false);  
        shootFrame.buttons(shootFrame.getContentPane());
        shootFrame.pack();
        shootFrame.setName("Spieler A: Felder beschieﬂen");
        shootFrame.setVisible(true);
        shootFrame.setLocationRelativeTo(null);
        JOptionPane.showMessageDialog(shootFrame, "Du kannst jetzt auf das Feld deines Gegners schieﬂen");
	}
}
