package me.jpalip.lexer.nodes;

import me.jpalip.lexer.Token;

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
