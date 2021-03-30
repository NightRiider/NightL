package me.jpalip.lexerparser.nodes;

public class ForNode extends Node
{
    private IntegerNode end, increment;
    private Node start;

    public ForNode(VariableNode node, Node start, IntegerNode end, IntegerNode increment) {
        super(node);
        this.start = start;
        this.end = end;
        this.increment = increment;
    }


    @Override
    public String toString() {
        return "ForNode(" + node + " EQUALS " + start.toString() + " TO " + end + " STEP " + increment + ")";
    }
}
