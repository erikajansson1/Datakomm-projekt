import java.rmi.*;
import java.rmi.server.*;
import java.util.*;
 

class BackUp{
    private Game backedUpGame;

    /**
     * Contstructor for the backup class
     */
    public BackUp (GameInterface serverGame) throws RemoteException{
	this.backedUpGame = new Game (serverGame.getRound(),
				      serverGame.getGameDeck(),
				      serverGame.getStarterDeck(),
				      serverGame.getGamePlayers()
				      );
    }
    
    /** 
     * Method to update the stored backup of the game  
     */
    public void update(GameInterface serverGame) throws RemoteException {
	    this.backedUpGame.setGameValues(
				   serverGame.getRound(),
				   serverGame.getGameDeck(),
				   serverGame.getStarterDeck(),
				   serverGame.getGamePlayers()
					    );
    }

    public Game getBackUp() {
	return this.backedUpGame;
    }
}
