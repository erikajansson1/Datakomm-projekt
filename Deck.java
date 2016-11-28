import java.util.*;

//Deck class
public class Deck {
    private Card[] cardList;
    private int amountOfCards;
    public String[] cardTypes = {"Heart", "Spade", "Club", "Diamond"};

    //Create deck of 52 cards(a whole deck)
    public Deck () {
    this.amountOfCards = 52;
	for (int i = 0; i < cardTypes.length; i++) {
	  for (int cardNumber = 0; cardNumber < 13; cardNumber++){
			this.cardList[(((i+1)*cardNumber)-1)] = new Card(cardNumber, cardTypes[i].toString());
		}
	  }       
	}
    

    //Get one card from deck, (Do we want to be able to grab more?)
    //Takes the last card in the deck since it upside down
    public Card getCard() {
    	Card getCard = cardList[amountOfCards];
    	cardList[amountOfCards] = null;
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
   
}
