package me.jpalip.lexerparser.nodes;

import me.jpalip.interpret.Interpreter;
import me.jpalip.interpret.primitive.Primitive;

public class ForNode extends StatementsNode
{
    private IntegerNode end, increment, start;
    private Node afterNEXT;

    public ForNode(VariableNode node, IntegerNode start, IntegerNode end, IntegerNode increment) {
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

    public IntegerNode getStep()
    {
        return increment;
    }

    @Override
    public Primitive<?> visit(Interpreter interpret) {
        return interpret.visitFOR(this);
    }

    @Override
    public String toString() {
        return "ForNode(" + node + " EQUALS " + start.toString() + " TO " + end + " STEP " + increment + ")";
    }
}
