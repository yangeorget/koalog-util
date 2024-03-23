package com.koalog.util.csv;

import java.util.List;
import java.util.ArrayList;

/** 
 * A simple class to parse comma-separated values (CSV).
 *
 * <P>Unquotes all the fields, accepts separators in quoted fields.</P>
 *
 * @author Yan Georget
 */
public class Parser {   
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static final char DEFAULT_SEP = ';';
    
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** The fields in the current string. */
    protected List list;
    
    /** The separator char for this parser. */
    protected char fieldSep;
 
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /** 
     * Constructs a CSV parser with the default separator. 
     */
    public Parser() {
        this(DEFAULT_SEP);
    }
    
    /** 
     * Constructs a CSV parser with a given separator. 
     * @param sep the separator as a character
     */
    public Parser(char sep) {
        fieldSep = sep;
        list = new ArrayList();
    }

   //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** 
     * Breaks the input string into fields.
     * @param line the input string
     * @return an array of strings containing each field
     * from the original as a string, in order
     */
    public String[] parse(String line) {
        if (line.length() == 0) {
            return new String[] {};
        }
        list.clear();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < line.length(); i++) {
            sb.setLength(0);
            if (i < line.length() && line.charAt(i) == '"') {
                i = advQuoted(line, sb, ++i); // skip quote
            } else {
                i = advPlain(line, sb, i);
            }
            list.add(sb.toString());
        }
        return (String[])list.toArray(new String[] {});
    }
    
    /** 
     * Parses a quoted field.
     * @param s the string to parse
     * @param sb a buffer to store the result
     * @param i the current index
     * @return the index of the next separator 
     */
    protected int advQuoted(String s, StringBuffer sb, int i) {
        int j;
        int len= s.length();
        for (j=i; j<len; j++) {
            if (s.charAt(j) == '"' && j+1 < len) {
                if (s.charAt(j+1) == '"') {
                    j++; // skip escape char
                } else if (s.charAt(j+1) == fieldSep) { // next delimeter
                    j++; // skip end quotes
                    break;
                }
            } else if (s.charAt(j) == '"' 
                       && j+1 == len) { // end quotes at end of line
                break; //done
            }
            sb.append(s.charAt(j));     // regular character.
        }
        return j;
    }
    
    /** 
     * Parses a non quoted field.
     * @param s the string to parse
     * @param sb a buffer to store the result
     * @param i the current index
     * @return the index of the next separator 
     */
    protected int advPlain(String s, StringBuffer sb, int i) {
        int j = s.indexOf(fieldSep, i); // look for separator
        if (j == -1) { // none found
            sb.append(s.substring(i));
            return s.length();
        } else {
            sb.append(s.substring(i, j));
            return j;
        }
    }
}
/*
 * $Log$
 * Revision 1.1  2003/10/02 09:42:40  yan
 * initial revision
 *
 */
