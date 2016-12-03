JFLAGS = -g -Xlint
JUNITTEST = org.junit.runner.JUnitCore
CLASSPATH =.:junit-4.10.jar:hamcrest-core-1.3.jar
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Card.java \
	Deck.java \
	Player.java \
	Game.java \
	GameInterface.java \
	GameServer.java \
	GameClient.java \
	Network.java \
	BackUp.java \

TESTC = \
	AllTest.java \
	CardTest.java \
	DeckTest.java \



all: $(CLASSES:.java=.class)

testall:	
	javac -cp $(CLASSPATH) $(TESTC)

test: testall
	java -cp $(CLASSPATH) $(JUNITTEST) AllTest

doc:
	javadoc -d documentation $(CLASSES)


clean:
	rm -f *.class
	rm -rf documentation
	rm -f *.java~
	rm -f *#
