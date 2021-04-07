package me.jpalip.lexerparser.nodes;

import me.jpalip.interpret.Interpreter;
import me.jpalip.lexerparser.Token;
import me.jpalip.lexerparser.TokenType;

/**
 * @version 1.0 - Parser #1
 * @JP Aliprantis
 * @date 2/28/2021
 * ICSI 311 - Michael Phipps
 */

// Node Class for Operators
public class UnaryOpNode extends Node {

    private Token token;
    private Node node;

    public UnaryOpNode(Token token, Node node)
    {
        super(token, null);
        this.token = token;
        this.node = node;
    }

    public TokenType representation()
    {
        return token.getType();
    }

    @Override
    public Node visit(Interpreter interpret) {
        return null;
    }

    @Override
    public String toString()
    {
        return "UnaryOpNode(" + token.getType().getName() + ", " + node + ")";
    }
}