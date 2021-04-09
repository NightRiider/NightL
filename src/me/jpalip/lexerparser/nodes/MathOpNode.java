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

// MathOpNode returns list of mathematical expression
// In proper order with, *&/ taking precedence
public class MathOpNode extends Node {

    protected Node leftOp;
    protected Node rightOp;

    public MathOpNode(Token operator, Node leftOp, Node rightOp)
    {
        super(operator, null);
        this.leftOp = leftOp;
        this.rightOp = rightOp;
    }

    public Node getLeft() { return leftOp; }

    public Node getRight() { return rightOp; }

    @Override
    public Primitive<?> visit(Interpreter interpret) { return null; }

    @Override
    public String toString()
    {
        return "MathOpNode(" + leftOp + ", " + token.getType() + ", " + rightOp + ")";
    }

    public String representation()
    {
        return token.toString();
    }
}