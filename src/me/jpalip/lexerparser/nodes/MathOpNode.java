package me.jpalip.lexerparser.nodes;

import me.jpalip.lexerparser.Token;
import me.jpalip.lexerparser.TokenType;

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
        super(operator);
        this.leftOp = leftOp;
        this.rightOp = rightOp;
    }

    public Node getLeft() { return leftOp; }

    public Node getRight() { return rightOp; }

    @Override
    public String toString()
    {
        return "MathOpNode(" + leftOp + ", " + token.getType() + ", " + rightOp + ")";
    }

    public TokenType representation()
    {
        return token.getType();
    }
}