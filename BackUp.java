import java.rmi.*;
import java.rmi.server.*;
import java.util.*;
 

class BackUp extends UnicastRemoteObject implements BackUpInterface {
    private static final long serialVersionUID = 1L;
    private Game backedUpGame;

    /**
     * Contstructor for the backup class
     */
    public BackUp (Game backedUpGame) throws RemoteException{
	this.backedUpGame = backedUpGame;
    }

    /** 
     * Method to update the stored backup of the game  
     */
    public void getBackUp(Game externalGame) throws RemoteException {
	externalGame.setGameValues(
				   backedUpGame.getRound(),
				   backedUpGame.getGameDeck(),
				   backedUpGame.getStarterDeck(),
				   backedUpGame.getGamePlayers(),
				   backedUpGame.getPlayerAliases()
				   );
    }
    
}
