package me.jpalip.lexerparser.nodes;

import me.jpalip.interpret.Interpreter;

import java.util.List;

public class DataNode extends Node
{

    public DataNode(List<Node> list) {
        super(list);
    }

    public List<Node> representation() { return list; }

    @Override
    public Node visit(Interpreter interpret) {
        return interpret.visitData(this);
    }

    @Override
    public String toString() {
        return "DataNode(" + list.toString() + ")";
    }
}
