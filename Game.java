import java.rmi.*;
import java.rmi.server.*;
import java.util.*;
 
public class Game extends UnicastRemoteObject implements GameInterface {
    private static final long serialVersionUID = 1L;
    private int round;
    private Deck gameDeck;
    private Deck starterDeck;
    private Player[] gamePlayers; 
    //   private ArrayList<String> playerNames;

    public Game (int numberOfPlayers, int playerNO, int controlValue) throws RemoteException {
	super(1099);
	this.round = 0;
    }

    /** Checks whether it's time to hit,
     *  that is, check whether any of the previous 4 cards
     *  match eachother
     */
    public long timeToHit() throws RemoteException { return 3; }; //TODO

    /** Prints out a view of the board:
     *  whose turn it is, and the latest card
     */
    public String displayBoard() throws RemoteException { return "hej"; };  //TODO

    /** Set the ready value for a player with alias "alias". 
     */
    public void setReadyValue(String alias, boolean readyValue) throws RemoteException {
	Player thisPlayer = this.findPlayer(alias);
	if (thisPlayer.getPlayerNumber() == -1) { return; } //if nobody has the given alias
	thisPlayer.setReadyValue(readyValue);	
    }; //TODO

    /** Update the hit time for a player with alias "alias"     
     */
    public void updatePlayerTime(String alias, int hitTime) throws RemoteException { 
	//TODO: matcha alias mot players för att hitta rätt player
	//TODO: uppdatera playerns hitTime-attribute	
    }

    /** Checks whose turn it is, and if it's time for a new turn. If so, it updates accordingly. 
     * @param currRound Taken to ensure that only one such update is done every round.
     * @return The round value 
     */
    public int whoseRound(int currRound) throws RemoteException{
	//TODO om alla spelar är Redo för (erika) att nästa spelare kan å lägga....sktunge..., increment round by 1
	//TODO Semaphore vid värde skifte/uppdatering av round.
	//TODO jömföra så man inte uppdaterar round mer än +1
	return round;
    }

    /** Finds the Player object in an array whose name matches the given parameter
	@param alias 
	@return The found Player object, or an Player object with specific invalid values     
     */
    public Player findPlayer(String alias) throws RemoteException {
	int len = gamePlayers.length;
	String name;
	for(int i=0; i < len; i++) {
	    name = gamePlayers[i].getPlayerName();
	    if (name.equals(alias)) {
		return gamePlayers[i];
	    }
	}
	Player nobody = new Player(-1);
	return nobody;
    }


    /**
       Initiate game by giving out cards and select who start first 
     */
	//TODO: Who should start first, create  
	public void startGame(int amountOfPlayers) { 
		this.gameDeck = new Deck();  
		for(int n = 1; amountOfPlayers == n; amountOfPlayers-- ){ 
			gamePlayers[amountOfPlayers-1] = new Player(amountOfPlayers); 
		} 
	return; 
	}

    //TODO public void addPlayer (string Alias) {}
    //funktion. Semaphore vid skapande. Alla clienter påkallar denna vid anslutning för att tilldelas sin "plats". Client klassen får stå för att fråga efter alias.
	 
    /** Next player should be able to place a cards 
     */
	//TODO: I don't know if this is needed, maybe this will tell next player to  
	//add its card 
	public void nextplayer() { 
		return; 
	} 
	 
	//Inform player who lost and resign it from game 
	public void looseGame(){ // Should have decided player that lost 
		return; 
	} 
 
 
    //Method to hand out the card from the middle deck to the player who lost
    /**
     * documentation
     */
	//TODO: This only gives one card, not whole deck 
	public void giveWholeDeck(Player loserPlayer){ 
		Card moveCard = gameDeck.getCard(); 
		loserPlayer.playerDeck.addCard(moveCard); 
		return; 
	} 
	 
	 
    //Handle if someone hits at wrong time 
	public void handleWrongHit(){ 
		//Look at the player that send the wrong signal, tell it it did bad 
		//Give the whole deck to it 
		return; 
	} 
		 
	 
    //Handle when players hit at right time, last on will have to  
	//pick up whole deck 
	public void handleRightHit(){ 
		//wait for all to hit, then tell the last one it lost 
		//give the whole deck to it 
		return; 
	} 
	 
 
	//Look what four cards are legit to be hit  
	public void whatFourCards(){ 
		if (gameDeck.possibleToHit() == true) { 
			return; 
		} 
		return; 
	} 
	 
	//Who is the slowest when hitting the deck. Give the GameDeck to the loser 
	public void whoLostRightHit(){
	    //Jömför tiderna i player objekten och nolla efter att ha kollat.
		return; 
	} 
}
