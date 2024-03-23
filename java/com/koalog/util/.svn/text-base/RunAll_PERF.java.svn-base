package com.koalog.util;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * Runs all the tests.
 * @author Yan Georget
 */
public class RunAll_PERF extends RunAll {
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
        TestRunner.run(time(suite()));
    }

    private static Test time(Test test) {
        return time(test, "");
    }
    
    
    private static Test time(Test test, String prefix) {
        if (test instanceof TestSuite) {
            return timeSuite((TestSuite) test, 
                             prefix + ";" + ((TestSuite) test).getName());
        } else {
            return timeTest((TestCase) test, 
                            prefix + ";" + ((TestCase) test).getName());
        }
    }

    private static TestSuite timeSuite(TestSuite in, String prefix) {
        TestSuite out = new TestSuite();
        java.util.Enumeration tests = in.tests();
        while (tests.hasMoreElements()) {
            out.addTest(time((Test) tests.nextElement(), prefix));
        }
        return out;
    }

    private static Test timeTest(TestCase testCase, String prefix) {
        return new TimedTest(testCase, prefix);
    }

    //------------------------------------------------------------------------
    // INNER CLASSES
    //------------------------------------------------------------------------
    /**
     * A timed test.
     * @author Yan Georget
     */
    private static class TimedTest extends junit.extensions.TestDecorator {
        String prefix;

        /**
         * Sole constructor.
         * @param test a test
         * @param prefix a string
         */
        public TimedTest(Test test, String prefix) {
            super(test);
            this.prefix = prefix;
        }
 
        /** @see junit.extensions.TestDecorator */
        public void run(junit.framework.TestResult result) {
            long time = System.currentTimeMillis();
            super.run(result);
            time = System.currentTimeMillis() - time;
            System.out.println(prefix + ";" + time); 
        }
    }
}

/*
 * $Log$
 * Revision 1.1  2003/10/01 18:15:56  yan
 * initial revision
 *
 */
