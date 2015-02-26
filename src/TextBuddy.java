import java.io.*;
import java.util.*;


public class TextBuddy{
	
	private static String MSG_ADD="added to %s: \"%s\"\n";
	private static String MSG_DELETE="deleted from %s: \"%s\"\n";
	private static String MSG_CLEAR="all content deleted from %s\n";
	private static String MSG_DEFAULT="command: \n";
	private static String MSG_UNKNOWN_COMMAND="unknown command\n";
	private static String MSG_NO_FILE_FOUND="file does not exists\n";
	private static String MSG_EMPTPY_FILE="%s is empty\n";
	private static String MSG_WELCOME="Welcome to TextBuddy. %s is ready for use\n";
	private static String MSG_DELETE_LINE_ERROR="Unable to delete line %d as it does not exist\n";
	private static String MSG_RETRIEVE_LINE_ERROR="Unable to retrieve line %d as it does not exist\n";
	private static String MSG_INVALID_FORMAT = "Invalid command, please use only available commands\n";
	private static String MSG_NULL_FORMAT = "No command has been entered\n";
	private static String MSG_KEYWORD_NOT_FOUND = "No lines in txt contains keyword\n";
	private static String MSG_SUCCESS_SORT = "Txt has been successfully sorted\n";
	private static String MSG_EMPTY_SORT = "Unable to sort txt as it is empty\n";
	
	private static String fileName="";
	static ArrayList<String> txtList=new ArrayList<String>();
	private static Scanner sc=new Scanner(System.in);

	//Constants
	private static int ARRAY_SIZE_TWO=2;
	private static int ARRAY_INDEX_ZERO=0;
	private static int ARRAY_INDEX_ONE=1;
	
	
	// Current possible commands a user can input
	enum USER_COMMAND {
		ADD,DELETE,DISPLAY,CLEAR,EXIT,INVALID,SORT,SEARCH;
	}
	public TextBuddy(String args) {
		initTextBuddy(args);
	}

	public static void main(String[] args) {
		TextBuddy tb=new TextBuddy(args[ARRAY_INDEX_ZERO]);
		run();
	}

	public static void initTextBuddy(String args){
		retrieveFileName(args);
		outputMsg(String.format(MSG_WELCOME, fileName));
	}

	//retrival of filename which user has entered in arguments
	private static void retrieveFileName(String args) {
		if(args.length()!=0){
			File txtFile=new File(args);
			fileName=args;
			setupTxtFile(txtFile);
		}
	}

	//to check if file exists. if it doesn't, program will create it
	private static void setupTxtFile(File txtFile) {
		if(!isFileExists(txtFile)){
			createFile(txtFile);
		}
	}

	public static void run(){
		while(true){
			loadList();
			outputMsg(MSG_DEFAULT);
			outputMsg(commandOperations(sc.nextLine()));
		}
	}
	
	public static String commandOperations(String inputLine) {
		if(!inputLine.isEmpty()){
			String userCommand=inputLine.split(" ",ARRAY_SIZE_TWO)[ARRAY_INDEX_ZERO];
			USER_COMMAND typeOfCommand=validateCommand(userCommand);
			return executeCommands(typeOfCommand, inputLine);
		}
		else{
			return String.format(MSG_NULL_FORMAT);
		}
	}

	/*This function carries out the appropriate action based on user's command
	 * possible commands are listed below
	 * add <name>
	 * delete <line number>
	 * clear
	 * exit
	 * */
	public static String executeCommands(USER_COMMAND userCommand, String content) {
		String output="";
		switch (userCommand){
		case ADD:
			output=addOperation(content);
			break;
		case DELETE:
			output=deleteOperation(content);
			break;
		case CLEAR:
			output=wipeFileOperation();
			break;
		case DISPLAY:
			output=listOperation();
			break;
		case SORT:
			output=sortOperation();
			break;
		case SEARCH:
			output=searchOperation(content);
			break;
		case EXIT:
			exit();
			break;
		default:
			output=MSG_UNKNOWN_COMMAND;
			break;
		}
		return output;
	}
	
	//sorts the list in ascending order
	private static String sortOperation(){
		if(!txtList.isEmpty()){
			Collections.sort(txtList);
			writeToFile();
			return String.format(MSG_SUCCESS_SORT);
		}
		return String.format(MSG_EMPTY_SORT);
	}
	
	//search for word in txt file
	private static String searchOperation(String input){
		String userKeyword=new String(input.split(" ")[ARRAY_INDEX_ONE].trim());
		int counter=1;
		StringBuffer sb=new StringBuffer();
		for (int i = 0; i < txtList.size(); i++) {
			if(txtList.get(i).contains(userKeyword)){
				sb.append((counter++ +". "+txtList.get(i)));
				sb.append(System.getProperty("line.separator"));
			}
		}
		String allFoundLines=sb.toString();
		if(allFoundLines.isEmpty()){
			return String.format(MSG_KEYWORD_NOT_FOUND);
		}
		return allFoundLines;
	}
	
	private static void exit() {
		sc.close();
		System.exit(0);
	}
	
	private static String deleteOperation(String content) {
		int actualLineIndex=Integer.parseInt(content.split(" ")[ARRAY_INDEX_ONE].trim())-1;
		String itemDeleted=deleteItemInList(actualLineIndex);
		return itemDeleted;
	}
	
	private static void outputMsg (String text){
		System.out.print(text);
	}
	
	//gets txtList aka txt file size
	public int getListSize(){
		loadList();
		return txtList.size();
	}

	//This function checks if the command entered by the user is supported by the program  
	private static USER_COMMAND validateCommand(String userInputCommand) {
		if (userInputCommand.equalsIgnoreCase(("add"))) {
			return USER_COMMAND.ADD;
		} 
		else if (userInputCommand.equalsIgnoreCase("delete")) {
			return USER_COMMAND.DELETE;
		} 
		else if (userInputCommand.equalsIgnoreCase("clear")) {
			return USER_COMMAND.CLEAR;
		} 
		else if (userInputCommand.equalsIgnoreCase("display")) {
			return USER_COMMAND.DISPLAY;
		}
		else if (userInputCommand.equalsIgnoreCase("exit")) {
			return USER_COMMAND.EXIT;
		}
		else if (userInputCommand.equalsIgnoreCase("sort")) {
			return USER_COMMAND.SORT;
		}
		else if (userInputCommand.equalsIgnoreCase("search")) {
			return USER_COMMAND.SEARCH;
		}
		else{
			return USER_COMMAND.INVALID;
		}
	}

	//adds line into the specific txtfile
	public static String addOperation(String st){
		String toBeAddedLine=st.split(" ", ARRAY_SIZE_TWO)[ARRAY_INDEX_ONE];
		txtList.add(toBeAddedLine);
		writeToFile();
		return String.format(MSG_ADD,fileName,toBeAddedLine);
	}

	//clears list before adding contents from txt file into list
	public static void loadList(){
		txtList.clear();
		Scanner sc2;
		try {
			sc2 = new Scanner(new File(fileName));
			while(sc2.hasNext())
				txtList.add(sc2.nextLine());
		} catch (FileNotFoundException e) {
			outputMsg(MSG_NO_FILE_FOUND);
		}
	}

	// If file exits, it will load content into list and print it. Else it displays the txt file is empty
	private static String listOperation(){
		if(!txtList.isEmpty()){
			return displayList();
		}
		else
			return String.format(MSG_EMPTPY_FILE, fileName);
	}

	private static String displayList() {
		StringBuffer sb=new StringBuffer();
		for (int i = 0; i < txtList.size(); i++) {
			sb.append((i+1 +". "+txtList.get(i)));
			sb.append(System.getProperty("line.separator"));
		}
		return sb.toString();
	}

	public static String wipeFileOperation(){
		txtList.clear();
		writeToFile();
		return String.format(MSG_CLEAR, fileName);
	}

	public static String deleteItemInList(int lineIndex){
		if(!txtList.isEmpty() && lineIndex>=0 && lineIndex<txtList.size()){
			String toBeDeletedLine=txtList.get(lineIndex);
			txtList.remove(lineIndex);
			writeToFile();
			return String.format(MSG_DELETE, fileName, toBeDeletedLine);
		}
		else
			return String.format(MSG_DELETE_LINE_ERROR,lineIndex+1);
	}

	private static void writeToFile(){
		try {
			FileWriter fw=new FileWriter(fileName,false);
			for(String temp:txtList){
				fw.write(temp+"\n");
				fw.flush();
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static boolean isFileExists(File txtFile){
		if(txtFile.exists()){
			return true;
		}
		return false;
	}

	public static void createFile(File txtFile){
		if(!isFileExists(txtFile))
			try {
				txtFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
