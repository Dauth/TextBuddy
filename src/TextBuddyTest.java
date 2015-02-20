import static org.junit.Assert.*;

import java.util.*;

import org.junit.*;
import org.junit.Test;

public class TextBuddyTest {
	private static String MSG_ADD="added to %s: \"%s\"\n";
	private static String MSG_DELETE="deleted from %s: \"%s\"\n";
	private static String MSG_CLEAR="all content deleted from %s\n";
	private static String MSG_SETUP="Welcome to TextBuddy. %s is ready for use"+"\n";
	private static String MSG_DEFAULT="command: \n";
	private static String MSG_UNKNOWN_COMMAND="unknown command\n";
	private static String MSG_NO_FILE_FOUND="file does not exists\n";
	private static String MSG_EMPTPY_FILE="%s is empty\n";
	private static String MSG_WELCOME="Welcome to TextBuddy. %s is ready for use\n";
	private static String MSG_DELETE_LINE_ERROR="Unable to delete line %d as it does not exist\n";
	private static String MSG_INVALID_FORMAT = "Invalid command, please use only available commands\n";
	private static String MSG_NULL_FORMAT = "No command has been entered\n";
	private static String MSG_KEYWORD_NOT_FOUND = "No lines in txt contains keyword\n";
	private static String MSG_SUCCESS_SORT = "Txt has been successfully sorted\n";
	private static String MSG_EMPTY_SORT = "Unable to sort txt as it is empty\n";
	private static final String[] TEST_FILE="testfile.txt";
	
	
	TextBuddy current;
	
	@Before
	public void setup(){
		current=new TextBuddy(TEST_FILE);
		current.executeCommands(sc, userCommand, content)
	}
	
	@After
	public void teardown(){
		current.wipeFile();
	}
	
	public void testAdd(){
		assertEquals("added to %s: \"%s\"\n", );
	}
	
}
