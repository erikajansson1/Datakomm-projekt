import java.util.*;

//Deck class
public class Deck implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    private ArrayList<Card> cardList;
    private ArrayList<String> cardTypes;


    /**
     * Create empty deck
     */
    public Deck (int AmountOfCards) {
	this.cardList = new ArrayList<Card>(AmountOfCards);
    }

    
    /**
     * creates deck of 52 cards(a whole deck)
     */
    public Deck () {
	this.cardList = new ArrayList<Card>(52);
	this.cardTypes = new ArrayList<String>(4);
	this.buildDeck();
    }


    /**
     * Build method that helps the constructor build an entire deck.
     * calls upon build suit to build the deck.
     */
    public void buildDeck() {
	this.buildSuit("Heart");
	this.buildSuit("Spade");
	this.buildSuit("Club");
	this.buildSuit("Diamond");
    }


    /**
     * Build method that helps the constrctor creating a suit.
     */
    public void buildSuit(String suit) {
	this.cardTypes.add(suit);
	for (int i = 1; i <= 13; i++) {
	    this.cardList.add(new Card(i,suit));
	}

    }


    /**
     * A get method that returns the number of cards in the deck.
     * @return the amount of cards in the deck.
     */
    public int getDeckSize() {
	return this.cardList.size();
    }

    
    /**
     * Method to show the top card in the deck.
     * @return returns a string describing top card.
     */
    public String showTopCard() {
	if(cardList.size() == 0) {
	    Card emptyCard = new Card (0,"EMPTY");
	    return emptyCard.showCard();
	}
	return this.cardList.get(cardList.size()-1).showCard();
    }

    
    /**
     * Gets the card last in order and removes it from the current deck.
     * @return The last card and removes it from the deck.
     */
    public Card getCard() {
	cardList.trimToSize();
	int indexLastCard = cardList.size()-1;
    	Card getCard = cardList.get(indexLastCard);
	cardList.remove(indexLastCard);
    	return getCard;
    }
        
    
    /**
     * Shuffles the deck.
     */
    public void mixup() {
    	Collections.shuffle(this.cardList);
        }
    
    /**Add a card to the deck
     * Do we want to have the circle game deck to be a own deck class?
    */
    public void addCard(Card newCard) {
	this.cardList.add(newCard);
	return;
    }
   
    /**
     * Checks if the deck is possible to hit or not
     * @return a bool indicating if its possible to hit or not.
     */
    public boolean possibleToHit(int noPlayers){
	if(cardList.size() < 2) return false;

	int n = 0;
	int topCard = cardList.size()-1;
	int amount = cardList.size();

	if(amount > noPlayers){
	    while (n != noPlayers &&
		   (cardList.get(topCard).getRank() !=
		    cardList.get(topCard-(1+n)).getRank())){    
		    n++;
	    }
        	if (n == noPlayers){
        		return false;
        		}
        	else {
        		return true;
        		}
        	}
    	else {
	    while( amount != 0 &&
		   (cardList.get(topCard).getRank() !=
		    cardList.get(topCard-(n+1)).getRank())){
			amount--;
			n++;
		    }
		    if(amount != 0){
			return true; 
		    }
		    else {
			return false;
		    }		    
	    }
    }


    /**
     * A get method for the amount of cards in the deck
     * @return The amount of cards in the deck.
     */
    public int getAmount() {
	return cardList.size();
    }


    /**
     * A get method for the cardList.
     * @return returns the cardsList.
     */
    public ArrayList<Card> getCardList() {
	return this.cardList;
    }


    /**
     * Adds a external deck to this deck.
     */
    public void combineDeck(ArrayList<Card> toAdd) {
	this.cardList.addAll(toAdd);
    }
    
    /**
     * Emptys the current deck of all cards.
     */
    public void cleanDeck() {
	this.cardList.clear();
    }
    
}
