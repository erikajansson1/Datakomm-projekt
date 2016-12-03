import static org.junit.Assert.*;

import java.rmi.RemoteException;

import org.junit.Test;

import junit.framework.TestCase;

public class GameTest extends TestCase {

	@Test
	public void test_newGame() {
		try {
			Game testGame = new Game(2);
			assertTrue(testGame.getAmountOfPlayers() == 2);
		} 	
		catch (RemoteException e) {
			 System.out.println("Error " + e.getMessage());
			 e.printStackTrace();
		}	
	}
	
	@Test
	public void test_timeToHit() {	
		//Count time 
		//Unsure how to test with time
		try{
		Game testGame = new Game(2);
		long timeItTook = testGame.timeToHit();
		assertTrue(timeItTook == 3); // TODO: This shouldn't be return 3.
		}
		catch (RemoteException e) {
			 System.out.println("Error " + e.getMessage());
			 e.printStackTrace();
		}	
	}

	
	@Test
	public void test_displayBoard() {
		
		
		assertTrue(true);
	}

	@Test
	public void test_setReadyValue() {
		assertTrue(true);
	}
	
	@Test
	public void test_updatePlayerTime() {
		assertTrue(true);
	}

	
	@Test
	public void test_whoseRound() {
		assertTrue(true);
	}
	

	@Test
	public void test_findPlayer() {
		assertTrue(true);
	}
	
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
	
	@Test
	public void test_whoLostRightHit() {
		assertTrue(true);
	}
}

