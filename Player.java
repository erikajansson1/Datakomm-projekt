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
    public String nameOfPlayer;
    public Deck playerDeck;
    public int amountOfCardsOnHands = 0;
    public int numberOfPlayer;
    //TODO: Add IP, ext and int.
    //TODO: Ready to play?
    //TODO: Won or lost game attribute? ended up at place 2...ex
    //TODO: int hitTime
    
      

    public Player (int numberOfPlayer) {
	this.numberOfPlayer = numberOfPlayer;
	this.playerDeck = new Deck(1);
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
    //Make a for loop
    public void getCardFromMiddleDeck(Deck gameDeck){
    	for (int n = 0; gameDeck.amountOfCards > n;){ 
    	playerDeck.cardList[amountOfCardsOnHands] = gameDeck.getCard();
    	amountOfCardsOnHands++;
    	}
    	return;
    }    
}