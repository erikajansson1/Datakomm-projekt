import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

public interface GameInterface extends Remote {

    ////////// Game object related //////////////////////////////////////

    /**
     * a get method for the attribut gameDeck
     * @return returns the gameDeck attribut
     */
    public Deck getGameDeck()throws RemoteException;


    /**
     * a get method for the attribut starterDeck
     * @return returns the starterDeck attribut
     */
    public Deck getStarterDeck() throws RemoteException;

    
    /**
     * a get method for the attribut starterDeck
     * @return returns the starterDeck attribut
     */
    public ArrayList<Player> getGamePlayers() throws RemoteException;

    
    /**
     * a update state method for the object game.
     * @rparam round is the new round value
     * @rparam gameDeck is the new gameDeck value
     * @rparam starterDeck is the new starterDeck value
     * @rparam gamePlayers is the new gamePlayers value
     * @rparam playerAliases is the new playerAliases value
     */
    public void setGameValues (int round,Deck gameDeck,Deck starterDeck,ArrayList<Player> gamePlayers) throws RemoteException;


    /**
     * method that adds a player requesting a slot to a existing game. 
     * Player is placed at the first available slot in the Arraylist.
     * @param inIp is the players internal IP.
     * @param exIp is the players external IP.
     * @param alias is the players alias.
     * @return an int indication the players assigned number.
     */
    public int addPlayer(String inIp, String exIp, String alias) throws RemoteException;





    /////////// Game status related //////////////////////////////////////
    
    /** Checks whether it's time to hit,
     *  that is, check whether any of the previous 4 cards
     *  match eachother
     */
    public boolean timeToHit() throws RemoteException;


    /**
     * a get method for the attribut round
     * @return returns the round attribut
     */
    public int getRound() throws RemoteException;

    /** Checks whose turn it is, and if it's time for a new turn. If so, it updates accordingly. 
     * @param currRound Taken to ensure that only one such update is done every round.
     * @return The round value 
     */
    public int updateRound(int currRound) throws RemoteException;

    
    /**
     * Checks whose turn it is and returns the index of the player.
     * @return an int which is the players index.
     */
    public int whoseTurn() throws RemoteException;

    /**  
     * Checks every players answer time and returns a boolean saying if all are ready.
     * @return returns true if all players have answered
     */
    public boolean everyoneHasMadeMove() throws RemoteException;


    /////////// Action //////////////////////////////////////

    /** Player tries to lay a card
     * @param Player Number ID of the player
     * @param playerRound The round it is according to the player when it tries to lay its card
     * @return Returns true if player could lay a card, otherwise false
     */
    public boolean tryToLayCard(int playerNo, int playerRound) throws RemoteException;

    public void askDealer() throws Exception;

    /** 
     * Update the action for a player
     * @param playerNo The player's number ID
     * @param action to set.
     */
    public void updatePlayerAction(int playerNo, String action) throws RemoteException;

    /////////// Player related //////////////////////////////////////

    /** Set the ready value for a player
     */
    public void setReadyValue(int playerNo, boolean readyValue) throws RemoteException; 

    /** Update the hit time for a player     
     */
    public void updatePlayerTime(int playerNo, long hitTime) throws RemoteException; 

    /** 
     * Finds the Player object in an array whose name matches the given parameter
     * @param alias 
     * @return The found Player object, or an Player object with specific invalid values     
     */
    public Player getPlayer(String alias) throws RemoteException;

    /** 
     * Finds the Player object in an array whose name matches the given parameter
     * @param playerNo number id of the player
     * @return The found Player object, or an Player object with specific invalid values     
     */
    public Player getPlayer(int playerNo) throws RemoteException;
 
    /**
     * a method to retrive a players alias in the game.
     * Will fail if the playerNo is not in the bounds of the game.
     * @param playerNo is the number of the player whos alias is to be fetched.
     * @return the players alias.
     */
    public String getPlayerAlias(int playerNo) throws RemoteException;


    public int myRank(int playerNo) throws RemoteException;

    public void removeQuitters() throws RemoteException;

    public int getPlayerNo(String alias) throws RemoteException;

    /////////// Other //////////////////////////////////////

    /** Initiate a game
     * @param amountOfPlayer The amount of players in the game
     */
    public void startGame(int playerNo) throws RemoteException;

    
    /** Prints out a view of the board:
     *  whose turn it is, and the latest card
     */
    public String displayBoard() throws RemoteException;
   
    /**
     * checks if the game is full.
     * @return a bollean indicating if full or not. True if full.
     */
    public boolean askIsGameFull() throws RemoteException;

    
    /**
     * Checks every players ready status and returns a boolean saying if all are ready.
     * @return returns true if all players are ready.
     */
    public boolean waitingForPlayers() throws RemoteException;

    
    /** 
     * Check who lost the whole game
     * @return player number id of the loser
     */
    public int checkLoser() throws RemoteException;

    
    /** 
     * Get middle deck size
     */
    public int getDeckSize()  throws RemoteException;


    /**
     * Ask method that checks if the game has ended.
     * @return boolean indicating if the game has ended.
     */
    public boolean askGameEnded() throws RemoteException;

	
    /**
     * display method for when the game has ended and the ranking is set.
     * @return a string describing the result.
     */
    public String displayGameResult() throws RemoteException;


    public String getLastEvent() throws RemoteException;

}

