package me.jpalip.LexerParser.nodes;

import me.jpalip.LexerParser.Token;

public class AssignmentNode extends StatementNode {

    public AssignmentNode(Token token, Node expression)
    {
        super(token, expression);
    }

    @Override
    public Node representation()
    {
        return expression;
    }

    public Node tokenToVariableNode() { return new VariableNode(token); }

    public Node expToIntNode() { return expression; }

    @Override
    public String toString()
    {
        return "AssignmentNode(" + token.getValue() + ", " + expression + ")";
    }
}