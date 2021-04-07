package me.jpalip.lexerparser.nodes;

import me.jpalip.interpret.Interpreter;
import me.jpalip.lexerparser.Token;

import java.util.List;

public class FunctionNode extends StatementsNode
{
    Token funcName;
    // Function with list of parameters
    public FunctionNode(Token token, List<Node> params) {
        super(params);
        funcName = token;
    }

    @Override
    public Node visit(Interpreter interpret) {
        return null;
    }

    @Override
    public String toString() {
        return "FunctionNode(" + funcName.toString() + "(" + list.toString() + "))";
    }
}
