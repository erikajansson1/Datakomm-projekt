import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.*;
import java.net.InetAddress;
import java.net.*;
import java.io.*;


public class GameServer {
    public static void main (String[] argv) {
	System.out.print("\033[2J\033[;H");
	System.out.println("Welcome to the great Opposom game server!" +
			   "\n" + 
			   "How many player slots would you like?");
	Scanner userInput = new Scanner(System.in);
	int no_players = userInput.nextInt();
	
	System.out.println("Creating space for "+ no_players + ""+
			   "\n"+
			   "RMI server started");

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
		Game game = new Game(no_players,1,50);
		Naming.rebind("rmi://localhost/theGame:1099", game);
		
		System.out.println("Game Server is ready.");
		InetAddress IP=InetAddress.getLocalHost();

		URL whatismyip = new URL("http://checkip.amazonaws.com");
		BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
		String ip = in.readLine(); //you get the IP as a String
		System.out.println(ip);
		System.out.println("The server is now reachable on \nLocal IP: "+
				   IP.getHostAddress() +
				   "\nExternal IP: "+
				   ip +
				   "\nBoth on port 1099 \n\n\n");
		
		GameClient.main(argv);
	    }catch (Exception e) {
	    System.out.println("Game Server failed: " + e);

	}
    }
}
