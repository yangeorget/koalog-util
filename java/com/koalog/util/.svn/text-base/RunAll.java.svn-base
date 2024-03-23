package com.koalog.util;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * Runs all the tests.
 * @author Yan Georget
 */
public class RunAll {
    //------------------------------------------------------------------------
    // STATIC METHODS
    //------------------------------------------------------------------------
    /** 
     * Configures the logs and runs the test suite.
     * @param args the JVM arguments
     */
    public static void main(String[] args) {
        switch (args.length) {
        case 0:
            BasicConfigurator.configure();
            break;
        case 1:
            if (args[0].endsWith(".properties")) {
                PropertyConfigurator.configure(args[0]);
            } else {
                DOMConfigurator.configure(args[0]);
            }
            break;
        default:
            throw new IllegalArgumentException("Invalid number of arguments.");
        }
        TestRunner.run(suite());
    }
    
    /**
     * Returns a test suite.
     * @return a test
     */
    public static Test suite() {
        TestSuite suite= new TestSuite("all");
        suite.addTest(com.koalog.util.TestSuite.suite());
        suite.addTest(com.koalog.util.cmdline.TestSuite.suite());
        suite.addTest(com.koalog.util.csv.TestSuite.suite());
        suite.addTest(com.koalog.util.console.TestSuite.suite());
        suite.addTest(com.koalog.util.graph.TestSuite.suite());
        suite.addTest(com.koalog.util.list.TestSuite.suite());
        suite.addTest(com.koalog.util.matrix.TestSuite.suite());
        suite.addTest(com.koalog.util.heap.TestSuite.suite());
        suite.addTest(com.koalog.util.zip.TestSuite.suite());
        return suite;
    }
}
