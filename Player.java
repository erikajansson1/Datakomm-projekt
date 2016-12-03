//Player class that should be given to each client
/*TODO: Needs time Attribute
 * long tStart = System.currentTimeMillis(); // When times starts
 * 
 * 
 * long tEnd = System.currentTimeMillis(); //When times starts
 * long tDelta = tEnd - tStart;
 * double elapsedSeconds = tDelta / 1000.0;
 */
public class Player {
    private String nameOfPlayer;
    private Deck playerDeck;
    private int amountOfCardsOnHands = 0;
    private int numberOfPlayer;
    private String extIP;
    private String intIP;
    private boolean readyToPlay;
    private String rankWhenFinished;
    private int hitTime;
      

    public Player (int numberOfPlayer) {
	this.numberOfPlayer = numberOfPlayer;
	this.playerDeck = new Deck(1);
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

    public Card dropCard(Deck gameDeck){
		Card cardToDrop = playerDeck.getCard();
		gameDeck.addCard(cardToDrop);
		amountOfCardsOnHands--;	
		return cardToDrop;
	}

    public void playNextCard(Deck gameDeck){
	Card cardToLay = playerDeck.getCard();
	gameDeck.addCard(cardToLay);

    }
    //Take up the game deck when you are the last player to hit the deck
    //Make a for loop
    public void getCardFromMiddleDeck(Deck gameDeck){
	
    amountOfCardsOnHands = amountOfCardsOnHands + gameDeck.getAmount();
	playerDeck.combineDeck(gameDeck.getCardList());
    }    
}
