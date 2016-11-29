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


    public void welcomeMSG(String user) {
	if (user.equals("server")) {
	    System.out.print("\033[2J\033[;H");
	    System.out.println("Welcome to the great Opposom game server!");
	}
	else if (user.equals("client")) {
	    System.out.print("\033[2J\033[;H");
	    System.out.printf("Welcome to the great Opposom game!"+
			      "\n" + 
			      "Which host would you lixe to join? \n");
	}
	else {
	    System.out.println("Not server or client?");
	}
    }
    
    public int askPlayerNo() {
	System.out.println("How many player slots would you like?");
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

    public String getServerInIp() {
	Scanner userInput = new Scanner(System.in);
	System.out.printf("Local IP: ");
	return userInput.nextLine();
    }

    public String getServerExIp() {
	Scanner userInput = new Scanner(System.in);
	System.out.printf("External IP: ");
	return userInput.nextLine();
    }
    
    public String getServerPort() {
	Scanner userInput = new Scanner(System.in);
	System.out.printf("Port: ");
	return userInput.nextLine();
    }

    public GameInterface clientConnect(String inIp,String exIp,String port) {
	GameInterface game = null;
	try {
	    // System.setSecurityManager(new SecurityManager());
	    Registry registry = LocateRegistry.getRegistry( exIp, Integer.parseInt(port));
	    System.out.println("Registry found in "  +exIp+ 
                               " :" + port + "\n" + registry);
	    game = (GameInterface) registry.lookup(inIp+"/theGame:"+port);
 	}catch (Exception e) {
	    System.out.println("HelloClient exception: " + e);
	    e.printStackTrace();
	    
	}
	return game;
    }




}
