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
	Network networkBuild = new Network();
	networkBuild.welcomeMSG("client");
	String inIp = networkBuild.getServerInIp();
	String exIp = networkBuild.getServerExIp();
	String port = networkBuild.getServerPort();

	game = networkBuild.clientConnect(inIp,exIp,port);
	//TODO: Skapa spelare inkl smeknamn?

	try {
	    int myGameNO = game.getPlayerNO();
	    System.out.println("Result is :"+ myGameNO);   
            
	    
	    int oldRound = 1;
	    int round = 1;
	    for (int i=0; i<5; i++) { //här ska vi egentligen ha en check att spelet inte är slut
		oldRound = game.getRound();
		while (oldRound == round) { //loopa tills det är en ny runda
		    round = game.getRound();
		}
	    
		Scanner userInput = new Scanner(System.in);
		boolean hit = true; //lol ändra sen
		if (hit == true) {
		    game.displayBoard();
		    long hitTime = timetoHit(); //innehåller tidsgrejer
		    //TODO: Change that particular Player's hit-time to this hitTime

		}else {
		    game.displayBoard();
		    System.out.println("Ready(r)/Next card(n)/Hit dick(h)?"); //ska ev vara i displayBoard
		
	  
		}
		//TODO: Uppdatera Player till att vara redo för nästa runda 

	    }
	    
	}
	catch (Exception e) {
	    System.out.println("Error " + e.getMessage());
	    e.printStackTrace();
	}
    }
}
