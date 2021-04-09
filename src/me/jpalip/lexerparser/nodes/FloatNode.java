package me.jpalip.lexerparser.nodes;

import me.jpalip.interpret.Interpreter;
import me.jpalip.interpret.primitive.Primitive;
import me.jpalip.lexerparser.Token;

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
        super(token, null);
    }

    @Override
    public Primitive<?> visit(Interpreter interpret) {
        return null;
    }

    public Float representation() {
        return Float.parseFloat(token.getValue());
    }

    @Override
    public String toString() {
        return "FloatNode(" + token.getValue() + ")";
    }
}
