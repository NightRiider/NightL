package me.jpalip.lexerparser.nodes;

import me.jpalip.interpret.Interpreter;

public class NextNode extends StatementsNode {

    private ForNode fNode;

    public NextNode(VariableNode node) {
        super(null, node);
    }

    @Override
    public Node visit(Interpreter interpret) {
        return null;
    }

    public void setRef(ForNode node) { fNode = node; }

    public ForNode getRef() { return fNode; }


    @Override
    public String toString() {
        return "NextNode(" + node.toString() + ")";
    }
}
