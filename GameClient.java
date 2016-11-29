import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.*;
import java.net.InetAddress;
import java.net.*;
import java.io.*;

public class GameClient {

    public static void main (String[] args) {
	GameInterface game;
	Network networkBuild = new Network();
	networkBuild.welcomeMSG("client");
	String inIp = networkBuild.getServerInIp();
	String exIp = networkBuild.getServerExIp();
	String port = networkBuild.getServerPort();

	game = networkBuild.clientConnect(inIp,exIp,port);

	try {
	    int myGameNO = game.getPlayerNO();
	    System.out.println("Result is :"+ myGameNO);	    
	}
	catch (Exception e) {
	    System.out.println("Error " + e.getMessage());
	    e.printStackTrace();
	}
    }
}
