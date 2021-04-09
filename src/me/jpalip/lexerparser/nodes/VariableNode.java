package me.jpalip.lexerparser.nodes;

import me.jpalip.interpret.Interpreter;
import me.jpalip.interpret.primitive.Primitive;
import me.jpalip.lexerparser.Token;

public class VariableNode extends Node {

    public VariableNode(Token token)
    {
        super(token, null);
    }

    @Override
    public Primitive<?> visit(Interpreter interpret) {
        return null;
    }

    public String representation()
    {
        return token.getValue();
    }

    @Override
    public String toString()
    {
        return "VariableNode(" + token.getValue() + ")";
    }
}