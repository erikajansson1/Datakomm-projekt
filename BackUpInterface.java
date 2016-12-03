import java.rmi.*;

public interface BackUpInterface extends Remote {

    /** 
     * Method to update the stored backup of the game  
     */
    public void update(Game Backup) throws RemoteException;
}
