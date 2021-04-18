package me.jpalip.lexerparser.nodes;

import me.jpalip.interpret.Interpreter;
import me.jpalip.interpret.primitive.Primitive;
import me.jpalip.lexerparser.Token;

public class IfNode extends StatementsNode{

    private Token elselabel;
    public IfNode(Node booleanOp, Token label, Token elselabel) {
        super(label, booleanOp);
        this.elselabel = elselabel;
    }

    public BooleanOperationNode getBoolOP() { return (BooleanOperationNode) node; }

    public Token getLabel() { return token; }

    public Token getElselabel() { return elselabel; }

    @Override
    public Primitive<?> visit(Interpreter interpret) {
        return interpret.visitIf(this);
    }

    @Override
    public String toString() {
        if(elselabel == null)
            return "IFNode(" + node.toString() + ") THEN " + token.toString();
        else
            return "IFNode(" + node.toString() + ") THEN " + token.toString() + " ELSE " + elselabel.toString();
    }
}
