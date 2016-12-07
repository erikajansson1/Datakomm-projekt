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
		int noPlayers = networkBuild.askPlayerNo();
		System.out.println("Creating a game for "+noPlayers+".");

		Game game = new Game(noPlayers);
		String RMIPort = "1099";
		String objPort = "1100";
		networkBuild.buildNetwork();
		networkBuild.welcomeMSG("server",0);

		Registry registry = networkBuild.startRMIserver();			
		// Naming.rebind(networkBuild.getInIp()+"/theGame:"+port, game);

		//		UnicastRemoteObject.exportObject(game, 1100);
		System.out.println(game);
		Naming.rebind("//"+networkBuild.getInIp()+":"+RMIPort+"/theGame", game);
		System.out.println(game);
		networkBuild.publishReady();
		String[] argvClient = new String[]{
		    networkBuild.getInIp(),
		    networkBuild.getExIp(),
		    RMIPort,
		    objPort
		};
		
		GameClient.main(argvClient);
	    }catch (Exception e) {
	    System.out.println("Game Server failed: " + e);

	}
	return;
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
