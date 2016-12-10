import java.util.ArrayList;
import java.util.HashMap;
import org.junit.*;
import junit.framework.TestCase;

/**
 * This class is a JUNIT test case class
 */
public class CardTest extends TestCase {

    private void create_Card() {
	//Tror det som skrivs har kors innan testen nedan
	//Sa man skapar det som behovs for testerna.
	// Sen kor man testerna med asserts under.
    }

    
    
    @Test
    public void test_showCard() {
	Card theCard = new Card(7, "D");
	assertTrue(theCard.showCard().equals("[D 7]"));
    }

    @Test
    public void test_getSuit() {
	Card theCard2 = new Card(13, "Heart");
	assertTrue(theCard2.getSuit().equals("Heart"));
    }

    @Test
    public void test_getRank() {
	Card theCard3 = new Card(4,"Spade");
	assertTrue(theCard3.getRank() == 4);
	
    }
}
