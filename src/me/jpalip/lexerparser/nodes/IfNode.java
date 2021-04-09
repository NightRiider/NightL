package me.jpalip.lexerparser.nodes;

import me.jpalip.interpret.Interpreter;
import me.jpalip.interpret.primitive.Primitive;
import me.jpalip.lexerparser.Token;

public class IfNode extends StatementsNode{

    private Token label;
    public IfNode(Node booleanOp, Token label) {
        super(null, booleanOp);
        this.label = label;
    }

    @Override
    public Primitive<?> visit(Interpreter interpret) {
        return null;
    }

    @Override
    public String toString() {
        return "IFNode(" + node.toString() + ") THEN " + label.toString();
    }
}
