import java.util.*;

public class Card {
    private int rank;
    private String suit;
    private String name;

    public Card (int rank, String suit, String name) {
	this.rank = rank;
	this.suit = suit;
	this.name = name;
    }

    public String getSuit() {
	return suit;
    }
    
}
