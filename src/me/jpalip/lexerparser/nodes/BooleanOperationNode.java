package me.jpalip.lexerparser.nodes;

import me.jpalip.interpret.Interpreter;
import me.jpalip.interpret.primitive.Primitive;
import me.jpalip.lexerparser.Token;

public class BooleanOperationNode extends Node{

    private Token operator;
    private Node secondNode;
    public BooleanOperationNode(Node node, Token operator, Node secondNode) {
        super(null, node);
        this.operator = operator;
        this.secondNode = secondNode;
    }

    public Token getOperator() {
        return operator;
    }

    public Node getNode() { return node; }

    public Node getSecondNode() { return secondNode; }


    @Override
    public Primitive<?> visit(Interpreter interpret) {
        return null;
    }

    @Override
    public String toString() {
        return "BooleanOpNode(" + node.toString() + " " + operator.toString() + " " + secondNode.toString() + ")";
    }
}
