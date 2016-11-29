

//Player class that should be given to each client
/*TODO: I think we need to place this a superclass, where we split
 * Players from game master.
 */
public class Player {
    public String nameOfPlayer;
    public Deck playerDeck;
    public int amountOfCardsOnHands = 0;
    public int numberOfPlayer;
      

    public Player (int numberOfPlayer) {
	this.numberOfPlayer = numberOfPlayer;
	this.playerDeck = new Deck();
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

    //Hit the deck
    public void hitTheDeck(){
    	//While loops that waits for a input?
    }

    //Take up the game deck from losing 
    public void getCardFromMiddleDeck(Deck gameDeck){
    	playerDeck.cardList[amountOfCardsOnHands] = gameDeck.getCard();
    	amountOfCardsOnHands++;
    }
    
   
    //Looks if it's players turn
    public void myTurn(){
    	//While loop that waits for it's turn or else it'll check the round again
    }

}
	    



	/* for(amountOfCards; amountOfCards > (amountOfCards - 5); amountOfCards--){
	   middleDeck[amountOfCards].Card.getRank() = 
	   }
	*/
