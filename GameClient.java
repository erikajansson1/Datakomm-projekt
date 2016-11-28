import java.rmi.*;
import java.util.*;


public class GameClient {

    public static void main (String[] args) {
	GameInterface game;
	System.out.print("\033[2J\033[;H");
	System.out.printf("Welcome to the great Opposom game!" +
			   "\n" + 
			   "Which host would you lixe to join? \n IP: ");
	Scanner userInput = new Scanner(System.in);
	String ip  = userInput.nextLine();
	System.out.printf("\n port:");
	String port  = userInput.nextLine();

	try {
	    // System.setSecurityManager(new SecurityManager());
	    game = (GameInterface)Naming.lookup("rmi://"+ip+"/theGame:"+port);
	    int myGameNO = game.getPlayerNO();
	    System.out.println("Result is :"+ myGameNO);
 	}catch (Exception e) {
	    System.out.println("HelloClient exception: " + e);
	}
    }
}
