//John Skinner
//James Harper
//Cullen Mollette
//CSC415 Assignment 4: Create the Lexer

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Scanner;

//import sun.tools.jstat.Identifier;

public class Main {
	Scanner sc;
	int length=0;
	String currentString;
	char currentChar;
	char[] lexArray = new char[] {'+', '=','?', '!', '<','>','-',':',')','('};
	char[] identifierArray = new char[] {'a', 'b','c', 'd', 'e','f','g','h','i','j','k','l','m','n',
			'o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J',
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
		System.out.println("-----------------");
		//String Tests
		lt.lex("\"Hello\"");
		lt.lex("\"\"");
		System.out.println("-----------------");
		//Numeric Tests
		lt.lex("0");
		lt.lex("1");
		lt.lex("1203");
		lt.lex("0123");
		//Identifier Test
		lt.lex("msg");
		lt.lex("i");
		lt.lex("blue");
		System.out.println("-----------------");
		//Comment Test
		lt.lex("{hello}");
		lt.lex("{}");
		System.out.println("-----------------");
		//Operator & Separator Tests
		lt.lex("=");
		lt.lex("?");
		lt.lex("(");
		lt.lex("()");
		System.out.println("-----------------");
		//Complex Testing
		lt.lex(" {Example Program #1} msg = \"Hi Mom!\" num=42 if : num ? 42 ( text : msg )");
		System.out.println("-----------------");
		lt.lex("redtextblue");
		lt.lex("red42blue");
		lt.lex("50=52");
		System.out.println("-----------------");
		lt.lex("12ab3c!@%#");
	}
	
	public void lex(String str) {
		
		sc = new Scanner(str);
		while(sc.hasNext())
		{
			currentString = sc.next();
			
			for(int i = 0; i<currentString.length();i++){
				currentChar = currentString.charAt(i);
				if(checkLexeme(currentChar)) {
					defineLexeme(currentChar);
				}else if(checkComment(currentChar)) {
					defineComment(currentString,sc); 
					break;
				}else if(checkString(currentChar)) {
					defineString(currentString,sc);
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
		//throw new UnsupportedOperationException();
	}
	
	private void print(char output, String descriptor) {
		System.out.printf("%-10s %-10s\n", output, descriptor);
		//throw new UnsupportedOperationException();

	}
	private void print(String output, String descriptor) {
		if(!output.equals(""))
		{
			System.out.printf("%-10s %-10s\n", output, descriptor);
		}
		
		//throw new UnsupportedOperationException();
	}
	
	//Check Methods
	//Would rather put the logic here and not in the lex file
	//Check set just determines which define method we use
	
	private Boolean checkLexeme(char c){
		//Check if c is equal to any of the lexeme
		return containsChar(lexArray,c);
		//throw new UnsupportedOperationException();
	}
	//Only true if the current is a opening string "
	//define string will close the string and print
	private Boolean checkComment(char c) {
		//Look for e char equals method
		return c == '{';
	}
	//Only true if the current is a [
	//Define method will close and print
	private Boolean checkString(char c)
	{
		//check if string
		return c == '\"';
		//throw new UnsupportedOperationException();
	}
	//Checks if c is equal to 0-9
	private Boolean checkNumeric(char c) {
		return containsChar(numericArray, c);
		//throw new UnsupportedOperationException();
	}
	
	//Define methods
	//this is where we return the definition string to be printed
	//Will take a char or String and print the definition
	private void defineKeyword(String s)
	{
		print(s,"Keyword");
		//May not need this method
			
		
	}
	private void defineLexeme(char c){
		//Return the description of Lexeme
		//Switch statement to decide?
		switch(c)
		{
			case '+': print(c,"Add/Concatenate");
			break;
			case '=': print(c,"Assignment");
			break;
			case '?': print(c,"Is Equal To");
			break;
			case '!': print(c,"Is Not Equal To");
			break;
			case '<': print(c,"Less Than");
			break;
			case '>': print(c,"Greater Than");
			break;
			case '-': print(c,"Negation");
			break;
			case ':': print(c,"Colon");
			break;
			case '(': print(c,"Open Parenthesis");
			break;
			case ')': print(c,"Close Parenthesis");
			break;
			default: print(c,"**Error: Invalid Character**");
		
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
		//System.out.println(s + " S Test");
		//Checking s and building a string
		//Starts at 1 because we already have the first \"
		for(int i=1; i<s.length(); i++)
		{
			//checks for final quote
			 if(s.charAt(i) == '\"')
			 {
				 strBuild.append("\"");
				 print(strBuild.toString(), "String Literal");
				 endStringFound = true;
			 }
			 else
			 {
				 strBuild.append(s.charAt(i));
				
			 }
			
		}
		while(!endStringFound)
		{
			strBuild.append(" ");
			nextString = sc.next();
			//System.out.println(sc.next() + " S Test");
			for(int i=0; i<nextString.length(); i++)
			{
				 if(nextString.charAt(i) == '\"')
				 {
					 strBuild.append("\"");
					 print(strBuild.toString(), "String Literal");
					 endStringFound = true;
				 }
				 else
				 {
					 strBuild.append(nextString.charAt(i));
					
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
			commentBuild.append(" ");
			nextString = sc.next();
			for(int i=0; i<nextString.length(); i++){
				 if(nextString.charAt(i) == '}') {
					 commentBuild.append("}");
					 print(commentBuild.toString(), "Comment");
					 endCommentFound = true;
				 }else{
					 commentBuild.append(nextString.charAt(i));							
				 }
			}					
		}
		//throw new UnsupportedOperationException();
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
				if(s.charAt(i) == '0' && strBuild.toString().equals("")) {
					strBuild.append(s.charAt(i));
					print(strBuild.toString(),"Numeric literal");
					//Clearing in case we need to make multiple numeric literals in one go
					strBuild = new StringBuilder();
				}else{
					strBuild.append(s.charAt(i));
				}
			}else
			{
				if(!strBuild.toString().equals(""))
				{
					print(strBuild.toString(), "Numeric Literal");
				}
				strBuild = new StringBuilder();
				defineIdentifier(s.substring(i));
				identifierfound = true;
				break;
			}
		}
		if(!strBuild.toString().equals("")&&!identifierfound)
		{
			print(strBuild.toString(), "Numeric Literal");
		}
		
		
		//throw new UnsupportedOperationException();
	}
	
	//Need to work on the logic for this.
	//It may need to continue to loop through checking for Lexemes
	//Any Ideas?
	private void defineIdentifier(String s) {
		//Will only check if full string is a keyword
		//Still need to check within the class
		//System.out.println(s);
		if(containsString(keywordArray, s))
		{
			defineKeyword(s);
		}else{
			boolean valid = false;
			StringBuilder identifierBuild = new StringBuilder("");
			for(int i=0; i<s.length();i++)
			{
				valid = false;
				if(containsChar(lexArray,s.charAt(i)))
				{
					if(containsString(keywordArray, identifierBuild.toString())){
						defineKeyword(identifierBuild.toString());
					}else{
						if(!identifierBuild.toString().equals(""))
						{
							print(identifierBuild.toString(), "Identifier");
						}
						
					}
					defineLexeme(s.charAt(i));
					identifierBuild = new StringBuilder(s.charAt(i+1));//Need to Test logic
				}else if(!containsChar(identifierArray,s.charAt(i)) 
				&& !containsChar(numericArray, s.charAt(i)))
				{
					identifierBuild.append(s.charAt(i));
					print(identifierBuild.toString(), "**Error: Ivalid Character**");
					identifierBuild = new StringBuilder();
					
				}else if(containsString(keywordArray,identifierBuild.toString()))
				{
					
					defineKeyword(identifierBuild.toString());
					identifierBuild = new StringBuilder("");
				}else if(containsChar(numericArray, s.charAt(i)))
				{
					int x = i;
					
					if(containsString(keywordArray, identifierBuild.toString())){
						defineKeyword(identifierBuild.toString());
					}else{
						if(!identifierBuild.toString().equals(""))
						{
							print(identifierBuild.toString(), "Identifier");
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
						print(identifierBuild.toString(), "Numeric Literal");
					}
					identifierBuild = new StringBuilder();
					i = x-1;
					
				}else{
					valid = true;
					identifierBuild.append(s.charAt(i));
				}
				
			}
			
			if(valid){
				if(containsString(keywordArray, identifierBuild.toString())){
					defineKeyword(identifierBuild.toString());
				}else{
					if(!identifierBuild.toString().equals(""))
					{
						print(identifierBuild.toString(), "Identifier");
					}	
				}
				
			}
				
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
