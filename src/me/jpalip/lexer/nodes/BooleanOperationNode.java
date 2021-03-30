package me.jpalip.lexer.nodes;

import me.jpalip.lexer.Token;

public class BooleanOperationNode extends Node{

    private Token operator;
    private Node secondNode;
    public BooleanOperationNode(Node firstNode, Token operator, Node secondNode) {
        super(firstNode);
        this.operator = operator;
        this.secondNode = secondNode;
    }

    @Override
    public String toString() {
        return "BooleanOpNode(" + node.toString() + " " + operator.toString() + " " + secondNode.toString() + ")";
    }
}
