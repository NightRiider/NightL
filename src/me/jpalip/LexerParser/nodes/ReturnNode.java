package me.jpalip.LexerParser.nodes;

import me.jpalip.LexerParser.Token;

public class ReturnNode extends Node{


    public ReturnNode(Token token) {
        super(token);
    }

    @Override
    public String toString() {
        return "ReturnNode(RETURN)";
    }
}
