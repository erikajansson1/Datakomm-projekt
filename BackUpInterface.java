import java.rmi.*;

public interface BackUpInterface extends Remote {

    /** 
     * Method to update the stored backup of the game  
     */
    public void getBackUp(Game externalGame) throws RemoteException;
}
