package com.koalog.util.cmdline;

import java.util.Arrays;
import junit.framework.TestCase;
import org.apache.log4j.Category;

/**
 * @author Yan Georget
 */
public class CommandLineParserTest extends TestCase {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
        Category.getInstance(CommandLineParserTest.class);

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a name
     */
    public CommandLineParserTest(String name) {
        super(name);
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the class.
     */
    public void testCommandLineParser1() {
        cat.info("testCommandLineParser1");
        CommandLineParser p = new CommandLineParser(new CommandLineOption[] { 
            new CommandLineOption('v',"verbose", false),
            new CommandLineOption('f',"foo", true),
            new CommandLineOption('d',"debug", false),
            new CommandLineOption('i',"input", true),
            new CommandLineOption('o',"output", true)});
        try {
            p.parse(new String[] {"-vdvd", 
                                  "--input", 
                                  "in", 
                                  "-oout", 
                                  "--foo=bar", 
                                  "toto", 
                                  "-debug"});
            assertTrue(p.getArgumentsAsList()
                       .equals(Arrays.asList(new String[] {"toto", 
                                                           "-debug"})));
            assertTrue(p.isEnabled('v'));
            assertTrue(p.isEnabled('d'));
            assertTrue("in".equals(p.getValue('i')));
            assertTrue("out".equals(p.getValue('o')));
            assertTrue("bar".equals(p.getValue('f')));
        } catch (InvalidCommandLineException icle) {
            // should not happen
        }
    }

    /**
     * Tests the class.
     */
    public void testCommandLineParser2() {
        cat.info("testCommandLineParser2");
        CommandLineParser p = new CommandLineParser();
        try {
            p.parse(new String[] {"--",
                                  "a",
                                  "b c"});
            cat.debug("result " + p.getArgumentsAsString());
            assertTrue(p.getArgumentsAsString()
                       .equals("a "  
                               + CommandLineParser.QUOTE 
                               + "b c" 
                               + CommandLineParser.QUOTE));
        } catch (InvalidCommandLineException icle) {
            // should not happen
        }
    }

    /**
     * Tests the class.
     */
    public void testCommandLineParser3() {
        cat.info("testCommandLineParser3");
        CommandLineParser p = new CommandLineParser();
        try {
            p.parse(new String[] {"--",
                                  "a",
                                  "b\" c"});
            String expected = "a " 
                + CommandLineParser.QUOTE 
                + "b" 
                + CommandLineParser.BACKSLASH 
                + CommandLineParser.QUOTE 
                + " c"
                + CommandLineParser.QUOTE;
            cat.debug("result " + p.getArgumentsAsString());
            cat.debug("expected " + expected);
            assertTrue(p.getArgumentsAsString().equals(expected));
        } catch (InvalidCommandLineException icle) {
            // should not happen
        }
    }

    /**
     * Tests the class.
     */
    public void testCommandLineParser4() {
        cat.info("testCommandLineParser4");
        CommandLineParser p = new CommandLineParser();
        try {
            p.parse(new String[] {"--",
                                  "a",
                                  "a b",
                                  "\"",
                                  "a\" b"});
            String expected = "a "
                + CommandLineParser.QUOTE 
                + "a b"
                + CommandLineParser.QUOTE 
                + " " 
                + CommandLineParser.BACKSLASH 
                + CommandLineParser.QUOTE 
                + " " 
                + CommandLineParser.QUOTE
                + "a" 
                + CommandLineParser.BACKSLASH 
                + CommandLineParser.QUOTE 
                + " b" 
                + CommandLineParser.QUOTE;
            cat.debug("result " + p.getArgumentsAsString());
            cat.debug("expected " + expected);
            assertTrue(p.getArgumentsAsString().equals(expected));
        } catch (InvalidCommandLineException icle) {
            // should not happen
        }
    }

}
/*
 * $Log$
 * Revision 1.9  2003/03/18 16:54:21  yan
 * fixed style
 *
 * Revision 1.8  2002/10/25 08:37:59  yan
 * *** empty log message ***
 *
 * Revision 1.7  2002/10/02 16:11:09  yan
 * added usage
 *
 * Revision 1.6  2002/09/26 09:57:42  yan
 * added cat.info
 *
 * Revision 1.5  2002/09/16 07:53:01  yan
 * *** empty log message ***
 *
 * Revision 1.4  2002/06/26 14:47:14  yan
 * fixed bug
 *
 * Revision 1.3  2002/06/24 08:56:54  yan
 * log4j not used anymore
 *
 * Revision 1.2  2002/06/20 12:10:08  yan
 * changed API
 *
 * Revision 1.1  2002/06/16 14:03:55  yan
 * initial revision
 *
 */
