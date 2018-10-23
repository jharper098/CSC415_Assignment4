
public class Token {
    String lexeme;
    int numLexeme;
    String operator;
    
    public Token(String lexeme, String operator){
        this.lexeme=lexeme;
        this.operator=operator;
    }
    
    public Token(int numLexeme, String operator){
        this.numLexeme = numLexeme;
        this.operator=operator;
    }
}
