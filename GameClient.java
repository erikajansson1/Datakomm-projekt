import java.rmi.*;
 
public class GameClient {
    public static void main (String[] args) {
	GameInterface newGame;
	try {
	    // System.setSecurityManager(new SecurityManager());
	    newGame = (GameInterface)Naming.lookup("rmi://localhost/ABC:1099");
	    int result= newGame.add(9,10);
	    System.out.println("Result is :"+result);
 
	}catch (Exception e) {
	    System.out.println("HelloClient exception: " + e);
	}
    }
}
