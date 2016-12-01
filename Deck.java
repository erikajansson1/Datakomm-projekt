import java.util.*;

//Deck class
public class Deck {
    public Card[] cardList;
    public int amountOfCards;
    private String[] cardTypes = {"Heart", "Spade", "Club", "Diamond"};

<<<<<<< ours
=======
    public Deck (int skitunge) {
    }

>>>>>>> theirs
    //Create deck of 52 cards(a whole deck)
    public Deck () {
    this.amountOfCards = 52;
	for (int i = 0; i < cardTypes.length; i++) {
	  for (int cardNumber = 1; cardNumber < 13; cardNumber++){
			this.cardList[(((i+1)*cardNumber)-1)] = new Card(cardNumber, cardTypes[i].toString());
		}
	  }       
	}    

    //Get one card from deck, (Do we want to be able to grab more?)
    //Takes the last card in the deck since it upside down
    public Card getCard() {
<<<<<<< ours
    	Card getCard = cardList[amountOfCards];
    	cardList[amountOfCards] = null;
=======
    	Card getCard = cardList[amountOfCards--];
    	cardList[amountOfCards--] = null;
>>>>>>> theirs
    	amountOfCards--;
    	return getCard;
    }
    
    
    //Mix up the deck so everything is not in order, TODO: Do we need to return?
    public Card[] mixup() {
    	Collections.shuffle(Arrays.asList(cardList));
    	return cardList;	
    }
    
    /*Add a card to the deck (This could be useful for the deck you have in the middle)
     * Do we want to have the circle game deck to be a own deck class?
    */
    public void addCard(Card newCard) {
    cardList[amountOfCards++] = newCard;
    amountOfCards++;
    return;
    }
   
    //Check if possible to hit, in case of starts to count time and 
    //will send back the time it takes for the player to hit.
    public boolean possibleToHit(){
    	int n = 0;
	    int index = amountOfCards--;
	    int amount = amountOfCards;
    	if(amountOfCards > 4){
        	while (cardList[index].getRank() != cardList[index-- - n].getRank() && n != 4){    
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
   	while((cardList[index].getRank() != cardList[index-- - n].getRank() && amount != 0)){
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

    
    
    
    
    
    
    
}
