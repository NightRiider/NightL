package me.jpalip.interpret;

import me.jpalip.lexerparser.TokenType;
import me.jpalip.lexerparser.nodes.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Interpreter {
    private List<Node> parse; // Full list of parsed Nodes
    private List<Node> parsed = parse;
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
        labels.put(node.getToken().toString(), node.getChild());
        parse.set(parse.indexOf(node), node.getChild());
        return null;
    }

    public Node visitData(DataNode node) {
        data.add(node);
        parse.remove(node);
        return null;
    }

    public Node visitAssignment(AssignmentNode node) {
        if(node.expToIntNode() instanceof MathOpNode || node.expToIntNode() instanceof IntegerNode)
            intVars.put(node.getToken().getValue(), evaluateIntMathOp(node.expToIntNode()));
        return null;
    }

    public Node visitPrint(PrintNode node) {
        for(Node n : node.representation()) {
            if(n instanceof VariableNode) {
                if(intVars.get(n) != null) {
                    System.out.print(intVars.get(n) + " ");
                }
                if(floatVars.get(n) != null) {
                    System.out.print(floatVars.get(n) + " ");
                }
                if(strVars.get(n) != null) {
                    System.out.print(strVars.get(n) + " ");
                }
            }
            if(n instanceof IntegerNode)
                System.out.print(((IntegerNode)n).representation() + " ");
            if(n instanceof FloatNode)
                System.out.print(((FloatNode)n).representation() + " ");
            if(n instanceof StringNode)
                System.out.print(((StringNode) n).representation() + " ");
            if(n instanceof MathOpNode) {
                System.out.print(evaluateIntMathOp(n) + " ");
            }
        }
        System.out.println();

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

    // Int values
    public int evaluateIntMathOp(Node node) {
        if(node instanceof MathOpNode) {
            int left = evaluateIntMathOp(((MathOpNode) node).getLeft());
            int right = evaluateIntMathOp(((MathOpNode) node).getRight());
            TokenType op = node.getToken().getType();
            switch(op) {
                case PLUS:
                    return right + left;
                case MINUS:
                    return left - right;
                case TIMES:
                    return right * left;
                case DIVIDE:
                    return left / right;
            }
        }
        else if(node instanceof IntegerNode) {
            return ((IntegerNode) node).representation();
        }
        else if(node instanceof VariableNode) {
            if(intVars.get(node.getToken().getValue()) != null) {
                return intVars.get(node.getToken().getValue());
            }
        }
        return 0;
    }
    // Decimal values
    public float evaluateFloatMathOp(Node node) {
        float left;
        float right;
        if(node instanceof MathOpNode) {
            left = evaluateFloatMathOp(((MathOpNode) node).getLeft());
            right = evaluateFloatMathOp(((MathOpNode) node).getRight());
            String operator = ((MathOpNode) node).representation();
            switch(operator) {
                case "PLUS":
                    return right + left;
                case "MINUS":
                    return left - right;
                case "TIMES":
                    return right * left;
                case "DIVIDE":
                    return left / right;
            }
        }
        if(node instanceof FloatNode) {
            return ((FloatNode) node).representation();
        }
        return 0;
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
        System.out.println(intVars);
        return null;
    }
}
