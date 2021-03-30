package me.jpalip.lexer.nodes;

import me.jpalip.lexer.Token;

import java.util.List;

public class StatementsNode extends Node {

    private List<Node> nodes;

    public StatementsNode(Token token, List<Node> nodes)
    {
        super(token);
        this.nodes = nodes;
    }


    public List<Node> representation()
    {
        return nodes;
    }

    @Override
    public String toString()
    {
        return "StatementsNode(" + nodes.toString() + ")";
    }
}
