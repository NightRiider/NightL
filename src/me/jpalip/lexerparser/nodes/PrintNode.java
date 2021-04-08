package me.jpalip.lexerparser.nodes;

import me.jpalip.interpret.Interpreter;

import java.util.List;

public class PrintNode extends StatementsNode {

    public PrintNode(List<Node> list)
    {
        super(list);
    }

    public List<Node> getValues() { return list; }

    @Override
    public Node visit(Interpreter interpret) {
        return interpret.visitPrint(this);
    }

    @Override
    public String toString()
    {
        return "PrintNode(" + list.toString() + ")";
    }
}