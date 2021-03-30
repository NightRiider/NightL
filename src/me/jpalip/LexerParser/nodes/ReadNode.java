package me.jpalip.LexerParser.nodes;

import java.util.List;

public class ReadNode extends Node {


    public ReadNode(List<Node> node) {
        super(node);
    }

    @Override
    public String toString() {
        return  "ReadNode(" + list.toString() + ")";
    }
}
