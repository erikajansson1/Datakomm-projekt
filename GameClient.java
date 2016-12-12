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
	if(args.length == 0) {
	    serverInIp = networkBuild.askServerInIp();
	    serverExIp = networkBuild.askServerExIp();
	    serverRMIPort = networkBuild.askServerPort();
	} else {
	    serverInIp = args[0];
	    serverExIp = args[1];
	    serverRMIPort = args[2];
	}
	GameInterface serverGame = networkBuild.getServerObj(serverInIp,
							     serverExIp,
							     serverRMIPort,
							     gameToGet);
	try {
	    networkBuild.buildNetwork(serverGame);
	    System.out.println("Build complete");
	    playerNo = networkBuild.joinGame();
	    BackUp backup = new BackUp(serverGame);
	    networkBuild.waitingUntilGameCanStart();

	    
	    //BEGINNING OF GAME
	    //PLAYER ZERo Starts the game always.
	    serverGame.startGame(playerNo);
	    while(serverGame.getPlayer(playerNo).getPlayerDeck().getAmount() == 0) {
		Thread.sleep(1000);
	    }
	    
	    //Gives out information about how many player. We need functions to get strings that get aliases
	    // and everyones Deck size.
	   
	    
	    //START VALUES
	    long startTime;
	    long hitTime;
	    String answer = "";
	    int oldRound = 0;
	    int round = serverGame.updateRound(oldRound);
	    oldRound = round;
	    Scanner userInput = new Scanner(System.in);
	    long maxAnswerTime = 30000000000L; //30 sekunder
	    boolean canHit = false;
	    boolean myRound = false; 
	    boolean checkVar = false;
	    
	    //>>>>STOR LOOP: Here we should also have a check if the game is finished or not
	    while (serverGame.getPlayer(playerNo).getPlayerRank() == -1) {
		backup.update(serverGame);
		serverGame.setReadyValue(playerNo, false);
		serverGame.updatePlayerTime(playerNo, 0L);
		//Check if it's possible to hit
		canHit = serverGame.timeToHit(); 

		//Display board
		System.out.printf("\033[2J\033[;H"); 
		System.out.println(serverGame.displayBoard());
		System.out.println("Current dick size " + serverGame.getDeckSize());
		//System.out.println(checkVar);

		//Check if it's current player's time to lay card
		if (serverGame.whoseTurn() == playerNo) { myRound = true; }
		else { myRound = false; }

		//Let the player make its move
		serverGame.setReadyValue(playerNo, true);
		getAnswer(serverGame, playerNo, maxAnswerTime);
		if(playerNo == 0) serverGame.askDealer();
		
		//TODO UPDATE THE WHOLE GAME STATUS TBH.
		//Kolla sa att alla har gjort sitt och att losern har fatt kort
		//Completely lost in how we compare players to eachother from this loop
		//TODO: kolla ifall personen fortfarande deltar i spelet eller har vunnit.
		//Take rankWhenFinished from player in startGame function
		//When cards on hand == 0, change the rankWhenFinished in player (need function)
		//Put in while a statement about rankWhenFinished. If we do this we can have to
		// state, 
		// 1.You are still playing and go though this loop
		// 2.You won but still se the outprint in another loop
		
	       
		
		//loop until next round
		//round = serverGame.updateRound(oldRound); 
		while (oldRound == round) {
		    System.out.printf("\033[2J\033[;H");
		    round = serverGame.getRound();
		    System.out.println(serverGame.displayBoard());
		    Thread.sleep(2000);
		}
		System.out.println(serverGame.getLastEvent());
		//TODO player put down a card.
		Thread.sleep(8000);

      
		oldRound = round;

	    }
	    
	    while((serverGame.getPlayer(playerNo).getPlayerRank() != -1) &&
		  !serverGame.askGameEnded()) {
		serverGame.displayBoard();
		Thread.sleep(2000);
	    }
	    System.out.println(serverGame.displayGameResult());
	}
	catch (Exception e) {
	    System.out.println("Error " + e.getMessage());
	    e.printStackTrace();
	}


    }



   

    /*
      Function that acts out following scenarios (this client/player= I, me, my):
      1. It is my round and I can choose whether to hit or play next card:
      1a. Hit is possible
      1b. Hit is not possible
      2. It is someone else's round, and I can choose whether to hit or not:
      2a. Hit is possible
      2b. Hit is not possible      
    *//*
    public static boolean  userAction(GameInterface serverGame, int playerNo, int round, boolean canHit, boolean myRound) throws RemoteException {
	String answer = "";
	String actionMessage = "";
	long answerTime;
	long startCounting;
	long maxAnswerTime = 30000000000L; //30 sekunder
	boolean card = false;
	
	if(myRound) {  //TODO: ar det har vi kollar ifall ngn har forsvunnit? iom att den personen inte kommer gora sitt drag
   	    System.out.println("Do you wan to hit the dick(h) or play next card(c)?");
	    answer = getAnswer(serverGame, playerNo, maxAnswerTime);
	    
	    if (canHit) {
		switch(answer) {
		case "h": serverGame.handleRightHit(playerNo, round); break; 
		case "c": card = serverGame.tryToLayCard(playerNo, round); break;
		default: break;
		}
	    } 
	    else {
		switch(answer) {
		case "h": actionMessage = serverGame.handleWrongHit(playerNo, round); break; //what happens with message?
		case "c": card = serverGame.tryToLayCard(playerNo, round); break; //Creates Error, IndexOutOfBoundsException
		default: break;
		}
	    }
	}
	else if (!myRound) {
	    System.out.println("Do you want to hit the dick? (y/n)"); 
	    answer = getAnswer(serverGame, playerNo, maxAnswerTime);
		
	    if(canHit) {
		switch(answer) {
		case "y": serverGame.handleRightHit(playerNo, round); break;
		case "n": actionMessage = serverGame.handleWrongHit(playerNo, round); break;
		default: break;
		}
	    }
	    else {
		switch(answer) {
		case "y": actionMessage = serverGame.handleWrongHit(playerNo, round); break;
		    //	case "n": serverGame.handleRightHit(playerNo, round); break;
		default: break;
		}
	    }
	    
	}

	return card;
    }

    /** Asks user for input
	@param game Current game being played
	@param playerNo Number ID of player
	@param maxAnswerTime The maximum time for the user to wait to answer
	@comments The first two parameters are used to update the players hitTime/answerTime attribute
    */
    public static void getAnswer(GameInterface game, int playerNo, long maxAnswerTime)  throws RemoteException {
	Scanner userInput = new Scanner(System.in);
	String answer = "";
	long answerTime = 0L;
	long startTime = System.nanoTime();
	System.out.println("Do you want to hit the dick? (y/n)"); 
	answer = userInput.nextLine();
	while(answerTime < maxAnswerTime) {
	    if (!answer.equals("")) { break; }
	    answer = userInput.nextLine();
	    answerTime = System.nanoTime() - startTime; 
	}	
	if(answer.equals("")) { answerTime = 0L; }
	game.updatePlayerTime(playerNo, answerTime);
	game.updatePlayerAction(playerNo,answer);
    }

}

