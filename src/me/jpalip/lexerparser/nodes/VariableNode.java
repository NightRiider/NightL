package me.jpalip.lexerparser.nodes;

import me.jpalip.lexerparser.Token;

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