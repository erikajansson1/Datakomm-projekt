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
		System.out.println("Creating Game.");

		Game game = new Game(noPlayers);
		System.out.println(game);

		BackUp backup = new BackUp(game);
		String ipExternal = networkBuild.extIP();
		String ipLocal = networkBuild.inIP();
		networkBuild.addAdresses(ipExternal,ipLocal);
		networkBuild.welcomeMSG("server");
		Registry registry = networkBuild.startRMIserver();
			
		registry.rebind(ipLocal+"/theGame:1099", game);
		registry.rebind(ipLocal+"/theBackUp:1099", backup);
		networkBuild.publishReady();
		
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
