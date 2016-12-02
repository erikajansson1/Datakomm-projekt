import java.util.*;

//Deck class
public class Deck {
    private ArrayList<Card> cardList;
    private ArrayList<String> cardTypes;

    public Deck (int skitunge) {
    }

    //Create deck of 52 cards(a whole deck)
    public Deck () {
	this.cardList = new ArrayList<Card>(52);
	this.cardTypes = new ArrayList<String>(4);

	this.cardTypes.add("Heart");
	this.cardTypes.add("Spade");
	this.cardTypes.add("Club");
	this.cardTypes.add("Diamond");
	for (int i = 1; i <= 13; i++) {
	    cardList.add(i-1,new Card(i,"Heart"));
	    cardList.add(i+12,new Card(i,"Spade"));
	    cardList.add(i+25,new Card(i,"Club"));
	    cardList.add(i+38,new Card(i,"Club"));   
	}
    }

    //Get one card from deck, (Do we want to be able to grab more?)
    //Takes the last card in the deck since it upside down
    public Card getCard() {
	cardList.trimToSize();
	int indexLastCard = cardList.size()-1;
    	Card getCard = cardList.get(indexLastCard);
	cardList.remove(indexLastCard);
    	return getCard;
    }
    
    
    //Mix up the deck so everything is not in order, TODO: Do we need to return?
    public ArrayList<Card> mixup() {
    	Collections.shuffle(this.cardList);
    	return this.cardList;	
    }
    
    /*Add a card to the deck (This could be useful for the deck you have in the middle)
     * Do we want to have the circle game deck to be a own deck class?
    */
    public void addCard(Card newCard) {
	this.cardList.add(newCard);
	return;
    }
   
    //Check if possible to hit, in case of starts to count time and 
    //will send back the time it takes for the player to hit.
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
    
    
    
    
}
