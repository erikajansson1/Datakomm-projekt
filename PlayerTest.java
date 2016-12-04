import static org.junit.Assert.*;
import org.junit.Test;

import junit.framework.TestCase;


public class PlayerTest extends TestCase {

	  private void create_Player() {
			//Tror det som skrivs har kors innan testen nedan
			//Sa man skapar det som behovs for testerna.
			// Sen kor man testerna med asserts under.
		    }
	
	@Test
	public void test_getPlayerNumber() {
	    Player testPlayer = new Player(2, "77660", "875943", "Dess", true);
		assertTrue(testPlayer.getPlayerNumber() == 2);
		}

	@Test
	public void test_amountOfCardsOnHands() {
		Player testPlayer = new Player(2, "77660", "875943", "Dess", true);
		assertTrue(testPlayer.getPlayerDeck().getAmount() == 0);
	}
	
	@Test
	public void test_getCardFromMiddleDeck() {
		Player testPlayer = new Player(2, "77660", "875943", "Dess", true);
		Deck gameDeck = new Deck();
		testPlayer.getCardFromMiddleDeck(gameDeck);
		assertTrue(testPlayer.getPlayerDeck().getAmount() == 52);
	}
	
	@Test
	public void test_playNextCard() {
		Player testPlayer = new Player(2, "77660", "875943", "Dess", true);
		Deck gameDeck = new Deck();
		testPlayer.getCardFromMiddleDeck(gameDeck);
		testPlayer.playNextCard(gameDeck);
		assertTrue(testPlayer.getPlayerDeck().getAmount() == 51);
	}
	
	@Test
	public void test_gameAddCard(){
		Player testPlayer = new Player(2, "77660", "875943", "Dess", true);
		Deck gameDeck = new Deck();
		testPlayer.getCardFromMiddleDeck(gameDeck);
		testPlayer.playNextCard(gameDeck);
		assertTrue(gameDeck.getAmount() == 1);
	}
	
	@Test
	public void test_takeAndAdd() {
	    Player testPlayer = new Player(2, "77660", "875943", "Dess", true);
		Deck gameDeck = new Deck();
		testPlayer.getCardFromMiddleDeck(gameDeck);
		testPlayer.playNextCard(gameDeck);
		testPlayer.getCardFromMiddleDeck(gameDeck);
		assertTrue(testPlayer.getPlayerDeck().getAmount() == 52);
	}
	
}
