package me.jpalip.lexerparser.nodes;

import me.jpalip.interpret.Interpreter;

import java.util.List;

public class PrintNode extends StatementsNode {

    public PrintNode(List<Node> list)
    {
        super(list);
    }

    public String getValue() { return token.getValue(); }

    @Override
    public Node visit(Interpreter interpret) {
        return null;
    }

    @Override
    public String toString()
    {
        return "PrintNode(" + list.toString() + ")";
    }
}