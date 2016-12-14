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
	    
	    //START VALUES
	    long startTime;
	    long hitTime;
	    String answer = "";
	    int oldRound = 0;
	    int round = 0;
	    oldRound = round;
	    Scanner userInput = new Scanner(System.in);
	    long maxAnswerTime = 30000000L; //30 sekunder
	    boolean canHit = false;
	    boolean myRound = false; 
	    boolean checkVar = false;
	    String alias = serverGame.getPlayerAlias(playerNo);
	    
	    //>>>>STOR LOOP: Here we should also have a check if the game is finished or not
	    while (serverGame.myRank(playerNo) == -1) {
		backup.update(serverGame);
		

		//Display board
		System.out.printf("\033[2J\033[;H"); 
		System.out.println(serverGame.displayBoard());
		//System.out.println("Current dick size " + serverGame.getDeckSize());

		//Let the player make its move
		getAnswer(serverGame, playerNo, maxAnswerTime);
		String nameTurnPlayer = serverGame.getPlayerAlias(serverGame.whoseTurn());
		serverGame.setReadyValue(playerNo, true);
		waitingDisplay(serverGame);
		if(playerNo == 0) serverGame.askDealer();
		Thread.sleep(100);
		playerNo = serverGame.getPlayerNo(alias);
		System.out.println(alias+" no "+playerNo);

		while (oldRound == round) {
		    round = serverGame.getRound();
		    Thread.sleep(500);
		}

		System.out.println(serverGame.getLastEvent());
		System.out.println("Player: "+
				   nameTurnPlayer +
				   " laid a card.");
		serverGame.setReadyValue(playerNo, false);
		serverGame.updatePlayerTime(playerNo, 0L);
		Thread.sleep(4000);
		oldRound = round;
	    }    
	    	
	    serverGame.setReadyValue(playerNo, true);
	    serverGame.updatePlayerTime(playerNo, 1L);
	    	
	    while(!serverGame.askGameEnded()) {
		waitingDisplay(serverGame);
		String nameTurnPlayer = serverGame.getPlayerAlias(serverGame.whoseTurn());
		if (playerNo == 0) {
		    serverGame.askDealer();
		}
		while (oldRound == round) {
		    round = serverGame.getRound();
		    Thread.sleep(500);
		}
		System.out.println(serverGame.getLastEvent());
		System.out.println("Player: "+
				   nameTurnPlayer +
				   " laid a card.");
		oldRound = round;
		Thread.sleep(2000);
	    }
		
	
	    
	    System.out.printf("\033[2J\033[;H");
	    System.out.println(serverGame.displayGameResult());
	}
	catch (Exception e) {
	    System.out.println("Error " + e.getMessage());
	    e.printStackTrace();
	}


    }

    /** 
     * Asks user for input
	 * @param game Current game being played
	 * @param playerNo Number ID of player
	 * @param maxAnswerTime The maximum time for the user to wait to answer
	 * @comments The first two parameters are used to update the players hitTime/answerTime attribute
    */
    public static void getAnswer(GameInterface game, int playerNo, long maxAnswerTime)  throws RemoteException {
	Scanner userInput = new Scanner(System.in);
	String answer = "";
	long answerTime = 0L;
	long startTime = System.nanoTime();
	System.out.println("Do you want to hit the deck? (y/n)"); 
	//while(answerTime < maxAnswerTime) {
	//if (!answer.equals("")) { break; }
	    answer = userInput.nextLine();
	    answerTime = System.nanoTime() - startTime; 
	    //System.out.println("time: "+answerTime);
	    //}	
	if(answer.equals("")) { answerTime = 0L; }
	game.updatePlayerTime(playerNo, answerTime); 
	// SECURITY! check if playerNO is the "matching" ip for that player.
	//In the player class the IP for each player is stored....
	game.updatePlayerAction(playerNo,answer);
	System.out.println("Your answer: "+ answer);
    }

    public static void waitingDisplay(GameInterface serverGame) throws Exception {
	long currentTime = 0L;
	long startTime = System.nanoTime();
	long endTime = 3000000000L;
	while(!serverGame.everyoneHasMadeMove()){
	    if (currentTime > endTime) { break; }
	    currentTime = System.nanoTime() - startTime;
	    System.out.printf("\033[2J\033[;H");
	    System.out.println(serverGame.displayBoard());
	    Thread.sleep(1000);
	}
    }

}

