import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*; 

       
 
public class GameServer {
    public static void main (String[] argv) {
            System.out.println("RMI server started");

            // Create and install a security manager
	    /* if (System.getSecurityManager() == null) 
		{
		    System.setSecurityManager(new SecurityManager());
		    System.out.println("Security manager installed.");
		}
            else
                System.out.println("Security manager already exists.");
	    */
            try  //special exception handler for registry creation
		{
		    LocateRegistry.createRegistry(1099); 
		    System.out.println("java RMI registry created.");
		}
            catch (RemoteException e)
		{
		    //do nothing, error means registry already exists
		    System.out.println("java RMI registry already exists.");
		}

	    try
		{
		    Game newGame = new Game();			   		   
		    Naming.rebind("rmi://localhost/ABC:1099", newGame);
		    System.out.println("Game Server is ready.");
		}catch (Exception e) {
		System.out.println("Game Server failed: " + e);
	}
    }
}
