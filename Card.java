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

    public int getRank(){
	return rank;
    }

    public String getCard(){
	String dispCard = "["+ suit + Integer.toString(rank) + "]";
    return dispCard;
    }
    
}
