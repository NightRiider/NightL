package me.jpalip.lexerparser.nodes;

import me.jpalip.interpret.Interpreter;
import me.jpalip.interpret.primitive.Primitive;

public class ForNode extends StatementsNode
{
    private IntegerNode end, increment;
    private Node start;

    private Node afterNEXT;

    public ForNode(VariableNode node, Node start, IntegerNode end, IntegerNode increment) {
        super(null, node);
        this.start = start;
        this.end = end;
        this.increment = increment;
    }

    public VariableNode getVariable() { return (VariableNode) node; }

    public Node getStart() { return start; }

    public IntegerNode getEnd() { return end; }

    public void setAfter(Node n) { afterNEXT = n; }

    public Node getAfter() { return afterNEXT; }


    @Override
    public Primitive<?> visit(Interpreter interpret) {
        return interpret.visitFOR(this);
    }

    @Override
    public String toString() {
        return "ForNode(" + node + " EQUALS " + start.toString() + " TO " + end + " STEP " + increment + ")";
    }
}
