package me.jpalip.lexerparser.nodes;

import me.jpalip.interpret.Interpreter;
import me.jpalip.interpret.primitive.Primitive;
import me.jpalip.lexerparser.Token;

public class BooleanOperationNode extends Node{

    private Token operator;
    private Node secondNode;
    public BooleanOperationNode(Node firstNode, Token operator, Node secondNode) {
        super(null, firstNode);
        this.operator = operator;
        this.secondNode = secondNode;
    }

    @Override
    public Primitive<?> visit(Interpreter interpret) {
        return null;
    }

    @Override
    public String toString() {
        return "BooleanOpNode(" + node.toString() + " " + operator.toString() + " " + secondNode.toString() + ")";
    }
}
