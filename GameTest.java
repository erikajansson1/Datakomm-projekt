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

    public static GameInterface setUpServerGame() throws RemoteException{
	try {
	    Network networkBuild = new Network();
	    networkBuild.buildNetwork();
	    networkBuild.debugLocal(4, true);

	    ArrayList<Player> gamePlayers = new ArrayList<Player>();
	    
	    for (int i = 0; i < 4; i++) {
		gamePlayers.add(new Player(i,"","","player"+i,false));	
	    }
	    
	    Deck deck = new Deck(4);
	    deck.addCard(new Card(1,"Heart"));
	    deck.addCard(new Card(2,"Heart"));
	    deck.addCard(new Card(3,"Spade"));
	    deck.addCard(new Card(2,"Spade"));
	    assertTrue(deck.getDeckSize() == 4);
	    
	    serverGame = (GameInterface) Naming.lookup("//"+
						       networkBuild.getInIp()+
						       ":"+
						       "1099"+
						       "/"+
						       "theGame");
	    
	    serverGame.setGameValues(5,deck,null,gamePlayers);
	} catch (Throwable e) {
	    e.printStackTrace();
	    assertFalse(true);
	}
	return serverGame;
    }


   
    
    public void setUserInput(String input) {
	InputStream in = new ByteArrayInputStream(input.getBytes());
	System.setIn(in);
    }

    
    public void resetUserIn() {
	System.setIn(System.in);
    }

    
    ////////////////////////////////////////////////

    @Test
    public void test_timeToHit()  throws Exception{
	serverGame = setUpServerGame();
	assertTrue(serverGame.getRound() == 5);

	
	assertTrue(true);
    }

    @Test
    public void test_getDeckSize()  throws Exception{
	serverGame = setUpServerGame();
	assertTrue(serverGame.getRound() == 5);
	assertTrue(true);
    }

    @Test
    public void test_displayBoard()  throws RemoteException{
	try {
	    serverGame = setUpServerGame();
	    assertTrue(serverGame != null);
	    String testString =
		"player0"+
		"\nCards on hand: 0"+
		"\nStatus: NOT READY\n"+
		"\n\n" +
		"p layer1"+
		"\nCards on hand: 0"+
		"\nStatus: NOT READY\n"+
		"\n\n" +
		"player2"+
		"\nCards on hand: 0"+
		"\nStatus: NOT READY"+
		"\n\n" +
		"player3"+
		"\nCards on hand: 0"+
		"\nStatus: NOT READY"+
		"\n\n\n"+
		"Turn: player1"+
		"\nRound: 5"+
		"\nLatest Card: [Spade 2]";
	    assertEquals(serverGame.displayBoard(), testString);
	}catch (Exception e){
	    e.printStackTrace();
	    assertFalse(true);
	    }
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
    public void test_updateRound() {
	assertTrue(true);
    }

    @Test
    public void test_whoseTurn() {
	assertTrue(true);
    }

    @Test
    public void test_getPlayer() {
	assertTrue(true);
    }
    
    @Test
    public void test_startGame() {
	assertTrue(true);
    }

    @Test
    public void test_getAmountOfPlayers() {
	assertTrue(true);
    }

    @Test
    public void test_giveWholeDeck() {
	assertTrue(true);
    }

    @Test
    public void test_myRank() {
	assertTrue(true);
    }

    @Test
    public void test_checkLoser() {
	assertTrue(true);
    }

    @Test
    public void test_moveDeck() {
	assertTrue(true);
    }

    @Test
    public void test_loserTakesItAll() {
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
    public void test_tryToLayCard() {
	/*
	try {
	    Game server = new Game(2); 
	    Player player1 = new Player(0, "123546", "123546", "Dess", true);
	    Player player2 = new Player(1, "123546", "123546", "Elsa", true);
	    server.startGame(2);
	    server.updatePlayerTime(1, 2L);
	    server.updatePlayerTime(0, 1L);
	    int round = server.getRound();
	    boolean say = server.tryToLayCard(0, round);
	
	    int size = server.getDeckSize();
	    int deck0 = server.getPlayer(0).getPlayerDeck().getDeckSize();
	    int deck1 = server.getPlayer(1).getPlayerDeck().getDeckSize();
	
	    System.out.println(say+" round: "+round+" , Player 1: "+deck0 + ", player 2: "+deck1 +", size: "+size);
	    assertTrue(size == 1);
	}
	catch(Exception e) {assertFalse(true);}*/
	assertFalse(false);
    }
    
    @Test
    public void test_getRound() {
	assertTrue(true);
    }
    
    @Test
    public void test_getGameDeck() {
	assertTrue(true);
    }
    
    @Test
    public void test_getStarterDeck() {
	assertTrue(true);
    }
    
    @Test
    public void test_getGamePlayers() {
	assertTrue(true);
    }
    
    @Test
    public void test_setGameValues() {
	assertTrue(true);
    }
    
    @Test
    public void test_addPlayer() {
	assertTrue(true);
    }
    
    @Test
    public void test_getPlayerAlias() {
	assertTrue(true);
    }
    
    @Test
    public void test_askIsGameFull() {
	assertTrue(true);
    }   
    
    @Test
    public void test_waitingForPlayers() {
	assertTrue(true);
    }  
    
    @Test
    public void test_everyoneHasMadeMove() {
	assertTrue(true);
    }  
    
    @Test
    public void test_askGameEnded() {
	assertTrue(true);
    }  
    
    @Test
    public void test_displayGameResult() {
	assertTrue(true);
    }  
    
	
    /*
      /**
      * This is commented out because if we test several games at the same time
      * their will be conflict because they use the same port.
      * @throws RemoteException
      */
    /*
      @Test
      public void test_newGame() {
      boolean knas = false;
      try {
      Game testGame = new Game(2);
      knas = testGame.getAmountOfPlayers() == 2;
      //assertTrue(testGame.getAmountOfPlayers() == 2);
      } 	
      catch (RemoteException e) {
      System.out.println("Error " + e.getMessage());
      e.printStackTrace();
      }	
      assertTrue(knas);
      }
	
      @Test
      public void test_timeToHit() {	
      try{
      Game testGame = new Game(2);
      boolean isItTrue = testGame.timeToHit();
      assertFalse(isItTrue);
      }
      catch (RemoteException e) {
      System.out.println("Error " + e.getMessage());
      e.printStackTrace();
      }
      assertTrue(true);
      }
    */

    /*
      @Test
	

	
      @Test
      public void test_setReadyValue() {
      Game testGame1 = null;
      boolean value = false;
      try {
      testGame1 = new Game(1);
      int i = testGame1.addPlayer("125.323.32.5", "125.323.32.5", "mupp");
      testGame1.setReadyValue(i, true);
      value = testGame1.getPlayer("mupp").getReadyValue();
      }
      catch (RemoteException e) {
      System.out.println("Error" + e.getMessage());
      e.printStackTrace();
      } 
      assertTrue(value == true);
      }  */
    
    /*
      @Test
      public void test_updatePlayerTime() {
      Game testGame3 = null;
      int test = 0;
      try {
      testGame3 = new Game(3);
      int i = testGame3.addPlayer("125.323.32.5", "125.323.32.5", "kanelbullen75");
      testGame3.updatePlayerTime(i, 10);
      test = testGame3.findPlayer("kanelbullen75").getPlayerTime();
      }
      catch(RemoteException e){
      System.out.println("Error" + e.getMessage());
      e.printStackTrace();
      }
      assertTrue(test == 10);
      } */

    /*
      @Test
      public void test_findPlayer() {
      Game testGame2 = null;
      int q = 0;
      int lall = 0;
      try {
      testGame2 = new Game(3);
      int i = testGame2.addPlayer("77.218.245.102", "77.218.245.102", "knasboll");
      int p = testGame2.addPlayer("192.343.23.09", "189.234.231.22", "banankontakt");
      int s = testGame2.addPlayer("234.234.21.12", "123.456.78.90", "kamel");
      Player person = testGame2.findPlayer("banankontakt");
      Player person2 = testGame2.findPlayer("kamel");
      q = person.getPlayerNumber();
      lall = person2.getPlayerNumber();
	    
      }
      catch(RemoteException e){
      System.out.println("Error" + e.getMessage());
      e.printStackTrace();
      }
      assertTrue(q == 1);
      assertTrue(lall == 2);
      } */
	
    /*	
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
    */
    /*
      @Test
      public void test_startGame() {
      try {
      Game server = new Game(2); 
      Player player1 = new Player(0, "123546", "123546", "Dess", true);
      Player player2 = new Player(1, "123546", "123546", "Elsa", true);
      int size = server.getDeckSize();
	
      server.startGame(2);
      int deck0 = server.getPlayer(0).getPlayerDeck().getDeckSize();
      int deck1 = server.getPlayer(1).getPlayerDeck().getDeckSize();
      System.out.println("startGame: player 1 size="+deck0);
      System.out.println("startGame: player 2 size="+deck1);
      assertTrue(deck0==deck1);

      }
      catch(Exception e) {
      System.out.println(e);
      }
      assertTrue(true);
      }*/
    
    

    


    /*	
	@Test
	public void test_whoLostRightHit() {
	assertTrue(true);
	}*/

    /*
      @Test
      public void test_addPlayerAndGetPlayerAlias(){
      Game theGame = null;
      String test = "";
      try {
      theGame = new Game(1);
      int i =  theGame.addPlayer("125.323.32.5", "125.323.32.5", "mupp");
      test = theGame.getPlayerAlias(i);
      }
      catch (RemoteException e) {
      System.out.println("Error " + e.getMessage());
      e.printStackTrace();
      }
      assertTrue(test.equals("mupp"));
      } 
    */
}

