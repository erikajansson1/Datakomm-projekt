import static org.junit.Assert.*;
import org.junit.Test;

import junit.framework.TestCase;


public class PlayerTest extends TestCase {

    @Test
    public void test_getReadyValue() {
	Player testPlayer = new Player(2, "77660", "875943", "Dess", true);
	assertTrue(testPlayer.getReadyValue() == true);
	Player testPlayer2 = new Player(2, "77660", "875943", "Dess", false);
	assertTrue(testPlayer2.getReadyValue() == false);
    }

    
    @Test
    public void test_setReadyValue() {
       	Player testPlayer = new Player(2, "123.34.22.1", "134.541.23.2", "Knaspannkaka", true);
	testPlayer.setReadyValue(false);
	assertTrue(testPlayer.getReadyValue() == false);
       
    }

    
    @Test
    public void test_getPlayerName() {
	Player testPlayer = new Player(2, "123.34.22.1", "134.541.23.2", "Knaspannkaka", true);
	String name = testPlayer.getPlayerName();
	assertTrue(name == "Knaspannkaka");
    }

    
    @Test
    public void test_setPlayerName() {
	Player testPlayer = new Player(2, "123.34.22.1", "134.541.23.2", "Knaspannkaka", true);
	testPlayer.setPlayerName("banankontakt");
	assertTrue(testPlayer.getPlayerName() == "banankontakt");
    }

    
    @Test
    public void test_getPlayerNumber() {
	Player testPlayer = new Player(2, "77660", "875943", "Dess", true);
	assertTrue(testPlayer.getPlayerNumber() == 2);
    }

    
    @Test
    public void test_getPlayerRank() {
		Player testPlayer = new Player(2, "123.34.22.1", "134.541.23.2", "Knaspannkaka", true);
		assertTrue(testPlayer.getPlayerRank() == -1);
    }

    
    @Test
    public void test_setPlayerRank() {
	Player testNisse = new Player(3, "423.645.23.2", "987.354.667.1", "Kallefjant", false);
	testNisse. setPlayerRank(2);
	assertTrue(testNisse.getPlayerRank() == 2);
    }

    
    @Test
    public void test_getPlayerDeck() {
	Player testPlayer = new Player(2, "77660", "875943", "Dess", true);
	Deck gameDeck = new Deck();
	testPlayer.getCardFromMiddleDeck(gameDeck);
	assertTrue(testPlayer.getPlayerDeck().getAmount() == 52);
    }

    
    @Test
    public void test_setAndGetPlayerTime() {
	Player testStina = new Player(5, "234.563.64.21", "544.675.32.13", "Fjollbert", true);
	testStina.setPlayerTime(12);
	assertTrue(testStina.getPlayerTime() == 12);	
    }


    @Test
    public void test_playNextCard() {
	Player testPlayer = new Player(2, "77660", "875943", "Dess", true);
	Deck gameDeck = new Deck();
	testPlayer.getCardFromMiddleDeck(gameDeck);
	testPlayer.playNextCard(gameDeck);
	assertTrue(testPlayer.getPlayerDeck().getAmount() == 51);
	assertTrue(gameDeck.getDeckSize() == 1);
    }

    
    @Test
    public void test_getCardFromMiddleDeck() {
	Player testPlayer = new Player(2, "77660", "875943", "Dess", true);
	Deck gameDeck = new Deck();
	testPlayer.getCardFromMiddleDeck(gameDeck);
	assertTrue(testPlayer.getPlayerDeck().getAmount() == 52);
    }

    
    @Test
    public void test_amountOfCardsOnHands() {
	Player testPlayer = new Player(2, "77660", "875943", "Dess", true);
	assertTrue(testPlayer.getPlayerDeck().getAmount() == 0);
    }
	
}
