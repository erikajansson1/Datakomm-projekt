//This is the general game concept
public class Game {
	public Deck gameDeck;
	public Deck starterDeck;
	public Player[] gamePlayers;
	
	//Initiate game by giving out cards and select who start first
	//TODO: Who should start first, create 
	public void startGame(int amountOfPlayers){
		this.gameDeck = new Deck(); 
		for(int n = 1; amountOfPlayers == n; amountOfPlayers-- ){
			gamePlayers[amountOfPlayers-1] = new Player(amountOfPlayers);
		}
	return;
	} 
	
	//Next player should be able to place a cards
	//TODO: I don't know if this is needed, maybe this will tell next player to 
	//add its card
	public void nextplayer() {
		return;
	}
	
	//Inform player who lost and resign it from game
	public void looseGame(){ // Should have decided player that lost
		return;
	}


    //Method to hand out the card from the middle deck to the player who lost
	//TODO: This only gives one card, not whole deck
	public void giveWholeDeck(Player loserPlayer){
		Card moveCard = gameDeck.getCard();
		loserPlayer.playerDeck.addCard(moveCard);
		return;
	}
	
	
    //Handle if someone hits at wrong time
	public void handleWrongHit(){
		//Look at the player that send the wrong signal, tell it it did bad
		//Give the whole deck to it
		return;
	}
		
	
    //Handle when players hit at right time, last on will have to 
	//pick up whole deck
	public void handleRightHit(){
		//wait for all to hit, then tell the last one it lost
		//give the whole deck to it
		return;
	}
	
	//If it's possible to hit start time
	public void timeToHit(){
		
		return;		
	}

	//Look what four cards are legit to be hit 
	public void whatFourCards(){
		if (gameDeck.possibleToHit() == true) {
			return;
		}
		return;
	}
	
	//Who is the slowest when hitting the deck. Give the GameDeck to the loser
	public void whoLostRightHit(){
		return;
	}
	
    /*Count the amount of times a card have been place in the deck
     * This to be able to tell other players you have put out something
     * While loop, waies until round updates, that is if someone places a card
     * */
	public void round(){
		//So much shit that need to done here OTL 	
		//This is important
		return;
	}
}
