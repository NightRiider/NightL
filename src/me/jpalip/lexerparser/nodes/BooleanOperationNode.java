package me.jpalip.lexerparser.nodes;

import me.jpalip.interpret.Interpreter;
import me.jpalip.interpret.primitive.Primitive;
import me.jpalip.lexerparser.Token;

public class BooleanOperationNode extends Node{

    private Node secondNode;
    public BooleanOperationNode(Node node, Token operator, Node secondNode) {
        super(operator, node);
        this.secondNode = secondNode;
    }

    /*public Token getOperator() {
        return operator;
    }*/

    public Node getNode() { return node; }

    public Node getSecondNode() { return secondNode; }

    public Token getToken() { return token; }

    @Override
    public Primitive<?> visit(Interpreter interpret) {
        return null;
    }

    @Override
    public String toString() {
        return "BooleanOpNode(" + node.toString() + " " + token.toString() + " " + secondNode.toString() + ")";
    }
}
