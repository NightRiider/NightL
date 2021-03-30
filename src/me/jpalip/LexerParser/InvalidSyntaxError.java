package me.jpalip.LexerParser;
/**
 * @version 1.0 - Parser #1
 * @JP Aliprantis
 * @date 2/28/2021
 * ICSI 311 - Michael Phipps
 */

import java.text.MessageFormat;

public class InvalidSyntaxError extends Error {

    private static final long serialVersionUID = -1252450604619465666L;

    public InvalidSyntaxError(String message)
    {
        super(MessageFormat.format("Invalid syntax, ''{0}''", message));
    }
}