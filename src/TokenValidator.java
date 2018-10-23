
import java.util.List;

public class TokenValidator {
    List<Token> lexemeList;
    int literal;
    int numLiteral;
    String stringLiteral;
    
    public TokenValidator(List<Token> tokenList){
        this.lexemeList = tokenList;
    }
    
    public TokenValidator(int literal){
        this.literal=literal;
    }
    
    public void createTokenList(){
        lexemeList.add(new Token("+", "ADD OPERATOR"));
        lexemeList.add(new Token("+", "CONCATENATE OPERATOR"));
        lexemeList.add(new Token("-", "NEGATION OPERATOR"));
        lexemeList.add(new Token("(", "OPEN PARENTHESIS"));
        lexemeList.add(new Token(")", "CLOSE PARENTHESIS"));
        lexemeList.add(new Token(">", "GREATER THAN COMPARASON OPERATOR"));
        lexemeList.add(new Token("<", "LESS THAN COMPARASON OPERATOR"));
        lexemeList.add(new Token("=", "ASSIGNMENT OPERATOR"));
        lexemeList.add(new Token(":", "COLON"));
        lexemeList.add(new Token("WHILE", "KEYWORD"));
        lexemeList.add(new Token("ENDWHILE", "KEYWORD"));
        lexemeList.add(new Token("PRINT", "KEYWORD"));
        lexemeList.add(new Token("IF", "KEYWORD"));
        lexemeList.add(new Token("ENDIF", "KEYWORD"));
        lexemeList.add(new Token("\"" + stringLiteral + "\"", "STRING LITERAL"));
        lexemeList.add(new Token(numLiteral, "NUMERIC LITERAL"));
        lexemeList.add(new Token(stringLiteral, "IDENTIFIER"));
        lexemeList.add(new Token("{" + stringLiteral + "}", "COMMENT"));
    }
    
    public boolean isValid(Token t){
        if (lexemeList.contains(t))
            return true;
        else
            return false;
    }
}
