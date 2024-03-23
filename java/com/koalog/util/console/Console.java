package com.koalog.util.console;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * An interface for consoles.
 * A console is an entity that can receive processes output and error
 * streams and feed processes input streams.
 *
 * @author Matthieu Philip
 */
public interface Console {
    /**
     * Add an output stream to the console. This means that this method
     * should be used to redirect some process output stream.
     * @param out A process output stream.
     */
    public void addOut(InputStream out);

    /**
     * Add an error stream to the console. This means that this method
     * should be used to redirect some process error stream.
     * @param err A process error stream.
     */
    public void addErr(InputStream err);

    /**
     * Add a console input listener. This means that this method
     * should be used to feed some process input with the console input.
     * @param in A process input stream.
     */
    public void addIn(OutputStream in);
}
/*
 * $Log$
 * Revision 1.1  2002/10/10 20:23:03  mphilip
 * Initial revision.
 *
 */
