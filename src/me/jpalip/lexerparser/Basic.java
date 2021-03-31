package me.jpalip.lexerparser;
/**
 * @version 2.0 - Lexer #2
 * @JP Aliprantis
 * @date 2/8/2021
 * ICSI 311 - Michael Phipps
 **/

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

            if(new File(filename).exists()) {
                for (String s : Files.readAllLines(Path.of(filename))) {
                    try {
                        // Reads each Line, then Lexed, then Parsed
                        List<Token> tokens = new ArrayList<>(); // Holds Lexed results for Parser
                        Lexer lexer = new Lexer(s);
                        tokens.addAll(lexer.lex());
                        System.out.println(new Lexer(s).lex()); // Prints Lexed Result
                        Parser parser = new Parser(tokens);
                        System.out.println(parser.parse()); // Prints Parsed Result
                    }
                    catch (InvalidCharError e) {
                        System.out.println(e.getLocalizedMessage() + " - " + s);
                    }
                }
            }
        }
        // Fail with no arguments
        else
        {
            System.err.println("Invalid Number of Arguments.");
        }
    }
}
