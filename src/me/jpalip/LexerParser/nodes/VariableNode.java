package me.jpalip.LexerParser.nodes;

import me.jpalip.LexerParser.Token;

public class VariableNode extends Node {

    public VariableNode(Token token)
    {
        super(token);
    }

    public String representation()
    {
        return token.getValue();
    }

    @Override
    public String toString()
    {
        return "VariableNode(" + token.getValue() + ")";
    }
}