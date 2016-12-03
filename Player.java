//Player class that should be given to each client
/*TODO: Needs time Attribute
 * long tStart = System.currentTimeMillis(); // When times starts
 * 
 * 
 * long tEnd = System.currentTimeMillis(); //When times starts
 * long tDelta = tEnd - tStart;
 * double elapsedSeconds = tDelta / 1000.0;
 */
public class Player implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    private String nameOfPlayer;
    private Deck playerDeck;
    private int numberOfPlayer;
    private String inIp;
    private String exIp;
    private boolean readyToPlay;
    private String rankWhenFinished;
    private int hitTime;
      

    public Player (int numberOfPlayer, String inIp, String exIp, String alias,boolean ready) {
	this.numberOfPlayer = numberOfPlayer;
	this.playerDeck = new Deck(1);
	this.inIp = inIp;
	this.exIp = exIp;
	this.nameOfPlayer = alias;
	this.readyToPlay = ready;
	this.rankWhenFinished = "";
	this.hitTime = -1;
    }

    
    public boolean getReadyValue() {
	return this.readyToPlay;
    }
    
    public void setReadyValue(boolean ready) {
	this.readyToPlay = ready;
    }

    public String getPlayerName() {
	return this.nameOfPlayer;
    }
    
    public int getPlayerNumber() {
	return numberOfPlayer;
    }

    public Deck getPlayerDeck() {
	return this.playerDeck;
    }
    
    public void playNextCard(Deck gameDeck){
	Card cardToLay = playerDeck.getCard();
	gameDeck.addCard(cardToLay);
    }
    //Take up the game deck from losing 
	public void getCardFromMiddleDeck(Deck gameDeck){
	playerDeck.combineDeck(gameDeck.getCardList());
	gameDeck.cleanDeck();
    }
}
