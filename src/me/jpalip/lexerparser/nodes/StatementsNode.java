package me.jpalip.lexerparser.nodes;

import me.jpalip.interpret.Interpreter;
import me.jpalip.lexerparser.Token;

import java.util.List;

public class StatementsNode extends Node {

    protected Node next;

    public StatementsNode(List<Node> list) { super(list); }

    public StatementsNode(Token token, Node node) {
        super(token, node);
    }

    public List<Node> representation()
    {
        return list;
    }

    @Override
    public Node visit(Interpreter interpret) {
        return null;
    }

    public void setNext(Node node) { next = node; }

    @Override
    public String toString()
    {
        return "StatementsNode(" + list.toString() + ")";
    }
}
