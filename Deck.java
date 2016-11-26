import java.util.*;

//Deck class
public class Deck {
    private Card card;
    private int amountOfCards;
    public String[] cardTypes = {"Heart", "Spade", "Club", "Diamond"};

    //Create deck, TODO: Make sure 52 cards are made of specific class.
    public Deck (int amountOfCards) {
    this.amountOfCards = amountOfCards;
	for (int i = 0; i < cardTypes.length; i++) {
	  for (int cardNumber = 0; cardNumber < 13; cardNumber++){
			this.card = new Card(cardNumber, cardTypes[i].toString());
		}
	}
    }

    //Get one card from deck, (Do we want to be able to grab more?)
    public Card getCard() {
	return card;
    }
    
    //Mix up the deck so everyting is not in order
    public Card mixup() {
    return card;	
    }
    
    /*Add a card to the deck (This could be useful for the deck you have in the middle)
     * Do we want to have the circle game deck to be a own deck class?
    */
    public Card addCard() {
    return card;
    }
   
}
