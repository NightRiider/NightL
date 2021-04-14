/**
 * @version 2.0 - Interpreter #2
 * @JP Aliprantis
 * @date 4/5/2021
 * ICSI 311 - Michael Phipps
 */

package me.jpalip.interpret;

import me.jpalip.interpret.primitive.*;
import me.jpalip.lexerparser.InvalidSyntaxError;
import me.jpalip.lexerparser.TokenType;
import me.jpalip.lexerparser.nodes.*;

import java.util.*;

public class Interpreter {
    private List<Node> parse; // Full list of parsed Nodes
    private List<Node> datalist = new ArrayList<>(); // Holds DATA from program
    private Stack<Node> stack = new Stack<>();


    // Hashmaps to store each variable
    private Map<String, IntPrimitive> intVars = new HashMap<>();
    private Map<String, FloatPrimitive> floatVars = new HashMap<>();
    private Map<String, String> strVars = new HashMap<>();
    private Map<String, Node> labels = new HashMap<>();

    public Interpreter(StatementsNode parsed) {
        this.parse = parsed.representation();
    }

    public Primitive<?> visitLabels(LabeledStatementNode node) {
        labels.put(node.getToken().getValue(), node.getChild());
        parse.set(parse.indexOf(node), node.getChild());
        return null;
    }

    public Primitive<?> visitData(DataNode node) {
        datalist.addAll(node.representation());
        parse.remove(node);
        return null;
    }

    public Primitive<?> visitRead(ReadNode node) {
        for(Node n : node.representation()) {
            if(n.getToken().getValue().contains("$")) {
                String name = n.getToken().getValue();
                // If types don't match - Strings
                if(datalist.get(0).getToken().getType() != TokenType.STRING) {
                    throw new InvalidSyntaxError("Invalid DATA Types!");
                }
                else {
                    String data = datalist.get(0).getToken().getValue();
                    strVars.put(name, data);
                }
            }
            else if(n.getToken().getValue().contains("%")) {
                String name = n.getToken().getValue();
                FloatPrimitive num = new FloatPrimitive(((FloatNode)(datalist.get(0))).representation());
                // If types don't match - Floats
                if(num.getType() != Type.FLOAT) {
                    throw new InvalidSyntaxError("Invalid DATA Types!");
                }
                floatVars.put(name, num);
            }
            else {
                String name = n.getToken().getValue();
                // If types don't match - Ints
                if(!(datalist.get(0) instanceof IntegerNode)) {
                    throw new InvalidSyntaxError("Invalid DATA Types!");
                }
                IntPrimitive num = new IntPrimitive(((IntegerNode)(datalist.get(0))).representation());
                intVars.put(name, num);
            }
            datalist.remove(0);
        }
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
            case STRING: {
                StringPrimitive strVal = value.getValue(Type.STRING);
                strVars.put(node.getToken().getValue(), strVal.getValue());
            }
            default: return null;
        }

        return null;
    }

    public Primitive<?> visitInput(InputNode node) {
        Scanner scan = new Scanner(System.in);
        System.out.println(((StringNode)(node.representation().get(0))).representation());
        for(Node n : node.representation()) {
            if(n != node.representation().get(0)) {
                if(n.getToken().getValue().contains("$")) {
                    String string = scan.nextLine();
                    strVars.put(n.getToken().getValue(), string);
                }
                else if(n.getToken().getValue().contains("%")) {
                    Float numb = scan.nextFloat();
                    floatVars.put(n.getToken().getValue(), new FloatPrimitive(numb));
                }
                else {
                    int num = scan.nextInt();
                    intVars.put(n.getToken().getValue(), new IntPrimitive(num));
                }
            }
        }

        return null;
    }

    public Primitive<?> visitPrint(PrintNode node) {
        for (Node n : node.representation()) {
            if (n instanceof VariableNode) {
                if (intVars.get(n.getToken().getValue()) != null) {
                    System.out.print(intVars.get(n.getToken().getValue()) + " ");
                }
                else if (floatVars.get(n.getToken().getValue()) != null) {
                    System.out.print(floatVars.get(n.getToken().getValue()) + " ");
                }
                else if (strVars.get(n.getToken().getValue()) != null) {
                    System.out.print(strVars.get(n.getToken().getValue()) + " ");
                }
                else {
                    throw new InvalidSyntaxError("Variable: \"" + n.getToken().getValue() + "\" does not exist!");
                }
            }
            else if (n instanceof IntegerNode)
                System.out.print(((IntegerNode) n).representation() + " ");
            else if (n instanceof FloatNode)
                System.out.print(((FloatNode) n).representation() + " ");
            else if (n instanceof StringNode)
                System.out.print(((StringNode) n).representation() + " ");
            else if (n instanceof MathOpNode) {
                System.out.print(evaluateMathOp(n) + " ");
            }
            else if (n instanceof FunctionNode) {
                System.out.print(n.visit(this));
            }
            else if(n instanceof InputNode) {
                System.out.println(n.visit(this));
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

    public BooleanPrimitive visitBooleanOp(BooleanOperationNode node) {
        Primitive<?> left = evaluateMathOp(node.getNode());
        Primitive<?> right = evaluateMathOp(node.getSecondNode());
        TokenType operator = node.getOperator().getType();
        switch (operator) {
            case EQUALS:
                return left.equal(right);
            case LESS:
                return left.less(right);
            case GREATER:
                return left.greater(right);
            case NOT_EQUAL:
                return left.notEqual(right);
            case LESS_EQUAL:
                return left.lessEqual(right);
            case GREATER_EQUAL:
                return left.greaterEqual(right);
        }
        return null;
    }

    public Primitive<?> visitIf(IfNode node) {
        BooleanPrimitive exp = visitBooleanOp(node.getBoolOP());
        if(exp.isTrue()) {
            System.out.println("true bitch");
            if(labels.get(node.getLabel().getValue()) != null) {
                labels.get(node.getLabel().getValue()).visit(this);
            }
            else {
                throw new InvalidSyntaxError("LabeledStatementNode not defined!");
            }
            //visitLabels((LabeledStatementNode) labels.get(node.getLabel()));
        }

        //System.out.println(exp);
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
        else if(node instanceof StringNode) {
            return new StringPrimitive(node.getToken().getValue());
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
            return new StringPrimitive(String.valueOf(node.getParams().get(0).getToken().getValue()));
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
        // Process DATA first
        for(int i = 0; i < parse.size(); i++) {
            if(parse.get(i) instanceof DataNode)
                visitData((DataNode) parse.get(i));
        }
        for (int i = 0; i < parse.size(); i++) {
            parse.get(i).visit(this);
            if (i + 1 < parse.size()) {
                ((StatementsNode) parse.get(i)).setNext(parse.get(i + 1));
            }
        }
    }

    public void interpret() {
        visitNodes();
        System.out.println(labels);
    }
}