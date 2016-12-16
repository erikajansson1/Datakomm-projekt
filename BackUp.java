import java.rmi.*;
import java.rmi.server.*;
import java.util.*;
 

class BackUp{
    private Game backedUpGame;
    private String BackUpInIp;
    private String BackUpExIp;
    private int numberOfPlayers;


        /**
     * Contstructor for the backup class
     */
    public BackUp (){
    }
    
    /**
     * Contstructor for the backup class
     */
    public BackUp (GameInterface serverGame) throws RemoteException{
	this.backedUpGame = new Game (serverGame.getRound(),
				      serverGame.getGameDeck(),
				      serverGame.getGamePlayers()
				      );
	this.numberOfPlayers = serverGame.getAmountOfPlayers();
	if(numberOfPlayers>1) {
	    this.BackUpInIp = serverGame.getPlayerInIp(1);
	    this.BackUpExIp = serverGame.getPlayerExIp(1);
	}
    }
    
    /** 
     * Method to update the stored backup of the game  
     */
    public void update(GameInterface serverGame) throws RemoteException {
	    this.backedUpGame.setGameValues(
				   serverGame.getRound(),
				   serverGame.getGameDeck(),
				   serverGame.getGamePlayers()
					    );
	    this.numberOfPlayers = serverGame.getAmountOfPlayers();
	    if(numberOfPlayers>1) {
	    this.BackUpInIp = serverGame.getPlayerInIp(1);
	    this.BackUpExIp = serverGame.getPlayerExIp(1);
	    }
    }

    public String getBackupInIp() {
	return BackUpInIp;
    }

    public String getBackupExIp() {
	return BackUpExIp;
    }

    public int getNumberOfPlayers() {
	return numberOfPlayers;
    }
    
    public Game getBackUp() {
	return this.backedUpGame;
    }

    public void removePlayer(int no) throws RemoteException {
	backedUpGame.removePlayer(backedUpGame.getPlayerAlias(no));
    }
    
}
