
public class Card implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    private int rank;
    private String suit;

    public Card (int rank, String suit) {
	this.rank = rank;
	this.suit = suit;
    }


    /**
     * A get method for suit.
     * @return returns the suit of the card.
     */
    public String getSuit() {
	return suit;
    }


    /**
     * A get method for rank.
     * @return returns the rank of the card.
     */
    public int getRank(){
	return rank;
    }


    /**
     * A toString method for card showing the suit and value.
     */
    public String showCard(){
	String dispCard = "["+ suit + Integer.toString(rank) + "]";
    return dispCard;
    }
    
}
