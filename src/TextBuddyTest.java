import static org.junit.Assert.*;
import java.util.*;
import org.junit.*;

public class TextBuddyTest {	
	static TextBuddy curr;
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
		System.out.println("out add "+curr.getListSize());
	}
	
	@Test
	public void testDeleteOperation(){
		assertEquals(String.format(MSG_ADD, fileName,"Kodiak Island"), curr.commandOperations("add Kodiak Island"));
		assertEquals(String.format(MSG_ADD, fileName,"King George Island"), curr.commandOperations("add King George Island"));
		assertEquals(String.format(MSG_ADD, fileName,"Yos Sudarso"), curr.commandOperations("add Yos Sudarso"));
		assertEquals(String.format(MSG_ADD, fileName,"Rangsang"), curr.commandOperations("add Rangsang"));
		System.out.println("in delete "+curr.getListSize());
		int randNum=1+(int)(Math.random()*curr.getListSize());
		System.out.println("my rand num is "+randNum);
		String toBeDeletedLine=curr.txtList.get(randNum-1);
		System.out.println("line to be deleted "+toBeDeletedLine);
		assertEquals(String.format(MSG_DELETE, fileName, toBeDeletedLine), curr.commandOperations("delete "+ randNum));
	}
	
	@Test
	public void testSearchOperation(){
		assertEquals(String.format(MSG_ADD, fileName,"Kodiak Island"), curr.commandOperations("add Kodiak Island"));
		assertEquals(String.format(MSG_ADD, fileName,"King George Island"), curr.commandOperations("add King George Island"));
		assertEquals(String.format(MSG_ADD, fileName,"Yos Sudarso"), curr.commandOperations("add Yos Sudarso"));
		System.out.println("in search "+curr.getListSize());
		
		
	}
	@Test
	public void testSortAndDisplayOperation(){
		String newLine=System.lineSeparator();
		String sortedString="1. King George Island"+newLine+"2. Kodiak Island"+newLine+"3. Rangsang"+newLine+"4. Yos Sudarso"+newLine;
		assertEquals(String.format(MSG_ADD, fileName,"Kodiak Island"), curr.commandOperations("add Kodiak Island"));
		assertEquals(String.format(MSG_ADD, fileName,"King George Island"), curr.commandOperations("add King George Island"));
		assertEquals(String.format(MSG_ADD, fileName,"Yos Sudarso"), curr.commandOperations("add Yos Sudarso"));
		assertEquals(String.format(MSG_ADD, fileName,"Rangsang"), curr.commandOperations("add Rangsang"));
		System.out.println("in sort "+curr.getListSize());
		curr.commandOperations("sort");
		String temp=curr.commandOperations("display");
		assertEquals(sortedString, temp);
	}
	@Test
	public void testClearOperation(){
		assertEquals(String.format(MSG_ADD, fileName,"Kodiak Island"), curr.commandOperations("add Kodiak Island"));
		assertEquals(String.format(MSG_ADD, fileName,"King George Island"), curr.commandOperations("add King George Island"));
		assertEquals(String.format(MSG_ADD, fileName,"Yos Sudarso"), curr.commandOperations("add Yos Sudarso"));
		assertEquals(String.format(MSG_ADD, fileName,"Rangsang"), curr.commandOperations("add Rangsang"));
		System.out.println("in clear "+curr.getListSize());
		assertEquals(String.format(MSG_CLEAR, fileName), curr.commandOperations("clear"));
		assertEquals(0, curr.getListSize());
		System.out.println("out clear "+curr.getListSize());
	}
}
