import java.util.*;

//Deck class
public class Deck {
    private Card card;
    private int amountOfCards;

    //Create deck, TODO: Make sure 52 cards are made of specific class.
    public Deck (Card card, int amountOfCards) {
	this.card = card;
	this.amountOfCards = amountOfCards;
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
