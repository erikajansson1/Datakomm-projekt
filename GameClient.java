

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

    /*
    //If it's possible to hit start time 
    public static long timeToHit(){ 
	Scanner ans = new Scanner(System.in);
	long startT = System.nanoTime();

	System.out.println("Ready(r)/Next card(n)/Hit dick(h)?");
	long hitT = System.nanoTime() - startT;   

	while(hitT< 300000) { //godtyckligt taget tal har
	    String answer = ans.nextLine();
	    hitT = System.nanoTime() - startT;  
	}
    }*/
 

    public static void main (String[] args) {
	int playerNo = -1;
	String gameToGet = "theGame";
	String backUpToGet = "theBackUp";
	Network networkBuild = new Network();
	networkBuild.welcomeMSG("client");

	String inIp = null;
	String exIp = null;
	String port = null;
	if(args[0].equals("debug")) {
	     inIp = "192.168.0.101";
	     exIp = "83.255.61.11";
	     port = "1099";
	} else {
	     inIp = networkBuild.askServerInIp();
	     exIp = networkBuild.askServerExIp();
	     port = networkBuild.askServerPort();
	}
		
	GameInterface serverGame = null;	
	serverGame = networkBuild.getServerObj(serverGame,inIp,exIp,port,gameToGet);
	try {
	    playerNo = networkBuild.joinGame(serverGame,inIp,exIp);
	    System.out.println(playerNo);

	    
	    BackUp backup = new BackUp(serverGame);
	    //backup.update(serverGame);
	    //BEGINNING OF GAME
	    
	    //so everyone knows who starts
	    //TODO: Uppdatera att playern ar redo

	    //START VALUES
	    long startTime;
	    long hitTime;
	    String answer = "";
	    int oldRound = 1;
	    int round = 1;
	    Scanner userInput = new Scanner(System.in);
	    boolean canHit = false;
	    boolean myRound = false; 

	    //>>>>STOR LOOP: har ska vi egentligen ha en check att spelet inte ar slut
	    for (int i=0; i<5; i++) { 

		//loop until next round
		oldRound = serverGame.whoseRound(oldRound); //or is it oldR?
		while (oldRound == round) {
		    round = serverGame.whoseRound(oldRound); //TODO: semaphores needed here, at client???
		}

		//TODO: myRound = kollar ifall det ar spelarens tur
		//ifall personen fortfarande deltar i spelet eller har vunnit.

		//Check if it's possible to hit
		canHit = true; //TODO: fkn for checking if its hit the dick time 
		    
		//Display board
		System.out.println(serverGame.displayBoard());
		    
		//Let the player make its move
		userAction(myRound,canHit);
	    }
	    //TODO: Uppdatera Player till att vara redo for nasta runda 

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
    public static void userAction(boolean canHit, boolean myRound){
	Scanner userInput = new Scanner(System.in);
	String answer = "";
	long answerTime;
	long startCounting;
	

	if (myRound) {
	    System.out.println("Hit dick(h) or play next card(c)?");
	}
	if (!myRound) {
	    System.out.println("Do you want to hit the dick? (y/n)"); 
	}
	if (myRound && !canHit) {
 
	    answer = userInput.nextLine(); //#1
	    switch(answer) {
	    case "h": System.out.println("there was no dick to hit :c"); break; //TODO: hit fail
	    case "c": System.out.println("Placed a card"); break; //TODO: place card
	    default: System.out.println("What are you trying to do?"); break;
	    }
	    
	}
	if (canHit) {
	    startCounting = System.nanoTime();

	    answerTime = System.nanoTime() - startCounting;   
	    while(answerTime < 300000) { //godtyckligt taget tal har
		answer = userInput.nextLine();
		answerTime = System.nanoTime() - startCounting;  
	    }

	    switch(answer) { //#1
	    case "h":
	    case "y": System.out.print("\nYOU HIT THE DICK :D"); break; //TODO: Updated player"s answerTime-attribute
	    case "n": answerTime = 0; System.out.println("there was no dick to hit :c"); break; //TODO: Lost round - update answerTime = 0?
	    case "c": System.out.print("\nYou tried to place a card"); break; //TODO: Update player"s answerTime-attribute and try to place card 
	    default: System.out.print("\nWhat are you trying to do?"); break;
	    }
	    
	    System.out.print(" and you took "+answerTime+"ns\n\n");
	}
	if (!myRound && !canHit) {
	
	    //System.out.println("Do you want to hit the dick? (y/n)");  
 
	    answer = userInput.nextLine(); //#1
	    switch(answer) {
	    case "y": System.out.println("there was no dick to hit :c"); break; //TODO: hit fail
	    case "n": System.out.println("Moving on"); break; //TODO: 
	    default: System.out.println("What are you trying to do?"); break;
	    }
	}
	


    }

}
