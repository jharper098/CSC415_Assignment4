import java.util.Scanner;

//John Skinner
//James Harper
//Cullen Mollette
//CSC415 Assignment 4: Create the Lexer




public class Main {
	Scanner sc;
	int length=0;
	char currentChar;
	
	public static void main(String[] args) {
		
		
		
	}
	
	public void lex(String str) {
		setLength(str);
		sc = new Scanner(str);
		while(sc.hasNext())
		{
			currentChar = sc.next().charAt(0);
			if(checkComment(currentChar)) {
				defineComment(currentChar,sc);
			}else if(checkString(currentChar)) {
				defineString(currentChar,sc);
			}else if(checkNumeric(currentChar)) {
				defineNumeric(currentChar,sc);
			}else if(checkLexeme(currentChar)) {
				defineLexeme(currentChar,sc);
			}else {
				defineIdentifier(currentChar,sc);
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
		throw new UnsupportedOperationException();
	}
	//Only true if the current is a opening string "
	//define string will close the string and print
	private Boolean checkComment(char c) {
		//Look for e char equals method
		if(c == '"') 
			return true;
		else
			return false;
	}
	//Only true if the current is a [
	//Define method will close and print
	private Boolean checkString(char c)
	{
		//check if string
		
		throw new UnsupportedOperationException();
	}
	//Checks if c is equal to 0-9
	private Boolean checkNumeric(char c) {
		
		throw new UnsupportedOperationException();
	}
	
	//Define methods
	//this is where we return the definition string to be printed
	//Will take a char or String and print the definition
	private void defineLexeme(char c, Scanner sc){
		//Return the description of Lexeme
		//Switch statement to decide?
		throw new UnsupportedOperationException();
	}
	//It takes the first " and then loops until the closing "
	//Check each token for " stop and print when found
	private void defineString(char c, Scanner sc)	{
		throw new UnsupportedOperationException();
	}
	//It takes the [ and loops until ]
	//Check each token for [ stop and print when found
	private void defineComment(char c, Scanner sc) {
	}
	//takes the first number, if 0 it prints
	//otherwise it loops until it finds a non number
	//then prints the number as a string NO NEED TO MAKE IT AN INT
	private void defineNumeric(char c, Scanner sc) {
		throw new UnsupportedOperationException();
	}
	//Need to work on the logic for this.
	//It may need to continue to loop through checking for Lexemes
	//Any Ideas?
	private void defineIdentifier(char c, Scanner sc) {
		throw new UnsupportedOperationException();

	}
	

}
