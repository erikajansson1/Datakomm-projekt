import java.rmi.*;
 
public interface GameInterface extends Remote {
    public int add(int a,int b) throws RemoteException;
}
