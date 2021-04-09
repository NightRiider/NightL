package me.jpalip.lexerparser;
/**
 * @version 2.0 - Lexer #2
 * @JP Aliprantis
 * @date 2/8/2021
 * ICSI 311 - Michael Phipps
 **/

import me.jpalip.interpret.Interpreter;
import me.jpalip.lexerparser.nodes.Node;
import me.jpalip.lexerparser.nodes.StatementsNode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

// This class takes a text file from arguments and performs a lexical analysis of the text
public class Basic {

    public static void main(String[] args) throws IOException
    {
        // Ensures an argument is present
        if(args.length == 1)
        {
            String filename = args[0]; // store name of file from args
            List<Node> parsed = new ArrayList<>(); // List of Parsed Nodes
            if(new File(filename).exists()) {
                for (String s : Files.readAllLines(Path.of(filename))) {
                    try {
                        List<Token> tokens = new ArrayList<>(); // List of Lexed Tokens
                        // Reads each Line, then Lexed, then Parsed
                        Lexer lexer = new Lexer(s);
                        tokens.addAll(lexer.lex());
                        //System.out.println(new Lexer(s).lex()); // Prints Lexed Result
                        Parser parser = new Parser(tokens);
                        parsed.add(parser.parse());
                    }
                    catch (InvalidCharError e) {
                        System.out.println(e.getLocalizedMessage() + " - " + s);
                    }
                }
                List<Node> statements = new ArrayList<>();
                StatementsNode curr;
                for(int i = 0; i < parsed.size(); i++) {
                    curr = (StatementsNode)parsed.get(i);
                    statements.add(curr.representation().get(0));
                }
                StatementsNode parse = new StatementsNode(statements);
                System.out.println(parse);

                Interpreter interpret = new Interpreter(parse);
                System.out.println(interpret.interpret());
            }
        }
        // Fail with no arguments
        else
        {
            System.err.println("Invalid Number of Arguments.");
        }
    }
}
