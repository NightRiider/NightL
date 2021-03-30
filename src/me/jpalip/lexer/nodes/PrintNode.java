package me.jpalip.lexer.nodes;

import java.util.List;

public class PrintNode extends Node {

    public PrintNode(List<Node> list)
    {
        super(list);
    }

    public String representation()
    {
        return token.getValue();
    }

    @Override
    public String toString()
    {
        return "PrintNode(" + list.toString() + ")";
    }
}