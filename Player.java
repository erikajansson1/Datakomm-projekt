//Player class that should be given to each client
/*TODO: Needs time Attribute
 * long tStart = System.currentTimeMillis(); // When times starts
 * 
 * 
 * long tEnd = System.currentTimeMillis(); //When times starts
 * long tDelta = tEnd - tStart;
 * double elapsedSeconds = tDelta / 1000.0;
 */
public class Player implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    private String nameOfPlayer;
    private Deck playerDeck;
    private int amountOfCardsOnHands = 0;
    private int numberOfPlayer;
    private String inIp;
    private String exIp;
    private boolean readyToPlay;
    //TODO: Won or lost game attribute? ended up at place 2...ex
    //TODO: int hitTime
    
      

    public Player (int numberOfPlayer, String inIp, String exIp, String alias) {
	this.numberOfPlayer = numberOfPlayer;
	this.playerDeck = new Deck(1);
	this.inIp = inIp;
	this.exIp = exIp;
	this.nameOfPlayer = alias;
	}

    
    public boolean getReadyValue() {
	return this.readyToPlay;
    }
    
    public void setReadyValue(boolean ready) {
	this.readyToPlay = ready;
    }

    public String getPlayerName() {
	return this.nameOfPlayer;
    }
    
    public int getPlayerNumber() {
	return numberOfPlayer;
    }

    //Add a card
    // This should have a list of 0 to 51 which is 52 cards. So we need 
    // to think about that
    public Card dropCard(Deck gameDeck){
		Card cardToDrop = playerDeck.getCard();
		gameDeck.addCard(cardToDrop);
		amountOfCardsOnHands--;	
		return cardToDrop;
	}

    //Take up the game deck from losing 
    public void getCardFromMiddleDeck(Deck gameDeck){
	playerDeck.combineDeck(gameDeck.getCardList());
    }    
}
