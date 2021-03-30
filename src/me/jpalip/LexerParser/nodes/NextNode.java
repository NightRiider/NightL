package me.jpalip.LexerParser.nodes;

public class NextNode extends Node {

    public NextNode(VariableNode node) {
        super(node);
    }

    @Override
    public String toString() {
        return "NextNode(" + node.toString() + ")";
    }
}
