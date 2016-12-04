import java.rmi.*;
import java.rmi.server.*;
import java.util.*;
import java.util.concurrent.Semaphore;
 
public class Game extends UnicastRemoteObject implements GameInterface {
    private static final long serialVersionUID = 1L;
    private final Semaphore lock;
    private int round;
    private Deck gameDeck;
    private Deck starterDeck;
    private ArrayList<Player> gamePlayers; 
 
    public Game (int round,Deck gameDeck,Deck starterDeck,ArrayList<Player> gamePlayers) throws RemoteException {
	this.lock = new Semaphore(1);
	setGameValues (round,gameDeck,starterDeck,gamePlayers);
    }

    public Game (int numberOfPlayers) throws RemoteException {
	super(1099);
	this.lock = new Semaphore(1);
	this.round = 0;
	this.gameDeck = new Deck(0); //Which should be empty?
	this.starterDeck = new Deck();
	this.gamePlayers = new ArrayList<Player>(numberOfPlayers);
	for (int i = 0; i < numberOfPlayers; i++) {
	    gamePlayers.add(i,new Player(i,"","","Empty",false));
	}
	
    }

    /** 
     * Checks whether it's time to hit,
     *  that is, check whether any of the previous 4 cards
     *  match eachother
     */
    public boolean timeToHit() throws RemoteException {
    	if (gameDeck.possibleToHit()){
    		//Start time? not sure
    		return true;
    	}
 	return false;
    }; //TODO


    /** Prints out a view of the board:
     *  whose turn it is, and the latest card
     */
    public String displayBoard() throws RemoteException {
	String players = "";
	for (int i = 0; i < this.gamePlayers.size(); i++) {
	    if(!(i == 0)) players += "\n";

	    players += ""+gamePlayers.get(i).getPlayerName();
	    players += "\nStatus: ";
	    
	    if(gamePlayers.get(i).getReadyValue()){
		players += "READY\n";
		    } else {
		players += "NOT READY\n";
		    }	
	}
	String whosTurn = "Turn: ";

	    return players;
    }

    
    /** 
     * Set the ready value for a player 
     * @param playerNo The player's number ID
     * @param readyValue The new ready value
     */
    public void setReadyValue(int playerNo, boolean readyValue) throws RemoteException {
	/*Player thisPlayer = this.findPlayer(alias);
	  if (thisPlayer.getPlayerNumber() == -1) { return; } //if nobody has the given alias*/
	Player thisPlayer = gamePlayers.get(playerNo);
	thisPlayer.setReadyValue(readyValue);
    }; 

    /** 
     * Update the hit time for a player
     * @param playerNo The player's number ID
     * @param hitTime The new hit time
     */
    public void updatePlayerTime(int playerNo, int hitTime) throws RemoteException { 
	/*Player thisGuy = findPlayer(alias);
	String name = thisGuy.getPlayerName();
	if (name.equals("")) { return; }
	else {
	    setPlayerTime(hitTime);
	    return;
	    }*/
	Player thisGuy = gamePlayers.get(playerNo);
	thisGuy.setPlayerTime(hitTime);
    }

    
    /** 
     * Checks whose turn it is, and if it's time for a new turn. If so, it updates accordingly. 
     * @param currRound Taken to ensure that only one such update is done every round.
     * @return The round value 
     */
    public int updateRound(int currRound) throws RemoteException{
	//TODO if all the players are ready to continue, round++
	//TODO Semaphore to ensure atomical updating
	//TODO Compare so that you dont update the Round twice
	this.round++;
	return round;
    }


    /**
     * Checks whose turn it is and returns the index of the player.
     * @return an int which is the players index.
     */
    public int whoseTurn() throws RemoteException{
	return 1;
    }

    /** 
     * Finds the Player object in an array whose name matches the given parameter
     * @param alias 
     * @return The found Player object, or an Player object with specific invalid values     
     */
    public Player findPlayer(String alias) throws RemoteException {
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
     * Initiate game by giving out cards and select who start first 
     */
    //TODO: Who should start first, create  ---the server starts the game (playerNo = 1)
    /*  public void startGame(int amountOfPlayers) { 

	} 

	}
	
    */
	 
    /**
     * Tells the amount of players in the game
     */
    public int getAmountOfPlayers(){
    	int amountPlayers = this.gamePlayers.size();
    	return amountPlayers;
    }
   
    
    /** Next player should be able to place a cards 
     */
    //TODO: I don't know if this is needed, maybe this will tell next player to  
    //add its card 
    public void nextplayer() { 
	
    } 
	 
    //Inform player who lost and resign it from game 
    public void looseGame(){ // Should have decided player that lost 
	
    } 
 
 
    //Method to hand out the card from the middle deck to the player who lost
    /**
     * documentation
     */
    //TODO: This only gives one card, not whole deck 
    public void giveWholeDeck(Player loserPlayer){ 
	loserPlayer.getCardFromMiddleDeck(this.gameDeck);
    } 
	 
	 
    //Handle if someone hits at wrong time 
    public void handleWrongHit(){ 
	//Look at the player that send the wrong signal, tell it it did bad 
	//Give the whole deck to it 
    } 
		 
	 
    //Handle when players hit at right time, last on will have to  
    //pick up whole deck 
    public void handleRightHit(){ 
	//wait for all to hit, then tell the last one it lost 
	//give the whole deck to it 
    } 
	 
 
    //Look what four cards are legit to be hit  
    public void whatFourCards(){ 
	if (gameDeck.possibleToHit() == true) { 
	    return; 
	} 
    } 
	 
    //Who is the slowest when hitting the deck. Give the GameDeck to the loser 
    public void whoLostRightHit(){
	//TODO Compare times in the player objects and reset it after determining who lost.
	return; 
    }

    /**
     * A get method for the attribut round.
     * @return returns the round attribut.
     */
    public int getRound() throws RemoteException{
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
	
	    for (int i = 0; i <= (gamePlayers.size()-1); i++) {
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
	    if(gamePlayers.get(i).getReadyValue() == false) return false;	    
	}
	return true;
    }




    
}

