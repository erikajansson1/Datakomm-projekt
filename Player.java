
public class Player implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    private String nameOfPlayer;
    private Deck playerDeck;
    private int numberOfPlayer;
    private String inIp;
    private String exIp;
    private boolean readyToPlay;
    private int rankWhenFinished;
    private long answerTime;
      

    public Player (int numberOfPlayer, String inIp, String exIp, String alias,boolean ready) {
	this.numberOfPlayer = numberOfPlayer;
	this.playerDeck = new Deck(1);
	this.inIp = inIp;
	this.exIp = exIp;
	this.nameOfPlayer = alias;
	this.readyToPlay = ready;
	this.rankWhenFinished = -1;
	this.answerTime = 0L;
    }

    
    /** 
     * Get this Player's ready value
     */
    public boolean getReadyValue() {
	return this.readyToPlay;
    }

    
    /** 
     * Set this Player's ready value-attribute to given parameter ready
     * @param ready value
     */
    public void setReadyValue(boolean ready) {
	this.readyToPlay = ready;
    }

    
    /** 
     * Get this Player's name (alias)
     */
    public String getPlayerName() {
	return this.nameOfPlayer;
    }

    
    /** 
     * Set this Player's name (alias)
     */
    public void setPlayerName(String alias) {
	this.nameOfPlayer = alias;
    }

    
    /** 
     * Get this Player's player number
     */
    public int getPlayerNumber() {
	return this.numberOfPlayer;
    }

    
    /** 
     * Get the final rank of the players     
     */
    public int getPlayerRank() {
	return this.rankWhenFinished;
    }

    
    /**
     * Set the final rank of the players     
     */
    public void setPlayerRank(int rank) {
	this.rankWhenFinished = rank;
    }

    
    /** 
     * Get this Player's deck
     */
    public Deck getPlayerDeck() {
	return this.playerDeck;
    }
    
    /** 
     * Set this player's answerTime-attribute to given parameter time
     * @param time
     */
    public void setPlayerTime(long time) {
	this.answerTime = time;
    }

/**
 * Returns the players answerTime.
 */
    public long getPlayerTime() {
	return this.answerTime;
    }

    
    /** 
     * TODO
     */
    public void playNextCard(Deck gameDeck){
	Card cardToLay = playerDeck.getCard();
	gameDeck.addCard(cardToLay);
    }

    
    /** 
     * Pick up the game deck  
     */
    public void getCardFromMiddleDeck(Deck gameDeck){
	playerDeck.combineDeck(gameDeck.getCardList());
	gameDeck.cleanDeck();
    }


    /**
     * returns the amount of cards the player currentyl has.
     */
    public int getAmountOfCardsOnHand() {
	return this.playerDeck.getDeckSize();
    }
 
}
