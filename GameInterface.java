import java.rmi.*;

public interface GameInterface extends Remote {
    public long timeToHit() throws RemoteException;

    public void displayBoard() throws RemoteException; //ej implementerad

}
