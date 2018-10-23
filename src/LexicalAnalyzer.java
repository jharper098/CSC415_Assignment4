
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

public class LexicalAnalyzer {
    List<Token> validTokens;
    //add all of the valid tokens to the list
    TokenValidator validator = new TokenValidator(validTokens);
    List<Token> outputList;
    private String operator;
    private String lexeme;
    
    
    public void analyze(String file){
        int fileLength = 23; //this will represent the end of the file
        
        try
        {
            
            BufferedReader reader = new BufferedReader(new FileReader(file + ".pos"));
            
            
            while(reader.readLine() != null){
                String currentLine = reader.readLine();
                    
                for(int charAtOnCurrentLine = 0; charAtOnCurrentLine < currentLine.length();charAtOnCurrentLine++){
                    
                    
                    switch(currentLine.charAt(charAtOnCurrentLine)){
                        case't':
                            charAtOnCurrentLine++;
                            if(currentLine.charAt(charAtOnCurrentLine) == "e".charAt(0)){
                                charAtOnCurrentLine++;
                                if(currentLine.charAt(charAtOnCurrentLine) == "x".charAt(0)){
                                    charAtOnCurrentLine++;
                                    if(currentLine.charAt(charAtOnCurrentLine) == "t".charAt(0)){
                                        validTokens.add(new Token("text", "TEXT KEYWORD"));
                                        break;
                                    }
                                    break;
                                }
                                break;
                            }
                            break;
                        case'i':
                            charAtOnCurrentLine++;
                            if(currentLine.charAt(charAtOnCurrentLine) == "f".charAt(0)){
                                validTokens.add(new Token("if", "IF KEYWORD"));
                                break;
                            }
                            break;
                        case'l':
                            charAtOnCurrentLine++;
                            if(currentLine.charAt(charAtOnCurrentLine) == "o".charAt(0)){
                                charAtOnCurrentLine++;
                                if(currentLine.charAt(charAtOnCurrentLine) == "o".charAt(0)){
                                    charAtOnCurrentLine++;
                                    if(currentLine.charAt(charAtOnCurrentLine) == "p".charAt(0)){
                                        validTokens.add(new Token("loop", "LOOP KEYWORD"));
                                        break;
                                    }
                                    break;
                                }
                                break;
                            }
                            break;
                            
                        //maybe separate this?
                        case'+':
                            validTokens.add(new Token("+", "ADD/CONCATENATE"));
                            break;
                        case'=':
                            validTokens.add(new Token("=", "ASSIGNMENT"));
                            break;
                        case'?':
                            validTokens.add(new Token("?", "IS EQUAL TO"));
                            break;
                        case'!':
                            validTokens.add(new Token("!", "IS NOT EQUAL TO"));
                            break;
                        case'<':
                            validTokens.add(new Token("<", "LESS THAN"));
                            break;
                        case'>':
                            validTokens.add(new Token(">", "GREATER THAN"));
                            break;
                        case'-':
                            validTokens.add(new Token("-", "NEGATION"));
                            break;
                        case':':
                            validTokens.add(new Token(":", "COLON"));
                            break;
                        case'(':
                            validTokens.add(new Token("(", "OPEN PARENTHESIS"));
                            break;
                        case')':
                            validTokens.add(new Token(")", "CLOSE PARENTHESIS"));
                            break;
                        case'{':
                            String commentString = "{";
                            
                            //loops until end of file OR finds the end curly brace
                            while(currentLine!=null && commentString.charAt(commentString.length()-1) != "}".charAt(0)){
                                //if end curly Brace is found, the index returned is where we are continuing to scan each lexeme.
                                //index = 0 if end curly brace isn't found.
                                charAtOnCurrentLine = findEndCurlyBrace(currentLine, charAtOnCurrentLine, commentString);
                                if(charAtOnCurrentLine == 0){
                                    currentLine = reader.readLine();
                                }
                            }
                            
                            //if it reaches end of file without finding the end curly brace, it's an error.
                            if(commentString.charAt(commentString.length()-1) != "}".charAt(0)){
                                    validTokens.add(new Token(commentString, "///ERROR///"));
                                    break;
                            } 
                            
                            break;
                        case'"':
                            String insideTheQuotes = "\"";
                            
                            while(currentLine!=null && insideTheQuotes.charAt(insideTheQuotes.length()-1) != "\"".charAt(0) || insideTheQuotes.length() == 1){
                                //if end quote is found, the index returned is where we are continuing to scan each lexeme.
                                //index = 0 if end quote isn't found.
                                charAtOnCurrentLine = findEndQuote(currentLine, charAtOnCurrentLine, insideTheQuotes);
                                if(charAtOnCurrentLine == 0){
                                    currentLine = reader.readLine();
                                }
                            }
                            
                            //if it reaches end of file without finding the end quote, it's an error.
                            if(insideTheQuotes.charAt(insideTheQuotes.length()-1) != "\"".charAt(0)){
                                    validTokens.add(new Token(insideTheQuotes, "///ERROR///"));
                                    break;
                            } 
                        default :
                            String errorMessage = "";
                            while (!checkForCharacter(currentLine, charAtOnCurrentLine)){
                                errorMessage = errorMessage + currentLine.substring(charAtOnCurrentLine, charAtOnCurrentLine);
                                charAtOnCurrentLine++;
                                if(checkForCharacter(currentLine, charAtOnCurrentLine)){
                                    validTokens.add(new Token(errorMessage, "///ERROR///"));
                                    break;
                                }
                            }
                            
                            
                        
                    }
                    //we could skip validation.
                    /*Token t = lex();    //ASK MATT: can we pass parameters to lex
                    if (validator.isValid(t))
                    {
                        outputList.add(t);//add s to the output array
                    } else {
                        //display an error of some kind
                        System.out.println("///ERROR///");
                    }*/
                }
            }
        } 
        catch (Exception e)
        {
            System.err.format("File " + file + " was not found");
            e.printStackTrace();
        }
        
        for (int n = 0; n < fileLength; n++){
            Token t = lex();    //ASK MATT: can we pass parameters to lex
            if (validator.isValid(t))
            {
                outputList.add(t);//add s to the output array
            } else {
                //display an error of some kind
            }
        }
    }
    
    public Token lex(){
        //the string from the file could be read in and assigned to a string in this class. Could use a stack/queue type thing where we "lex" one token, then take it off the stack
        //that way we can avoid using a counter in our loops
        //returns the lexeme concatenated to the operator for the current position in the string/stream/file/whatever
        return new Token(lexeme, operator);
    }
    
    public void printOutputList(){
        for (Token t : outputList){
            System.out.println(t.lexeme + " " + t.operator);
        }
        //loops through the output array and prints to console; could also return a string
    }
    
    public int findEndCurlyBrace(String currentLine, int i, String commentString){
        
        for(int j = i; j < currentLine.length(); ++j){
            if(currentLine.charAt(j) == "}".charAt(0)){
                commentString = commentString + "}";
                validTokens.add(new Token(commentString, "COMMENT"));
                return j;
            } else {
                commentString = commentString + currentLine.charAt(j);
                return 0;
            }
        }
        return 0;
        
    }
    
    public int findEndQuote(String currentLine, int i, String insideTheQuotes){
        
        for(int j = i; j < currentLine.length(); ++j){
            if(currentLine.charAt(j) == "}".charAt(0)){
                insideTheQuotes = insideTheQuotes + "}";
                validTokens.add(new Token(insideTheQuotes, "STRING LITERAL"));
                return j;
            } else {
                insideTheQuotes = insideTheQuotes + insideTheQuotes.charAt(j);
                return 0;
            }
        }
        return 0;
        
    }
    
    public boolean checkForCharacter(String currentLine, int i){
        if(currentLine.charAt(i) == "t".charAt(0) && currentLine.charAt(i+1) == "e".charAt(0) 
                && currentLine.charAt(i+2) == "x".charAt(0) && currentLine.charAt(i+3) == "t".charAt(0)
                || currentLine.charAt(i) == "l".charAt(0) && currentLine.charAt(i+1) == "o".charAt(0) 
                && currentLine.charAt(i+2) == "o".charAt(0) && currentLine.charAt(i+3) == "p".charAt(0)
                || currentLine.charAt(i) == "i".charAt(0) && currentLine.charAt(i+1) == "f".charAt(0)
                || currentLine.charAt(i) == "+".charAt(0) || currentLine.charAt(i) == "=".charAt(0)
                || currentLine.charAt(i) == "?".charAt(0) || currentLine.charAt(i) == "!".charAt(0) 
                || currentLine.charAt(i) == "<".charAt(0) || currentLine.charAt(i) == ">".charAt(0) 
                || currentLine.charAt(i) == "-".charAt(0) || currentLine.charAt(i) == ":".charAt(0)
                || currentLine.charAt(i) == "(".charAt(0) || currentLine.charAt(i) == ")".charAt(0)
                || currentLine.charAt(i) == "{".charAt(0) || currentLine.charAt(i) == "\"".charAt(0)){
            
            return true;
        }
        return false;
    }
}
