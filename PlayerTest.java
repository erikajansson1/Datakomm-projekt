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
	assertTrue(true);
       
    }

    
    @Test
    public void test_getPlayerName() {
	assertTrue(true);
    }

    
    @Test
    public void test_setPlayerName() {
	assertTrue(true);
    }

    
    @Test
    public void test_getPlayerNumber() {
	Player testPlayer = new Player(2, "77660", "875943", "Dess", true);
	assertTrue(testPlayer.getPlayerNumber() == 2);
    }

    
    @Test
    public void test_getPlayerRank() {
	assertTrue(true);
    }

    
    @Test
    public void test_setPlayerRank() {
	assertTrue(true);
    }

    
    @Test
    public void test_getPlayerDeck() {
	Player testPlayer = new Player(2, "77660", "875943", "Dess", true);
	Deck gameDeck = new Deck();
	testPlayer.getCardFromMiddleDeck(gameDeck);
	assertTrue(testPlayer.getPlayerDeck().getAmount() == 52);
    }

    
    @Test
    public void test_setPlayerTime() {
	assertTrue(true);	
    }

    
    @Test
    public void test_getPlayerTime() {
	assertTrue(true);
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
