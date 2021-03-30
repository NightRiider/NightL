package me.jpalip.LexerParser.nodes;

import java.util.List;

public class DataNode extends Node
{

    public DataNode(List<Node> node) {
        super(node);
    }

    @Override
    public String toString() {
        return "DataNode(" + list.toString() + ")";
    }
}
