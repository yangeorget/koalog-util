package com.koalog.util.cmdline;

import junit.framework.TestCase;

/**
 * @author Yan Georget
 */
public class CommandLineArgumentsHelperTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a name
     */
    public CommandLineArgumentsHelperTest(String name) {
        super(name);
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the extractArgument method.
     */
    public void testExtractArgument1() {
        String arg = CommandLineArgumentsHelper
            .extractArgument("-a AAA -b BBB -c CCC", 
                             new String[] {"-a"}, 
                             true,
                             null);
        assertTrue("AAA".equals(arg));
    }

    /**
     * Tests the extractArgument method.
     */
    public void testExtractArgument2() {
        String arg = CommandLineArgumentsHelper
            .extractArgument("-a AAA -b BBB -c CCC", 
                             new String[] {"-d"}, 
                             true,
                             "DDD");
        assertTrue("DDD".equals(arg));
    }

    /**
     * Tests the extractArgument method.
     */
    public void testExtractArgument3() {
        String arg = CommandLineArgumentsHelper
            .extractArgument("-a AAA -b BBB -c CCC", 
                             new String[] {"-b"}, 
                             true,
                             null);
        assertTrue("BBB".equals(arg));
    }

    /**
     * Tests the extractArgument method.
     */
    public void testExtractArgument4() {
        String arg = CommandLineArgumentsHelper
            .extractArgument("-a AAA -bBBB -c CCC", 
                             new String[] {"-b"}, 
                             false,
                             null);
        assertTrue("BBB".equals(arg));
    }

    /**
     * Tests the extractArgument method.
     */
    public void testExtractArgument5() {
        String quotedArg = '"' + "BBB" + '"';
        String arg = CommandLineArgumentsHelper
            .extractArgument("-a AAA -b " + quotedArg + " -c CCC", 
                             new String[] {"-b"}, 
                             true,
                             null);
        assertTrue(quotedArg.equals(arg));
    }

    /**
     * Tests the extractArgument method.
     */
    public void testExtractArgument6() {
        String quotedArg = '"' + "BBB";
        String arg = CommandLineArgumentsHelper
            .extractArgument("-b " + quotedArg,
                             new String[] {"-b"}, 
                             true,
                             null);
        assertTrue(arg == null);
    }

    /**
     * Tests the extractArgument method.
     */
    public void testExtractArgument7() {
        String arg = CommandLineArgumentsHelper
            .extractArgument(
                new String[] {
                    "-a", "AAA", "-b", "BBB", "-c", "CCC"
                }, 
                "-a", 
                true,
                null);
        assertTrue("AAA".equals(arg));
    }

    /**
     * Tests the extractArgument method.
     */
    public void testExtractArgument8() {
        String arg = CommandLineArgumentsHelper
            .extractArgument(
                "-a AAA -b BBB -c CCC", 
                "-a", 
                true,
                null);
        assertTrue("AAA".equals(arg));
    }
}
/*
 * $Log$
 * Revision 1.2  2002/06/24 08:56:54  yan
 * log4j not used anymore
 *
 * Revision 1.1  2002/06/19 15:41:56  yan
 * moved from parent directory
 *
 */
