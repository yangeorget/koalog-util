package com.koalog.util.console;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.IOException;
import java.io.PrintStream;

import junit.framework.TestCase;

import org.apache.log4j.Category;

/**
 * Tests the StandardConsole class.
 * @author Matthieu Philip
 */
public class StandardConsoleTest extends TestCase {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
        Category.getInstance(StandardConsoleTest.class);

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name a name
     */
    public StandardConsoleTest(String name) {
        super(name);
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Test that the class is a singleton
     */
    public void testSingleton() {
        StandardConsole c1 = StandardConsole.getInstance();
        StandardConsole c2 = StandardConsole.getInstance();
        assertNotNull(c1);
        assertTrue("Instances are distinct.", c1 == c2);
    }

    /**
     * Test the outputs.
     * @throws IOException this should not happen
     * @throws InterruptedException this should not happen
     */
    public void testOut() throws IOException, InterruptedException {
        StandardConsole c = StandardConsole.getInstance();
        // create a file that will collect the output
        File tmp = File.createTempFile("console", ".txt");
        PrintStream out = new PrintStream(new FileOutputStream(tmp));
        PrintStream oldOut = System.out;
        System.setOut(out);
        try {
            // create two output streams
            String s1 ="string 1";
            String s2 ="string 2";
            ByteArrayInputStream out1 =
                new ByteArrayInputStream(s1.getBytes());
            c.addOut(out1);
            ByteArrayInputStream out2 =
                new ByteArrayInputStream(s2.getBytes());
            c.addOut(out2);
            // give enough time to make sure the output will reach
            // std out
            Thread.sleep(1000);
        } finally {
            System.setOut(oldOut);
        }
        out.close();
        // control the file
        boolean test1 = false;
        boolean test2 = false;
        BufferedReader reader = new BufferedReader(new FileReader(tmp));
        String line;
        // cannot warranty the order or that no additional output is present
        while ((line = reader.readLine()) != null) {
            test1 |= line.equals("string 1");
            test2 |= line.equals("string 2");
        }
        assertTrue("Output 1 not found.", test1);
        assertTrue("Output 2 not found.", test2);
        tmp.delete();
    }

    /**
     * Test the errors.
     * @throws IOException this should not happen
     * @throws InterruptedException this should not happen
     */
    public void testErr() throws IOException, InterruptedException {
        StandardConsole c = StandardConsole.getInstance();
        // create a file that will collect the output
        File tmp = File.createTempFile("console", ".txt");
        PrintStream out = new PrintStream(new FileOutputStream(tmp));
        PrintStream oldOut = System.err;
        System.setErr(out);
        try {
            // create two output streams
            String s1 ="string 1";
            String s2 ="string 2";
            ByteArrayInputStream out1 =
                new ByteArrayInputStream(s1.getBytes());
            c.addErr(out1);
            ByteArrayInputStream out2 =
                new ByteArrayInputStream(s2.getBytes());
            c.addErr(out2);
            // give enough time to make sure the output will reach
            // std out
            Thread.sleep(1000);
        } finally {
            System.setErr(oldOut);
        }
        out.close();
        // control the file
        boolean test1 = false;
        boolean test2 = false;
        BufferedReader reader = new BufferedReader(new FileReader(tmp));
        String line;
        // cannot warranty the order or that no additional output is present
        while ((line = reader.readLine()) != null) {
            test1 |= line.equals("string 1");
            test2 |= line.equals("string 2");
        }
        assertTrue("Error 1 not found.", test1);
        assertTrue("Error 2 not found.", test2);
        tmp.delete();
    }


    /**
     * Test the inputs.
     * @throws IOException this should not happen
     * @throws InterruptedException this should not happen
     */
    public void testIn() throws IOException, InterruptedException {
        StandardConsole c = StandardConsole.getInstance();
        // create a finite input stream
        String inStr = "hello\n";
        ByteArrayInputStream in = new ByteArrayInputStream(inStr.getBytes());
        InputStream oldIn = System.in;
        System.setIn(in);
        assertTrue("Cannot change standard input.", System.in != oldIn);
        ByteArrayOutputStream in1 = null;
        try {
            // create an input streams
            in1 = new ByteArrayOutputStream();
            c.addIn(in1);
            // give enough time to make sure the inputs will be reached
            Thread.sleep(1000);
        } finally {
            System.setIn(oldIn);
        }
        in.close();
        // control the file
        assertEquals("hello\n", in1.toString());
    }

}
/*
 * $Log$
 * Revision 1.2  2003/03/18 16:55:20  yan
 * fixed style
 *
 * Revision 1.1  2002/10/12 00:09:45  mphilip
 * Initial revision.
 *
 */
