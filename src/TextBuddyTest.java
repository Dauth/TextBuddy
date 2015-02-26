import static org.junit.Assert.*;
import java.util.*;
import org.junit.*;

public class TextBuddyTest {	
	static TextBuddy curr;
	String newLine=System.lineSeparator();
	private static String MSG_ADD="added to %s: \"%s\"\n";
	private static String MSG_CLEAR="all content deleted from %s\n";
	private static String MSG_DELETE="deleted from %s: \"%s\"\n";
	static String fileName;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		fileName="testfile.txt";
		curr=new TextBuddy(fileName);
	}

	@After
	public void tearDown() throws Exception {
		curr.wipeFileOperation();
	}
	
	@Test
	public void testAddOperation() {	
		assertEquals(String.format(MSG_ADD, fileName,"New Guinea"), curr.commandOperations("add New Guinea"));
		assertEquals(String.format(MSG_ADD, fileName,"Ellesmere Island"), curr.commandOperations("add Ellesmere Island"));
		assertEquals(2,curr.getListSize());
	}
	
	@Test
	public void testDeleteOperation(){
		assertEquals(String.format(MSG_ADD, fileName,"Kodiak Island"), curr.commandOperations("add Kodiak Island"));
		assertEquals(String.format(MSG_ADD, fileName,"King George Island"), curr.commandOperations("add King George Island"));
		assertEquals(String.format(MSG_ADD, fileName,"Yos Sudarso"), curr.commandOperations("add Yos Sudarso"));
		assertEquals(String.format(MSG_ADD, fileName,"Rangsang"), curr.commandOperations("add Rangsang"));
		String expectedDeletedLine="Yos Sudarso";
		assertEquals(String.format(MSG_DELETE, fileName, expectedDeletedLine), curr.commandOperations("delete 3"));
		assertEquals(3,curr.getListSize());
	}
	
	@Test
	public void testSearchOperation(){
		assertEquals(String.format(MSG_ADD, fileName,"Kodiak Island"), curr.commandOperations("add Kodiak Island"));
		assertEquals(String.format(MSG_ADD, fileName,"King George Island"), curr.commandOperations("add King George Island"));
		assertEquals(String.format(MSG_ADD, fileName,"Yos Sudarso"), curr.commandOperations("add Yos Sudarso"));
		String expectedLines="1. Kodiak Island"+newLine+"2. King George Island"+newLine;
		assertEquals(expectedLines, curr.commandOperations("search Island"));
		
	}
	@Test
	public void testSortAndDisplayOperation(){
		String expectedsortedLines="1. King George Island"+newLine+"2. Kodiak Island"+newLine+"3. Rangsang"+newLine+"4. Yos Sudarso"+newLine;
		assertEquals(String.format(MSG_ADD, fileName,"Kodiak Island"), curr.commandOperations("add Kodiak Island"));
		assertEquals(String.format(MSG_ADD, fileName,"King George Island"), curr.commandOperations("add King George Island"));
		assertEquals(String.format(MSG_ADD, fileName,"Yos Sudarso"), curr.commandOperations("add Yos Sudarso"));
		assertEquals(String.format(MSG_ADD, fileName,"Rangsang"), curr.commandOperations("add Rangsang"));
		curr.commandOperations("sort");
		String actualLines=curr.commandOperations("display");
		assertEquals(expectedsortedLines, actualLines);
	}
	@Test
	public void testClearOperation(){
		assertEquals(String.format(MSG_ADD, fileName,"Kodiak Island"), curr.commandOperations("add Kodiak Island"));
		assertEquals(String.format(MSG_ADD, fileName,"King George Island"), curr.commandOperations("add King George Island"));
		assertEquals(String.format(MSG_ADD, fileName,"Yos Sudarso"), curr.commandOperations("add Yos Sudarso"));
		assertEquals(String.format(MSG_ADD, fileName,"Rangsang"), curr.commandOperations("add Rangsang"));
		assertEquals(4, curr.getListSize());
		assertEquals(String.format(MSG_CLEAR, fileName), curr.commandOperations("clear"));
		assertEquals(0, curr.getListSize());
	}
}
