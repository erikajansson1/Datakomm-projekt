import java.rmi.*;
import java.rmi.server.*;
 
public class Addition extends UnicastRemoteObject
    implements AdditionInterface {

    private static final long serialVersionUID = 1L;
 
    public Addition () throws RemoteException {   }
 
    public int add(int a, int b) throws RemoteException {
	int result=a+b;
	return result;
    }
}
