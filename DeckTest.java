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

}
