package me.jpalip.lexerparser;
/**
 * @version 3.0 - Parser #3
 * @JP Aliprantis
 * @date 3/30/2021
 * ICSI 311 - Michael Phipps
 **/

import me.jpalip.lexerparser.nodes.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {
    // Needed in order to call function as parameter
    private interface Function {
        Node get(); // Override and call factor() to get proper Node to return in parameter
    }

    private final List<Token> tokens; // Tokenized
    private Token current; // keep track of current Token
    private int index;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.index = 0;
        this.current = tokens.get(index);
    }

    // build the expression
    public Node parse()
    {
        while(current.getType() == TokenType.EOL || current.getType() == TokenType.EOF)
        {
            break;
        }

        // statements() -> statement() -> assignment() || (print() || data || input) -> returns StatementsNode || return expression()
        Node node = statements();
        return node;
    }

    // Accepts  statements, calls Statement() then if no statement exists, call expression()
    public Node statements() {

        List<Node> statements = new ArrayList<>();
        Node node = statement(); // returns (assignment or print Nodes) OR expression();
            if (node != null) {
                statements.add(node);
            }
            // * Potential issue *
            // If node is not null, statements will always be at least 1, only returning 1 statement
            if (statements.size() != 0) {
                return new StatementsNode(statements);
            }
            else {
                return expression();
            }
    }

    // Single Statement, return it's node or null
    public Node statement() {

        // Creates IF Node
        if(current.getType() == TokenType.IF) {
            advance();
            Node firstExp = expression();
            if(isMatch(OPERATORS)) {
                Token op = current; // boolean operator
                advance();
                Node secondExp = expression();
                BooleanOperationNode boolOp = new BooleanOperationNode(firstExp, op, secondExp);
                if(current.getType() == TokenType.THEN) {
                    advance();
                    Token label = current;
                    return new IfNode(boolOp, label);
                }
                return null;
            }
        }

        // Creates pre defined function call
        if(isMatch(FUNCTIONS)) {
            FunctionNode function = (FunctionNode) functionInvocation(); // we know current is now a function so call its Invocation method & return it
            return function;
        }

        // Creates a FOR loop node
        if(current.getType() == TokenType.FOR) {
            advance();
            IntegerNode inc = new IntegerNode(new Token(TokenType.NUMBER, "1"));
            AssignmentNode assignment = (AssignmentNode) assignmentStatement();
            VariableNode var = (VariableNode) assignment.tokenToVariableNode();
            Node start = assignment.expToIntNode();
            advance();
            IntegerNode limit = (IntegerNode) factor();
            if(current.getType() == TokenType.STEP) {
                advance();
                inc = (IntegerNode) factor();
            }
            return new ForNode(var, start, limit, inc);
        }

        if(current.getType() == TokenType.NEXT) {
            advance();
            Node var = factor();
            return new NextNode((VariableNode) var);
        }

        if(current.getType() == TokenType.RETURN) {
            return new ReturnNode(current);
        }
        if(current.getType() == TokenType.GOSUB) {
            advance();
            VariableNode var = (VariableNode) factor();
            return new GosubNode(var);
        }

        // Creates Labeled Statement Node to refer back too
        if(current.getType() == TokenType.LABEL) {
            Token label = current;
            advance();
            if(current.getType() != null) {
                Node exp = statement();
                advance();
                return new LabeledStatementNode(label, exp);
            }
        }

        // Checks if statement is any of the following types and returns it, otherwise statement is null
        Node assignment = assignmentStatement();
        if(assignment != null) {
            return assignment;
        }
        if(current.getType() == TokenType.PRINT) {
            Node print = printStatement();
            if(print != null) {
                return print;
            }
        }
        if(current.getType() == TokenType.DATA) {
            Node data = dataStatement();
            if(data != null) {
                return data;
            }
        }
        if(current.getType() == TokenType.READ) {
            Node read = readStatement();
            if(read != null) {
                return read;
            }
        }
        if(current.getType() == TokenType.INPUT) {
            Node input = inputStatement();
            if(input != null) {
                return input;
            }
        }

        return null;
    }

    // Generates the assignmentNode and Ensures proper syntax (var = expression)
    private Node assignmentStatement() {
        Token token = current;
        if (current.getType() == TokenType.IDENTIFIER)
        {
            // Checks to make sure it is assignment
            if (tokens.get(index + 1).getType() == TokenType.EQUALS) {
                // Since above statement does not advance, move twice to skip over IDENTIFIER and EQUALS to get next token
                advance();
                advance();
                return new AssignmentNode(token, expression());
            }
        }
        return null;
    }

    // These 4 methods all return their respective Node or null
    private Node inputStatement() {
        List<Node> inputStuff = createList();
        return inputStuff == null ? null : new InputNode(inputStuff);
    }

    private Node readStatement() {
        List<Node> readStuff = createList();
        return readStuff == null ? null : new ReadNode(readStuff);
    }

    // Returns DataNode or null
    private Node dataStatement() {
        List<Node> dataStuff = createList();
        return dataStuff == null ? null : new DataNode(dataStuff);
    }

    // Returns PrintNode or null
    private Node printStatement() {
        List<Node> printStuff = createList();
        return printStuff == null ? null : new PrintNode(printStuff);
    }

    // Creates a list of expressions to use in each xxxxStatement() method
    private List<Node> createList() {
        List<Node> exps = new ArrayList<>();
        advance();
        Node print = expression();
        if(print != null) {
            exps.add(print);
            while(current.getType() != TokenType.EOL) {
                advance();
                print = expression();
                exps.add(print);
            }
        }

        return exps;
    }


    // Sets up mathematical statements
    // Makes left node priority for proper calculations in order
    private Node mathOp(Function func, TokenType[] types) {
        // Factoring left node
        Node left = func.get();

        // While left is not null, and current is a operator in our Array
        // While there is more than 1 term, loop through array of ops
        // and build MathOpNodes for each
        while (current.getType() != TokenType.EOF && isMatch(types)) {
            Token operator = current;
            advance();
            Node right = func.get();

            left = new MathOpNode(operator, left, right);
        }

        return left;
    }

    // Created to convert user inputted function -> functionNode()
    private Node functionInvocation() {
        Token func = current;
        advance();
        List<Node> params = new ArrayList<>(); // List of parameters from user input function
        if(current.getType() == TokenType.LPAREN) {
            advance();
            // If nothing in parenthesis return func call w/ no parameters
            if (current.getType() == TokenType.RPAREN) {
                return new FunctionNode(func, params);
            }
            else {
                Node param = statement();
                if (param != null) {
                    params.add(param);
                    while (current.getType() != TokenType.EOL && current.getType() != TokenType.RPAREN) {
                        advance();
                        param = expression();
                        params.add(param);
                    }
                }
            }
        }
        return new FunctionNode(func, params);
    }

    // Helper variables for helper method isMatch()
    private static final TokenType[] EXPRESSION = new TokenType[]{ TokenType.TIMES, TokenType.DIVIDE };
    private static final TokenType[] TERM = new TokenType[] { TokenType.PLUS, TokenType.MINUS };
    private static final TokenType[] OPERATORS = new TokenType[] { TokenType.LESS, TokenType.GREATER, TokenType.GREATER_EQUAL, TokenType.LESS_EQUAL, TokenType.EQUALS, TokenType.NOT_EQUAL};
    private static final TokenType[] FUNCTIONS = new TokenType[] { TokenType.RANDOM, TokenType.LEFT, TokenType.RIGHT, TokenType.MID, TokenType.VALFloat, TokenType.VALInt, TokenType.NUM};

    // TERM { (plus or minus) TERM} #1
    private Node expression()
    {
        return mathOp(() -> term(), EXPRESSION); // creates mathOp with term() and *|/
    }

    // FACTOR {*|/ FACTOR} #2
    private Node term()
    {
        return mathOp(() -> factor(), TERM);
    }

    // number OR ( EXPRESSION ) OR (number) OR functionCall() #3
    private Node factor() {
        Token token = current;

        if(current.getType() == TokenType.IDENTIFIER) {
            advance();
            return new VariableNode(token);
        }
        else if(current.getType() == TokenType.STRING) {
            advance();
            return new StringNode(token);
        }
        else if (isMatch(EXPRESSION))
        {
            advance();
            Node factor = factor();
            return new UnaryOpNode(token, factor);
        }
        else if (current.getType() == TokenType.NUMBER)
        {
            advance();
            return isDecimal(token.getValue()) ? new FloatNode(token) : new IntegerNode(token);
        }
        else if(current.getType() == TokenType.PRINT) {
            advance();
        }
        else if (isMatch(new TokenType[] { TokenType.LPAREN }))
        {
            advance();
            Node expression = expression();

            if (isMatch(new TokenType[] { TokenType.RPAREN }))
            {
                advance();
                return expression;
            }
            else
            {
                throw new InvalidSyntaxError("Expected ')'");
            }
        }
        if(current.getType() != TokenType.EOL) {
            throw new InvalidSyntaxError("Expected statement");
        }
        return null;
    }

    // Advance to next Token to walk through the list
    private Token advance() {
        index++;
        current = index >= tokens.size() ? current : tokens.get(index);
        return current;
    }

    // Helper Methods - Self Explanatory
    private boolean isMatch(TokenType[] types) {
        return Arrays.stream(types).anyMatch(current.getType()::equals);
    }

    private boolean isDecimal(String num) {
        if (num == null) {
            return false;
        }
        return num.contains(".");
    }
}