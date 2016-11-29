import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.*;
import java.net.InetAddress;
import java.net.*;
import java.io.*;

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
	    Registry registry = LocateRegistry.getRegistry( "83.255.61.11" , 1099 );
	    System.out.println("Registry found in " +  "83.255.61.11" + 
                               " :" + "1099" + "\n" + registry);
	    System.out.println("muppskit");

	    game = (GameInterface) registry.lookup("192.168.0.101/theGame:1099");

	    // System.setProperty("java.rmi.server.hostname", "83.255.61.11");
	    //game = (GameInterface)Naming.lookup("rmi://"+"83.255.61.11"+"/theGame:1099");
	    int myGameNO = game.getPlayerNO();
	    System.out.println("Result is :"+ myGameNO);
 	}catch (Exception e) {
	    System.out.println("HelloClient exception: " + e);
	    e.printStackTrace();
	    
	}
    }
}
