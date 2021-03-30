package me.jpalip.lexer.nodes;

import me.jpalip.lexer.Token;

public class NextNode extends Node {

    public NextNode(VariableNode node) {
        super(node);
    }

    @Override
    public String toString() {
        return "NextNode(" + node.toString() + ")";
    }
}
