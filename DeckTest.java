import java.util.ArrayList;
import java.util.HashMap;
import org.junit.*;
import junit.framework.TestCase;

/**
 * This class is a JUNIT test case class
 */
public class DeckTest extends TestCase {

    private void create_Deck() {
	//Tror det som skrivs har kors innan testen nedan
	//Sa man skapar det som behovs for testerna.
	// Sen kor man testerna med asserts under.
    }

    
    @Test
    public void test_getCard() {
	Deck theDeck = new Deck();
	assertTrue(theDeck.getCard().showCard().equals("[Diamond13]"));
    }

    @Test
    public void test_mixup() {
	Deck theDeck2 = new Deck();
	Deck theDeck3 = new Deck();
	theDeck2.mixup();
	assertFalse(theDeck2.getCard().showCard().equals(theDeck3.getCard().showCard()));
    }

    @Test
    public void test_mixupAndAddCard() {
	Deck theDeck4 = new Deck(3);
	Card theCard = new Card(7,"Heart");
	Card theCard2 = new Card(8,"Spade");
	Card theCard3 = new Card(9,"Diamond");
	boolean knas;
	theDeck4.addCard(theCard);
	theDeck4.addCard(theCard2);
	theDeck4.addCard(theCard3);
	if(theDeck4.getCardList().get(0).showCard().equals("[Heart7]") && theDeck4.getCardList().get(1).showCard().equals("[Spade8]") && theDeck4.getCardList().get(2).showCard().equals("[Diamond9]")) {
	    knas = true;
	}
	else {
	    knas = false;
	}
	assertTrue(knas);
	
    }

    @Test
    public void test_possibleToHit() {
		
    }
    @Test
    public void test_getAmount() {
	Deck theDeck5 = new Deck(3);
       	Card theCard = new Card(7,"Heart");
	Card theCard2 = new Card(8,"Spade");
	Card theCard3 = new Card(9,"Diamond");
	theDeck5.addCard(theCard);
	theDeck5.addCard(theCard2);
	theDeck5.addCard(theCard3);
	assertTrue(theDeck5.getAmount() == 3);
    }

    @Test
    public void test_getAmount2() {
	Deck theDeck6 = new Deck(0);
	Deck theDeck7 = new Deck();
	assertTrue(theDeck6.getAmount() == 0);
	assertTrue(theDeck7.getAmount() == 52);
    }
}
