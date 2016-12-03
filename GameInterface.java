import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

public interface GameInterface extends Remote {

    /** Checks whether it's time to hit,
     *  that is, check whether any of the previous 4 cards
     *  match eachother
     */
    public long timeToHit() throws RemoteException;

    
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
     * a get method for the attribut playerAliases
     * @return returns the playerAliases attribut
     */
    public ArrayList<String> getPlayerAliases() throws RemoteException;

    
   /**
     * a update state method for the object game.
     * @rparam round is the new round value
     * @rparam gameDeck is the new gameDeck value
     * @rparam starterDeck is the new starterDeck value
     * @rparam gamePlayers is the new gamePlayers value
     * @rparam playerAliases is the new playerAliases value
     */
    public void setGameValues (int round,Deck gameDeck,Deck starterDeck,ArrayList<Player> gamePlayers,ArrayList<String> playerAliases) throws RemoteException;

    public boolean addPlayer(String inIp, String exIp, String alias) throws RemoteException;
}
