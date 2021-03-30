package me.jpalip.LexerParser.nodes;

import me.jpalip.LexerParser.Token;

public class StringNode extends Node
{

    public StringNode(Token token) {
        super(token);
    }


    public Object representation() { return token.getValue(); }

    @Override
    public String toString()
    {
        return "StringNode(" + token.getValue() + ")";
    }
}
