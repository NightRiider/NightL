package me.jpalip.lexerparser.nodes;

import me.jpalip.interpret.Interpreter;
import me.jpalip.interpret.primitive.Primitive;
import me.jpalip.lexerparser.Token;
import me.jpalip.lexerparser.TokenType;

import java.util.List;

public class FunctionNode extends StatementsNode
{
    Token funcName;
    // Function with list of parameters
    public FunctionNode(Token token, List<Node> params) {
        super(params);
        funcName = token;
    }

    public TokenType getType() { return funcName.getType(); }

    public List<Node> getParams() { return list; }

    @Override
    public Primitive<?> visit(Interpreter interpret) {
        return interpret.visitFunction(this);
    }

    @Override
    public String toString() {
        return "FunctionNode(" + funcName.toString() + "(" + list.toString() + "))";
    }
}
