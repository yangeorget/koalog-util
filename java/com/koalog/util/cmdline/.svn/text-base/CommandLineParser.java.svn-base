package com.koalog.util.cmdline;

import java.util.List;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.log4j.Category;

/**
 * Parses a command-line (represented by a string array) 
 * of the form: [OPTIONS] ARGUMENTS, with the
 * following conventions (POSIX, GNU and custom):
 * 
 * <P>An option is a hyphen followed by a single alphanumeric character, 
 * like this: -o. 
 * An option may require an argument
 * which must appear immediately after the option, 
 * for example: -o argument or -oargument. 
 * Options may have long names: --option, if the option requires an argument
 * --option argument and --option=argument can be used.
 * </P>
 *
 * <P>Options that do not require arguments can be grouped after a hyphen, 
 * for example, -lst is equivalent to -l -s -t. 
 * Options can appear in any order; thus -lst is equivalent to -tls. 
 * </P> 
 * 
 * <P>Options can appear multiple times. In this case, 
 * and when they have arguments, the last argument is taken into account.</P> 
 *
 * <P>A non-recognized option generates an error.</P>
 *
 * <P>Options precede other nonoption arguments: -lst nonoption. 
 * If necessary, the -- argument can be used to terminate options. </P> 
 * 
 * <P>The - option is typically used to represent one of 
 * the standard input streams (not implemented yet).</P> 
 *
 * @author Yan Georget
 */
public class CommandLineParser {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
        Category.getInstance(CommandLineParser.class);

    /** The quote as a string. */
    static final String QUOTE = "\"";
    /** The backslash as a string. */
    static final String BACKSLASH = "\\";
    /** The quote as a char. */
    public static final char QUOTE_CHAR = 34;
    private static final String OWSN = "Option with short name";
    private static final String OWLN = "Option with long name";

    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** A map mapping short names to options. */
    protected Map shortNames;
    private Map longNames;
    private Map parsedOptions;
    private String[] arguments;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------ 
    /** 
     * Internal constructor. 
     */
    protected CommandLineParser() {
        shortNames = new HashMap();
        longNames = new HashMap();
    }

    /** 
     * Auxilliary constructor. 
     * @param options a list of options
     */
    public CommandLineParser(List options) {
        this();
        addOptions(options);
    }

    /** 
     * Main constructor. 
     * @param options an array of options
     */
    public CommandLineParser(CommandLineOption[] options) {
        this();
        addOptions(options);
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------ 
    /** 
     * Adds an array of options.
     * @param options an array of command line options
     */
    public void addOptions(CommandLineOption[] options) {
        for (int i=0; i<options.length; i++) {
            addOption(options[i]);
        }
    }

    /** 
     * Adds a list of options.
     * @param options a list of command line options
     */
    public void addOptions(List options) {
        Iterator i = options.iterator();
        while (i.hasNext()) {
            addOption((CommandLineOption) i.next());
        }
    }

    /** 
     * Add an option.
     * @param option a command line option
     */
    public void addOption(CommandLineOption option) {
        shortNames.put(option.getKey(), option);
        if (option.getLongName() != null) {
            longNames.put(option.getLongName(), option);
        }
    }

    /**
     * Parses the command line.
     * @param args an array of string
     * @throws InvalidCommandLineException when an option is not recognized
     */
    public void parse(String[] args) throws InvalidCommandLineException {
        parsedOptions = new HashMap();
        int argumentsIndex = parseOptions(args);
        arguments = new String[args.length-argumentsIndex];
        System.arraycopy(args, argumentsIndex, 
                         arguments, 0, 
                         args.length-argumentsIndex);
    }

    /**
     * Parses the options of command line.
     * @param args an array of string
     * @return the index of the first argument which is not an option
     * @throws InvalidCommandLineException when an option is not recognized
     */
    private int parseOptions(String[] args) 
        throws InvalidCommandLineException {
        int argsIndex = 0;
        while (argsIndex < args.length) {
            String arg = args[argsIndex];
            if ("--".equals(arg)) { // end of options
                return argsIndex+1;
            } else {
                if ('-' == arg.charAt(0)) { // option(s)
                    argsIndex = parseOptions(args, arg, argsIndex);
                } else {
                    return argsIndex;
                }
            }
        }
        return argsIndex;
    }

    /**
     * Parse a option or a group of options of command line.
     * @param args an array of string
     * @param arg a string
     * @param argsIndex the current index
     * @return the index of the next argument to look at
     * @throws InvalidCommandLineException when an option is not recognized
     */
    private int parseOptions(String[] args, String arg, int argsIndex) 
        throws InvalidCommandLineException {
        if ('-' == arg.charAt(1)) { // option with long name
            arg = arg.substring(2);
            Iterator i = longNames.keySet().iterator();
            while (i.hasNext()) {
                String longName = (String) i.next();
                CommandLineOption option = 
                    (CommandLineOption) longNames.get(longName);
                Character shortName = option.getKey();
                if (arg.startsWith(longName)) {
                    arg = arg.substring(longName.length());
                    if (option.requiresArgument()) {
                        if ("".equals(arg)) { //--option arg
                            if (argsIndex+1 < args.length) {
                                parsedOptions.put(shortName, args[argsIndex+1]);
                                return argsIndex+2;
                            } else {
                                throw new InvalidCommandLineException
                                    (OWLN+", missing argument after 'space'");
                            }
                        } else { //--option=arg
                            if (('=' == arg.charAt(0)) && (arg.length() > 1)) {
                                parsedOptions.put(shortName, arg.substring(1));
                                return argsIndex+1;
                            } else {
                                throw new InvalidCommandLineException
                                    (OWLN+", missing argument after 'equal'");
                            }
                        }
                    } else {
                        if ("".equals(arg)) {
                            parsedOptions.put(shortName, null);
                            return argsIndex+1;
                        } else {
                            throw new InvalidCommandLineException
                                (OWLN+", unexpected argument");
                        }
                    }
                }
            }
            throw new InvalidCommandLineException
                (OWLN+", unrecognized option");
        } else { // option(s) with short name
            arg = arg.substring(1);
            Iterator i = shortNames.keySet().iterator();
            while (i.hasNext()) {
                Character shortName = (Character) i.next();
                if (shortName.charValue() == arg.charAt(0)) {
                    if (((CommandLineOption) shortNames.get(shortName))
                        .requiresArgument()) {
                        arg = arg.substring(1);
                        if ("".equals(arg)) { //-o arg
                            if (argsIndex+1 < args.length) {
                                parsedOptions.put(shortName, args[argsIndex+1]);
                                return argsIndex+2;
                            } else {
                                throw new InvalidCommandLineException
                                    (OWSN+", missing argument after 'space'");
                            }
                        } else { //-oarg
                            if (arg.length() > 0) {
                                parsedOptions.put(shortName, arg);
                                return argsIndex+1;
                            } else {
                                throw new InvalidCommandLineException
                                    (OWSN+", missing argument after option");
                            }
                        }
                    } else {
                        // we do some redundant work for the first option
                        for (int j=0; j<arg.length(); j++) {
                            Character key = new Character(arg.charAt(j));
                            if (shortNames.containsKey(key)) {
                                CommandLineOption option = 
                                    (CommandLineOption) shortNames.get(key);
                                if (option.requiresArgument()) {
                                    throw new InvalidCommandLineException();
                                } else {
                                    parsedOptions.put(key, null);
                                }
                            } else {
                                throw new InvalidCommandLineException();
                            } 
                        }
                        return argsIndex+1;
                    }
                } 
            }
            throw new InvalidCommandLineException
                (OWSN+", unrecognized option");
        }
    }
    
    /**
     * Returns the argument corresponding to an option.
     * @param option a char
     * @return a string
     */
    public String getValue(char option) {
        CommandLineOption opt = 
            (CommandLineOption) shortNames.get(new Character(option));
        if (opt == null) {
            return null;
        } else {
            return getValue(opt);
        }
    }

    /**
     * Returns the argument corresponding to an option.
     * @param option a string
     * @return a string
     */
    public String getValue(String option) {
        CommandLineOption opt = (CommandLineOption) longNames.get(option);
        if (opt == null) {
            return null;
        } else {
            return getValue(opt);
        }
    }

    /**
     * Returns the argument corresponding to an option.
     * @param option an option
     * @return a string
     */
    public String getValue(CommandLineOption option) {
        if (option.requiresArgument()) {
            return (String) parsedOptions.get(option.getKey());
        } else {
            throw new BadOptionUsageException();
        }
    }
    
    /**
     * Returns true if the option is set.
     * @param option a char
     * @return a boolean
     */
    public boolean isEnabled(char option) {
        CommandLineOption opt = 
            (CommandLineOption) shortNames.get(new Character(option));
        if (opt == null) {
            return false;
        } else {
            return isEnabled(opt);
        }
    }

    /**
     * Returns true if the option is set.
     * @param option a string
     * @return a boolean
     */
    public boolean isEnabled(String option) {
        CommandLineOption opt = (CommandLineOption) longNames.get(option);
        if (opt == null) {
            return false;
        } else {
            return isEnabled(opt);
        }
    }

    /**
     * Returns true if the option is set.
     * @param option an option
     * @return a boolean
     */
    public boolean isEnabled(CommandLineOption option) {
        if (!option.requiresArgument()) {
            return parsedOptions.containsKey(option.getKey());
        } else {
            throw new BadOptionUsageException();
        }
    }

    /**
     * Returns a list of the non-optional arguments.
     * @return a list
     */
    public List getArgumentsAsList() {
        return Arrays.asList(arguments);
    }

    /**
     * Returns the non-optional arguments as a string.
     * @return a string
     */
    public String getArgumentsAsString() {
        String s = "";
        for (int i=0; i<arguments.length; i++) {
            s += quoteIfNecessary(escapeQuote(arguments[i]));
            if (i < arguments.length-1) {
                s += " ";
            }
        }
        return s;
    }
    
    /**
     * Quotes a string if necessary (ie if it contains a space).
     * @param s a string
     * @return a string
     */
    private String quoteIfNecessary(String s) {
        if (s.indexOf(" ") >= 0) {
            return quote(s);
        } else {
            return s;
        }
    }

    /**
     * Quotes a string.
     * @param s a string
     * @return a string
     */
    private String quote(String s) {
        return QUOTE + s + QUOTE;
    }

    /**
     * Escapes a quote.
     * @param s a string
     * @return a string
     */
    private String escapeQuote(String s) {
        String tmp = "";
        int i = 0;
        while (i<s.length()) {
            char c = s.charAt(i++);
            if (c == QUOTE_CHAR) {
                tmp += BACKSLASH + QUOTE;
            } else {
                tmp += new Character(c);
            }
        }
        return tmp;
    }

    //------------------------------------------------------------------------
    // ACCESSORS
    //------------------------------------------------------------------------ 
    /**
     * Returns the non-optional arguments.
     * @return an array of string
     */
    public String[] getArguments() {
        return arguments;
    }

    /**
     * Returns the map of the options
     * @return a map
     */
    public Map getParsedOptions() {
        return parsedOptions;
    }
}
/*
 * $Log$
 * Revision 1.12  2003/03/18 17:34:30  yan
 * obfuscation related fixes
 *
 * Revision 1.11  2003/03/18 16:54:21  yan
 * fixed style
 *
 * Revision 1.10  2002/10/18 10:07:06  mphilip
 * Removed class.getName() for obfuscation.
 *
 * Revision 1.9  2002/10/02 16:11:09  yan
 * added usage
 *
 * Revision 1.8  2002/09/27 09:38:48  yan
 * do not use jdk1.4 specific methods anymore
 *
 * Revision 1.7  2002/09/26 13:18:26  yan
 * fixed bug
 *
 * Revision 1.6  2002/09/26 09:57:08  yan
 * fixed quote bug
 *
 * Revision 1.5  2002/09/16 07:53:01  yan
 * *** empty log message ***
 *
 * Revision 1.4  2002/06/24 08:56:54  yan
 * log4j not used anymore
 *
 * Revision 1.3  2002/06/20 12:10:08  yan
 * changed API
 *
 * Revision 1.2  2002/06/19 15:42:44  yan
 * added useful signatures
 *
 * Revision 1.1  2002/06/16 14:03:55  yan
 * initial revision
 *
 */
