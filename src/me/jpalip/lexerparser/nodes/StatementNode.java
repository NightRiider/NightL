package me.jpalip.lexerparser.nodes;

import me.jpalip.lexerparser.Token;

public class StatementNode extends Node {

    protected Node expression;

    public StatementNode(Token token, Node expression)
    {
        super(token);
        this.expression = expression;

    }

    public Node representation()
    {
        return null;
    }

    @Override
    public String toString()
    {
        return null;
    }
}