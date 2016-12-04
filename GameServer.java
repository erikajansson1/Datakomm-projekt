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
		String port = "1099";
		networkBuild.buildNetwork();
		networkBuild.welcomeMSG("server",0);
		Registry registry = networkBuild.startRMIserver();
			
		registry.rebind(networkBuild.getInIp()+"/theGame:"+port, game);
		networkBuild.publishReady();
		String[] argvClient = new String[]{
		    networkBuild.getInIp(),
		    networkBuild.getExIp(),
		    port};
		
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
