//John Skinner
//James Harper
//Cullen Mollette
//CSC415 Assignment 4: Create the Lexer

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
	Scanner sc;
	int length=0;
	String currentString;
	char currentChar;
	char[] lexArray = new char[] {'+', '=','?', '!', '<','>','-',':',')','('};
	char[] identifierArray = new char[] {'a', 'b','c', 'd', 'e','f','g','h','i','j','k','l','m','n',
			'o','p','q','r','s','t','u','v','w','x','y','A','B','C','D','E','F','G','H','I','J',
			'K','L','M','N' ,'O','P','Q','R','S','T','U','V','W','X','Y','Z','_'};
	char[] numericArray = new char[] {'0','1','2','3','4','5','6','7','8','9'};
	String[] keywordArray = new String[] {"text", "if","loop"};
	public static void main(String[] args) {
		//Used for initial testing
		Main lt = new Main();
		
		//Keyword Test
		lt.lex("text");
		lt.lex("if");
		lt.lex("loop");
		
		//String Tests
		lt.lex("\"Hello\"");
		lt.lex("\"\"");
		
		//Numeric Tests
		lt.lex("0");
		lt.lex("1");
		lt.lex("123");
		lt.lex("0123");
		//Identifier Test
		//to be implemented
		
		//Comment Test
		lt.lex("{hello}");
		lt.lex("{}");

		//Operator & Separator Tests
		lt.lex("=");
		lt.lex("?");
		lt.lex("(");
		lt.lex("()");
		System.out.println("-----------------");
		//Complex Testing
		lt.lex(" {Example Program #1} msg = \"Hi!\" num = 42 if : num ? 42 ( text : msg )");
	}
	
	public void lex(String str) {
		setLength(str);
		sc = new Scanner(str);
		while(sc.hasNext())
		{
			currentString = sc.next();
			currentChar = currentString.charAt(0);
			if(checkLexeme(currentChar)) {
				defineLexeme(currentChar,sc);
			}else if(checkComment(currentChar)) {
				defineComment(currentString,sc); 
			}else if(checkString(currentChar)) {
				defineString(currentString,sc);
			}else if(checkNumeric(currentChar)) {
				defineNumeric(currentString,sc);
			}else {
				defineIdentifier(currentString,sc);
			}
		
		}
		
		//throw new UnsupportedOperationException();
	}
	private void setLength(String str){
		sc = new Scanner(str);
		while(sc.hasNext())
		{
			sc.next();
			length++;
		}
		
		//throw new UnsupportedOperationException();
	}
	private void print(char lexeme, String descriptor) {
		System.out.printf("%-10s %-10s\n", lexeme, descriptor);
		//throw new UnsupportedOperationException();

	}
	private void print(String output, String descriptor) {
		System.out.printf("%-10s %-10s\n", output, descriptor);
		//throw new UnsupportedOperationException();
	}
	
	//Check Methods
	//Would rather put the logic here and not in the lex file
	//Check set just determines which define method we use
	
	private Boolean checkLexeme(char c){
		//Check if c is equal to any of the lexeme
		
		for(int i=0; i<lexArray.length; i++)
		{
			if(c == lexArray[i])
			{
				return true;
			}
		}
		return false;
		//throw new UnsupportedOperationException();
	}
	//Only true if the current is a opening string "
	//define string will close the string and print
	private Boolean checkComment(char c) {
		//Look for e char equals method
		if(c == '{') 
			return true;
		else
			return false;
	}
	//Only true if the current is a [
	//Define method will close and print
	private Boolean checkString(char c)
	{
		//check if string
		if(c == '\"') 
			return true;
		else
			return false;
		//throw new UnsupportedOperationException();
	}
	//Checks if c is equal to 0-9
	private Boolean checkNumeric(char c) {
		return Character.isDigit(c);
		//throw new UnsupportedOperationException();
	}
	
	//Define methods
	//this is where we return the definition string to be printed
	//Will take a char or String and print the definition
	private void defineKeyword(String s, Scanner sc)
	{
		throw new UnsupportedOperationException();
	}
	private void defineLexeme(char c, Scanner sc){
		//Return the description of Lexeme
		//Switch statement to decide?
		switch(c)
		{
			case '+': print(c,"add/concatenate");
			break;
			case '=': print(c,"assignment");
			break;
			case '?': print(c,"is equal to");
			break;
			case '!': print(c,"is not equal to");
			break;
			case '<': print(c,"less than");
			break;
			case '>': print(c,"greater than");
			break;
			case '-': print(c,"negation");
			break;
			case ':': print(c,"colon");
			break;
			case '(': print(c,"open parenthesis");
			break;
			case ')': print(c,"close parenthesis");
			break;
			default: print(c,"**Error**");
		
		}
		
		//throw new UnsupportedOperationException();
	}
	//It takes the first " and then loops until the closing "
	//Check each token for " stop and print when found
	private void defineString(String s, Scanner sc)	{
		//Starts with the quotes in it
		StringBuilder strBuild = new StringBuilder("\"");
		boolean endStringFound = false;
		String nextString;
		//Checking s and building a string
		//Starts at 1 because we already have the first \"
		for(int i=1; i<s.length(); i++)
		{
			//checks for final quote
			 if(s.charAt(i) == '\"')
			 {
				 strBuild.append("\"");
				 print(strBuild.toString(), "string literal");
				 endStringFound = true;
			 }
			 else
			 {
				 strBuild.append(s.charAt(i));
				
			 }
			
		}
		while(!endStringFound)
		{
			nextString = sc.next();
			for(int i=0; i<nextString.length(); i++)
			{
				 if(nextString.charAt(i) == '\"')
				 {
					 strBuild.append("\"");
					 print(strBuild.toString(), "string literal");
					 endStringFound = true;
				 }
				 else
				 {
					 strBuild.append(s.charAt(i));
					
				 }
				
			}
			
		}
		
		
		
		//throw new UnsupportedOperationException();
	}
	//It takes the [ and loops until ]
	//Check each token for [ stop and print when found
	private void defineComment(String s, Scanner sc) {
		//Starts with the quotes in it
		StringBuilder commentBuild = new StringBuilder("{");
		boolean endCommentFound = false;
		String nextString;
		//Checking s and building a string
		//Starts at 1 because we already have the first \"
		for(int i=1; i<s.length(); i++){
			//checks for final comment
			 if(s.charAt(i) == '}'){
				 commentBuild.append("}");
				 print(commentBuild.toString(), "Comment");
				 endCommentFound = true;
			 }else{
				 commentBuild.append(s.charAt(i));
			 }
		}
		while(!endCommentFound){
			nextString = sc.next();
			for(int i=0; i<nextString.length(); i++){
				 if(nextString.charAt(i) == '}') {
					 commentBuild.append("}");
					 print(commentBuild.toString(), "String");
					 endCommentFound = true;
				 }else{
					 commentBuild.append(s.charAt(i));							
				 }
			}					
		}
		//throw new UnsupportedOperationException();
	}
	//takes the first number, if 0 it prints
	//otherwise it loops until it finds a non number
	//then prints the number as a string NO NEED TO MAKE IT AN INT
	private void defineNumeric(String s, Scanner sc) {
		StringBuilder strBuild = new StringBuilder();
		for(int i=0; i<s.length();i++)
		{
			if(containsChar(numericArray,s.charAt(i)))
			{
				if(s.charAt(i) == '0') {
					strBuild.append(s.charAt(i));
					print(strBuild.toString(),"numeric literal");
					//Clearing in case we need to make multiple numeric literals in one go
					strBuild = new StringBuilder();
				}else{
					strBuild.append(s.charAt(i));
				}
			}
			else
			{
				throw new InvalidParameterException("A NON NUMBER IN MY NUMERIC LITERAL??");
			}
		}
		//if(s.charAt(0) != '0')
		//{
		print(strBuild.toString(), "numeric Literal");
		//}
		
		
		//throw new UnsupportedOperationException();
	}
	
	//Need to work on the logic for this.
	//It may need to continue to loop through checking for Lexemes
	//Any Ideas?
	private void defineIdentifier(String s, Scanner sc) {
		//Will only check if full string is a keyword
		//Still need to check within the class
		if(containsString(keywordArray, s))
		{
			defineKeyword(s,sc);
		}
		
		
		//throw new UnsupportedOperationException();

	}
	//Could probably use a undefined type here but w/e
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
            if(index == c){
                result = true;
                break;
            }
        }

        return result;
    }
	

}
