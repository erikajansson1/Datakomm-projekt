import java.rmi.*;

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
}
