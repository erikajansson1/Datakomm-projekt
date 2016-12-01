import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.*;
import java.net.InetAddress;
import java.net.*;
import java.io.*;

public class GameClient {

    	//If it's possible to hit start time 
	public static long timeToHit(){ 
	    Scanner userInput = new Scanner(System.in);
	    long startTime = System.nanoTime();

	    System.out.println("Ready(r)/Next card(n)/Hit dick(h)?");
	    long hitTime = System.nanoTime() - startTime);   

	    while(hitTime < 300000) { //godtyckligt taget tal här
		String answer = userInput.nextLine();
		hitTime = System.nanoTime() - startTime);  
	    }

	    return hitTime;		 
	} 

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
		//TODO: kollar ifall det är spelarens tur
		//ifall personen fortfarande deltar i spelet eller har vunnit.
		Scanner userInput = new Scanner(System.in);
		boolean hit = true; //lol ändra sen
		if (hit == true) {
		    game.displayBoard();
		    long hitTime = timetoHit(); //innehåller tidsgrejer
		    //Spar tiden i klienten attribut - hitTime. Den nollas efter koll.
		    //TODO: Change that particular Player's hit-time to this hitTime

		}else {
		    game.displayBoard();
		    System.out.println("Ready(r)/Next card(n)/Hit dick(h)?"); //ska ev vara i displayBoard
		
	  
		}

		//TODO: Uppdatera Player till att vara redo för nästa runda 

	    }
	    //TODO Add ability to passivly look at game after winning.	    
	}
	catch (Exception e) {
	    System.out.println("Error " + e.getMessage());
	    e.printStackTrace();
	}
    }
}
