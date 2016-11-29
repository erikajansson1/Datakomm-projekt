import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.*;
import java.net.InetAddress;
import java.net.*;
import java.io.*;


public class GameServer {
    public static void main (String[] argv) {

	try
	    {
		Network networkBuild = new Network();
		
		String ipExternal = networkBuild.extIP();
		String ipLocal = networkBuild.inIP();
		
		networkBuild.addAdresses(ipExternal,ipLocal);
		int noPlayers = networkBuild.askPlayerNo();
		
		networkBuild.startRMIserver();
		networkBuild.publishReady();
	
		Game game = new Game(noPlayers,1,50);
		Naming.rebind("rmi://"+ipLocal+"/theGame:1099", game);
		
		GameClient.main(argv);
	    }catch (Exception e) {
	    System.out.println("Game Server failed: " + e);

	}
    }
}





// Create and install a security manager
/* if (System.getSecurityManager() == null) 
   {
   System.setSecurityManager(new SecurityManager());
   System.out.println("Security manager installed.");
   }
   else
   System.out.println("Security manager already exists.");
*/
