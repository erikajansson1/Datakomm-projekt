import java.rmi.*;

public interface GameInterface extends Remote {
    public int gamePasser(int playerNO) throws RemoteException;

    public int getPlayerNO() throws RemoteException;
}