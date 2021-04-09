package me.jpalip.lexerparser.nodes;

import me.jpalip.interpret.Interpreter;
import me.jpalip.interpret.primitive.Primitive;

import java.util.List;

public class PrintNode extends StatementsNode {

    public PrintNode(List<Node> list)
    {
        super(list);
    }

    public List<Node> getValues() { return list; }

    @Override
    public Primitive<?> visit(Interpreter interpret) {
        return interpret.visitPrint(this);
    }

    @Override
    public String toString()
    {
        return "PrintNode(" + list.toString() + ")";
    }
}