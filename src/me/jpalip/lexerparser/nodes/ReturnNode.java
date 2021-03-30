package me.jpalip.lexerparser.nodes;

import me.jpalip.lexerparser.Token;

public class ReturnNode extends Node{


    public ReturnNode(Token token) {
        super(token);
    }

    @Override
    public String toString() {
        return "ReturnNode(RETURN)";
    }
}
