package me.jpalip.lexer.nodes;

import me.jpalip.lexer.Token;

public class ReturnNode extends Node{


    public ReturnNode(Token token) {
        super(token);
    }

    @Override
    public String toString() {
        return "ReturnNode(RETURN)";
    }
}