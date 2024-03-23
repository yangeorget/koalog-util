package com.koalog.util.cmdline;

/**
 * Default command line parser for GUI applications.
 * 
 * <P>Understands the options:</P> 
 * <UL>
 * <LI><CODE>-w (--working DIRECTORY)</CODE> 
 * where <CODE>DIRECTORY</CODE> is a DIRECTORY.</LI>
 * </UL>
 * in addition of the options understood by 
 * <CODE>DefaultCommandLineParser</CODE>.
 *
 * @author Yan Georget
 */
public class GUICommandLineParser extends DefaultCommandLineParser {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------ 
    /** 
     * Auxilliary constructor. 
     * @param name the name of the application
     * @param i18n the name of the i18n resource
     * @param options an array of options
     */
    public GUICommandLineParser(String name,
                                String i18n,
                                CommandLineOption[] options) {
        this(name, i18n);
        addOptions(options);
    }

    /** 
     * Main constructor. 
     * @param name the name of the application
     * @param i18n the name of the i18n resource
     */
    public GUICommandLineParser(String name, String i18n) {
        super(name, i18n);
        CommandLineOption opt = new CommandLineOption("SYNTAX_WORKING",
                                                      "USAGE_WORKING", 
                                                      'w', 
                                                      "working", 
                                                      true);
        defaultOptions.add(opt);
        addOption(opt);
        opt = new CommandLineOption("SYNTAX_DISPLAY",
                                    "USAGE_DISPLAY", 
                                    'd', 
                                    "display", 
                                    true);
        defaultOptions.add(opt);
        addOption(opt);
    }

    //------------------------------------------------------------------------
    // STATIC METHODS
    //------------------------------------------------------------------------ 
    /** 
     * Extracts the width of the window 
     * from a string of the form WIDTHxHEIGHT.
     * @param display a string
     * @return an int
     * @exception InvalidCommandLineException 
     * when the argument string is malformed
     */
    public static int extractWidth(String display) 
        throws InvalidCommandLineException {
        try {
            return new Integer(display.substring(0, display.indexOf('x')))
                .intValue();
        } catch (Exception e) {
            throw new InvalidCommandLineException();
        }
    }

    /** 
     * Extracts the height of the window 
     * from a string of the form WIDTHxHEIGHT.
     * @param display a string
     * @return an int
     * @exception InvalidCommandLineException 
     * when the argument string is malformed
     */
    public static int extractHeight(String display) 
        throws InvalidCommandLineException {
        try {
            return new Integer(display.substring(display.indexOf('x')+1))
                .intValue();
        } catch (Exception e) {
            throw new InvalidCommandLineException();
        }
    }
}
/*
 * $Log$
 * Revision 1.3  2003/03/18 16:54:21  yan
 * fixed style
 *
 * Revision 1.2  2002/11/13 10:33:03  yan
 * added --display option
 *
 * Revision 1.1  2002/10/07 13:50:44  yan
 * initial revision
 *
 */

