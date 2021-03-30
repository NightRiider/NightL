package me.jpalip.LexerParser.nodes;

import me.jpalip.LexerParser.Token;

/**
 * @version 1.0 - Parser #1
 * @JP Aliprantis
 * @date 2/28/2021
 * ICSI 311 - Michael Phipps
 */

// Node for Floats
public class FloatNode extends Node
{
    public FloatNode(Token token)
    {
        super(token);
    }

    @Override
    public String toString() {
        return "FloatNode(" + token.getValue() + ")";
    }
}
