package me.jpalip.lexerparser.nodes;

import me.jpalip.interpret.Interpreter;
import me.jpalip.interpret.primitive.Primitive;

import java.util.List;

public class InputNode extends StatementsNode{


    public InputNode(List<Node> list) {
        super(list);
    }

    @Override
    public Primitive<?> visit(Interpreter interpret) {
        return interpret.visitInput(this);
    }

    @Override
    public String toString() {
        return "InputNode(" + list.toString() + ")";
    }
}