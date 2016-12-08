import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.*;
import java.net.InetAddress;
import java.net.*;
import java.io.*;


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
	    int round = serverGame.updateRound(oldRound);
	    oldRound = round;
	    Scanner userInput = new Scanner(System.in);
	    boolean canHit = false;
	    boolean myRound = false; 

	    //>>>>STOR LOOP: har ska vi egentligen ha en check att spelet inte ar slut
	    while (serverGame.getPlayer(playerNo).getPlayerRank() == -1) {
		backup.update(serverGame);
		serverGame.setReadyValue(playerNo, false);
		//Check if it's possible to hit
		canHit = serverGame.timeToHit(); //TODO: fkn for checking if its hit the dick time 

		//Display board
		System.out.printf("\033[2J\033[;H");
		System.out.println(serverGame.displayBoard());


		//Kolla ifall det ar spelarens tur
		if (serverGame.whoseTurn() == playerNo) { myRound = true; }
		else { myRound = false; }
		

		    
		//Let the player make its move
		userAction(serverGame, playerNo, round, canHit,myRound);
		
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
    public static void userAction(GameInterface serverGame, int playerNo, int round, boolean canHit, boolean myRound) throws RemoteException {
	String answer = "";
	String actionMessage = "";
	long answerTime;
	long startCounting;
	long maxAnswerTime = 30000000000L; //30 sekunder
	
	serverGame.setReadyValue(playerNo, false);
	
	if(myRound) {  //TODO: ar det har vi kollar ifall ngn har forsvunnit? iom att den personen inte kommer gora sitt drag
   	    System.out.println("Do you wan to hit the dick(h) or play next card(c)?");
	    answer = getAnswer(serverGame, playerNo, maxAnswerTime);
	    
	    if (canHit) {
		switch(answer) {
		case "h": serverGame.handleRightHit(playerNo); break;
		case "c": serverGame.tryToLayCard(playerNo, round); break;
		default: break;
		}
	    } 
	    else {
		switch(answer) {
		case "h": actionMessage = serverGame.handleWrongHit(playerNo); break;
		case "c": serverGame.tryToLayCard(playerNo, round); break;
		default: break;
		}
	    }
	}
	else if (!myRound) {
	    System.out.println("Do you want to hit the dick? (y/n)"); 
	    answer = getAnswer(serverGame, playerNo, maxAnswerTime);
		
	    if(canHit) {
		switch(answer) {
		case "y": serverGame.handleRightHit(playerNo); break;
		case "n": actionMessage = serverGame.handleWrongHit(playerNo); break;
		default: break;
		}
	    }
	    else {
		switch(answer) {
		case "y":  actionMessage = serverGame.handleWrongHit(playerNo); break;
		case "n": serverGame.handleRightHit(playerNo); break;
		default: break;
		}
	    }
	    
	}
	serverGame.setReadyValue(playerNo, true);
    }

    /** Asks user for input
	@param game Current game being played
	@param playerNo Number ID of player
	@param maxAnswerTime The maximum time for the user to wait to answer
	@comments The first two parameters are used to update the players hitTime/answerTime attribute
    */
    public static String getAnswer(GameInterface game, int playerNo, long maxAnswerTime)  throws RemoteException {
	Scanner userInput = new Scanner(System.in);
	String answer = "";
	long answerTime = 0L;
	long startTime = System.nanoTime();
	answer = userInput.nextLine();
	while(answerTime < maxAnswerTime) {
	    answer = userInput.nextLine();
	    answerTime = System.nanoTime() - startTime;  
	    if (!answer.equals("")) { break; }
	}	
	if(answer.equals("")) { answerTime = -1L; }
	game.updatePlayerTime(playerNo, answerTime);
	return answer;       
    }

}

