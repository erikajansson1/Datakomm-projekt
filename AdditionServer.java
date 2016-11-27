import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*; 

       
 
public class AdditionServer {
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
		    Addition Hello = new Addition();			   		   
		    System.out.println("hej");
		    Naming.rebind("rmi://localhost/ABC:1099", Hello);
		    System.out.println("hej");
		    
		    System.out.println("Addition Server is ready.");
		}catch (Exception e) {
		System.out.println("Addition Server failed: " + e);
	}
    }
}
