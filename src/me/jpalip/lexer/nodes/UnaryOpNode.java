package me.jpalip.lexer.nodes;

import me.jpalip.lexer.Token;
import me.jpalip.lexer.TokenType;
import me.jpalip.lexer.nodes.Node;

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
        super(token);
        this.token = token;
        this.node = node;
    }

    public TokenType representation()
    {
        return token.getType();
    }

    @Override
    public String toString()
    {
        return "UnaryOpNode(" + token.getType().getName() + ", " + node + ")";
    }
}