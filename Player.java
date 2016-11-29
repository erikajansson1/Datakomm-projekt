

//Player class that should be given to each client
/*TODO: I think we need to place this a superclass, where we split
 * Players from game master.
 */
public class Player {
    public String nameOfPlayer;
    public Deck playerDeck;
    public int amountOfCardsOnHands = 0;
    public int numberOfPlayer;
      

    public Player (numberOfPlayer) {
	this.numberOfPlayer = numberOfPlayer;
	this.nameOfPlayer = nameOfPlayer;
    }

    //Lägga ett kort

    public Card dropCard() {
	Card cardToDrop = playerDeck[amountOfCardsOnHands];
	Deck.addCard(cardToDrop);
	playerDeck[amountOfCardsOnHands--];
	amountOfCardsOnHands--;

	return cardToDrop;
    }

    //Slå på högen

    public void hitTheDeck(){

    }

    //Ta upp mitten högens kort

    public void getCardFromMiddleDeck(){
	playerDeck[amountOfCardsOnHands++] = Deck.getCard();
	amountOfCardsOnHands++;
    }
    //Kolla om det är "min" tur

    public void myTurn(){

    }

    //Kolla om det går att slå, isf börja mät tid, tid = int?????

    public boolean possibleToHit(Deck middleDeck){
	if(middleDeck.amountOfCards > 4){
	    int n = 0;
	    while ((middleDeck[amountOfCards].getRank() != middleDeck[amountOfCards-- - n].getRank() && n != 4){    
		    n++;
		}

		if (n == 4){
		    return false;
		}


		else {
		    //börja mät tid
		    return true;
		}
		}
	    else {
		int amount = amountOfCards;
		int n = 0;
		while((middleDeck[amountOfCards].getRank() != middleDeck[amountOfCards-- - n].getRank() && amount != 0){
			amount--;
			n++;
		    }

		    if(amount != 0){
			return false; 
		    }
		    else {
			//börja mät tid
			return true;
		    }

	    }
		return; 
	}
	    



	/* for(amountOfCards; amountOfCards > (amountOfCards - 5); amountOfCards--){
	   middleDeck[amountOfCards].Card.getRank() = 
	   }
	*/
