package me.jpalip.lexer.nodes;

import me.jpalip.lexer.Token;

import java.util.List;

/**
 * @version 1.0 - Parser #1
 * @JP Aliprantis
 * @date 2/28/2021
 * ICSI 311 - Michael Phipps
 */

// Abstract Node class
public abstract class Node {

    protected Token token;
    protected Node node;
    protected List<?> list;

    public Node(Token token)
    {
        this.token = token;
    }

    public Node(Node node) {
        this.node = node;
    }

    public Node(List<?> list) { this.list = list; }

    public Token getToken() {
        return token;
    }


    public abstract String toString();
}