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
<<<<<<< ours
=======
    //TODO: Add IP, ext and int.
    //TODO: Ready to play?
    //TODO: Won or lost game attribute? ended up at place 2...ex
    //TODO: int hitTime
    
>>>>>>> theirs
      

    public Player (int numberOfPlayer) {
	this.numberOfPlayer = numberOfPlayer;
<<<<<<< ours
	this.playerDeck = new Deck();
=======
	this.playerDeck = new Deck(1);
>>>>>>> theirs
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

<<<<<<< ours
    //Hit the deck
    public void hitTheDeck(){
    	//While loops that waits for a input?
    }

=======
>>>>>>> theirs
    //Take up the game deck from losing 
    //Make a for loop
    public void getCardFromMiddleDeck(Deck gameDeck){
    	for (int n = 0; gameDeck.amountOfCards > n;){ 
    	playerDeck.cardList[amountOfCardsOnHands] = gameDeck.getCard();
    	amountOfCardsOnHands++;
    	}
    	return;
<<<<<<< ours
    }
    
    //Let player check if it possible to hit now
    public void canPlayerhit(Deck middleDeck){
    	if (middleDeck.possibleToHit() == true){
    		//start time
    		//wait until player hit or someone places a new card(implement in round)
    		//stop time
    		//return time it took
    		return;
    	}
    	return;
    }
    
   
    //Looks if it's players turn
    public void myTurn(){
    	//While loop that waits for it's turn or else it'll check the round again
    }
}
=======
    }    
}
>>>>>>> theirs
