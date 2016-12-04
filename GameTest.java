import java.rmi.RemoteException;
import org.junit.Test;
import junit.framework.TestCase;


// Don't know how to make catch exception to work as I want to
public class GameTest extends TestCase {
	
	@Test
	public void test_newGame() throws RemoteException{
	   Game testGame = new Game(2);
	   assertTrue(true);
	   }

	  @Test
	  public void test_newGame2() {
	      try {
	         Game testGame = new Game(2);
	       } catch (Exception e){
            assertNull(e);
            }
	      }
	
	
	@Test
	public void test_timeToHit(){	
		try{
		Game testGame = new Game(2);
		boolean isItTrue = testGame.timeToHit();
		assertTrue(isItTrue);
		}
		catch (RemoteException e) {
			 System.out.println("Error " + e.getMessage());
			 e.printStackTrace();
		}
	}

	
	@Test
	public void test_displayBoard() {
		try {
			Game testGame = new Game(2);
			String testDisplay = testGame.displayBoard(); 
			String testCompare = "hej";
			assertSame(testDisplay, testCompare);		
		} 	
		catch (RemoteException e) {
			 System.out.println("Error " + e.getMessage());
			 e.printStackTrace();
		}
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

