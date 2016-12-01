import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.*;
import java.net.InetAddress;
import java.net.*;
import java.io.*;

/*
 #1 maybe put this in a seperate function, with argument that determines if hit is possible or not
*/

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
            

	//BEGINNING OF GAME
	game.displayBoard(); //so everyone knows who starts
	//TODO: Uppdatera att playern är redo

	//START VALUES
	long startTime;
	long hitTime;
	String answer = "";
	int oldRound = 1;
	int round = 1;
	Scanner userInput = new Scanner(System.in);
	boolean hit = false;

	//>>>>STOR LOOP: här ska vi egentligen ha en check att spelet inte är slut
	for (int i=0; i<5; i++) { 

	    //loop until next round
	    oldRound = game.getRound();
	    while (oldRound == round) {
		round = game.getRound(); //TODO: semaphores needed here
	    }

	    //TODO: kollar ifall det är spelarens tur
	    //ifall personen fortfarande deltar i spelet eller har vunnit.


	    //>>>>HIT IS POSSIBLE
	    hit = true; //TODO: fkn for checking if its hit the dick time 
	    if (hit == true) {
		game.displayBoard();

		startTime = System.nanoTime();

		System.out.println("Do you want to hit the dick? (y/n)");  //ska ev vara i displayBoard

		hitTime = System.nanoTime() - startTime;   
		while(hitTime < 300000) { //godtyckligt taget tal här
		    answer = userInput.nextLine();
		    hitTime = System.nanoTime() - startTime;  
		}

		switch(answer) { //#1
		case 'y': break; //TODO
		case 'n': break; //TODO
		default: System.out.println("What are you trying to do?"); break;
		}
		
		//TODO: Updated player's hitTime-attribute

	    }    
	    //>>>>HIT IS NOT POSSIBLE
	    else {
		game.displayBoard();
		System.out.println("Do you want to hit the dick? (y/n)");  //ska ev vara i displayBoard
		//TODO: typ hela den här delen
	  
		answer = userInput.nextLine(); //#1
		switch(answer) {
		case 'y': break; //TODO
		case 'n': break; //TODO
		default: System.out.println("What are you trying to do?"); break;
		}
	    }
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
