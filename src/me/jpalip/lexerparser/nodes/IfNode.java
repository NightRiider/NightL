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

    public BooleanOperationNode getBoolOP() { return (BooleanOperationNode) node; }

    public Token getLabel() { return label; }

    @Override
    public Primitive<?> visit(Interpreter interpret) {
        return interpret.visitIf(this);
    }

    @Override
    public String toString() {
        return "IFNode(" + node.toString() + ") THEN " + label.toString();
    }
}
