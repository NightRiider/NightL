package me.jpalip.lexerparser.nodes;

import me.jpalip.interpret.Interpreter;
import me.jpalip.lexerparser.Token;

public class ReturnNode extends StatementsNode{


    public ReturnNode(Token token) {
        super(token, null);
    }

    @Override
    public Node visit(Interpreter interpret) {
        return null;
    }

    @Override
    public String toString() {
        return "ReturnNode(RETURN)";
    }
}
