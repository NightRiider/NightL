package me.jpalip.lexerparser.nodes;

import me.jpalip.interpret.Interpreter;
import me.jpalip.lexerparser.Token;

/**
 * @version 1.0 - Parser #1
 * @JP Aliprantis
 * @date 2/28/2021
 * ICSI 311 - Michael Phipps
 */

// Node for Integers
public class IntegerNode extends Node {

    public IntegerNode(Token token)
    {
        super(token, null);
    }

    @Override
    public Node visit(Interpreter interpret) {
        return null;
    }

    public Integer representation() {
        return Integer.parseInt(token.getValue());
    }

    @Override
    public String toString() {
        return "IntegerNode(" + token.getValue() + ")";
    }
}