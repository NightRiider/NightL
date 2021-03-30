package me.jpalip.LexerParser.nodes;

import java.util.List;

public class InputNode extends Node{


    public InputNode(List<?> list) {
        super(list);
    }

    @Override
    public String toString() {
        return "InputNode(" + list.toString() + ")";
    }
}