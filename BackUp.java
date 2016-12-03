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
    public void update(Game backedUpGame) throws RemoteException {
	this.backedUpGame = backedUpGame;
    }
    
}
