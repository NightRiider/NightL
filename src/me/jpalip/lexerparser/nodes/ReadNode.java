package me.jpalip.lexerparser.nodes;

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
