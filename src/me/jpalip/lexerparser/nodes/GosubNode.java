package me.jpalip.lexerparser.nodes;

public class GosubNode extends Node{

    public GosubNode(VariableNode node) {
        super(node);
    }

    @Override
    public String toString() {
        return "GosubNode(" + node.toString() + ")";
    }
}
