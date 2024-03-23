package com.koalog.util.console;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStream;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Category;

/**
 * A base console implementation.
 * <p>This implementation handles the reading of the processes outputs,
 * as well as the dispatching of strings to the processes inputs. It
 * does not provide any implementation of how the console displays the
 * outputs and how the console get the input.</p>
 *
 * @author Matthieu Philip
 */
public abstract class BaseConsole implements Console {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = 
        Category.getInstance(BaseConsole.class);

    //------------------------------------------------------------------------
    // INSTANCE PROPERTIES
    //------------------------------------------------------------------------
    /** The list of outputs. */
    protected List _outputs;

    /** The list of errors. */
    protected List _errors;

    /** The list of inputs. */
    protected List _inputs;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Default constructor.
     */
    public BaseConsole() {
        _outputs = new LinkedList();
        _errors = new LinkedList();
        _inputs = new LinkedList();
    }

    //------------------------------------------------------------------------
    // INSTANCE METHODS
    //------------------------------------------------------------------------
    /** @see com.koalog.util.console.Console */
    public synchronized void addOut(InputStream out) {
        if (!_outputs.contains(out)) {
            _outputs.add(out);
            getOutputReader(out).start();
        }
    }

    /** @see com.koalog.util.console.Console */
    public synchronized void addErr(InputStream err) {
        if (!_errors.contains(err)) {
            _errors.add(err);
            getErrorReader(err).start();
        }
    }

    /** @see com.koalog.util.console.Console */
    public synchronized void addIn(OutputStream in) {
        if (!_inputs.contains(in)) {
            _inputs.add(new BufferedOutputStream(in));
        }
    }

    /**
     * Get a thread to redirect an output stream.
     * Overriding this method makes it possible
     * to customize the thread naming or to use a faster draining
     * method.
     * @param out The output to redirect.
     * @return A non started thread.
     */
    protected Thread getOutputReader(InputStream out) {
        return new OutputReader(this, out, false);
    }

    /**
     * Get a thread to redirect an error stream.
     * Overriding this method makes it possible
     * to customize the thread naming or to use a faster draining
     * method.
     * @param err The error to redirect.
     * @return A non started thread.
     */
    protected Thread getErrorReader(InputStream err) {
        return new OutputReader(this, err, true);
    }

    /**
     * Append a string to the console output.
     * @param str The string to append.
     */
    protected abstract void appendOut(String str);

    /**
     * Append a string to the console error.
     * @param str The string to append.
     */
    protected abstract void appendErr(String str);

    /**
     * Dispatch a string to the processes inputs.
     * @param str The string to dispatch.
     */
    protected void dispatch(String str) {
        byte[] tab = str.getBytes();
        synchronized(_inputs) {
            Iterator i = _inputs.iterator();
            while (i.hasNext()) {
                BufferedOutputStream in = (BufferedOutputStream)i.next();
                try {
                    in.write(tab, 0, tab.length);
                    in.flush();
                } catch(IOException ioe) {
                    synchronized(_inputs) {
                        _inputs.remove(in);
                    }
                    cat.debug("Removed input: " + in, ioe);
                }
            }
        }
    }

    /**
     * A class that drains a process output and appends it to
     * the parent console.
     * @author Matthieu Philip
     */
    protected class OutputReader extends Thread {
        /** The parent console. */
        BaseConsole _console;
        /** The output to drain. */
        InputStream _out;
        /** A flag indicating whether the stream is an error stream. */
        boolean _isError;
        /**
         * Default constructor.
         * @param console The parent console.
         * @param out the output
         * @param isError a flag indicating whether 
         * the stream is an error stream
         */
        public OutputReader(BaseConsole console,
                            InputStream out,
                            boolean isError) {
            super("Base Output Reader");
            _console = console;
            _out = out;
            _isError = isError;
            setDaemon(true);
        }

        /**
         * The main loop.
         */
        public void run() {
            BufferedReader out = new BufferedReader(
                new InputStreamReader(_out));
            String str;
            try {
                while ((str = out.readLine()) != null) {
                    if (_isError) {
                        _console.appendErr(str + "\n");
                    } else {
                        _console.appendOut(str + "\n");
                    }
                }
            } catch(IOException ioe) {
                    if (_isError) {
                        cat.debug("Failed to read from error stream.", ioe);
                    } else {
                        cat.debug("Failed to read from output stream.", ioe);
                    }
            }
        }
    }
}
/*
 * $Log$
 * Revision 1.3  2003/03/18 16:55:20  yan
 * fixed style
 *
 * Revision 1.2  2002/10/12 00:04:34  mphilip
 * Added missing eol to input and flush.
 *
 * Revision 1.1  2002/10/10 20:23:47  mphilip
 * Initial revision.
 *
 */
