
package src;


public class POSParser {
    
    //TODO  - Match it with the lexical analyzer
    //      - Find out if we need all the excess lex calls [lex();]
    //      - Fix Bugs
    
    
    //<Start> -> <Stmts>
    public void Start(){
        System.out.print("Begin Parsing\n");
        stmts();
        //lex();
    }
    
    //<Stmts> -> <Stmt> {<{Stmt>}
    public void stmts(){
        System.out.print("Begin <Stmts>\n");
        stmt();
        //lex();
        //while(!End_Of_File){
            stmt();
            //lex();
        //}    
    }
    
    //<Stmt> -> <Ass-Stmt> | <If-Stmt> | <Loop-Stmt> | <Text-Stmt>
    public void stmt(){
        System.out.print("Begin <Stmt>\n");
        //switch(expected)
            //case(%id%)
                assStmt();
                //lex();
            //case("if")
                ifStmt();
                //lex();
            //case("loop")
                loopStmt();
                //lex();
            //case("text")
                textStmt();
                //lex();
    }
    
    //<Ass-Stmt> -> %id% = <Expr>
    public void assStmt(){
        System.out.print("Begin <Ass-Stmt>\n");
        System.out.print("matched %id%\n");
        //lex();
        //if token matched "="
            System.out.print("matched =\n");
        //lex();    
        //if token matched <Value>
            expr();
            //lex();
    }
    
    //<If-Stmt> -> if : <Bool-Expr> ( <Stmts> )
    public void ifStmt(){
        System.out.print("Begin <If-Stmt>\n");
        System.out.print("matched if\n");
        //lex();
        //if token matched ":"
            System.out.print("matched :\n");
        //lex();
        //if token matched <Value>
            boolExpr();
        //lex();
        //if token matched "("
            System.out.print("matched (\n");
        //lex();
        //if token matched <Stmts>
            stmts();
        //lex();
        //if token matched ")"
            System.out.print("matched )\n");
        //lex();
    }
    
    //
    //<Loop-Stmt> -> loop : <Bool-Expr> ( <Stmts> )
    public void loopStmt(){
        System.out.print("Begin <Loop-Stmt>\n");
        System.out.print("matched loop\n");
        //lex();
        //if token matched ":"
            System.out.print("matched :\n");
        //lex();
        //if token matched <Value>
            boolExpr();
        //lex();
        //if token matched "("
            System.out.print("matched (\n");
        //lex();
        //if token matched <Stmts>
            stmts();
        //lex();
        //if token matched ")"
            System.out.print("matched )\n");
        //lex();
    }
    
    //<Text-Stmt> -> text : <Expr>
    public void textStmt(){
        System.out.print("Begin <Text-Stmt>\n");
        System.out.print("matched text\n");
        //lex();
        //if token matched ":"
            System.out.print("matched :\n");
        //lex();
        //if token matched <<Value>
            expr();
        //lex();
    }
    
    //<Expr> -> <Value> {+ <Value>}
    public void expr(){
        System.out.print("Begin <Expr>\n");
        value();
        //lex();
        //while next token !"+"
            value();
            //lex();
    }
    
    //<Value> -> [-]%id% | [-]%num% | [-]%string%
    public void value(){
        System.out.print("Begin <Value>\n");
        //if matched "-" {
            System.out.print("matched -\n");
            //lex();
            valCheck();
            //lex();
        //}    
        //else
            valCheck();
            //lex();
    }
    
    //checks what value to use
    public void valCheck(){
        //if matched "%id%"
            System.out.print("matched %id%\n");
        //else if matched %num%
            System.out.print("matched %num%\n");
        //else if matched %string%
            System.out.print("matched %string%\n");
    }
    
    //<Bool-Expr> -> <Value> <Comparison-Op> <Value>
    public void boolExpr(){
        System.out.print("Begin <Bool-Expr>\n");
        value();
        //lex();
        comparisonOp();
        //lex();
        value();
        //lex();
    }
    
    //<Comparison-Op> -> ? | ! | < | >
    public void comparisonOp(){
        System.out.print("Begin <Comparison-Op>\n");
        //switch(expected)
            //case("?")
                System.out.print("matched ?\n");
            //case("!")
                System.out.print("matched !\n");
            //case("<")
                System.out.print("matched <\n");
            //case(">")
                System.out.print("matched >\n");
    }
    
}
