package me.jpalip.interpret;

import me.jpalip.interpret.primitive.*;
import me.jpalip.lexerparser.TokenType;
import me.jpalip.lexerparser.nodes.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Interpreter {
    private List<Node> parse; // Full list of parsed Nodes
    private List<Node> data = new ArrayList<>(); // Holds DATA from program


    // Hashmaps to store each variable
    private Map<String, IntPrimitive> intVars = new HashMap<>();
    private Map<String, Primitive> floatVars = new HashMap<>();
    private Map<String, String> strVars = new HashMap<>();
    private Map<String, Node> labels = new HashMap<>();

    public Interpreter(StatementsNode parsed) {
        this.parse = parsed.representation();
    }

    public Primitive<?> visitLabels(LabeledStatementNode node) {
        labels.put(node.getToken().toString(), node.getChild());
        parse.set(parse.indexOf(node), node.getChild());
        return null;
    }

    public Primitive<?> visitData(DataNode node) {
        data.add(node);
        parse.remove(node);
        return null;
    }


    public Primitive<?> visitAssignment(AssignmentNode node) {
        Primitive<?> value = evaluateMathOp(node.expToIntNode());

        switch (value.getType()) {
            case FLOAT: {
                FloatPrimitive floatVal = value.getValue(Type.FLOAT);
                floatVars.put(node.getToken().getValue(), floatVal);
                break;
            }
            case INT: {
                IntPrimitive intVal = value.getValue(Type.INT);
                intVars.put(node.getToken().getValue(), intVal);
                break;
            }
            default: return null;
        }

        return null;
    }

    public Primitive<?> visitPrint(PrintNode node) {
        for (Node n : node.representation()) {
            if (n instanceof VariableNode) {
                if (intVars.get(n) != null) {
                    System.out.print(intVars.get(n) + " ");
                }
                if (floatVars.get(n) != null) {
                    System.out.print(floatVars.get(n) + " ");
                }
                if (strVars.get(n) != null) {
                    System.out.print(strVars.get(n) + " ");
                }
            }
            if (n instanceof IntegerNode)
                System.out.print(((IntegerNode) n).representation() + " ");
            if (n instanceof FloatNode)
                System.out.print(((FloatNode) n).representation() + " ");
            if (n instanceof StringNode)
                System.out.print(((StringNode) n).representation() + " ");
            if (n instanceof MathOpNode) {
                System.out.print(evaluateMathOp(n) + " ");
            }
            if (n instanceof FunctionNode) {
                System.out.print(n.visit(this));
            }
        }
        System.out.println();

        return null;
    }


    public Primitive<?> visitFOR(ForNode fornode) {
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

    public Primitive<?> evaluateMathOp(Node node) {
        if (node instanceof MathOpNode) {
            Primitive<?> left = evaluateMathOp(((MathOpNode) node).getLeft());
            Primitive<?> right = evaluateMathOp(((MathOpNode) node).getRight());
            TokenType op = node.getToken().getType();
            switch (op) {
                case PLUS:
                    return left.add(right);
                case MINUS:
                    return left.min(right);
                case TIMES:
                    return left.mul(right);
                case DIVIDE:
                    return left.div(right);
            }
        } else if (node instanceof IntegerNode) {
            return new IntPrimitive(((IntegerNode) node).representation());
        } else if (node instanceof FloatNode) {
            return new FloatPrimitive(((FloatNode) node).representation());
        } else if (node instanceof VariableNode) {
            String key = node.getToken().getValue();
            if (intVars.containsKey(key)) {
                return intVars.get(key);
            }
            else if (floatVars.containsKey(key)) {
                return floatVars.get(key);
            }
        }
        else if(node instanceof FunctionNode) {
            return visitFunction((FunctionNode) node);
        }
        return new IntPrimitive(0);
    }

    public Primitive<?> visitFunction(FunctionNode node) {
        if(node.getType() == TokenType.RANDOM) {
            int rand = (int)Math.floor(Math.random()*(100) + 1);
            return new IntPrimitive(rand);
        }
        if(node.getType() == TokenType.LEFT) {
            String string = ((StringNode)(node.getParams().get(0))).representation();
            int index = ((IntegerNode)(node.getParams().get(1))).representation();
            return new StringPrimitive(string.substring(0, index));
        }
        if(node.getType() == TokenType.RIGHT) {
            String string = ((StringNode)(node.getParams().get(0))).representation();
            int index = ((IntegerNode)(node.getParams().get(1))).representation();
            return new StringPrimitive(string.substring(string.length() - index));
        }
        if(node.getType() == TokenType.MID) {
            String string = ((StringNode)(node.getParams().get(0))).representation();
            int start = ((IntegerNode)(node.getParams().get(1))).representation();
            int count = ((IntegerNode)(node.getParams().get(2))).representation();
            return new StringPrimitive(string.substring(start, start+count));
        }
        if(node.getType() == TokenType.NUM) {
            return new StringPrimitive(String.valueOf(node.getParams().get(0)));
        }
        if(node.getType() == TokenType.VALInt) {
            return new IntPrimitive(Integer.parseInt(((StringNode)(node.getParams().get(0))).representation()));
        }
        if(node.getType() == TokenType.VALFloat) {
            return new FloatPrimitive(Float.parseFloat(((StringNode)(node.getParams().get(0))).representation()));
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
        //System.out.println(labels);
        //System.out.println(intVars);
        //System.out.println(floatVars);
        return null;
    }
}
