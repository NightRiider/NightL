package me.jpalip.lexerparser.nodes;

import me.jpalip.interpret.Interpreter;
import me.jpalip.interpret.primitive.Primitive;
import me.jpalip.lexerparser.Token;

import java.util.List;

public class StatementNode extends Node {

    protected Node expression;
    protected StatementNode next;

    public StatementNode(Token token, Node expression)
    {
        super(token, null);
        this.expression = expression;
    }

    public StatementNode(List<Node> params) {
        super(params);
    }

    public Node representation()
    {
        return null;
    }

    @Override
    public Primitive<?> visit(Interpreter interpret) {
        return null;
    }

    @Override
    public String toString()
    {
        return null;
    }
}