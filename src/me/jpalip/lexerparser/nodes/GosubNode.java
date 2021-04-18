package me.jpalip.lexerparser.nodes;

import me.jpalip.interpret.Interpreter;
import me.jpalip.interpret.primitive.Primitive;
import me.jpalip.lexerparser.Token;

public class GosubNode extends StatementsNode{

    public GosubNode(Token label) {
        super(label, null);
    }

    public Token getLabel() { return token; }

    @Override
    public Primitive<?> visit(Interpreter interpret) {
        return null;
    }

    @Override
    public String toString() {
        return "GosubNode(" + token.toString() + ")";
    }
}
