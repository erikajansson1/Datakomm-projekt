import static org.junit.Assert.*;
import java.rmi.RemoteException;
import org.junit.*;
import junit.framework.TestCase;
import java.io.*;
import java.util.*;
import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;


// Don't know how to make catch exception to work as I want to
public class GameTest extends TestCase {
    private static GameInterface serverGame;

    /**
     * This method builds a RMI server with a existing game in it.
     * CATUION!! Using this method invokes extreme latency on the test suit
     */
    public static GameInterface setUpRMIGame() throws RemoteException{
	try {
	    Network networkBuild = new Network();
	    networkBuild.buildNetwork();
	    networkBuild.debugLocal(4, true);

	    ArrayList<Player> gamePlayers = new ArrayList<Player>();
	    
	    for (int i = 0; i < 4; i++) {
		gamePlayers.add(new Player(i,"","","player"+i,false));
		gamePlayers.get(i).getPlayerDeck().addCard(new Card((i+1)*2,"Diamond"));
	    }


	    
	    Deck deck = new Deck(4);
	    deck.addCard(new Card(1,"Heart"));
	    deck.addCard(new Card(2,"Heart"));
	    deck.addCard(new Card(3,"Spade"));
	    deck.addCard(new Card(2,"Spade"));
	    
	    serverGame = (GameInterface) Naming.lookup("//"+
						       networkBuild.getInIp()+
						       ":"+
						       "1099"+
						       "/"+
						       "theGame");
	    
	    serverGame.setGameValues(5,deck,new Deck(0),gamePlayers);
	} catch (Throwable e) {
	    e.printStackTrace();
	    assertFalse(true);
	}
	return serverGame;
    }


    /**
     * A method which creates a game for 4 which hittable.
     */
    public static Game setUpNonRMIGame4() throws RemoteException{
	Game newGame = new Game(4);
	ArrayList<Player> gamePlayers = new ArrayList<Player>();
	for (int i = 0; i < 4; i++) {
	    gamePlayers.add(new Player(i,"","","player"+i,false));
	    gamePlayers.get(i).getPlayerDeck().addCard(new Card((i+1)*2,"Diamond"));
	}
	Deck deck = new Deck(4);
	deck.addCard(new Card(1,"Heart"));
	deck.addCard(new Card(2,"Heart"));
	deck.addCard(new Card(3,"Spade"));
	deck.addCard(new Card(2,"Spade"));
	
	newGame.setGameValues(5,deck,new Deck(0),gamePlayers);
	return newGame;
    }


    /**
     * A method which creates a game for 2 player. No hit.
     */
    public static Game setUpNonRMIGame2() throws RemoteException{
	Game newGame = new Game(2);
	ArrayList<Player> gamePlayers = new ArrayList<Player>();
	for (int i = 0; i < 2; i++) {
	    gamePlayers.add(new Player(i,"","","player"+i,false));
	    gamePlayers.get(i).getPlayerDeck().addCard(new Card((i+1)*2,"Diamond"));
	}
	Deck deck = new Deck(2);
	deck.addCard(new Card(1,"Heart"));
	deck.addCard(new Card(2,"Heart"));
	
	newGame.setGameValues(3,deck,new Deck(0),gamePlayers);
	return newGame;
    }


   
    /**
     * Emulates console input.
     * @param input is the string the user wants to emulate.
     */
    public void setUserInput(String input) {
	InputStream in = new ByteArrayInputStream(input.getBytes());
	System.setIn(in);
    }

    
    public void resetUserIn() {
	System.setIn(System.in);
    }

    
    ////////////////////////////////////////////////

    @Test
    public void test_timeToHit() throws Exception{
	Game testGame1 = setUpNonRMIGame4();
	assertTrue(testGame1.timeToHit());
	Game testGame2 = setUpNonRMIGame2();
	assertFalse(testGame2.timeToHit());
    }

    
    @Test
    public void test_getDeckSize() throws Exception{
	Game testGame = setUpNonRMIGame4();
	assertEquals(testGame.getDeckSize(),4);
    }

    
    @Test
    public void test_displayBoard() throws Exception{
	    Game newGame = setUpNonRMIGame4();
	    String testString =
		"player0"+
		"\nCards on hand: 1"+
		"\nStatus: NOT READY\n"+
		"\n" +
		"player1"+
		"\nCards on hand: 1"+
		"\nStatus: NOT READY\n"+
		"\n" +
		"player2"+
		"\nCards on hand: 1"+
		"\nStatus: NOT READY\n"+
		"\n" +
		"player3"+
		"\nCards on hand: 1"+
		"\nStatus: NOT READY\n"+
		"\n\n"+
		"Turn: player1"+
		"\nRound: 5"+
		"\nLatest Card: [Spade 2]";
	    assertEquals(newGame.displayBoard(), testString);
    }

    
    @Test
    public void test_setReadyValue() throws Exception {
	Game testGame = setUpNonRMIGame4();
	for (int i = 0; i < 4; i++) {
	    testGame.setReadyValue(i, true);
	    assertTrue(testGame.getPlayer("player"+i).getReadyValue());
	    testGame.setReadyValue(i, false);
	    assertFalse(testGame.getPlayer("player"+i).getReadyValue());
	}
    }

    
    @Test
    public void test_updatePlayerTime() throws Exception {
	Game testGame = setUpNonRMIGame4();
      int test = 0;
      for (int i = 0; i < 4; i++) {
	  testGame.updatePlayerTime(i, 10);
	  assertEquals(testGame.getPlayer("player"+i).getPlayerTime(),10);
      }
    }

    
    @Test
    public void test_updateRound() throws Exception {
	Game testGame = setUpNonRMIGame4();
	testGame.updateRound(5);
	assertEquals(testGame.getRound(),5);
	
	for (int i = 0; i < 4; i++) {
	    testGame.setReadyValue(i,true);
	}
	
	testGame.updateRound(5);
	assertEquals(testGame.getRound(),6);
    }

    
    @Test
    public void test_whoseTurn() throws Exception {
	Game testGame = setUpNonRMIGame4();
	assertEquals(testGame.whoseTurn(),1);
    }

    
    @Test
    public void test_getPlayer() throws Exception {
	int a = -1;
	int b = -1;
	Game testGame2 = setUpNonRMIGame2();
	    Player person1 = testGame2.getPlayer("player0");
	    Player person2 = testGame2.getPlayer("player1");
	    a = person1.getPlayerNumber();
	    b = person2.getPlayerNumber();

	assertTrue(a == 0);
	assertTrue(b == 1);
	assertFalse(a == 2);
	assertFalse(b == 3);
    }

    
    @Test
    public void test_startGame() throws Exception {
	Game testGame = setUpNonRMIGame4();
	boolean knas = false;
	if(testGame.getAmountOfPlayers() == 4) knas = true;
	assertTrue(knas);
    }

    
    @Test
    public void test_getAmountOfPlayers() throws Exception {
	Game testGame1 = setUpNonRMIGame4();
	assertEquals(testGame1.getAmountOfPlayers(),4);
	Game testGame2 = setUpNonRMIGame2();
	assertEquals(testGame2.getAmountOfPlayers(),2);
    }

    
    @Test
    public void test_giveWholeDeck() throws Exception {
	Game testGame = setUpNonRMIGame4();

	int amountBefore = testGame.getPlayer(0).getAmountOfCardsOnHand();
	assertTrue(amountBefore == 1);

	assertTrue(testGame.getDeckSize() == 4);
	
	testGame.giveWholeDeck(testGame.getPlayer(0));
	int amountAfter = testGame.getPlayer(0).getAmountOfCardsOnHand();
	assertTrue(amountAfter == 5);

	assertTrue(testGame.getDeckSize() == 0);
    }

    
    @Test
    public void test_myRank() throws Exception {
	Game testGame = setUpNonRMIGame4();
	assertEquals(testGame.myRank(0),-1);
    }

    
    @Test
    public void test_checkLoser() throws Exception {
	Game testGame = setUpNonRMIGame4();
	for (int i = 0; i < 4; i++) {
	    testGame.getPlayer(i).setPlayerRank(i+1);	    
	}
	assertTrue(testGame.checkLoser() == 3);
    }
    

    @Test
    public void test_moveDeck() throws Exception {
	Game testGame = setUpNonRMIGame4();

	Deck a = testGame.getPlayer(0).getPlayerDeck();
	assertTrue(a.getAmount() == 1);
	Deck b = testGame.getPlayer(1).getPlayerDeck();
	assertTrue(b.getAmount() == 1);

	testGame.moveDeck(a,b);
	assertTrue(b.getAmount() == 2);
    }


    @Test
    public void test_handleWrongHit() throws Exception {
	assertTrue(true);
    }

    
    @Test
    public void test_handleRightHit() throws Exception {
	Game testGame = setUpNonRMIGame4();
	for (int i = 0; i < 4; i++) {
	    testGame.getPlayer(i).setPlayerTime(i+1);	    
	}
	assertEquals(testGame.handleRightHit(0,5),"Hit succesfull! player3 lost.");

    }

    
    @Test
    public void test_tryToLayCard() throws Exception {
	Game testGame = setUpNonRMIGame4();
	assertEquals(testGame.getDeckSize(),4);
	for (int i = 0; i < 4; i++) {
	    assertTrue(testGame.getPlayer(i).getPlayerDeck().getDeckSize() == 1);
	}
	boolean say = testGame.tryToLayCard(0, 5);
	Card topCard = testGame.getGameDeck().getCard(false);
	assertEquals(topCard.getSuit(),"Diamond");
	assertEquals(topCard.getRank(),2);
    }

    
    @Test
    public void test_getRound() throws Exception {
	Game testGame = setUpNonRMIGame4();
	assertTrue(testGame.getRound() == 5);
    }

    
    @Test
    public void test_getGameDeck() throws Exception {
	Game testGame = setUpNonRMIGame4();
	assertTrue(testGame.getGameDeck() != null);
	assertTrue(testGame.getGameDeck().getAmount() == 4);
    }

    
    @Test
    public void test_getStarterDeck() throws Exception {
	Game testGame = setUpNonRMIGame4();
	assertTrue(testGame.getStarterDeck() != null);
	assertTrue(testGame.getStarterDeck().getAmount() == 0);
    }

    
    @Test
    public void test_getGamePlayers() throws Exception {
	Game testGame = setUpNonRMIGame4();
	assertTrue(testGame.getGamePlayers() != null);
	assertEquals(testGame.getGamePlayers().get(0).getPlayerName(),"player0");
    }

    
    @Test
    public void test_setGameValues() throws Exception {
	Game testGame = new Game(4);
	assertTrue(testGame.getRound() == 0);
	testGame.setGameValues(10,new Deck(0),null,null);
	assertTrue(testGame.getRound() == 10);
	assertTrue(testGame.getGameDeck().getAmount() == 0);
    }

    
    @Test
    public void test_addPlayer() throws Exception {
	Game testGame = new Game(1);
	assertEquals(testGame.getPlayer(0).getPlayerName(), "Empty");
	testGame.addPlayer("","","Muppet0");
	assertEquals(testGame.getPlayer(0).getPlayerName(), "Muppet0");
    }

    
    @Test
    public void test_getPlayerAlias() throws Exception {
	Game testGame = setUpNonRMIGame4();
	assertEquals(testGame.getPlayerAlias(0),"player0");
    }

    
    @Test
    public void test_askIsGameFull() throws Exception {
	Game testGame = setUpNonRMIGame4();
	assertTrue(testGame.askIsGameFull());
	Game testGameEmpty = new Game(4);
	assertFalse(testGameEmpty.askIsGameFull());
    }   

    
    @Test
    public void test_waitingForPlayers() throws Exception {
	Game testGame = setUpNonRMIGame4();
	assertTrue(testGame.waitingForPlayers());
	Game testGameEmpty = new Game(4);
	assertFalse(testGameEmpty.waitingForPlayers());
    }  

    
    @Test
    public void test_everyoneHasMadeMove() throws Exception {
	Game testGame = setUpNonRMIGame4();
	assertFalse(testGame.everyoneHasMadeMove());
	for (int i = 0; i < 4; i++) {
	    testGame.getPlayer(i).setPlayerTime(i+1);
	}
	assertTrue(testGame.everyoneHasMadeMove());
    }  

    
    @Test
    public void test_askGameEnded() throws Exception {
	Game testGame = setUpNonRMIGame4();
	assertFalse(testGame.askGameEnded());
	for (int i = 0; i < 4; i++) {
	    testGame.getPlayer(i).setPlayerRank(i);
	}
	assertTrue(testGame.askGameEnded());
    }  

    
    @Test
    public void test_displayGameResult() throws Exception {
	Game testGame = setUpNonRMIGame4();
	String result = "";
	for (int i = 0; i < 4; i++) {
	    testGame.getPlayer(i).setPlayerRank(i);

	    if (i != 0) result += "\n";
	    result += "player"+i+"\nPlace: "+i+"\n";
	}
	assertEquals(testGame.displayGameResult(),result);
    }
    
}

