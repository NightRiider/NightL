package me.jpalip.lexerparser.nodes;

import me.jpalip.lexerparser.Token;

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
