import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.ArrayList;


       
 
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
		    Game game1 = new Game(2,1,50);
		    Game game2 = new Game(2,2,500);
		    ArrayList<Game> gameArray = new ArrayList<Game>();
		    /*
		      gameArray.add(game1);
		      gameArray.add(game2);
		      Naming.rebind("rmi://localhost/game2:1099", gameArray);
		    */
		    
		    Naming.rebind("rmi://localhost/game1:1099", game1);
		    Naming.rebind("rmi://localhost/game2:1099", game2);
		    System.out.println("Game Server is ready.");
		    GameClient.main(argv);
		}catch (Exception e) {
		System.out.println("Game Server failed: " + e);

	}
    }
}
