

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
     public static void main (String[] args) {
	int playerNo = -1;
	String gameToGet = "theGame";
	Network networkBuild = new Network();
	networkBuild.welcomeMSG("client",args.length);

	String serverInIp = null;
	String serverExIp = null;
	String serverRMIPort = null;
	//String serverObjPort = null;
	if(args.length == 0) {
	    serverInIp = networkBuild.askServerInIp();
	    serverExIp = networkBuild.askServerExIp();
	    serverRMIPort = networkBuild.askServerPort();
	    //serverObjPort = networkBuild.askServerObjPort();
	} else {
	    serverInIp = args[0];
	    serverExIp = args[1];
	    serverRMIPort = args[2];
	    //serverObjPort = args[3];
	}

	 GameInterface serverGame= networkBuild.getServerObj(serverInIp,
							     serverExIp,
							     serverRMIPort,
							     gameToGet);
	System.out.println(serverGame);
	try {
	    networkBuild.buildNetwork(serverGame);
	    playerNo = networkBuild.joinGame();
	    BackUp backup = new BackUp(serverGame);
	    networkBuild.waitingUntilGameCanStart();

	    //backup.update(serverGame);
	    //BEGINNING OF GAME
	    
	    //so everyone knows who starts <- crap what does this mean

	    serverGame.setReadyValue(playerNo, true);

	    //START VALUES
	    long startTime;
	    long hitTime;
	    String answer = "";
	    int oldRound = 0;
	    int round = 1;
	    Scanner userInput = new Scanner(System.in);
	    boolean canHit = false;
	    boolean myRound = false; 

	    //>>>>STOR LOOP: har ska vi egentligen ha en check att spelet inte ar slut
	    for (int i=0; i<5; i++) { 

		//loop until next round
		round = serverGame.updateRound(oldRound); 
		while (oldRound == round) {
		    serverGame.updateRound(oldRound);//Doesnt return round nr. 
		    round = serverGame.getRound();
		    Thread.sleep(1000);
		}
		/* //Reset player's ready value
		serverGame.setReadyValue(playerNo, false);
		*/

		//Kolla ifall det ar spelarens tur
		if (serverGame.whoseTurn() == playerNo) { myRound = true; }
		else { myRound = false; }
		
		//Check if it's possible to hit
		//canHit = serverGame.timeToHit(); //TODO: fkn for checking if its hit the dick time 
		    
		//Display board
		System.out.println(serverGame.displayBoard());
		    
		//Let the player make its move
		userAction(serverGame, playerNo,canHit,myRound);
		
		//TODO UPDATE THE WHOLE GAME STATUS TBH. Kolla sa att alla har gjort sitt och att losern har fatt kort
		//TODO: kolla ifall personen fortfarande deltar i spelet eller har vunnit.
		
		//Uppdatera Player till att vara redo for nasta runda 
		serverGame.updatePlayerTime(playerNo, -1);
		serverGame.setReadyValue(playerNo, true);
	    }
	    
	}
	catch (Exception e) {
	    System.out.println("Error " + e.getMessage());
	    e.printStackTrace();
	}

	//TODO Add ability to passivly look at game after winning.	    
    }

    /*
      Function that acts out following scenarios (this client/player= I, me, my):
      1. It is my round and I can choose whether to hit or play next card:
      1a. Hit is possible
      1b. Hit is not possible
      2. It is someone else's round, and I can choose whether to hit or not:
      2a. Hit is possible
      2b. Hit is not possible      
    */
    public static void userAction(GameInterface game, int playerNo, boolean canHit, boolean myRound) throws RemoteException {
	Scanner userInput = new Scanner(System.in);
	String answer = "";
	long answerTime;
	long startCounting;
	

	
	//Reset player's ready value
	game.setReadyValue(playerNo, false);

	if (myRound) {
	    System.out.println("Hit dick(h) or play next card(c)?");
	}
	if (!myRound) {
	    System.out.println("Do you want to hit the dick? (y/n)"); 
	}

	//Tidtagning pa svar
	startCounting = System.nanoTime();

	answerTime = System.nanoTime() - startCounting;   
	while(answerTime < 300000) { //godtyckligt taget tal har
	    answer = userInput.nextLine();
	    answerTime = System.nanoTime() - startCounting;  
	    if (!answer.equals("")) { break; } //break when player answer
	}

	if (myRound && !canHit) {
 
	    answer = userInput.nextLine(); //#1
	    switch(answer) {
	    case "h": answerTime = 0; game.updatePlayerTime(playerNo,answerTime); break; //TODO: hit fail
	    case "c": game.updatePlayerTime(playerNo,answerTime); break; //TODO: try to place card
	    default: break;
	    }
	    
	}
	if (canHit) {
	    switch(answer) { 
	    case "h":
	    case "y": game.updatePlayerTime(playerNo,answerTime); break; 
	    case "n": answerTime = 0; game.updatePlayerTime(playerNo,answerTime);
 break;
	    case "c": game.updatePlayerTime(playerNo,answerTime); break; //TODO: try to place card 
	    default: answerTime = 0; break;
	    }

	    System.out.println("you took "+answerTime+"ns\n\n");
	}
	if (!myRound && !canHit) {
	
	    answer = userInput.nextLine(); //#1
	    switch(answer) {
	    case "y": break; //TODO: hit fail
	    case "n": /* NOTHING HAPPENS */  break;
	    default: System.out.println("What's wrong with waiting you impatient monkeybastard?"); break;
	    }
	}

	//Made move, ready to do another!
	game.setReadyValue(playerNo, true);


    }

}
