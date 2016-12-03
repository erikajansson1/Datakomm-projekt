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
		String ipExternal = networkBuild.extIP();
		String ipLocal = networkBuild.inIP();
		networkBuild.addAdresses(ipExternal,ipLocal);
		networkBuild.welcomeMSG("server");
		Registry registry = networkBuild.startRMIserver();
			
		registry.rebind(ipLocal+"/theGame:1099", game);
		networkBuild.publishReady();
		String[] argvClient = new String[]{ipLocal,ipExternal,"1099"};
		
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
