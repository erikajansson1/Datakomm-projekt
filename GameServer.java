import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.*;
import java.net.InetAddress;
import java.net.*;
import java.io.*;


public class GameServer {
    public static Game serverGame;
    public static Registry registry;
    
    public static void main (String[] argv) {
	try
	    {
		Network networkBuild = new Network();
		int noPlayers = networkBuild.askPlayerNo();
		System.out.println("Creating a game for "+noPlayers+".");

		
		
		String RMIPort = "1099";
		networkBuild.buildNetwork();
		networkBuild.welcomeMSG("server",0);

		//System.getProperties().put("http.proxyHost", "83.255.61.11");
		//System.getProperties().put("http.proxyPort", "1099");
		//System.getProperties().put("socksProxyHost", "83.255.61.11");
		//System.getProperties().put("socksProxyPort", "1099");
		registry = networkBuild.startRMIserver();
		serverGame = new Game(noPlayers);
		//Naming.rebind("//"+networkBuild.getExIp()+"/theGame:"+RMIPort, game);

		//		UnicastRemoteObject.exportObject(serverGame, 1100);
		//System.out.println(game);

	
		
		
		Naming.rebind("//"+networkBuild.getInIp()+":"+RMIPort+"/theGame", serverGame);
		//System.out.println(game);
		networkBuild.publishReady();

		String[] argvClient = new String[]{
		    networkBuild.getInIp(),
		    networkBuild.getExIp(),
		    RMIPort
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
