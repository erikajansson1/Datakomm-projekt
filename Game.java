import java.rmi.*;
import java.rmi.server.*;
 
public class Game extends UnicastRemoteObject implements GameInterface {

    private static final long serialVersionUID = 1L;
 
    public Game () throws RemoteException {   }

    public int add(int a, int b) throws RemoteException {
	int result=a+b;
	return result;
    }
}
