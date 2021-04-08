package me.jpalip.lexerparser.nodes;

import me.jpalip.interpret.Interpreter;
import me.jpalip.lexerparser.Token;

import java.util.ArrayList;
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
    protected List<Node> list = new ArrayList<Node>();

    public Node(List<Node> list) { this.list = list; }

    public Node(Token token, Node node) {
        this.token = token;
        this.node = node;
    }

    public Token getToken() {
        return token;
    }

    public abstract Node visit(Interpreter interpret);

    public abstract String toString();

}