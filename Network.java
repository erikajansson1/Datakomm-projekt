import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.*;
import java.net.InetAddress;
import java.net.*;
import java.io.*;

class Network {

    private String exIP;
    private String inIP;
    
    public Network () {}

    public void addAdresses(String exIP, String inIP) {
	this.exIP = exIP;
	this.inIP = inIP;
    }

      public String extIP() throws Exception {
	String ipExternal = "Adress lookup failed!";
	URL whatismyip = new URL("http://checkip.amazonaws.com");
	BufferedReader in = null;
	try {
	    in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
	    ipExternal = in.readLine(); //you get the IP as a String    
	}
	catch (RemoteException e) {
	    System.out.println("Error " + e.getMessage());
	    e.printStackTrace();
	}
	return ipExternal;
    }


    
    public String inIP() {
	String ipLocal = "Failed to get a adress";
	try {
	    ipLocal=InetAddress.getLocalHost().getHostAddress();
	}
	catch (Exception e) {
	    System.out.println("Error " + e.getMessage());
	    e.printStackTrace();
	}
	return ipLocal;
    }


    
    public int askPlayerNo() {
	System.out.print("\033[2J\033[;H");
	System.out.println("Welcome to the great Opposom game server!" +
			   "\n" + 
			   "How many player slots would you like?");

	Scanner userInput = new Scanner(System.in);
	int noPlayers = userInput.nextInt();
	
	System.out.println("Creating space for "+noPlayers+"\n");
	return noPlayers;
    }


    
    public Registry startRMIserver() {
	Registry registry = null;
	try  //special exception handler for registry creation
	    {
		System.setProperty("java.rmi.server.hostname","83.255.61.11");
		System.out.println("RMI server started");
		//System.getProperties().put("java.rmi.server.hostname", "rmi://"+exIP);
		registry = LocateRegistry.createRegistry(1099); 
		System.out.println("java RMI registry created.");
	    }
	catch (RemoteException e)
	    {
		//do nothing, error means registry already exists
		System.out.println("java RMI registry already exists.");
		System.out.println("Or the RMI server is already running");
	    }
	return registry;
    }


    
    public void publishReady() {
	System.out.println("Game Server is ready.");
	System.out.println("The server is now reachable on \nLocal IP: "+
			   inIP +
			   "\nExternal IP: "+
			   exIP +
			   "\nBoth on port 1099 \n\n\n");
    }
}
