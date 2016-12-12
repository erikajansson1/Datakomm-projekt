import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.*;
import java.net.InetAddress;
import java.net.*;
import java.io.*;


/**
 * Class for building a network connection.
 */
class Network {
    private String exIP;
    private String inIP;
    private GameInterface serverGame;

    
    /**
     * Constructor for the class Network.
     */
    public Network () {}

    
    /**
     * Method to add the objects IP-adresses.
     * @param exIP is the users external IP.
     * @param inIP is the users internal IP.
     */
    public void buildNetwork() throws Exception {
	this.exIP = this.extIP();
	this.inIP = this.inIP();
    }

    /**
     * Method to add the objects IP-adresses and remote object.
     * @param exIP is the users external IP.
     * @param inIP is the users internal IP.
     * @param serverGame is the remote object.
     */
    public void buildNetwork(GameInterface serverGame) throws Exception  {
	this.serverGame = serverGame;
    }


    /**
     * get method for the internal IP.
     * @param returns the internal IP.
     */
    public String getInIp() {
	return this.inIP;
    }


    /**
     * get method for the external IP.
     * @param returns the external IP.
     */
    public String getExIp() {
	return this.exIP;
    }

    
    /**
     * Method that looksup the users external IP.
     * @return A string containig the external IP.
     */
    private String extIP() throws Exception {
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
     * @return A string containig the local IP.
     */
    private String inIP() {
	String ipLocal = "Failed to get a adress";
	try {
	    ipLocal=InetAddress.getLocalHost().getHostAddress();
	    // if(ipLocal.equals("127.0.1.1")) return this.extIP();
	}
	catch (Exception e) {
	    System.out.println("Error " + e.getMessage());
	    e.printStackTrace();
	}
	return ipLocal;
    }

    
    /**
     * Method to print out the welcome message for the user.
     * @param user should intended the type of message to print. 
     * Currently only "server"/"client" is accepted.
     * @param gameFromServer is 3 if client is also server.
     */
    public void welcomeMSG(String user, int gameFromServer) {
	if (user.equals("server")) {
	    System.out.print("\033[2J\033[;H");
	    System.out.println("Welcome to the great Opposom game server!");
	}
	else if (user.equals("client")) {
	    System.out.print("\033[2J\033[;H");
	    System.out.printf("Welcome to the great Opposom game!\n");
	    if(gameFromServer == 0) System.out.printf("Which host would you lixe to join? \n");
	}
	else {
	    System.out.println("Not server or client?");
	}
    }

    
    /**
     * Method to get how many players the server intends to host a game for.
     */
    // TODO : Needs to loop if given not int 
    public int askPlayerNo() {
	System.out.println("How many player slots would you like?");
	Scanner userInput = new Scanner(System.in);
	int noPlayers = userInput.nextInt();
	return noPlayers;
    }


    /**
     * Method to start a RMI server on port 1099.
     */
    public Registry startRMIserver(String type, boolean Junit) {
	Registry registry = null;
	try  //special exception handler for registry creation
	    {
		if (type.equals("standard")){
			//System.setProperty("java.rmi.server.hostname",this.exIP);
			System.out.println("RMI server started");
			//System.getProperties().put("http.proxyHost", "83.255.61.11");
			//System.getProperties().put("http.proxyPort", "1099");
			//System.getProperties().put("java.rmi.server.hostname", "//"+exIP);
			//System.setProperty("java.rmi.server.useLocalHostname",inIP);
			//System.setProperty("java.rmi.server.logCalls","true");
			System.setProperty("java.rmi.server.hostname",this.exIP);
			registry = LocateRegistry.createRegistry(1099);
			System.out.println("java RMI registry created.");
		    } else if (type.equals("debug")) {
		    if (!Junit) System.out.println("RMI server started");
		    registry = LocateRegistry.createRegistry(1099);
		}
	    }
	catch (RemoteException e)
	    {
		//do nothing, error means registry already exists
		if(!Junit) {
		    System.out.println("java RMI registry already exists.");
		    System.out.println("Or the RMI server is already running");
		}
	    }
	return registry;
    }

    
    /**
     * Print statement declaring that the servers is ready 
     * and can be reached on the adresses the servers currently has.
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
     * @return a string containg the users answer.
     */
    public String askServerInIp() {
	Scanner userInput = new Scanner(System.in);
	System.out.printf("Local IP: ");
	return userInput.nextLine();
    }


    /**
     * method to ask the user which external IP port the server has whom he wishes to join.
     * @return a string containg the users answer.
     */
    /*    public String askServerNAT() {
	  Scanner userInput = new Scanner(System.in);
	  System.out.printf("Is the server behind a NAT?");

	  }*/

    
    /**
     * method to ask the user which external IP port the server has whom he wishes to join.
     * @return a string containg the users answer.
     */
    public String askServerExIp() {
	Scanner userInput = new Scanner(System.in);
	System.out.printf("External IP: ");
	return userInput.nextLine();
    }

    
    /**
     * method to ask the user which port the server has whom he wishes to join.
     * @return a string containg the users answer.
     */
    public String askServerPort() {
	Scanner userInput = new Scanner(System.in);
	System.out.printf("Servers port: ");
	return userInput.nextLine();
    }


    /**
     * method to ask the user which port the servers object is reachable at.
     * @return a string containg the users answer.
     */
    public String askServerObjPort() {
	Scanner userInput = new Scanner(System.in);
	System.out.printf("Servers object port: ");
	return userInput.nextLine();
    }

   
    /**
     * Method that connects the client to the servers rmi registry 
     * and returns a reference to the shared game object.
     * @param inIp is the servers internal IP.
     * @param exIp is the servers external IP.
     * @param port is the servers port.
     * @param objectToGet is the name of the object to get the reference to.
     * @return a GameInterface object containing the reference to the external object.
     */    
    public GameInterface getServerObj(String serverInIp, String serverExIp, String serverRMIPort, String objectToGet)
    {
	GameInterface serverGame = null;
	try {
	       
	    /*
	      Registry registry = LocateRegistry.getRegistry( "//"+serverExIp,
	      Integer.parseInt(serverRMIPort));
		   
	      System.out.println("Registry found in "+
	      serverExIp +
	      ":" +
	      serverRMIPort +
	      "\n" +
	      registry);
	    
		   
	      serverGame = (GameInterface) Naming.lookup("//"+
	      serverExIp+
	      "/"+
	      objectToGet+
	      ":"+
	      serverRMIPort);
	    */
	    //System.getProperties().put("http.proxyHost", "83.255.61.11");
	    //System.getProperties().put("http.proxyPort", "1099");
	    System.setProperty("java.rmi.server.hostname",serverExIp);
	    serverGame = (GameInterface) Naming.lookup("//"+
						       serverExIp+
						       ":"+
						       serverRMIPort+
						       "/"+
						       objectToGet);
	    /*
	      System.out.println("\n\nfound on: //"+
	      serverExIp+
	      ":"+
	      serverRMIPort+
	      "/"+
	      objectToGet+
	      "\n"+
	      serverGame);
	    */
	    
	}catch (Exception e) {
	    System.out.println(" exception: " + e);
	    e.printStackTrace();
	}
	return serverGame;
    }


    /**
     * A method that asks the client what alias he or she wants to use.
     * @return a string containing the users wished alias.
     */
    public String askAlias() {
	Scanner userInput = new Scanner(System.in);
	System.out.printf("What alias would you like: ");
	return userInput.nextLine();
    }

    
    /**
     * Method for the client to join a game he or she is connected to.
     * Also checks if the game is full or not before trying to join.
     * @param serverGame is the game to join.
     * @return int symbolising the players assigned player number.
     */
    public int joinGame() throws RemoteException {
	int playerNo = -1;
	System.out.println("You are now connected!\nChecking if game is full!");
	if(!this.serverGame.askIsGameFull()) {
	    playerNo = serverGame.addPlayer(inIP,exIP,this.askAlias());
	}
	if(playerNo == -1)
	    {
		System.out.println("Sorry this server is full. \n Please try another one.");
		System.exit(0);
	    }
	int playerNoShow = playerNo+1;
	System.out.println("You have now joined the game with alias: "+
			   serverGame.getPlayerAlias(playerNo)+"\n"+
			   "You are currently playerNo: "+
			   playerNoShow);
	return playerNo;
    }


    /**
     *  A method that holds the client in a wait state until the game is ready to start.
     * @throws RemoteException
     */
    public void waitingUntilGameCanStart() throws RemoteException {
	try {
	    while (!serverGame.waitingForPlayers()) {
		for (int i = 0; i < 3; i++) {
		    String count = "";
		    switch(i) {
		    case 1:count = "."; break;
		    case 2:count = ".."; break;
		    default:;
		    };
		    System.out.printf("\033[2J\033[;H");
		    System.out.println(serverGame.displayBoard());
		    System.out.printf("Waiting for players"+count);
		    Thread.sleep(500);				  
		}
		
	    }
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
    }

    
    /**
     * So we can try it
     * @param serverGame
     * @param registry
     * @param noPlayers
     * @throws RemoteException
     */
    public String[] debugLocal(int noPlayers, boolean Junit) throws RemoteException{
	String[] argvClient = null;
	try {
	    Registry registry = this.startRMIserver("debug",true);
	    Game serverGame = new Game(noPlayers);
	    Naming.rebind("//"+this.getInIp()+":1099/theGame", serverGame);
	   if (!Junit) {
	       this.publishReady();
	       System.out.println("ignore external IP!");
	   }
	       argvClient = new String[] {
		   this.getInIp(),
		   this.getInIp(),
		   "1099"
	    };
	    
	}catch (Exception e){
	    System.out.printf("You got fucked!%n");
	    e.printStackTrace();
	    System.exit(0);
	}
	return argvClient;
    }
}



