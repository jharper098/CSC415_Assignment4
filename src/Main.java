import java.util.Scanner;

//John Skinner
//James Harper
//Cullen Mollette
//CSC415 Assignment 4: Create the Lexer




public class Main {
	Scanner sc;
	int length=0;
	String currentString;
	char currentChar;
	char[] lexArray = new char[] {'+', '=','?', '!', '<','>','-',':',')','('};
	public static void main(String[] args) {
		//Used for initial testing
		
	
		
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
		//Print formatted version
		throw new UnsupportedOperationException();

	}
	private void print(String output, String descriptor) {
		//Print formatted version
		throw new UnsupportedOperationException();
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
		if(c == '[') 
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
		
		
		
		
		throw new UnsupportedOperationException();
	}
	//It takes the [ and loops until ]
	//Check each token for [ stop and print when found
	private void defineComment(String s, Scanner sc) {
		
		throw new UnsupportedOperationException();
	}
	//takes the first number, if 0 it prints
	//otherwise it loops until it finds a non number
	//then prints the number as a string NO NEED TO MAKE IT AN INT
	private void defineNumeric(String s, Scanner sc) {
		if(s == "0") {
			print(s,"numeric literal");
		}else {
			
		}
		throw new UnsupportedOperationException();
	}
	//Need to work on the logic for this.
	//It may need to continue to loop through checking for Lexemes
	//Any Ideas?
	private void defineIdentifier(String s, Scanner sc) {
		throw new UnsupportedOperationException();

	}
	

}
