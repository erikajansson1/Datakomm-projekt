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
		Player testPlayer = new Player(2);
		assertTrue(testPlayer.getPlayerNumber() == 2);
		}

	@Test
	public void test_amountOfCardsOnHands() {
		Player testPlayer = new Player(5);
		assertTrue(testPlayer.amountOfCardsOnHands == 0);
	}
	
	@Test
	public void test_combineCards() {
		Player testPlayer = new Player(5);
		Deck gameDeck = new Deck();
		testPlayer.getCardFromMiddleDeck(gameDeck);
		assertTrue(testPlayer.amountOfCardsOnHands == 52);
	}
	
	@Test
	public void test_takeACard() {
		Player testPlayer = new Player(5);
		Deck gameDeck = new Deck();
		testPlayer.getCardFromMiddleDeck(gameDeck);
		testPlayer.dropCard(gameDeck);
		assertTrue(testPlayer.amountOfCardsOnHands == 51);
	}
	
	@Test
	public void test_gameAddCard(){
		Player testPlayer = new Player(5);
		Deck gameDeck = new Deck();
		testPlayer.getCardFromMiddleDeck(gameDeck);
		testPlayer.dropCard(gameDeck);
		assertTrue(gameDeck.getAmount() == 1);
	}
	
	@Test
	public void test_tackeAndAdd() {
		Player testPlayer = new Player(5);
		Deck gameDeck = new Deck();
		testPlayer.getCardFromMiddleDeck(gameDeck);
		testPlayer.dropCard(gameDeck);
		testPlayer.getCardFromMiddleDeck(gameDeck);
		assertTrue(testPlayer.amountOfCardsOnHands == 52);
	}
	
}
