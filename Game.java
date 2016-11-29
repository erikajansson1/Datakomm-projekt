import java.rmi.*;
import java.rmi.server.*;
 
public class Game extends UnicastRemoteObject implements GameInterface {
    private static final long serialVersionUID = 1L;
    private int playerNO;
    private int numberOfPlayers;
    private int controlValue;
 
    public Game (int numberOfPlayers, int playerNO, int controlValue) throws RemoteException {
	super(1099);
	this.playerNO = playerNO;
	this.numberOfPlayers = numberOfPlayers;
	this.controlValue = controlValue;
    }

    public int gamePasser(int playerNO) throws RemoteException {
	System.out.println(playerNO);
	return 1000 ;
    }

    public int getPlayerNO() throws RemoteException {
	return this.playerNO;
    }
}
