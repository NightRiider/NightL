package me.jpalip.interpret;

import me.jpalip.lexerparser.nodes.Node;

import java.util.HashMap;
import java.util.Map;

public class Interpreter
{
    private Node parsed;
    private Map<String, Integer> strIntVars = new HashMap<>();
    private Map<String, Float> strFloatVars = new HashMap<>();
    private Map<String, String> strStrVars = new HashMap<>();
    private Map<String, Node> labels = new HashMap<>();

    public Interpreter(Node parsed) {
        this.parsed = parsed;
    }

    private void visit(Node node) {
        //Class nodeType = node.getClass();



    }

    public String interpret() {

        return null;
    }

}
