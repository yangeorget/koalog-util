package com.koalog.util.cmdline;

/**
 * Provides utility methods for dealing with command-line arguments.
 * @author Matthieu Philip
 * @author Yan Georget
 */
public class CommandLineArgumentsHelper {
    //------------------------------------------------------------------------
    // STATIC METHODS
    //------------------------------------------------------------------------ 
    /**
     * <P>Extracts an argument from the command line arguments. This version
     * processes a command line specified as a single string.</P>
     *
     * @param args the command line arguments 
     * (quotes can be used but shouldn't be quoted)
     * @param tag the tag used to find the argument
     * @param space a boolean indicating 
     * if there is a space between the tag and the argument
     * @param defaultArg a default value
     * @return the argument or the default value if it is not found
     */
    public static String extractArgument(String args,
                                         String tag,
                                         boolean space,
                                         String defaultArg) {
        return extractArgument(args, new String[] {tag}, space, defaultArg);
    }

    /**
     * <P>Extracts an argument from the command line arguments. This version
     * processes a command line specified as a string array.</P>
     *
     * @param args the command line arguments 
     * (quotes can be used but shouldn't be quoted)
     * @param tag the tag used to find the argument
     * @param space a boolean indicating 
     * if there is a space between the tag and the argument
     * @param defaultArg a default value
     * @return the argument or the default value if it is not found
     */
    public static String extractArgument(String[] args,
                                         String tag,
                                         boolean space,
                                         String defaultArg) {
        String flatArgs = "";
        for (int i = 0; i < args.length; i++) {
            flatArgs += args[i] + " ";
        }
        return extractArgument(flatArgs, 
                               new String[] {tag}, 
                               space, 
                               defaultArg);
    }

    /**
     * <P>Extracts an argument from the command line arguments. This version
     * processes a command line specified as a single string.</P>
     * <P>The tag identifying the argument can have synonyms. That is why
     * it is specified by a string array.</P>
     *
     * @param args the command line arguments 
     * (quotes can be used but shouldn't be quoted)
     * @param tags an array of tags used to find the argument
     * @param space a boolean indicating 
     * if there is a space between the tag and the argument
     * @param defaultArg a default value
     * @return the argument or the default value if it is not found
     */
    public static String extractArgument(String args,
                                         String[] tags,
                                         boolean space,
                                         String defaultArg) {
        for (int i=0; i<tags.length; i++) {
            String tag = tags[i];
            if (space) {
                tag += " "; 
            }
            String arg = extractArgument(args, tag);
            if (arg != null) {
                return arg;
            }
        }
        return defaultArg;
    }

    
    /**
     * Extracts an argument from the command line arguments.
     * @param args the command line arguments
     * @param tag a tag used to find the argument
     * @return the argument or null if it is not found
     */
    private static String extractArgument(String args, 
                                          String tag) {
        args = " " + args + " ";
        int startIndex = args.indexOf(" " + tag);
        if (startIndex < 0) {
            return null;
        }
        return extractWord(args.substring(startIndex + tag.length() + 1));
    }

    /**
     * Extracts a word for the command line arguments.
     * <P>TODO: allow to escape the quotes
     * @param args the command line arguments
     * @return the word 
     */
    public static String extractWord(String args) {
        boolean cite = false;
        for (int endIndex = 0; endIndex < args.length(); endIndex++) {
            if (!cite && args.charAt(endIndex) == ' ') {
                return args.substring(0, endIndex);
            }
            if (args.charAt(endIndex) == '"') {
                cite = !cite;
            }
        }
        // quote error or no value at the end of the line
        return null;
    }
}
/*
 * $Log$
 * Revision 1.3  2003/05/31 00:10:59  yan
 * added extractWord
 *
 * Revision 1.2  2002/06/24 08:56:54  yan
 * log4j not used anymore
 *
 * Revision 1.1  2002/06/19 15:41:56  yan
 * moved from parent directory
 *
 */
