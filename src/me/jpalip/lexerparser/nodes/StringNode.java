package me.jpalip.lexerparser.nodes;

import me.jpalip.interpret.Interpreter;
import me.jpalip.lexerparser.Token;

public class StringNode extends Node
{

    public StringNode(Token token) {
        super(token, null);
    }

    @Override
    public Node visit(Interpreter interpret) {
        return null;
    }


    public Object representation() { return token.getValue(); }

    @Override
    public String toString()
    {
        return "StringNode(" + token.getValue() + ")";
    }
}
