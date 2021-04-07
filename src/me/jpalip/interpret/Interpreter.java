package me.jpalip.interpret;

import me.jpalip.lexerparser.nodes.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Interpreter {
    private List<Node> parse; // Full list of parsed Nodes
    private List<Node> data = new ArrayList<>(); // Holds DATA from program


    // Hashmaps to store each variable
    private Map<String, Integer> intVars = new HashMap<>();
    private Map<String, Float> floatVars = new HashMap<>();
    private Map<String, String> strVars = new HashMap<>();
    private Map<String, Node> labels = new HashMap<>();

    public Interpreter(StatementsNode parsed) {
        this.parse = parsed.representation();
    }

    public Node visitLabels(LabeledStatementNode node) {
        labels.put(node.getToken(), node.getChild());
        parse.set(parse.indexOf(node), node.getChild());
        return null;
    }

    public Node visitData(DataNode node) {
        data.add(node);
        parse.remove(node);
        return null;
    }


    public Node visitFOR(ForNode fornode) {
        for (int j = 0; j < parse.size(); j++) {
            Node curr = parse.get(j);
            if (curr instanceof NextNode) {
                NextNode nNode = (NextNode) curr;
                nNode.setRef(fornode);
                if (j + 1 < parse.size()) {
                    curr = parse.get(j + 1);
                    fornode.setAfter(curr);
                } else {
                    fornode.setAfter(null);
                }
            }
        }
        return null;
    }

    public void visitNodes() {
        for (int i = 0; i < parse.size(); i++) {
            parse.get(i).visit(this);
            if (i + 1 < parse.size()) {
                ((StatementsNode) parse.get(i)).setNext(parse.get(i + 1));
            }
        }
    }

    public String interpret() {
        visitNodes();
        System.out.println(labels);
        System.out.println(parse);
        return null;
    }
}
