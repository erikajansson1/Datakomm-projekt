import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

public interface GameInterface extends Remote {

    /** Checks whether it's time to hit,
     *  that is, check whether any of the previous 4 cards
     *  match eachother
     */
    public boolean timeToHit() throws RemoteException;

    
    /** Prints out a view of the board:
     *  whose turn it is, and the latest card
     */
    public String displayBoard() throws RemoteException; //ej implementerad

    /** Set the ready value for a player with alias "alias". 
     */
    public void setReadyValue(String alias, boolean readyValue) throws RemoteException; 

    /** Update the hit time for a player with alias "alias"     
     */
    public void updatePlayerTime(String alias, int hitTime) throws RemoteException; //ej implementerad
    
    /** Checks whose turn it is, and if it's time for a new turn. If so, it updates accordingly. 
     * @param currRound Taken to ensure that only one such update is done every round.
     * @return The round value 
     */
    public int whoseRound(int currRound) throws RemoteException;

    /**
     * a get method for the attribut round
     * @return returns the round attribut
     */
    public int getRound() throws RemoteException;


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

    
    /**
     * a method to retrive a players alias in the game.
     * Will fail if the playerNo is not in the bounds of the game.
     * @param playerNo is the number of the player whos alias is to be fetched.
     * @return the players alias.
     */
    public String getPlayerAlias(int playerNo) throws RemoteException;

    
    /**
     * checks if the game is full.
     * @return a bollean indicating if full or not. True if full.
     */
    public boolean askIsGameFull() throws RemoteException;
}
