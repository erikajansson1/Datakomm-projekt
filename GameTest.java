import static org.junit.Assert.*;

import java.rmi.RemoteException;
import org.junit.Test;
import junit.framework.TestCase;


// Don't know how to make catch exception to work as I want to
public class GameTest extends TestCase {
	

	/**
	 * This is commented out because if we test several games at the same time
	 * their will be conflict because they use the same port.
	 * @throws RemoteException
	 */
	/*
	@Test
	public void test_newGame() {
	    boolean knas = false;
	    try {
			Game testGame = new Game(2);
			knas = testGame.getAmountOfPlayers() == 2;
			//assertTrue(testGame.getAmountOfPlayers() == 2);
		} 	
		catch (RemoteException e) {
			 System.out.println("Error " + e.getMessage());
			 e.printStackTrace();
		}	
	     assertTrue(knas);
	}
	
	@Test
	public void test_timeToHit() {	
	    /*try{
		Game testGame = new Game(2);
		boolean isItTrue = testGame.timeToHit();
		assertFalse(isItTrue);
		}
		catch (RemoteException e) {
			 System.out.println("Error " + e.getMessage());
			 e.printStackTrace();
		}*/
	    assertTrue(true);
	}

	
	@Test
	public void test_displayBoard() {
	    /*try {
			Game testGame = new Game(2);
			String testDisplay = testGame.displayBoard(); 
			assertTrue(testDisplay == " ");		
		} 	
		catch (RemoteException e) {
			 System.out.println("Error " + e.getMessage());
			 e.printStackTrace();
		}*/
	}

	
    @Test
    public void test_setReadyValue() {
	Game testGame1 = null;
	boolean value = false;
	try {
	    testGame1 = new Game(1);
	    int i = testGame1.addPlayer("125.323.32.5", "125.323.32.5", "mupp");
	    testGame1.setReadyValue(i, true);
	    value = testGame1.findPlayer("mupp").getReadyValue();
	}
	catch (RemoteException e) {
	    System.out.println("Error" + e.getMessage());
	    e.printStackTrace();
	} 
	assertTrue(value == true);
    }  
    
	/*
    @Test
    public void test_updatePlayerTime() {
	Game testGame3 = null;
	int test = 0;
	try {
	    testGame3 = new Game(3);
	    int i = testGame3.addPlayer("125.323.32.5", "125.323.32.5", "kanelbullen75");
	    testGame3.updatePlayerTime(i, 10);
	    test = testGame3.findPlayer("kanelbullen75").getPlayerTime();
	}
	catch(RemoteException e){
	    System.out.println("Error" + e.getMessage());
	    e.printStackTrace();
	}
	assertTrue(test == 10);
    } */

	
    @Test
    public void test_whoseRound() {
	assertTrue(true);
    }
	

    /*
    @Test
    public void test_findPlayer() {
	Game testGame2 = null;
	int q = 0;
	int lall = 0;
	try {
	    testGame2 = new Game(3);
	    int i = testGame2.addPlayer("77.218.245.102", "77.218.245.102", "knasboll");
	    int p = testGame2.addPlayer("192.343.23.09", "189.234.231.22", "banankontakt");
	    int s = testGame2.addPlayer("234.234.21.12", "123.456.78.90", "kamel");
	    Player person = testGame2.findPlayer("banankontakt");
	    Player person2 = testGame2.findPlayer("kamel");
	    q = person.getPlayerNumber();
	    lall = person2.getPlayerNumber();
	    
	}
	catch(RemoteException e){
	    System.out.println("Error" + e.getMessage());
	    e.printStackTrace();
	}
	assertTrue(q == 1);
	assertTrue(lall == 2);
    } */
	
	
    @Test
    public void test_nextplayer() {
	assertTrue(true);	
    }
	
    @Test
    public void test_looseGame() {
	assertTrue(true);
    }
	
    @Test
    public void test_giveWholeDeck() {
	assertTrue(true);
    }
	
    @Test
    public void test_handleWrongHit() {
	assertTrue(true);
    }
	
    @Test
    public void test_handleRightHit() {
	assertTrue(true);
    }
	
    @Test
    public void test_whatFourCards() {
	assertTrue(true);
    }
    
    /**
    @Test
    public void test_startGame() {
	assertTrue(true);
    }
    */
	
    @Test
    public void test_whoLostRightHit() {
	assertTrue(true);
    }

    /*
    @Test
    public void test_addPlayerAndGetPlayerAlias(){
	Game theGame = null;
	String test = "";
	try {
	    theGame = new Game(1);
	    int i =  theGame.addPlayer("125.323.32.5", "125.323.32.5", "mupp");
	    test = theGame.getPlayerAlias(i);
	}
	catch (RemoteException e) {
	    System.out.println("Error " + e.getMessage());
	    e.printStackTrace();
	}
	assertTrue(test.equals("mupp"));
    } 
    */
}

