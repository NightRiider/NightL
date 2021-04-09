package me.jpalip.lexerparser.nodes;

import me.jpalip.interpret.Interpreter;
import me.jpalip.interpret.primitive.Primitive;

public class GosubNode extends StatementsNode{

    public GosubNode(VariableNode node) {
        super(null, node);
    }

    @Override
    public Primitive<?> visit(Interpreter interpret) {
        return null;
    }

    @Override
    public String toString() {
        return "GosubNode(" + node.toString() + ")";
    }
}
