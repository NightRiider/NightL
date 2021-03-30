package me.jpalip.LexerParser;

/**
 * @version 2.0 - Lexer #2
 * @JP Aliprantis
 * @date 2/20/2021
 * ICSI 311 - Michael Phipps
 */

// This class helps keep track of what exact position you are in, while reading a text file
public class Position implements Cloneable {

    private int index;
    private int line;
    private int column;

    public Position(int index, int line, int column)
    {
        this.index = index;
        this.line = line;
        this.column = column;
    }

    // Advances through a string after past char
    public void advance(char current)
    {
        index++;
        column++;

        if (current == '\n')
        {
            line++;
            column = 0;
        }
    }

    public int getIndex()
    {
        return index;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public int getLine()
    {
        return line;
    }

    public void setLine(int line)
    {
        this.line = line;
    }

    public int getColumn()
    {
        return column;
    }

    public void setColumn(int column)
    {
        this.column = column;
    }

    public Position clone()
    {
        return new Position(index, line, column);
    }
}