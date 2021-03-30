package me.jpalip.lexer.nodes;

import me.jpalip.lexer.Token;

public class LabeledStatementNode extends Node
{
    private Node node;

    public LabeledStatementNode(Token token, Node node) {
        super(token);
        this.node = node;
    }

    @Override
    public String toString() {
        return "LabeledStatementNode(" + token.getValue() + ", " + node.toString() + ")";
    }
}
