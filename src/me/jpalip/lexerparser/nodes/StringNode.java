package me.jpalip.lexerparser.nodes;

import me.jpalip.lexerparser.Token;

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
