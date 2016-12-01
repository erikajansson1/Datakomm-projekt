import java.rmi.*;
import java.rmi.server.*;
 
public class Game extends UnicastRemoteObject implements GameInterface {
    private static final long serialVersionUID = 1L;
    private int round;
    private Deck gameDeck;
    private Deck starterDeck;
    private Player[] gamePlayers; 

    public Game (int numberOfPlayers, int playerNO, int controlValue) throws RemoteException {
	super(1099);
	this.round = 0;
    }

    public int whoseRound(int currRound) throws RemoteException{
	//TODO om alla spelar är Redo för (erika) att nästa spelare kan å lägga....sktunge..., increment round by 1
	//TODO Semaphore vid värde skifte/uppdatering av round.
	//TODO jömföra så man inte uppdaterar round mer än +1
	return round;
    }

  	//Initiate game by giving out cards and select who start first 
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
	 
	//Next player should be able to place a cards 
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
