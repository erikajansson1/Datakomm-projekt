

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

    //Slå på högen

    //Ta upp mitten högens kort

    //Kolla om det är "min" tur

    //Kolla om det går att slå, isf börja mät tid
}
