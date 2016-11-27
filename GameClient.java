import java.rmi.*;
 
public class GameClient {

    public static void main (String[] args) {
	GameInterface game;
	try {
	    // System.setSecurityManager(new SecurityManager());
	    game = (GameInterface)Naming.lookup("rmi://localhost/game2:1099");
	    int myGameNO= game.getPlayerNO();
	    System.out.println("Result is :"+ myGameNO);
 
	}catch (Exception e) {
	    System.out.println("HelloClient exception: " + e);
	}
    }
}
