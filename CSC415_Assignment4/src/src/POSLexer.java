//John Skinner
//Cullen Mollette
//James Harper
//CSC415 Assignment 4: Create the Lexer
//If you would like to see the github for this project I can add you as a collaborator

import java.io.File;
import java.io.FileNotFoundException;
import java.security.InvalidParameterException;
import java.util.Scanner;

public class POSLexer {
	Scanner sc;
	int length=0;
	char[] lexArray = new char[] {'+', '=','?', '!', '<','>','-',':','(',')'};
	char[] identifierArray = new char[] {'a','b','c','d','e','f','g','h','i','j','k','l','m','n',
			'o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J',
			'K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','_'};
	char[] numericArray = new char[] {'0','1','2','3','4','5','6','7','8','9'};
	String[] keywordArray = new String[] { "text", "if", "loop" };
	public static void main(String[] args) throws FileNotFoundException {
		POSLexer lt = new POSLexer();
		StringBuilder lexArgs = new StringBuilder();
		//Checks if the args are empty if so runs test()
		if(args.length != 0){
			//checking if calling for the lex function or a filename
			for(int x = 0; x<3; x++)
			{
				lexArgs.append(args[0].charAt(x));
			}
			if(lexArgs.toString().equals("lex"))
			{	
				//Auto Excludes the surrounding string delimiters
				lt.lex();
			}else{
				//Not empy arg but also not lex means filename. 
				//main throws FileNotFound for screwups
				String filename;
				StringBuilder input=new StringBuilder();
				for(String s : args){
					input.append(s);
					input.append(" ");
				}
				filename = input.toString();
				System.out.println(filename);
				input = new StringBuilder();
				Scanner scanner = new Scanner(new File(filename));
				
				scanner.useDelimiter(" ");
				while(scanner.hasNext()){
					input.append(scanner.next());
					input.append(" ");
				}
				lt.setScanner(scanner);
				scanner.close();
				
			}
				
		}else{
			//Enable to run tests and run the program with no arguments
			lt.test();	
		}
		
		
	}
	public void setScanner(Scanner inputScanner)
	{
		this.sc = inputScanner;
	}
	//Lex function Takes a string of any size and returns the lexical analysis
	//Needs to be able to be called 
	public void lex() {
		String currentString;
		char currentChar;
		String str = sc.next();
		//Formatting
		str = str.replaceAll("\"", "\\\"");
		str = str.replace("\n", " ");
		//Starting the loop
		sc = new Scanner(str);
		//Checks for each kind of result and returns the correct definition
		currentString = sc.next();			
		for(int i = 0; i<currentString.length();i++){
			currentChar = currentString.charAt(i);					
			if(checkLexeme(currentChar)) {
				defineLexeme(currentChar);
			}else if(checkComment(currentChar)) {
				defineComment(currentString); 
				break;
			}else if(checkString(currentChar)) {
				defineString(currentString);
				break;
			}else if(checkNumeric(currentChar)) {
				defineNumeric(currentString);
				break;
			}else {
				defineIdentifier(currentString);
				break;
			}
			
		}
		
		
	}
	public void lex(String str) {
		String currentString;
		char currentChar;
		//String str = sc.next();
		//Formatting
		str = str.replaceAll("\"", "\\\"");
		str = str.replace("\n", " ");
		//Starting the loop
		sc = new Scanner(str);
		//Checks for each kind of result and returns the correct definition
		currentString = sc.next();			
		for(int i = 0; i<currentString.length();i++){
			currentChar = currentString.charAt(i);					
			if(checkLexeme(currentChar)) {
				defineLexeme(currentChar);
			}else if(checkComment(currentChar)) {
				defineComment(currentString); 
				break;
			}else if(checkString(currentChar)) {
				defineString(currentString);
				break;
			}else if(checkNumeric(currentChar)) {
				defineNumeric(currentString);
				break;
			}else {
				defineIdentifier(currentString);
				break;
			}
			
		}
		
		
	}
	
	
	//Define methods
	//this is where we return the definition string to be printed
	//Will take a char or String and print the definition
	private void defineKeyword(String s)
	{
		printString(s,"Keyword");
	}
	//Return the description of Lexeme
	//Switch statement to decide
	private void defineLexeme(char c){
		
		switch(c){
			case '+': printChar(c,"Add/Concatenate");
			break;
			case '=': printChar(c,"Assignment");
			break;
			case '?': printChar(c,"Is Equal To");
			break;
			case '!': printChar(c,"Is Not Equal To");
			break;
			case '<': printChar(c,"Less Than");
			break;
			case '>': printChar(c,"Greater Than");
			break;
			case '-': printChar(c,"Negation");
			break;
			case ':': printChar(c,"Colon");
			break;
			case '(': printChar(c,"Open Parenthesis");
			break;
			case ')': printChar(c,"Close Parenthesis");
			break;
			default: printChar(c,"**Error: Invalid Character**");
		}
		
	}
	//It takes the first " and then loops until the closing "
	//Check each token for " stop and print when found
	private void defineString(String s)	{
		StringBuilder strBuild = new StringBuilder("\"");
		boolean endStringFound = false;
		String nextString;
		for(int i=1; i<s.length(); i++){
			//checks for final quote
			if(s.charAt(i) == '\"'){
				strBuild.append("\"");
				printString(strBuild.toString(), "String Literal");
				endStringFound = true;
			}
			else{
				strBuild.append(s.charAt(i));
			}
		}
		while(!endStringFound){
			if(sc.hasNext()){
				//Checks the rest of the file for the end "
				strBuild.append(" ");
				nextString = sc.next();
				for(int i=0; i<nextString.length(); i++){
					if(nextString.charAt(i) == '\"'){
						strBuild.append("\"");
						printString(strBuild.toString(), "String Literal");
						endStringFound = true;
					}else{
						strBuild.append(nextString.charAt(i));
					}	
				}
			}else{
				throw new InvalidParameterException("String Literal Never Closed");
			}
			
			
		}
	}
	//It takes the { and loops until }
	//Check each token for { stop and print when found
	private void defineComment(String s) {
		StringBuilder commentBuild = new StringBuilder("");
		boolean endCommentFound = false;
		String nextString;
		//Checking s and building a string
		//Starts at 1 because we already have the first \"
		for(int i=0; i<s.length(); i++){
			//checks for final comment
			 if(s.charAt(i) == '}'){
				 commentBuild.append("}");
				 printString(commentBuild.toString(), "Comment");
				 endCommentFound = true;
			 }else{
				 commentBuild.append(s.charAt(i));
			 }
		}
		while(!endCommentFound){
			commentBuild.append(" ");
			nextString = sc.next();
			for(int i=0; i<nextString.length(); i++){
				 if(nextString.charAt(i) == '}') {
					 commentBuild.append("}");
					 printString(commentBuild.toString(), "Comment");
					 endCommentFound = true;
				 }else{
					 commentBuild.append(nextString.charAt(i));							
				 }
			}					
		}
	}
	//takes the first number, if 0 it prints
	//otherwise it loops until it finds a non number
	//then prints the number as a string NO NEED TO MAKE IT AN INT
	private void defineNumeric(String s) {
		StringBuilder strBuild = new StringBuilder();
		boolean identifierfound = false;
		for(int i=0; i<s.length();i++)
		{
			if(containsChar(numericArray,s.charAt(i)))
			{
				//Catches the 0 case
				if(s.charAt(i) == '0' && strBuild.toString().equals("")) {
					strBuild.append(s.charAt(i));
					printString(strBuild.toString(),"Numeric literal");
					//Clearing in case we need to make multiple numeric literals in one go
					strBuild = new StringBuilder();
				}else{
					strBuild.append(s.charAt(i));
				}
			}else
			{ //Valid for all non leading 0 cases
				if(!strBuild.toString().equals(""))
				{
					printString(strBuild.toString(), "Numeric Literal");
				}
				strBuild = new StringBuilder();
				defineIdentifier(s.substring(i));
				identifierfound = true;
				break;
			}
		}
		if(!strBuild.toString().equals("")&&!identifierfound)
		{
			printString(strBuild.toString(), "Numeric Literal");
		}
	}
	
	//Need to work on the logic for this.
	//It may need to continue to loop through checking for Lexemes
	//Any Ideas?
	private void defineIdentifier(String s) {
		//Will only check if full string is a keyword
		//Still need to check within the class
		if(containsString(keywordArray, s))
		{
			defineKeyword(s);
		}else{
			boolean valid = false;
			StringBuilder identifierBuild = new StringBuilder("");
			for(int i=0; i<s.length();i++)
			{
				valid = false;
				//Makes sure first element is not a lexeme
				if(containsChar(lexArray,s.charAt(i)))
				{
					if(containsString(keywordArray, identifierBuild.toString())){
						defineKeyword(identifierBuild.toString());
					}else{
						if(!identifierBuild.toString().equals(""))
						{
							printString(identifierBuild.toString(), "Identifier");
						}
						
					}
					defineLexeme(s.charAt(i));
					identifierBuild = new StringBuilder();//Need to Test logic
				}else if(checkComment(s.charAt(i))){
					int x = i+1;
					//Check if keyword or identifier and move on
					if(containsString(keywordArray, identifierBuild.toString())){
						defineKeyword(identifierBuild.toString());
					}else{
						if(!identifierBuild.toString().equals(""))
						{
							printString(identifierBuild.toString(), "Identifier");
						}	
					}
					//Loops much like defineComment but keeps track of length
					//X updates i so the program can continue without interruption
					identifierBuild = new StringBuilder();
					identifierBuild.append("{");
					while(x<s.length() && s.charAt(x) != ('}'))
					{
						identifierBuild.append(s.charAt(x));
						x++;
					}
					if(!identifierBuild.toString().equals(""))
					{
						identifierBuild.append("}");
						printString(identifierBuild.toString(), "Comment");
					}
					identifierBuild = new StringBuilder();
					i = x;
					
				}else if(checkString(s.charAt(i))){
					int x = i+1;
					//Check if keyword or identifier and move on
					if(containsString(keywordArray, identifierBuild.toString())){
						defineKeyword(identifierBuild.toString());
					}else{
						if(!identifierBuild.toString().equals(""))
						{
							printString(identifierBuild.toString(), "Identifier");
						}	
					}
					//Loops much like defineComment but keeps track of length
					//X updates i so the program can continue without interruption
					identifierBuild = new StringBuilder();
					identifierBuild.append("\"");
					while(x<s.length() && s.charAt(x) != ('\"'))
					{
						identifierBuild.append(s.charAt(x));
						x++;
					}
					if(!identifierBuild.toString().equals(""))
					{
						identifierBuild.append("\"");
						printString(identifierBuild.toString(), "String Literal");
					}
					identifierBuild = new StringBuilder();
					i = x;				
				}else if(containsChar(numericArray, s.charAt(i))){
					//checks for number
					int x = i;
					
					if(containsString(keywordArray, identifierBuild.toString())){
						defineKeyword(identifierBuild.toString());
					}else{
						if(!identifierBuild.toString().equals(""))
						{
							printString(identifierBuild.toString(), "Identifier");
						}	
					}
					identifierBuild = new StringBuilder();
					while(x<s.length() && containsChar(numericArray, s.charAt(x)) )
					{
						identifierBuild.append(s.charAt(x));
						x++;
					}
					if(!identifierBuild.toString().equals(""))
					{
						printString(identifierBuild.toString(), "Numeric Literal");
					}
					identifierBuild = new StringBuilder();
					i = x-1;
					
				}//checks for invalid characters that
				else if(!containsChar(identifierArray,s.charAt(i)))
				{
					defineIdentifier(identifierBuild.toString());
					printChar(s.charAt(i), "***Error: Invalid Character***");
					identifierBuild = new StringBuilder();
					
				}else{
					valid = true;
					identifierBuild.append(s.charAt(i));
				}
				
			} //If all tests above pass it gets a valid identifier and will print it
			if(valid){
				if(!identifierBuild.toString().equals(""))
				{
					printString(identifierBuild.toString(), "Identifier");
				}	
				
				
			}
				
		}

	}
	//Prints in desired format, Template changes happen here
	private void printChar(char output, String descriptor) {
		System.out.printf("%-30s %-10s\n", output, descriptor);
		
	}
	private void printString(String output, String descriptor) {
		if(!output.equals(""))
		{
			System.out.printf("%-30s %-10s\n", output, descriptor);
		}
		
		
	}
	//Check Methods
	//Would rather put the logic here and not in the lex file
	private Boolean checkLexeme(char c){
		return containsChar(lexArray,c);
		
	}
	//Only true if the current is a opening string "
	//define string will close the string and print
	private Boolean checkComment(char c) {
		return c == '{';
	}
	//Only true if the current is a [
	//Define method will close and print
	private Boolean checkString(char c)
	{
		//check if string
		return c == '\"';
		
	}
	//Checks if c is equal to 0-9
	private Boolean checkNumeric(char c) {
		return containsChar(numericArray, c);
	}
	//More Helper Methods
	private boolean containsChar(char[] array, char c)
	{
		boolean result = false;

        for(char index : array){
            if(index == c){
                result = true;
                break;
            }
        }

        return result;
    }
	private boolean containsString(String[] array, String c)
	{
		boolean result = false;

        for(String index : array){
            if(index.equals(c)){
                result = true;
                break;
            }
        }

        return result;
    }
	public void test(){
		//Used for initial testing
		POSLexer lt = new POSLexer();	
		//Keyword Test
		lt.lex("text");
		lt.lex("if");
		lt.lex("loop");
		System.out.println("-----------------");
		//String Tests
		lt.lex("\"Hello\"");
		//lt.lex("\"");
		lt.lex("\" Hello @#$ {goodbye}\"");
		System.out.println("-----------------");
		//Numeric Tests
		lt.lex("0");
		lt.lex("9");
		lt.lex("1203");
		lt.lex("0123");
		//Identifier Test
		System.out.println("-----------------");
		lt.lex("msg");
		lt.lex("i");
		lt.lex("blue");
		System.out.println("-----------------");
		//Comment Test
		lt.lex("{hello}");
		lt.lex("{}");
		lt.lex("{he{{llo}");
		System.out.println("-----------------");
		//Operator & Separator Tests
		lt.lex("+");
		lt.lex("=");
		lt.lex("?");
		lt.lex("!");
		lt.lex("<");
		lt.lex(">");
		lt.lex("-");
		lt.lex(":");
		lt.lex("(");
		lt.lex(")");
		lt.lex("(+");
		System.out.println("-----------------");
		//Complex Testing
		lt.lex(" {Example Program #1} msg = \"Hi Mom!\" num=42 if : num ? 42 ( text : msg )");
		System.out.println("-----------------");
		lt.lex("redtextbl{purple}ue");
		lt.lex("r\"Blabakadede\"ed42blue");
		lt.lex("50=52");
		System.out.println("-----------------");
		lt.lex("1@2ab3c!@%#");
		lt.lex("*1@2ab3c!@%#");
	}

}
