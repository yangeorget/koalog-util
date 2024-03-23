package com.koalog.util.cmdline;

/**
 * This class represents a command line option.
 *
 * @author Yan Georget
 */
public class CommandLineOption {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    private char shortName;
    private String longName;
    private boolean argument;
    private Character key;
    private String syntaxKey;
    private String usageKey;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------ 
    /** 
     * Auxilliary constructor.
     * @param shortName the option short name 
     * @param argument a boolean indicating if an argument is expected
     */
    public CommandLineOption(char shortName, 
                             boolean argument) {
        this(null, null, shortName, null, argument);
    }

    /** 
     * Auxilliary constructor.
     * @param shortName the option short name 
     * @param longName the option long name 
     * @param argument a boolean indicating if an argument is expected
     */
    public CommandLineOption(char shortName, 
                             String longName, 
                             boolean argument) {
        this(null, null, shortName, longName, argument);
    }

    /** 
     * Main constructor.
     * @param syntaxKey a string
     * @param usageKey a string
     * @param shortName the option short name 
     * @param longName the option long name 
     * @param argument a boolean indicating if an argument is expected
     */
    public CommandLineOption(String syntaxKey,
                             String usageKey,
                             char shortName, 
                             String longName, 
                             boolean argument) {
        this.shortName = shortName;
        this.longName = longName;
        this.argument = argument;
        this.syntaxKey = syntaxKey;
        this.usageKey = usageKey;
        key = new Character(shortName);
    }

    //------------------------------------------------------------------------
    // ACCESSORS
    //------------------------------------------------------------------------ 
    /**
     * Returns a key of the option.
     * @return a char object
     */
    public Character getKey() {
        return key;
    }

    /** 
     * Returns the long name of the option.
     * @return a string
     */
    public String getLongName() {
        return longName;
    }

    /** 
     * Returns the key for the usage string of the option.
     * @return a string
     */
    public String getUsageKey() {
        return usageKey;
    }

    /** 
     * Returns the key for the syntax string of the option.
     * @return a string
     */
    public String getSyntaxKey() {
        return syntaxKey;
    }
    
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------ 
    /**
     * Converts the command line option to a string.
     * @return a string
     */
    public String toString() {
        return shortName + "/" + longName + "/" + argument;
    }

    /**
     * Returns true if the option requires an argument.
     * @return a boolean
     */
    public boolean requiresArgument() {
        return argument;
    }
}
/*
 * $Log$
 * Revision 1.3  2002/10/02 16:11:09  yan
 * added usage
 *
 * Revision 1.2  2002/06/24 08:56:54  yan
 * log4j not used anymore
 *
 * Revision 1.1  2002/06/16 14:03:55  yan
 * initial revision
 *
 */
