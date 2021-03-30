package me.jpalip.lexer.nodes;

import me.jpalip.lexer.Token;

import java.util.List;

public class FunctionNode extends Node
{
    Token funcName;
    // Function with list of parameters
    public FunctionNode(Token token, List<?> params) {
        super(params);
        funcName = token;
    }

    @Override
    public String toString() {
        return "FunctionNode(" + funcName.toString() + "(" + list.toString() + "))";
    }
}
