package me.jpalip.lexerparser.nodes;

import me.jpalip.interpret.Interpreter;
import me.jpalip.interpret.primitive.Primitive;

import java.util.List;

public class DataNode extends Node
{

    public DataNode(List<Node> list) {
        super(list);
    }

    public List<Node> representation() { return list; }

    @Override
    public Primitive<?> visit(Interpreter interpret) {
        return interpret.visitData(this);
    }

    @Override
    public String toString() {
        return "DataNode(" + list.toString() + ")";
    }
}
