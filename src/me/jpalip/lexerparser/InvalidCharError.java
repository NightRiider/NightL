package me.jpalip.lexerparser;
/**
 * @version 2.0 - Lexer #2
 * @JP Aliprantis
 * @date 2/20/2021
 * ICSI 311 - Michael Phipps
 */

// Class to create custom error formatting
import java.text.MessageFormat;


public class InvalidCharError extends Error {

    private static final long serialVersionUID = -278329155739649599L;

    public InvalidCharError(String message)
    {
        super(MessageFormat.format("''{0}''", message));
    }
}