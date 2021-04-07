package me.jpalip.lexerparser.nodes;

import me.jpalip.interpret.Interpreter;

public class GosubNode extends StatementsNode{

    public GosubNode(VariableNode node) {
        super(null, node);
    }

    @Override
    public Node visit(Interpreter interpret) {
        return null;
    }

    @Override
    public String toString() {
        return "GosubNode(" + node.toString() + ")";
    }
}
