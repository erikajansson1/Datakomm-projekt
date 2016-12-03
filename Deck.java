import java.util.*;

//Deck class
public class Deck {
    private ArrayList<Card> cardList;
    private ArrayList<String> cardTypes;

    //Create empty deck
    public Deck (int AmountOfCards) {
	this.cardList = new ArrayList<Card>(AmountOfCards);
    }

    //Create deck of 52 cards(a whole deck)
    public Deck () {
	this.cardList = new ArrayList<Card>(52);
	this.cardTypes = new ArrayList<String>(4);
	this.buildDeck();
    }


    public void buildDeck() {
	this.buildSuit("Heart");
	this.buildSuit("Spade");
	this.buildSuit("Club");
	this.buildSuit("Diamond");
    }

    
    public void buildSuit(String suit) {
	this.cardTypes.add(suit);
	for (int i = 1; i <= 13; i++) {
	    this.cardList.add(new Card(i,suit));
	}

    }

    //Get one card from deck
    public Card getCard() {
	cardList.trimToSize();
	int indexLastCard = cardList.size()-1;
    	Card getCard = cardList.get(indexLastCard);
	cardList.remove(indexLastCard);
    	return getCard;
    }
    
    
    //Mix up the deck so everything is not in order
    public ArrayList<Card> mixup() {
    	Collections.shuffle(this.cardList);
    	return this.cardList;	
    }
    
    /**Add a card to the deck
     * Do we want to have the circle game deck to be a own deck class?
    */
    public void addCard(Card newCard) {
	this.cardList.add(newCard);
	return;
    }
   
    //Check if possible to hit, 
    public boolean possibleToHit(){
    	int n = 0;
	int index = cardList.size()-1;
	    int amount = cardList.size();
    	if(amount > 4){
	    while (cardList.get(index).getRank() !=
		   cardList.get(index-(1+n)).getRank() &&
		   n != 4){    
		    n++;
		    }
        	if (n == 4){
        		return false;
        		}
        	else {
        		return true;
        		}
        	}
    	else {
	    while((cardList.get(index).getRank() !=
		   cardList.get(index-(n+1)).getRank() &&
		   amount != 0)){
			amount--;
			n++;
		    }
		    if(amount != 0){
			return false; 
		    }
		    else {
			return true;
		    }		    
	    }
    }

    public int getAmount() {
	return cardList.size();
    }
    
    public ArrayList<Card> getCardList() {
	return this.cardList;
    }

    public void combineDeck(ArrayList<Card> toAdd) {
	this.cardList.addAll(toAdd);
    }
    
    
    public void cleanDeck() {
	this.cardList.clear();
    }
    
}
