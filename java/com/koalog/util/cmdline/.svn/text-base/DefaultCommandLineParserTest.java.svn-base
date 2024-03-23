package com.koalog.util.cmdline;

import junit.framework.TestCase;
import org.apache.log4j.Category;

/**
 * @author Yan Georget
 */
public class DefaultCommandLineParserTest extends TestCase {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
        Category.getInstance(DefaultCommandLineParserTest.class);

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a name
     */
    public DefaultCommandLineParserTest(String name) {
        super(name);
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the class.
     */
    public void testDefaultCommandLineParser1() {
        cat.info("testDefaultCommandLineParser1");
        CommandLineParser p = 
            new DefaultCommandLineParser(null,
                                         null,
                                         new CommandLineOption[] {
                new CommandLineOption('i', "inclusions", true),
                new CommandLineOption('e', "exclusions", true),
                new CommandLineOption('r', "remote", true),
                new CommandLineOption('m', "merge", false),
                new CommandLineOption('s', "session", true),
                new CommandLineOption('g', "generate", true),
                new CommandLineOption('o', "only_report", false),
                new CommandLineOption('x', "xsl", true)});
        try {
            p.parse(new String[] {
                "--log4j=f:/yan/src/koalog/kover/tests/logconfig.xml",
                "--session=f:/yan/src/koalog/jcs/tmp/session.ccs",
                "--xsl=com/koalog/kover/xsl/csv.xsl",
                "--generate=f:/yan/src/koalog/jcs/tmp/report.csv",
                "--only_report"});
            assertTrue("f:/yan/src/koalog/kover/tests/logconfig.xml"
                       .equals(p.getValue('l')));
        } catch (InvalidCommandLineException icle) {
            // should not happen
        }
    }
}
/*
 * $Log$
 * Revision 1.7  2003/03/18 16:54:21  yan
 * fixed style
 *
 * Revision 1.6  2002/10/25 08:37:59  yan
 * *** empty log message ***
 *
 * Revision 1.5  2002/10/02 16:11:09  yan
 * added usage
 *
 * Revision 1.4  2002/09/27 09:38:48  yan
 * do not use jdk1.4 specific methods anymore
 *
 * Revision 1.3  2002/09/26 13:18:26  yan
 * fixed bug
 *
 * Revision 1.2  2002/09/26 09:57:09  yan
 * fixed quote bug
 *
 * Revision 1.1  2002/09/16 07:52:08  yan
 * initial revision
 *
 */
