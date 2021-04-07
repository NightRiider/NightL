package me.jpalip.lexerparser.nodes;

import me.jpalip.interpret.Interpreter;

import java.util.List;

public class InputNode extends StatementsNode{


    public InputNode(List<Node> list) {
        super(list);
    }

    @Override
    public Node visit(Interpreter interpret) {
        return null;
    }

    @Override
    public String toString() {
        return "InputNode(" + list.toString() + ")";
    }
}