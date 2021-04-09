package me.jpalip.lexerparser.nodes;

import me.jpalip.interpret.Interpreter;
import me.jpalip.interpret.primitive.Primitive;
import me.jpalip.lexerparser.Token;

public class LabeledStatementNode extends StatementsNode
{
    public LabeledStatementNode(Token token, Node node) {
        super(token, node);
    }

    public Node getChild() { return node; }

    @Override
    public Primitive<?> visit(Interpreter interpret) {
        return interpret.visitLabels(this);
    }

    @Override
    public String toString() {
        return "LabeledStatementNode(" + token.getValue() + ", " + node.toString() + ")";
    }
}
