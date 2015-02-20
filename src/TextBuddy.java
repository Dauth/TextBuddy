import java.io.*;
import java.util.*;


public class TextBuddy{

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
	private static String fileName="";
	static ArrayList<String> list=new ArrayList<String>();
	private static Scanner sc=new Scanner(System.in);

	// Current possible commands a user can input
	enum USER_COMMAND {
		ADD,DELETE,DISPLAY,CLEAR,EXIT,INVALID,SORT,SEARCH;
	}
	public static void main(String[] args) {
		TextBuddy textBuddy = new TextBuddy(args);
	}

	public TextBuddy(String[] args){
		initTextBuddy(args);
		run();
	}

	public void initTextBuddy(String[] args){
		retrieveFileName(args);
		outputMsg(String.format(MSG_WELCOME, fileName));
	}

	//retrival of filename which user has entered in arguments
	private void retrieveFileName(String[] args) {
		if(args.length!=0){
			File txtFile=new File(args[0]);
			fileName=args[0];
			setupTxtFile(txtFile);
		}
	}

	//to check if file exists. if it doesn't, program will create it
	private void setupTxtFile(File txtFile) {
		if(!isFileExists(txtFile)){
			createFile(txtFile);
		}
	}

	public void run(){
		while(sc.hasNext()){
			loadList();
			outputMsg(MSG_DEFAULT);
			outputMsg(commandOperations());
		}
	}
	
	private String commandOperations() {
		String inputLine=sc.nextLine();
		if(!inputLine.isEmpty()){
			USER_COMMAND typeOfCommand=determineCommand(inputLine.split(" ",2)[0]);
			return executeCommands(typeOfCommand, inputLine);
		}
		else
			return String.format(MSG_NULL_FORMAT);
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
			output=addLine(content);
			break;
		case DELETE:
			output=deleteOperations(content);
			break;
		case CLEAR:
			output=wipeFile();
			break;
		case DISPLAY:
			output=listMem();
			break;
		case SORT:
			output=sortList();
			break;
		case SEARCH:
			output=searchList(content);
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
	
	private static String sortList(){
		if(!list.isEmpty()){
			Collections.sort(list);
			writeToFile();
			return String.format(MSG_SUCCESS_SORT);
		}
		return String.format(MSG_EMPTY_SORT);
	}
	
	private static String searchList(String input){
		String UserKeyword=new String(input.split(" ")[1].trim());
		int counter=1;
		StringBuffer sb=new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).contains(UserKeyword)){
				sb.append((counter++ +". "+list.get(i)));
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
	
	private static String deleteOperations(String content) {
		String itemDeleted=deleteItemInList(Integer.parseInt(content.split(" ")[1].trim())-1);
		return itemDeleted;
	}
	
	private static void outputMsg (String text){
		System.out.print(text);
	}

	//This function checks if the command entered by the user is supported by the program  
	private static USER_COMMAND determineCommand(String userInputCommand) {
		if (userInputCommand.equalsIgnoreCase(("add"))) {
			return USER_COMMAND.ADD;
		} else if (userInputCommand.equalsIgnoreCase("delete")) {
			return USER_COMMAND.DELETE;
		} else if (userInputCommand.equalsIgnoreCase("clear")) {
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
		else
			return USER_COMMAND.INVALID;
	}

	private static String addLine(String st){
		list.add(st.split(" ")[1].trim());
		writeToFile();
		return String.format(MSG_ADD,fileName,st.trim());
	}

	//clears list before adding contents from txt file into list
	private static void loadList(){
		list.clear();
		Scanner scc;
		try {
			scc = new Scanner(new File(fileName));
			while(scc.hasNext())
				list.add(scc.nextLine());
		} catch (FileNotFoundException e) {
			outputMsg(MSG_NO_FILE_FOUND);
		}
	}

	// If file exits, it will load content into list and print it. Else it displays the txt file is empty
	private static String listMem(){
		//loadList();
		if(!list.isEmpty()){
			return displayList();
		}
		else
			return String.format(MSG_EMPTPY_FILE, fileName);
	}

	private static String displayList() {
		StringBuffer sb=new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			sb.append((i+1 +". "+list.get(i)));
			sb.append(System.getProperty("line.separator"));
		}
		return sb.toString();
	}

	public static String wipeFile(){
		list.clear();
		writeToFile();
		return String.format(MSG_CLEAR, fileName);
	}

	public static String deleteItemInList(int lineIndex){
		loadList();
		if(!list.isEmpty() && lineIndex>=0 && lineIndex<list.size()){
			String toBeDeletedLine=list.get(lineIndex);
			list.remove(lineIndex);
			writeToFile();
			return String.format(MSG_DELETE, fileName, toBeDeletedLine);
		}
		else
			return String.format(MSG_DELETE_LINE_ERROR,lineIndex+1);
	}

	private static void writeToFile(){
		try {
			FileWriter fw=new FileWriter(fileName,false);
			for(String temp:list){
				fw.write(temp+"\n");
				fw.flush();
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public boolean isFileExists(File txtFile){
		if(txtFile.exists()){
			return true;
		}
		return false;
	}

	public void createFile(File txtFile){
		if(!isFileExists(txtFile))
			try {
				txtFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
