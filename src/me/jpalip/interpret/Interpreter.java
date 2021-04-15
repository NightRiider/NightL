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
    private final Map<String, IntPrimitive> intVars = new HashMap<>();
    private final Map<String, FloatPrimitive> floatVars = new HashMap<>();
    private final Map<String, String> strVars = new HashMap<>();
    private final Map<String, Node> labels = new HashMap<>();

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
                } else if (n.getToken().getValue().contains("%")) {
                    FloatPrimitive numb = new FloatPrimitive(scan.nextFloat());
                    floatVars.put(n.getToken().getValue(), numb);
                } else {
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
        IntPrimitive step = fornode.getStep() == null ? new IntPrimitive(1) : (IntPrimitive) evaluateMathOp(fornode.getStep());
        IntPrimitive begin = null;
        IntegerNode end = fornode.getEnd();
        Node var = fornode.getVariable();
        Node start = fornode.getStart();
        // If variable is not defined, add it to int variables
        if(!intVars.containsKey(var.getToken().getValue())) {
            if(!(start instanceof VariableNode)) {
                begin = new IntPrimitive(((IntegerNode) (start)).representation());
                intVars.put(var.getToken().getValue(), begin);
            }
        }
        // Variable IS defined, set begin to its value from hashmap
        else {
            begin = intVars.get(start.getToken().getValue());
        }

        int i = begin.getValue();

        while(i < end.representation()) {
            Node execute = fornode.getNext(); // execute -> For Loop's first statement
            // fornode.getNext().visit(this); // visits For loop's first statement
            // Loops through For loop's statements until last statement's next is null
            while(((StatementsNode)execute).getNext() != null) {
                execute.visit(this);
                execute = ((StatementsNode) execute).getNext();
                //execute.visit(this);
            }
            execute.visit(this);
            //System.out.println(fornode.getNext().toString() + " next: " + ((StatementsNode)fornode.getNext()).getNext()); // debug statement
            i += step.getValue();
            intVars.put(var.getToken().getValue(), new IntPrimitive(i));
        }
        intVars.remove(var.getToken().getValue());
        return null;
    }

    public Primitive<?> visitIf(IfNode node) {
        BooleanPrimitive exp = (BooleanPrimitive) evaluateMathOp(node.getBoolOP());
        if(exp.isTrue()) {
            if(labels.get(node.getLabel().getValue()) != null) {
                (labels.get(node.getLabel().getValue())).visit(this);
            }
            else {
                throw new InvalidSyntaxError("LabeledStatementNode not defined!");
            }
        }
        return null;
    }

    public Primitive<?> visitNext(NextNode node) {


        return null;
    }

    public Primitive<?> evaluateMathOp(Node node) {
        if (node instanceof MathOpNode || node instanceof BooleanOperationNode) {
                Primitive<?> left = null;
                Primitive<?> right = null;
            if(node instanceof MathOpNode n) {
                left = evaluateMathOp(n.getLeft());
                right = evaluateMathOp(n.getRight());
            }
            else if(node instanceof BooleanOperationNode n) {
                left = evaluateMathOp(n.getNode());
                right = evaluateMathOp(n.getSecondNode());
            }
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
        } else if (node instanceof IntegerNode n) {
            return new IntPrimitive(n.representation());
        } else if (node instanceof FloatNode n) {
            return new FloatPrimitive(n.representation());
        } else if (node instanceof VariableNode) {
            String key = node.getToken().getValue();
            if (intVars.containsKey(key)) {
                return intVars.get(key);
            }
            else if (floatVars.containsKey(key)) {
                return floatVars.get(key);
            }
        }
        else if(node instanceof FunctionNode n) {
            return visitFunction(n);
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
        // Process DATA statements first
        for(int i = 0; i < parse.size(); i++) {
            if(parse.get(i) instanceof DataNode data)
                visitData(data);
        }

        int index = 0;
        // First Loop through Parsed List
        for (int i = 0; i < parse.size(); i++) {
            // Deals with processing For Loops and whats inside of them
            if(parse.get(i) instanceof ForNode fNode) {
                ForNode currentFor = fNode;
                Node forStatement;
                if(i + 1 < parse.size()) {
                    currentFor.setNext(parse.get(i + 1));
                    forStatement = currentFor.getNext();
                    int ref = parse.indexOf(forStatement);
                    while(((StatementsNode)(forStatement)).getNext() == null) {
                        if(!(parse.get(ref+1) instanceof NextNode) && parse.get(ref + 1) != null) {
                            ((StatementsNode)(forStatement)).setNext(parse.get(ref+1));
                            forStatement = ((StatementsNode)(forStatement)).getNext();
                            ref = parse.indexOf(forStatement);
                        }
                        else {
                            break;
                        }
                    }
                }
                for(int j = index; j < parse.size(); j++) {
                    if(parse.get(j) instanceof NextNode nNode) {
                        nNode.setRef(fNode);
                        if(j + 1 < parse.size()) {
                            currentFor.setAfter(parse.get(j+1));
                            index = j + 1;
                            break;
                        } else {
                            currentFor.setAfter(null);
                        }
                    }
                }
                visitFOR(fNode);
                i = index;
            }
            parse.get(i).visit(this);
            if (i + 1 < parse.size()) {
                ((StatementsNode) parse.get(i)).setNext(parse.get(i + 1));
            }
        }
    }

    public void interpret() {
        visitNodes();
    }
}