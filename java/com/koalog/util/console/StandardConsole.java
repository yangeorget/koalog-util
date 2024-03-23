package com.koalog.util.console;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.log4j.Category;

/**
 * The standard console encapsulation.
 *
 * @author Matthieu Philip
 */
public class StandardConsole extends BaseConsole {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
        Category.getInstance(StandardConsole.class);

    /** The singleton instance. */
    private static StandardConsole _singleton;

    //------------------------------------------------------------------------
    // INSTANCE PROPERTIES
    //------------------------------------------------------------------------
    /** The input reader thread. */
    Thread _inputReader;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Default constructor.
     */
    private StandardConsole() {
    }

    //------------------------------------------------------------------------
    // INSTANCE METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.util.console.BaseConsole */
    protected void appendOut(String str) {
        System.out.print(str);
    }

    /** @see com.koalog.util.console.BaseConsole */
    protected void appendErr(String str) {
        System.err.print(str);
    }

    /** @see com.koalog.util.console.Console */
    public synchronized void addIn(OutputStream in) {
        super.addIn(in);
        if (_inputReader == null) {
            _inputReader = new StandardInputReader(this);
            _inputReader.start();
        }
    }

    //------------------------------------------------------------------------
    // STATIC METHODS
    //------------------------------------------------------------------------
    /**
     * Get the singleton instance of this class.
     * @return The unique instance of this class.
     */
    public static synchronized StandardConsole getInstance() {
        if (_singleton == null) {
            _singleton = new StandardConsole();
        }
        return _singleton;
    }

    //------------------------------------------------------------------------
    // INNER CLASSES
    //------------------------------------------------------------------------
    /**
     * A class that reads from the console input and dispatches
     * the data to the console input listeners.
     * @author Matthieu Philip
     */
    protected class StandardInputReader extends Thread {
        /** The parent console. */
        BaseConsole _console;

        /**
         * Default constructor.
         * @param console The parent console.
         */
        public StandardInputReader(BaseConsole console) {
            super("Base Console Input Reader");
            _console = console;
            setDaemon(true);
        }

        /**
         * The main loop.
         */
        public void run() {
            BufferedReader in = new BufferedReader(
                new InputStreamReader(System.in));
            String str;
            try {
                while ((str = in.readLine()) != null) {
                    _console.dispatch(str + "\n");
                }
            } catch(IOException ioe) {
                cat.debug("Failed to read from the console input.", ioe);
            }
        }
    }
}
/*
 * $Log$
 * Revision 1.3  2003/03/18 16:55:20  yan
 * fixed style
 *
 * Revision 1.2  2002/10/12 00:08:53  mphilip
 * Added missing eol.
 * Std input is only drained if an input is added.
 *
 * Revision 1.1  2002/10/10 20:24:10  mphilip
 * Initial revision.
 *
 */
