package me.jpalip.lexerparser;

/**
 * @version 2.0 - Lexer #2
 * @JP Aliprantis
 * @date 2/8/2021
 * ICSI 311 - Michael Phipps
 */

// TokenType Enum
// Made to simply convert proper characters to words
public enum TokenType {

    NUMBER ("NUMBER"),
    PLUS ("PLUS"),
    MINUS ("MINUS"),
    TIMES ("TIMES"),
    DIVIDE ("DIVIDE"),
    STRING ("STRING"),
    PRINT ("PRINT"),
    IDENTIFIER ("IDENTIFIER"),
    LABEL ("LABEL"),
    EQUALS ("EQUALS"),
    NOT_EQUAL ("NOT EQUAL"),
    LPAREN ("LPAREN"),
    RPAREN ("RPAREN"),
    LESS ("LESS THAN"),
    GREATER ("GREATER THAN"),
    LESS_EQUAL ("LESS THAN EQUAL TO"),
    GREATER_EQUAL ("GREATER THAN EQUAL TO"),
    COMMA("COMMA"),
    DATA("DATA"),
    READ("READ"),
    INPUT("INPUT"),
    FOR("FOR"),
    STEP("STEP"),
    TO("TO"),
    IF("IF"),
    ELSE("ELSE"),
    THEN("THEN"),
    GOSUB("GOSUB"),
    RETURN("RETURN"),
    NEXT("NEXT"),
    RANDOM("RANDOM"),
    LEFT("LEFT$"),
    RIGHT("RIGHT$"),
    MID("MID$"),
    NUM("NUM$"),
    VALInt("VAL"),
    VALFloat("VAL%"),
    EOL ("EndOfLine"),
    EOF("EndOfFile");

    private final String name;

    TokenType(final String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}