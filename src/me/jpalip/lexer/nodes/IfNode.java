package me.jpalip.lexer.nodes;

import me.jpalip.lexer.Token;

public class IfNode extends Node{

    private Token label;
    public IfNode(Node booleanOp, Token label) {
        super(booleanOp);
        this.label = label;
    }

    @Override
    public String toString() {
        return "IFNode(" + node.toString() + ") THEN " + label.toString();
    }
}
