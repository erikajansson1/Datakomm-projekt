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
		int noPlayers = 0;
		noPlayers = networkBuild.askPlayerNo();
		System.out.println("Creating a game for "+noPlayers+".");
		String RMIPort = "1099";
		networkBuild.buildNetwork();
		networkBuild.welcomeMSG("server",0);
	
		if (argv.length > 0 && argv[0].equals("debug")) {
		    GameClient.main(networkBuild.debugLocal(noPlayers, false));
				    System.exit(0);
		}
		
		Registry registry = networkBuild.startRMIserver("standard",false);
		Game serverGame = new Game(noPlayers);
		Naming.rebind("//"+networkBuild.getInIp()+":"+RMIPort+"/theGame", serverGame);
		networkBuild.publishReady();

		String[] argvClient = new String[]{
		    networkBuild.getInIp(),
		    networkBuild.getExIp(),
		    RMIPort,
		    "0"
		};
		GameClient.main(argvClient);
		
	    }catch (Exception e) {
	    System.out.println("Game Server failed: " + e);

	}
	return;
    }

    public static void rebuild(Game serverGameOLD, String serverRetryCount) throws Exception {
	    String RMIPort = "1099";
	    Network networkBuild = new Network();
	    networkBuild.buildNetwork();
	    networkBuild.welcomeMSG("server",0);
	    Registry registry = networkBuild.startRMIserver("standard",false);

	    Game serverGameNEW = new Game(serverGameOLD.getAmountOfPlayers());
	    serverGameNEW.setGameValues(serverGameOLD.getRound(),
					serverGameOLD.getGameDeck(),
					serverGameOLD.getGamePlayers()
					);
	    Naming.rebind("//"+networkBuild.getInIp()+":"+RMIPort+"/theGame", serverGameNEW);
	    networkBuild.publishReady();
	    String[] argvClient = new String[]{
		networkBuild.getInIp(),
		networkBuild.getExIp(),
		RMIPort,
		serverRetryCount
	    };
	    GameClient.main(argvClient);
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


		//System.getProperties().put("socksProxyHost", "83.255.61.11");
		//System.getProperties().put("socksProxyPort", "1099");
		//Naming.rebind("//"+networkBuild.getExIp()+"/theGame:"+RMIPort, game);
		//UnicastRemoteObject.exportObject(serverGame, 1100);
		//System.out.println(game);
		//System.out.println(game);
