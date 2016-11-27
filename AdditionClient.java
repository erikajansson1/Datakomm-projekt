import java.rmi.*;
 
public class AdditionClient {
    public static void main (String[] args) {
	AdditionInterface hello;
	try {
	    System.setSecurityManager(new RMISecurityManager());
	    hello = (AdditionInterface)Naming.lookup("//192.168.0.101:1099/ABC");
	    int result=hello.add(9,10);
	    System.out.println("Result is :"+result);
 
	}catch (Exception e) {
	    System.out.println("HelloClient exception: " + e);
	}
    }
}
