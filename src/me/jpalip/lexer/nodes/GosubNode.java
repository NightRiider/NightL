package me.jpalip.lexer.nodes;

import me.jpalip.lexer.Token;

public class GosubNode extends Node{

    public GosubNode(VariableNode node) {
        super(node);
    }

    @Override
    public String toString() {
        return "GosubNode(" + node.toString() + ")";
    }
}
