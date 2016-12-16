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
	int serverRetryCount = 0;
	if(args.length == 0) {
	    serverInIp = networkBuild.askServerInIp();
	    serverExIp = networkBuild.askServerExIp();
	    serverRMIPort = networkBuild.askServerPort();
	} else {
	    serverInIp = args[0];
	    serverExIp = args[1];
	    serverRMIPort = args[2];
	    serverRetryCount = Integer.parseInt(args[3]);
	}
	GameInterface serverGame = networkBuild.getServerObj(serverInIp,
							     serverExIp,
							     serverRMIPort,
							     gameToGet);
	BackUp backup = new BackUp();
	try {
	    backup = new BackUp(serverGame);
	    //if(backup == null) System.exit(0);
	    networkBuild.buildNetwork(serverGame);
	    //System.out.println("Build complete");
	    playerNo = networkBuild.joinGame();
	  
	    networkBuild.waitingUntilGameCanStart();

	    
	    //BEGINNING OF GAME
	    //PLAYER ZERo Starts the game always.
	    if (serverRetryCount == 0) serverGame.startGame(playerNo);
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
	    long maxAnswerTime = 3000000000L; //30 sekunder
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

		serverGame.removeQuitters();
		playerNo = serverGame.getPlayerNo(alias);
		//	System.out.println(alias+" no "+playerNo);

		if(playerNo == 0) serverGame.askDealer();


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
		    Thread.sleep(1000);
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
		e.printStackTrace();
	    try {
   
		System.out.println("Error! Connection lost!\n"+
				   "Assuming server chrashed! \n"+
				   "reconnecting to new server!");

		if(playerNo == 1) {
		    backup.removePlayer(0);
		    GameServer.rebuild(backup.getBackUp(),"1");
		} else if (serverRetryCount < 10 && serverRetryCount >= 1){
		    String[] argvClient2 = new String[]{
			args[0],
			args[1],
			"1099",
			Integer.toString(serverRetryCount++)
		    };
		    System.out.println("Waiting before retrying connection");
		    Thread.sleep(10000);
		    GameClient.main(argvClient2);
		} else if (serverRetryCount == 0) {
		    String[] argvClient2 = new String[]{
			backup.getBackupInIp(),
			backup.getBackupExIp(),
			"1099",
			Integer.toString(serverRetryCount++)
		    };
		    System.out.println("trying to connect to"+
				       	backup.getBackupInIp() +
				       	backup.getBackupExIp() +
				       Integer.toString(serverRetryCount)
				       );

		    System.out.println("Waiting before retrying connection");
		    Thread.sleep(10000);
		    GameClient.main(argvClient2);
		}
		System.exit(0);
	    }
	    catch (Throwable e2) {
		System.out.println("Inte ska du va har! :D");

		e2.printStackTrace();
		System.exit(0);
	    }
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
	long endTime = 30000000000L;
	while(!serverGame.everyoneHasMadeMove()){
	    if (currentTime > endTime) { break; }
	    currentTime = System.nanoTime() - startTime;
	    System.out.printf("\033[2J\033[;H");
	    System.out.println(serverGame.displayBoard());
	    Thread.sleep(1000);
	}
    }

}

