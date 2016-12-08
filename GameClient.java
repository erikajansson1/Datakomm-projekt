

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
	 //	System.out.println(serverGame);
	try {
	    networkBuild.buildNetwork(serverGame);
	    playerNo = networkBuild.joinGame();
	    BackUp backup = new BackUp(serverGame);
	    networkBuild.waitingUntilGameCanStart();

	    
	    //BEGINNING OF GAME
	    
	    serverGame.startGame(playerNo);
	    //START VALUES
	    long startTime;
	    long hitTime;
	    String answer = "";
	    int oldRound = 0;
	    int round = 0;
	    Scanner userInput = new Scanner(System.in);
	    boolean canHit = false;
	    boolean myRound = false; 

	    //>>>>STOR LOOP: har ska vi egentligen ha en check att spelet inte ar slut
	    while (serverGame.getPlayer(playerNo).getPlayerRank() == -1) {
		backup.update(serverGame);
		serverGame.setReadyValue(playerNo, false);
		//Kolla ifall det ar spelarens tur
		if (serverGame.whoseTurn() == playerNo) { myRound = true; }
		else { myRound = false; }
		
		//Check if it's possible to hit
		canHit = serverGame.timeToHit(); //TODO: fkn for checking if its hit the dick time 
		    
		//Display board
		System.out.printf("\033[2J\033[;H");
		System.out.println(serverGame.displayBoard());
		    
		//Let the player make its move
		userAction(serverGame, playerNo,canHit,myRound);
		
		//TODO UPDATE THE WHOLE GAME STATUS TBH. Kolla sa att alla har gjort sitt och att losern har fatt kort
		//TODO: kolla ifall personen fortfarande deltar i spelet eller har vunnit.
		
		//Uppdatera Player till att vara redo for nasta runda 
		
		//	serverGame.setReadyValue(playerNo, true);

		
		//loop until next round
		serverGame.updatePlayerTime(playerNo, 0L);
		//round = serverGame.updateRound(oldRound); 
		while (oldRound == round) {
		    System.out.printf("\033[2J\033[;H");
		    round = serverGame.updateRound(oldRound);
		    System.out.println(serverGame.displayBoard());
		    //round = serverGame.getRound();
		    Thread.sleep(500);
		}
      
		oldRound = round;

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
    public static void userAction(GameInterface serverGame, int playerNo, boolean canHit, boolean myRound) throws RemoteException {
	Scanner userInput = new Scanner(System.in);
	String answer = "";
	long answerTime;
	long startCounting;
	long maxAnswerTime = 30000000000L; //30 sekunder
	

	
	//Reset player's ready value
	serverGame.setReadyValue(playerNo, false);

	if (myRound) {
	    System.out.println("Hit dick(h) or play next card(c)?");
	}
	if (!myRound) {
	    System.out.println("Do you want to hit the dick? (y/n)"); 
	}

	//Tidtagning pa svar
	startCounting = System.nanoTime();
	answerTime = System.nanoTime() - startCounting;   

	while(answerTime < maxAnswerTime) {
	    answer = userInput.nextLine();
	    answerTime = System.nanoTime() - startCounting;  
	    if (!answer.equals("")) { break; } //break when player answer
	}

	if (myRound && !canHit) {
	    switch(answer) {
	    case "h":
		//serverGame.updatePlayerTime(playerNo,0L); //is done in main
		serverGame.handleWrongHit(serverGame.getPlayer(playerNo));
		break; 		
	    case "c":
		//serverGame.updatePlayerTime(playerNo,0L);  //is done in main
		serverGame.tryToLayCard(playerNo);
		break;
	    default: break;
	    }
	    
	}
	if (canHit) {
	    switch(answer) { 
	    case "h":
	    case "y": serverGame.updatePlayerTime(playerNo,answerTime); break; 
	    case "n": answerTime = 0; serverGame.updatePlayerTime(playerNo,answerTime);
 break;
	    case "c": serverGame.updatePlayerTime(playerNo,answerTime); break; //TODO: try to place card 
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
	serverGame.setReadyValue(playerNo, true);


    }

}
