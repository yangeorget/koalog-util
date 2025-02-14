package com.koalog.util.graph;

import java.lang.reflect.Constructor;
import junit.framework.Test;
import junit.textui.TestRunner;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * Runs all the tests of the graph package.
 * @author Matthieu Philip
 * @author Yan Georget
 */
public class TestSuite extends junit.framework.TestSuite {
    /**
     * Construct a suite with all test classes found in the package.
     * So far, no automatic way to load all the package classes has been
     * found. The classes have to be manually added here in the constructor.
     * @param name the name of this test suite.
     */
    public TestSuite(String name) {
        super(name);
        addTestSuite(NGraphTest.class);
        addTestSuite(DijkstraTest.class);
        addTestSuite(FlowGraphTest.class);
        addTestSuite(FastGraphTest.class);
        addTestSuite(GraphTest.class);
        addTestSuite(MatrixGraphTest.class);
    }

    /**
     * Run a specified test or all tests in this package.
     * Use as follows:
     * <P><TT>TestSuite [&lt;class name&gt; &lt;function name&gt;] [&lt;log 
     * configuration filename&gt;]</TT>
     * <BR>where:
     * <UL>
     * <LI>&lt;class name&gt; is the name of the test class in case of a
     * single test execution. If this argument is specified, then the
     * following one must be specified as well.</LI>
     * <LI>&lt;function name&gt; is the name of the test function to execute
     * in the case of a single test execution. If this argument is specified,
     * then the previous one must be specified as well.</LI>
     * <LI>&lt;log configuration filename&gt; is the name of the Log4j
     * configuration file.</LI>
     * </UL>
     * @param args the JVM arguments as an array of strings
     * @throws Exception when the arguments are not valid
     */
    public static void main(String[] args) throws Exception {
        if (args.length > 3) {
            throw new IllegalArgumentException("Invalid number of arguments.");
        }
        String logFilename = null;
        Test test;
        if (args.length % 2 == 1) {
            logFilename = args[args.length - 1];
        }
        // enable logging
        if (logFilename != null) {
            if (logFilename.endsWith(".properties")) {
                // didn't find a better way than checking the file extension
                PropertyConfigurator.configure(logFilename);
            } else {
                DOMConfigurator.configure(logFilename);
            }
        } else {
            BasicConfigurator.configure();
        }
        // now instantiate the test class
        if (args.length > 1) {
            // we're in the single test case
            // get the class
            Class testClass = Class.forName(args[0]);
            // get the constructor
            Constructor c = testClass.getConstructor(
                new Class[] {String.class});
            // instantiate
            test = (Test)c.newInstance(new Object[] {args[1]});
        } else {
            // run all tests
            test = suite();
        }
        TestRunner.run(test);
    }

    /**
     * Build a test suite for all test classes in this package.
     * @return the resulting test suite.
     */
    public static Test suite() {
        return new TestSuite("graph");
    }
}
/*
 * $Log$
 * Revision 1.11  2005/07/11 15:33:03  yan
 * added method for maximumMatching
 *
 * Revision 1.10  2004/11/25 08:40:52  yan
 * added a test for MatrixGraph
 *
 * Revision 1.9  2004/11/24 14:54:00  yan
 * removed tests
 *
 * Revision 1.8  2003/10/07 18:31:46  yan
 * removed tests
 *
 * Revision 1.7  2003/10/07 11:01:54  yan
 * added tests
 *
 * Revision 1.6  2003/08/07 12:40:23  yan
 * added tests
 *
 * Revision 1.5  2003/07/18 13:37:07  yan
 * added tests
 *
 * Revision 1.4  2003/07/17 15:16:47  yan
 * added tests
 *
 * Revision 1.3  2002/05/02 16:02:54  yan
 * moved
 *
 * Revision 1.2  2002/03/26 15:06:00  yan
 * Style related changes
 *
 * Revision 1.1  2002/03/25 18:46:46  yan
 * initial revision
 *
 */
