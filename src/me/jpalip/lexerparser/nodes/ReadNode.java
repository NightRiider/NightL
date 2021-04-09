package me.jpalip.lexerparser.nodes;

import me.jpalip.interpret.Interpreter;
import me.jpalip.interpret.primitive.Primitive;

import java.util.List;

public class ReadNode extends StatementsNode {


    public ReadNode(List<Node> node) {
        super(node);
    }

    @Override
    public Primitive<?> visit(Interpreter interpret) {
        return null;
    }

    @Override
    public String toString() {
        return  "ReadNode(" + list.toString() + ")";
    }
}
