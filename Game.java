import java.rmi.*;
import java.rmi.server.*;
import java.util.*;
import java.util.concurrent.Semaphore;

public class Game extends UnicastRemoteObject implements GameInterface, java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private final Semaphore lock;
    private int round;
    private Deck gameDeck;
    private Deck starterDeck;
    private ArrayList<Player> gamePlayers;
    private String lastEvent;
 
    public Game (int round,Deck gameDeck,Deck starterDeck,ArrayList<Player> gamePlayers) throws RemoteException {
	//super(1099);
	this.lock = new Semaphore(1);
	setGameValues (round,gameDeck,starterDeck,gamePlayers);
    }

    public Game (int numberOfPlayers) throws RemoteException {
	super(1100);
	this.lock = new Semaphore(1);
	this.round = 0;
	this.gameDeck = new Deck(0); 
	this.starterDeck = new Deck();
	this.lastEvent = "";
	this.gamePlayers = new ArrayList<Player>(numberOfPlayers);
	for (int i = 0; i < numberOfPlayers; i++) {
	    gamePlayers.add(i,new Player(i,"","","Empty",false));
	}
    }

    /** 
     * Checks whether it's time to hit,
     * that is, check whether any of the previous 4 cards
     * match eachother
     */
    public boolean timeToHit() throws RemoteException {
    	return gameDeck.possibleToHit(gamePlayers.size());
    }


    /** Get middle deck size
     */
    public int getDeckSize() throws RemoteException{
	return gameDeck.getDeckSize();
    }

    /** 
     * Prints out a view of the board:
     * whose turn it is, and the latest card
     */
    public String displayBoard() throws RemoteException {
	String players = "";
	for (int i = 0; i < this.gamePlayers.size(); i++) {
	    if(!(i == 0)) players += "\n";
	    players += ""+gamePlayers.get(i).getPlayerName();
	    players += "\nCards on hand: "+gamePlayers.get(i).getAmountOfCardsOnHand();
	    players += "\nStatus: ";
	    
	    
	    if(gamePlayers.get(i).getReadyValue()){
		players += "READY\n";
	    } else {
		players += "NOT READY\n";
	    }	
	}
	String whosTurn = "\n\nTurn: "+ gamePlayers.get(this.whoseTurn()).getPlayerName();
	String round = "\nRound: "+this.round;
	String currCard = "\nLatest Card: "+this.gameDeck.showTopCard();

	return ""+ players + whosTurn + round + currCard;
    }

    
    /** 
     * Set the ready value for a player 
     * @param playerNo The player's number ID
     * @param readyValue The new ready value
     */
    public void setReadyValue(int playerNo, boolean readyValue) throws RemoteException {
	Player thisPlayer = gamePlayers.get(playerNo);
	thisPlayer.setReadyValue(readyValue);
    }; 

    /** 
     * Update the hit time for a player
     * @param playerNo The player's number ID
     * @param hitTime The new hit time
     */
    public void updatePlayerTime(int playerNo, long hitTime) throws RemoteException { 
	Player thisGuy = gamePlayers.get(playerNo);
	thisGuy.setPlayerTime(hitTime);
    }

    
    /** 
     * Checks whose turn it is, and if it's time for a new turn. If so, it updates accordingly. 
     * @param currRound Taken to ensure that only one such update is done every round.
     * @return The round value 
     */
    public int updateRound(int currRound) throws RemoteException{
	try{
	    this.lock.acquire();

	    //Compare so that you dont update the Round twice
	    if (this.round == currRound+1) {
		this.lock.release();
		return this.round;
	    }
	    
	    //If all the players are ready to continue, round++
	    int allReady = gamePlayers.size();
	    int readyPlayers = 0;
	    Player currPlayer;
	    for (int i = 0; i < gamePlayers.size(); i++) {
		currPlayer = gamePlayers.get(i);
		boolean playerIsReady = currPlayer.getReadyValue();
		if (playerIsReady) {
		    readyPlayers++;
		}
		if (readyPlayers == allReady) {
		    //Since this function is used in a loop,
		    //this will eventually be true for one player
		    this.round++;
		    this.lock.release();
		    return this.round;
		}
	    }

	} catch( Exception e) {
	    e.printStackTrace();
	}
	this.lock.release();

	return this.round;
    }


    /**
     * Checks whose turn it is and returns the index of the player.
     * @return an int which is the players index.
     */
    public int whoseTurn() throws RemoteException{
	int playerNo = this.round % gamePlayers.size();
	while(gamePlayers.get(playerNo).getPlayerRank() != -1) {
	    playerNo++;
	}
	return playerNo;
    }

    
    /** 
     * Finds the Player object in an array whose name matches the given parameter
     * @param alias 
     * @return The found Player object, or an Player object with specific invalid values     
     */
    public Player getPlayer(String alias) throws RemoteException {
	int len = gamePlayers.size();
	String name;
	for(int i=0; i < len; i++) {
	    name = gamePlayers.get(i).getPlayerName();
	    if (name.equals(alias)) {
		return gamePlayers.get(i);
	    }
	}
	Player nobody = new Player(-1,"","","",false);
	return nobody;
    }

    
    /** 
     * Finds the Player object in an array whose name matches the given parameter
     * @param player number 
     * @return The found Player object, or an Player object with specific invalid values     
     */
    public Player getPlayer(int playerNo) throws RemoteException {
	return gamePlayers.get(playerNo);
    }


    /**
     * Initiate game by giving out cards and select who start first 
     */
     public void startGame(int playerNo) throws RemoteException {
	if(playerNo == 0) {
	    this.starterDeck.mixup();
	    Player thePlayer;
	    Card cardToInsert;
 	    while (starterDeck.getAmount() > 0) {
		for (int i = 0; i < gamePlayers.size(); i++){
		    if (starterDeck.getAmount() > 0){
			thePlayer = gamePlayers.get(i);
			cardToInsert = starterDeck.getCard(true);
			thePlayer.getPlayerDeck().addCard(cardToInsert);
		    }
		}
	    }
	    for(int j=0; j<gamePlayers.size();j++) {
		thePlayer = gamePlayers.get(j);
	    }
	}
    }
	
	 
    /**
     * Tells the amount of players in the game
     */
    public int getAmountOfPlayers(){
    	int amountPlayers = this.gamePlayers.size();
    	return amountPlayers;
    }
    
 
    /**
     * Gives the deck in the middle to whoever lost
     * @param Player who will recieve the deck.
     */
    public void giveWholeDeck (Player loserPlayer) throws RemoteException{
	loserPlayer.getCardFromMiddleDeck(this.gameDeck);
    } 

  
    //SAME AS ABOVE!
    /** 
     * Gives the losing player all the thrown cards
     * @param Number ID of the player
     */
    public void giveWholeDeck (int playerNo) throws RemoteException {
	Player loser = this.gamePlayers.get(playerNo);
	Deck loserDeck = loser.getPlayerDeck();
	moveDeck(this.gameDeck, loserDeck);    
    }
    
     
    /** 
     * Get the rank of player #playerNo 
     * @param playerNo the player's no id
     */
    public int myRank(int playerNo) throws RemoteException {
	Player pyret = this.gamePlayers.get(playerNo);
	int rank = pyret.getPlayerRank();
	return rank;	
    } 	 
	 
    /** 
     * Check who lost the whole(!) game
     * @return player number id of the loser
     */
    public int checkLoser() throws RemoteException {
	int currID = 0; //Didnt exist so added so compilation could be done.
	int currRank = 0; //Didnt exist so added so compilation could be done.

	int loserID = -1;
	int loserRank = -1;
	int len = gamePlayers.size();
	Player currPlayer;
	for(int i=0; i<len; i++) {
	    currPlayer = gamePlayers.get(i);
	    currID = currPlayer.getPlayerNumber();
	    currRank = currPlayer.getPlayerRank();
	    if (currRank >= loserRank) { loserID = currID; }
	}

	return loserID;	
    }

    /** 
     * Move all cards from one deck to another
     * @param from Deck to take cards from
     * @param to Deck to add cards to
     */
    public void moveDeck(Deck from, Deck to) {
	Card curr;
	int to_len = to.getDeckSize();
	for (int i=0; i<to_len; i++) {
	    curr = from.getCard(true);
	    to.addCard(curr);
	}
    }

    
    /**
     * Handle if someone hits at the wrong time.
     * @param playerNo of the player trying to hit.
     */
    public String handleWrongHit(int playerNo, int round) throws RemoteException { 
	String loserMessage = "Too slow, it's a new round!";
	//String loserMessage = "Awh you hit at the wrong time buddy";
	//String loserMessage = "You said no, to bad you should have said yes!";
	try{
	    this.lock.acquire();
	    
	    while(!everyoneHasMadeMove()) { /* WAITING LOOP */ }
	    
	    if(round < this.round) { 	    
		this.lock.release();
		return loserMessage; 
	    }

	    int currentPlayer = whoseTurn();
	    long myTime = this.gamePlayers.get(playerNo).getPlayerTime();
	    long theirTime =  this.gamePlayers.get(currentPlayer).getPlayerTime();
	    if ((currentPlayer != playerNo) && (myTime > theirTime)) {
		giveWholeDeck(playerNo);
		loserMessage = "Wrong hit! Pick up all the cards.";
	    }
	    else if (currentPlayer == playerNo) {
		giveWholeDeck(playerNo);
		loserMessage = "Wrong hit! Pick up all the cards.";	    
	    }
	    
	}
	catch( Exception e) {
	    e.printStackTrace();
	}
	this.lock.release();
	return loserMessage;
    }  

    
    /**
     * handle when the hit is in the right time.
     * @param playerNo of the player trying to hit.
     */
    public String handleRightHit(int playerNo, int playerRound) throws RemoteException{
    	String actionMessage = ""; 
	try {
	    this.lock.acquire();
	    if((playerRound < this.round) | (playerRound > this.round)) { 	    
		this.lock.release();
		return "Too slow, it's a new round!"; 
	    }
	    while(!everyoneHasMadeMove()) { /* WAITING LOOP */ }
	    

	    long longestAnswerTime = 0;
	    Player currGuy;
	    long currAnswerTime;
	    int len = this.gamePlayers.size();
	    int loserID = -1;
	    Player loser = null;
	    for(int i=0; i<len; i++) {
		currGuy = this.gamePlayers.get(i);
		currAnswerTime = currGuy.getPlayerTime();
		if(currAnswerTime > longestAnswerTime) { 
		    longestAnswerTime = currAnswerTime; 
		    loserID = i;
		    loser = currGuy;
		}
	    }
	    actionMessage = "Hit successful!"; 
	    // Player loser = this.gamePlayers.get(playerNo);
    	    if (loser != null) {
		    giveWholeDeck(loser);
		    String loserName = loser.getPlayerName();
		    actionMessage = "Hit succesfull! "+loserName+" lost.";
	    }
	    
	    this.lock.release();
	    return actionMessage;
	}
	catch(Exception e){
	    System.out.println("handleRightHit: "+e);
	    e.printStackTrace();
	}
	this.lock.release();
	return actionMessage; 
    }
	 
      
    /** 
     * Player tries to lay a card
     * @param Player Number ID of the player
     * @param playerRound The round it is according to the player when it tries to lay its card
     * @return Returns true if player could lay a card, otherwise false
     */
    public boolean tryToLayCard(int playerNo, int playerRound) throws RemoteException {
	if(playerRound == this.round) {
	    Player trying = this.gamePlayers.get(playerNo);
	    trying.playNextCard(this.gameDeck);
	    return true;
	    }
	return false;
    }
   

    /**
     * A get method for the attribut round.
     * @return returns the round attribut.
     */
    public int getRound() throws RemoteException {
	return this.round;
    }

    
    /**
     * A get method for the attribut gameDeck.
     * @return returns the gameDeck attribut.
     */
    public Deck getGameDeck()throws RemoteException {
	return this.gameDeck;
    }
    

    /**
     * A get method for the attribut starterDeck.
     * @return returns the starterDeck attribut.
     */
    public Deck getStarterDeck() throws RemoteException{
	return this.starterDeck;
    }

    
    /**
     * A get method for the attribut gamePlayers.
     * @return returns the gamePlayers attribut.
     */
    public ArrayList<Player> getGamePlayers() throws RemoteException{
	return this.gamePlayers;
    }

    
    /**
     * A update state method for the object game.
     * @param round is the new round value.
     * @param gameDeck is the new gameDeck value.
     * @param starterDeck is the new starterDeck value.
     * @param gamePlayers is the new gamePlayers value.
     */
    public void setGameValues (int round,Deck gameDeck,Deck starterDeck,ArrayList<Player> gamePlayers) throws RemoteException {
	this.round = round;
	this.gameDeck = gameDeck;
	this.starterDeck = starterDeck;
	this.gamePlayers = gamePlayers;
    }


    /**
     * method that adds a player requesting a slot to a existing game. 
     * Player is placed at the first available slot in the Arraylist.
     * Slots are inhabited by "Empty" players intially. 
     * Theese are removed when asigning player to slot
     * @param inIp is the players internal IP.
     * @param exIp is the players external IP.
     * @param alias is the players alias.
     * @return an int indication the players assigned number.
     */
    public int addPlayer(String inIp, String exIp, String alias) throws RemoteException {
	try {
	    this.lock.acquire();

	    if(this.askIsGameFull()) {
		this.lock.release();	
		return -1;
	    }
	

	    for (int i = 0; i < gamePlayers.size(); i++) {
		if(gamePlayers.get(i).getPlayerName().equals("Empty")) {
		    gamePlayers.set(i,new Player(i, inIp, exIp,alias,true));
		    this.lock.release();
		    return i;
		}
	    }
	} catch( Exception e) {
	    e.printStackTrace();
	}
	this.lock.release();
	return -1;
    }


    /**
     * A method to retrive a players alias in the game.
     * Will fail if the playerNo is not in the bounds of the game.
     * @param playerNo is the number of the player whos alias is to be fetched.
     * @return the players alias.
     */
    public String getPlayerAlias(int playerNo) throws RemoteException {
	return this.gamePlayers.get(playerNo).getPlayerName();
    }


    /**
     * checks if the game is full.
     * @return a bollean indicating if full or not. True if full.
     */
    public boolean askIsGameFull() throws RemoteException {
	int lastIndex = this.gamePlayers.size()-1;
	if(this.gamePlayers.isEmpty()) return true;
	if(!this.gamePlayers.get(lastIndex).getPlayerName().equals("Empty")) return true;
	return false;
    }


    /**
     * Checks every players ready status and returns a boolean saying if all are ready.
     * @return returns true if all players are ready.
     */
    public boolean waitingForPlayers() throws RemoteException{
	for (int i = 0; i < gamePlayers.size(); i++) {
	    if((gamePlayers.get(i).getReadyValue() == false) &&
	       (gamePlayers.get(i).getPlayerName().equals("Empty"))) return false;	    
	}
	return true;
    }
    
    /**  
     * Checks every players answer time and returns a boolean saying if all are ready.
     * @return returns true if all players have answered
     */
    public boolean everyoneHasMadeMove() throws RemoteException{
	for (int i = 0; i < gamePlayers.size(); i++) {
	    if( (gamePlayers.get(i).getPlayerTime() == 0L) ) { return false; }
	       
	}
	return true;
    }


    /**
     * Ask method that checks if the game has ended.
     * @return boolean indicating if the game has ended.
     */
    public boolean askGameEnded() throws RemoteException{
	for (int i = 0; i < gamePlayers.size(); i++) {
	    if (gamePlayers.get(i).getPlayerRank() == -1) return false;
	}
	return true;
    }


    /**
     * display method for when the game has ended and the ranking is set.
     * @return a string describing the result.
     */
    public String displayGameResult() throws RemoteException{
	String result = "";
	for (int i = 0; i < this.gamePlayers.size(); i++) {
	    if(i != 0) result += "\n";
	    result += gamePlayers.get(i).getPlayerName();
	    result += "\nPlace: " + gamePlayers.get(i).getPlayerRank() + "\n";
	}
	return result;
    }

    
    public ArrayList<Player> sortPlayers() {
	ArrayList <Player> returnList = new ArrayList<Player>();
	for (int i = 0; i < gamePlayers.size(); i++) {
	    if (returnList.size() == 0) returnList.add(0,gamePlayers.get(i));

	    for (int i2 = 0; i2 < returnList.size(); i2++) {
		int compareTime = gamePlayers.get(i).compareTime(gamePlayers.get(i2));
		switch (compareTime) {
		case  1: if (gamePlayers.size()-1 == i &&
			     gamePlayers.size()-2 == i2) {
			//Kanske fett fel!
			returnList.add(i2+1,gamePlayers.get(i));
		    }
			break;
		case -1: returnList.add(i2,gamePlayers.get(i)); break;
		case  0: returnList.add(i2,gamePlayers.get(i)); break;
		}
	    }
	}
	return returnList;
    }


    
    public void distributeCards(ArrayList <Player> sortedList) throws RemoteException{
	while (starterDeck.getAmount() > 0) {
		for (int i = 0; i < sortedList.size(); i++){
		    if (gameDeck.getAmount() > 0){
			Card cardToInsert = gameDeck.getCard(true);
			sortedList.get(i).getPlayerDeck().addCard(cardToInsert);
		    }
		}
	}
    }


    public String enforceHitStatus(ArrayList <Player> sortedList, String action) throws RemoteException{
	for (int i = 0; i < sortedList.size();) {

	     if(sortedList.size() == 1 && action.equals("y")) {
		 giveWholeDeck(sortedList.get(0));
		    return "Player: "+
			sortedList.get(0).getPlayerName()+
			"picks up the game deck";
	     } else if (sortedList.size() == 1 && action.equals("n")) {
		 return "Goooooood";

	     } else if (sortedList.get(i).getPlayerAction().equals(action)) {
		 sortedList.remove(i);
	     } else i++;

	 }
	 distributeCards(sortedList);
	 String returnString = "Players: ";
	 for (int i = 0; i < sortedList.size(); i++) {
	     returnString += sortedList.get(0).getPlayerName()+" ";
	 }
	 returnString += "\nPicks up the game deck \n";
	 return returnString;
    }


    /**
     * get method for last event. 
     * @return returns the last event.
     */
    public String getLastEvent() throws RemoteException{
	return lastEvent;
    }
    
    
    /** 
     * Update the action for a player
     * @param playerNo The player's number ID
     * @param action to set.
     */
    public void updatePlayerAction(int playerNo, String action) throws RemoteException { 
	Player thisGuy = gamePlayers.get(playerNo);
	thisGuy.setPlayerAction(action);
    }

    
    public void askDealer() throws RemoteException{
	while(!everyoneHasMadeMove()) {
	    Thread.sleep(1000);
	    System.out.println("sleeeep");
	}
	ArrayList <Player> sortedList = sortPlayers();
	String action = sortedList.get(0).getPlayerAction();
	boolean movingOn = sortedList.get(0).getPlayerNumber() == whoseTurn() &&
	    action.equals("n");
	if (movingOn) {
	    lastEvent = "";
	} else if (!movingOn && timeToHit()) {
	    lastEvent = enforceHitStatus(sortedList,"y");
	    
	} else if (!movingOn && !timeToHit()) {
	    lastEvent = enforceHitStatus(sortedList,"n");
	    
	}
	
	tryToLayCard(gamePlayers.get(whoseTurn()).getPlayerNumber(), round);
	if( gamePlayers.get(whoseTurn()).getPlayerDeck().getDeckSize() == 0) {
	    int rank = 1;
	    for (int i = 0; i < gamePlayers.size(); i++) {
		if(gamePlayers.get(i).getPlayerNumber() != -1) rank++;
	    }
	    gamePlayers.get(whoseTurn()).setPlayerRank(rank);
	}
	round++;
	
    }

}

    

