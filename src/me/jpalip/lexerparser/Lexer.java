package me.jpalip.lexerparser;
/**
 * @version 2.0 - Lexer #2
 * @JP Aliprantis
 * @date 2/8/2021
 * ICSI 311 - Michael Phipps
 */


// Add - num support

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// This class performs lexical analysis on lines of text
public class Lexer {

    private static final char NUL = Character.MIN_VALUE; // equivalent char null value
    // Hashmap to simply convert specific token types
    private static final Map<String, TokenType> TOKENS = new HashMap<>();
    static
    {
        TOKENS.put("+", TokenType.PLUS);
        TOKENS.put("-", TokenType.MINUS);
        TOKENS.put("*", TokenType.TIMES);
        TOKENS.put("/", TokenType.DIVIDE);
        TOKENS.put("(", TokenType.LPAREN);
        TOKENS.put(")", TokenType.RPAREN);
        TOKENS.put("<", TokenType.LESS);
        TOKENS.put(">", TokenType.GREATER);
        TOKENS.put("<=", TokenType.LESS_EQUAL);
        TOKENS.put(">=", TokenType.GREATER_EQUAL);
        TOKENS.put("<>", TokenType.NOT_EQUAL);
        TOKENS.put("=", TokenType.EQUALS);
        TOKENS.put("DATA", TokenType.DATA);
        TOKENS.put("READ", TokenType.READ);
        TOKENS.put("INPUT", TokenType.INPUT);
        TOKENS.put("PRINT", TokenType.PRINT);
        TOKENS.put("GOSUB", TokenType.GOSUB);
        TOKENS.put("FOR", TokenType.FOR);
        TOKENS.put("IF", TokenType.IF);
        TOKENS.put("THEN", TokenType.THEN);
        TOKENS.put("STEP", TokenType.STEP);
        TOKENS.put("TO", TokenType.TO);
        TOKENS.put("RETURN", TokenType.RETURN);
        TOKENS.put("NEXT", TokenType.NEXT);
        TOKENS.put("RANDOM", TokenType.RANDOM);
        TOKENS.put("LEFT$", TokenType.LEFT);
        TOKENS.put("RIGHT$", TokenType.RIGHT);
        TOKENS.put("MID$", TokenType.MID);
        TOKENS.put("NUM$", TokenType.NUM);
        TOKENS.put("VAL", TokenType.VALInt);
        TOKENS.put("VAL%", TokenType.VALFloat);
    }

    private final String input;
    private char current;
    private Position pos = new Position(0, 1, 0); // Keeps track of positioning while reading file

    // Constructor to get input, set positioning and starting char
    public Lexer (final String input)
    {
        this.input = input.strip();

        if (input != null && !input.isBlank())
        {
            this.current = this.input.charAt(pos.getIndex());
        }
    }

    // This is the method that is called when you need to perform lexical analysis on a line of text
    public List<Token> lex()
    {
        List<Token> tokens = new ArrayList<>();

        // EOL to list if empty or blank
        if (input == null || input.isBlank())
        {
            tokens.add(new Token(TokenType.EOL));
            return tokens;
        }

        // Loop through chars that aren't NUL
        while (current != NUL)
        {
            // Ignores white space when needed
            if (isWhiteSpace())
            {
                advance();
            }
            // The following statements check for specific elements and call their corresponding methods
            // Which cleans things up and makes it easier to read
            else if (isNumber())
            {
                tokens.add(consumeNumber());
            }
            else if (isOperator())
            {
                tokens.add(consumeOperator());
            }
            else if (isOpeningString())
            {
                tokens.add(consumeString());
            }
            else if (isComparisonOperator())
            {
                tokens.add(consumeComparisonOperator());
            }
            else if (isParen())
            {
                tokens.add(new Token(TOKENS.get(Character.toString(current))));
                advance();
            }
            else
            {
                tokens.add(consumeWord());
            }
        }

        // Always add EOL after EVERY line
        tokens.add(new Token(TokenType.EOL));
        return tokens;
    }

    // Returns current operator
    private Token consumeOperator()
    {
        char op = current;
        advance();
        return new Token(TOKENS.get(Character.toString(op)));
    }

    // Move onto next char in line
    // Adjust for correct positioning
    public void advance()
    {
        pos.advance(current);
        current = pos.getIndex() != input.length() ? input.charAt(pos.getIndex()) : NUL;
    }

    private Token consumeComparisonOperator()
    {
        StringBuilder buffer = new StringBuilder();

        // Makes sure we are adding a legit Comparison Operator
        while (current != NUL && isComparisonOperator())
        {
            buffer.append(current);
            advance();
        }

        String operator = buffer.toString();

        if (TOKENS.containsKey(operator))
        {
            return new Token(TOKENS.get(operator));
        }
        // Error for unknown char
        throw new InvalidCharError("Invalid char @ line: " + pos.getLine() + "\tcolumn: " + pos.getColumn() + "\tindex: " + pos.getIndex());
    }

    // Method called when a string is present in quotes
    private Token consumeString()
    {
        advance();
        StringBuilder buffer = new StringBuilder();

        // makes sure string is in quote
        while (current != NUL && !isOpeningString())
        {
            buffer.append(current);
            advance();
        }

        // Unclosed string
        if (current == NUL)
        {
            throw new InvalidCharError("Invalid char @ line: " + pos.getLine() + "\tcolumn: " + pos.getColumn() + "\tindex: " + pos.getIndex());
        }

        advance();
        return new Token(TokenType.STRING, buffer.toString());
    }

    // Method to build a String of numbers
    private String numString()
    {
        StringBuilder buffer = new StringBuilder();
        boolean decimal = false; // tracks to make sure we only have one

        while (current != NUL && (isNumber() || isPeriod()))
        {
            if (isPeriod() && decimal)
            {
                throw new InvalidCharError("Invalid char @ line: " + pos.getLine() + "\tcolumn: " + pos.getColumn() + "\tindex: " + pos.getIndex());
            }
            else if (isPeriod())
            {
                decimal = true;
            }

            buffer.append(current);
            advance();
        }

        String num = buffer.toString();

        // Invalid syntax - number ending with "."
        if (num.endsWith("."))
        {
            throw new InvalidCharError("Invalid char @ line: " + pos.getLine() + "\tcolumn: " + pos.getColumn() + "\tindex: " + pos.getIndex());
        }

        return num;
    }

    // Adds number to list
    private Token consumeNumber()
    {
        return new Token(TokenType.NUMBER, numString());
    }

    private Token consumeWord()
    {
        if(current == ',')
        {
            advance();
            return new Token(TokenType.COMMA);
        }
        StringBuilder buffer = new StringBuilder();
        boolean start = true;
        boolean suffix = false;

        // Builds a string with suffix or without
        while (current != NUL && (isLetter() || isSuffix() || isColon()))
        {
            if (isSuffix())
            {
                if (suffix || start)
                {
                    throw new InvalidCharError("Invalid char @ line: " + pos.getLine() + "\tcolumn: " + pos.getColumn() + "\tindex: " + pos.getIndex());
                }
                suffix = true;  // keeps track if suffix present
            }
            // if word followed by invalid suffix
            else if (suffix)
            {
                throw new InvalidCharError("Invalid char @ line: " + pos.getLine() + "\tcolumn: " + pos.getColumn() + "\tindex: " + pos.getIndex());
            }
            // prevents double :: input
            else if (isColon())
            {
                if (buffer.length() == 0)
                {
                    throw new InvalidCharError("Invalid char @ line: " + pos.getLine() + "\tcolumn: " + pos.getColumn() + "\tindex: " + pos.getIndex());
                }

                // continues and returns proper token for a label
                advance();
                return new Token(TokenType.LABEL, buffer.toString());
            }

            // Consumes number without suffix
            buffer.append(current);
            advance();
            start = false;
        }

        String tok = buffer.toString();

        // Checks if keyword
        if (TOKENS.containsKey(tok))
        {
            return new Token(TOKENS.get(tok));
        }

        // Bad Input
        if (tok.isEmpty())
        {
            throw new InvalidCharError("Invalid char @ line: " + pos.getLine() + "\tcolumn: " + pos.getColumn() + "\tindex: " + pos.getIndex());
        }

        // Unclosed String word -> Identifier
        return new Token(TokenType.IDENTIFIER, tok);
    }

    private static final String WHITESPACE = " \t";

    // Helper Methods
    private boolean isWhiteSpace()
    {
        return WHITESPACE.contains(Character.toString(current));
    }

    private boolean isOperator()
    {
        return current == '+' || current == '-' || current == '*' || current == '/';
    }

    private boolean isPeriod()
    {
        return current == '.';
    }

    private boolean isNumber()
    {
        return Character.isDigit(current);
    }

    private boolean isOpeningString()
    {
        return current == '\"';
    }

    private boolean isLetter()
    {
        return Character.isLetter(current);
    }

    private boolean isParen()
    {
        return current == '(' || current == ')';
    }

    private boolean isComparisonOperator()
    {
        return current == '<' || current == '>' || current == '=';
    }

    private boolean isSuffix()
    {
        return current == '%' || current == '$';
    }

    private boolean isColon()
    {
        return current == ':';
    }
}