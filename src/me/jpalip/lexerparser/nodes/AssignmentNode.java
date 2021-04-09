package me.jpalip.lexerparser.nodes;

import me.jpalip.interpret.Interpreter;
import me.jpalip.interpret.primitive.Primitive;
import me.jpalip.lexerparser.Token;

public class AssignmentNode extends StatementsNode {

    public AssignmentNode(Token token, Node node)
    {
        super(token, node);
    }

    public Node tokenToVariableNode() { return new VariableNode(token); }

    public Node expToIntNode() { return node; }

    @Override
    public Primitive<?> visit(Interpreter interpret) { return interpret.visitAssignment(this); }

    @Override
    public String toString()
    {
        return "AssignmentNode(" + token.getValue() + ", " + node + ")";
    }
}
