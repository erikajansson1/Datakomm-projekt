import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.*;
import java.net.InetAddress;
import java.net.*;
import java.io.*;


/**
 * Class for building a network connection
 */
class Network {
    private String exIP;
    private String inIP;
    
    /**
     * Constructor for the class Network.
     */
    public Network () {}

    /**
     * Method to add the objects IP-adresses
     * @param exIP is the users external IP
     * @param inIP is the users internal IP
     */
    public void addAdresses(String exIP, String inIP) {
	this.exIP = exIP;
	this.inIP = inIP;
    }

    /**
     * Method that looksup the users external IP.
     */
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


    /**
     * Method that looksup the users internal IP.
     */
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

    /**
     * Method to print out the welcome message for the user.
     * @param user should intended the type of message to print. Currently only "server"/"client" is accepted.
     */
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

    /**
     * Method to get how many players the server intends to host a game for.
     */
    public int askPlayerNo() {
	System.out.println("How many player slots would you like?");
	Scanner userInput = new Scanner(System.in);
	int noPlayers = userInput.nextInt();
	System.out.println("Creating space for "+noPlayers);
	return noPlayers;
    }


    /**
     * Method to start a RMI server on port 1099
     */
    public Registry startRMIserver() {
	Registry registry = null;
	try  //special exception handler for registry creation
	    {
		System.setProperty("java.rmi.server.hostname",exIP);
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

    
    /**
     * Print statement declaring that the servers is ready and can be reached on the adresses the servers currently has.
     *
     */
    public void publishReady() {
	System.out.println("Game Server is ready.");
	System.out.println("The server is now reachable on \nLocal IP: "+
			   inIP +
			   "\nExternal IP: "+
			   exIP +
			   "\nBoth on port 1099 \n\n\n");
    }

    
    /**
     * method to ask the user which internal IP port the server has whom he wishes to join.
     */
    public String askServerInIp() {
	Scanner userInput = new Scanner(System.in);
	System.out.printf("Local IP: ");
	return userInput.nextLine();
    }

    
    /**
     * method to ask the user which external IP port the server has whom he wishes to join.
     */
    public String askServerExIp() {
	Scanner userInput = new Scanner(System.in);
	System.out.printf("External IP: ");
	return userInput.nextLine();
    }

    
    /**
     * method to ask the user which port the server has whom he wishes to join.
     */
    public String askServerPort() {
	Scanner userInput = new Scanner(System.in);
	System.out.printf("Port: ");
	return userInput.nextLine();
    }


   
    /**
     * Method that connects the client to the servers rmi registry and returns a reference to the shared game object.
     *@param inIp is the servers internal IP
     *@param exIp is the servers external IP
     *@param port is the servers port
     *@param objectToGet is the name of the object to get the reference to
     */    
    public void getServerObj(GameInterface game, String inIp, String exIp, String port ,String objectToGet)
    {
       	try {
	    Registry registry = LocateRegistry.getRegistry( exIp, Integer.parseInt(port));
	    System.out.println("Registry found in "  +exIp+ 
                               " :" + port + "\n" + registry);
	    game = (GameInterface) registry.lookup(inIp+"/"+objectToGet+":"+port);		
	}catch (Exception e) {
	    System.out.println(" exception: " + e);
	    e.printStackTrace();
	}
    }

    /**
     * Method that connects the client to the servers rmi registry and returns a reference to the shared backup object.
     *@param inIp is the servers internal IP
     *@param exIp is the servers external IP
     *@param port is the servers port
     *@param objectToGet is the name of the object to get the reference to
     */   
    public void getServerObj(BackUpInterface backUp,String inIp, String exIp, String port, String objectToGet)
    {
	try {
	    Registry registry = LocateRegistry.getRegistry( exIp, Integer.parseInt(port));
	    System.out.println("Registry found in "  +exIp+ 
                               " :" + port + "\n" + registry);
	    backUp = (BackUpInterface) registry.lookup(inIp+"/"+objectToGet+":"+port);		
	}catch (Exception e) {
	    System.out.println(" exception: " + e);
	    e.printStackTrace();
	}
    }


}
