package clientGame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import game.Buttons;
import javax.swing.JOptionPane;

public class main {
	public static Player player = new Player();
	public static Player player2 = new Player();
	public static Socket _Socket = null;
	public static PrintStream _out = null;
	public static BufferedReader _in = null;
	
	public static void main(String[] args){	
		init();
		initiateSetPhase();	
	}
		
		private static void init(){
			try{
			_Socket = new Socket("localhost", 8080);  
			_out = new PrintStream(_Socket.getOutputStream(), true);     
			_in = new BufferedReader(new InputStreamReader(_Socket.getInputStream()));          
			}catch(IOException e){
				JOptionPane.showMessageDialog(null, "Es konnte keine Verbindung hergestellt werden");
				System.exit(0);
			}
		}
		
		private static void initiateSetPhase(){
			Player player = new Player();
			SetPhase setShipFrame = new SetPhase(player);		
		        setShipFrame.setResizable(false);  
		        setShipFrame.buttons(setShipFrame.getContentPane());
		        setShipFrame.pack();
		        setShipFrame.setTitle("Spieler B: Schiffe setzen");
		        setShipFrame.setVisible(true);
		        setShipFrame.setLocationRelativeTo(null);
		        JOptionPane.showMessageDialog(setShipFrame, "Du kannst jetzt deine Schiffe setzen\n"
		        		+ "Klick dazu immer auf das Anfangs- und Endfeld");
		}
		
		public static void initiateShootPhase(Player player) throws IOException{
			int warte = sendReady();
			System.out.println(warte);
			ShootPhase shootFrame = new ShootPhase(player);
			shootFrame.setResizable(false);  
	        shootFrame.buttons(shootFrame.getContentPane());
	        shootFrame.pack();
	        shootFrame.setName("Spieler B: Felder beschießen");
	        shootFrame.setVisible(true);
	        shootFrame.setLocationRelativeTo(null);
	        JOptionPane.showMessageDialog(shootFrame, "Du kannst jetzt auf das Feld deines Gegners schießen");
		}
		
		public static int sendToServer(int[] coords, int meth) throws IOException{    	                            
			try{
				//_Socket.setSoTimeout(5000);
				int x = coords[0];
				int y = coords[1];
				int endx = 0;
				int endy = 0;
				if(meth == 0){
				endx = coords[2];
				endy = coords[3];
				}
				
				_out.print(x +"" + y + ""+ endx + ""+ endy + "" + meth + "\n");   
				_out.flush();
		        String serverResponse = null;	       
	            while ((serverResponse = _in.readLine()) != null){
	            	if(Integer.parseInt(serverResponse) >= 0){            	
	            		System.out.println(serverResponse);
	            		return Integer.parseInt(serverResponse);
	            	}
	            }                  
			} catch (UnknownHostException e) {
				JOptionPane.showMessageDialog(null, "Zeitüberschreiung bei Serveranfrage");
	        } catch (IOException e) {       
	        }
			return -1;
		
	    } // run()*/
		
		public static int sendReady() throws IOException{    	                            
			try{
				//_Socket.setSoTimeout(5000);		
				_out.print("ready\n");   
				_out.flush();
		        String serverResponse = null;	
		        JOptionPane.showMessageDialog(null, "Warte auf Spieler1");
	            while ((serverResponse = _in.readLine()) != null){
	            	if(serverResponse.equals("ready")){            	
	            		System.out.println(serverResponse);
	            		return 1;
	            	}
	            }                  
			} catch (UnknownHostException e) {
				JOptionPane.showMessageDialog(null, "Zeitüberschreiung bei Serveranfrage");
	        } catch (IOException e) {       
	        }
			return 0;
		
	    } // run()*/
}
