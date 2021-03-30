package me.jpalip.lexer; /**
 * @version 2.0 - Lexer #2
 * @JP Aliprantis
 * @date 2/8/2021
 * ICSI 311 - Michael Phipps
 */

import java.text.MessageFormat;

public class Token {

    // Field of values
    private TokenType tokenType;
    private String value;

    // Constructor Method
    public Token(TokenType type, String value)
    {
        this.tokenType = type;
        this.value = value;
    }

    public Token(TokenType type)
    {
        this.tokenType = type;
    }

    public String getValue()
    {
        return value;
    }

    public TokenType getType() { return tokenType; }

    @Override
    public String toString()
    {
        return tokenType.getName() + (value == null ? "" : MessageFormat.format("({0})", value)); // Input formatted for all possible chars
    }
}