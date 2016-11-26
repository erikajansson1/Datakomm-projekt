import java.util.*;

public class Card {
    private int rank;
    private String suit;

    public Card (int rank, String suit) {
	this.rank = rank;
	this.suit = suit;
    }

    public String getSuit() {
	return suit;
    }
    
}
