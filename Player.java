

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

    public int possibleToHit(){
	for(int i = 0; i < 4; )
	
	return time; 
    }
}
