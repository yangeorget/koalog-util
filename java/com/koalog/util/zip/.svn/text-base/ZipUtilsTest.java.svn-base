package com.koalog.util.zip;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import junit.framework.TestCase;

/**
 * Test the ZipUtils class.
 *
 * @author Matthieu Philip
 */
public class ZipUtilsTest extends TestCase {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name A method name.
     */
    public ZipUtilsTest(String name) {
        super(name);
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /**
     * Tests the appendEntry method.
     * @throws IOException should not happen
     */
    public void testAppendEntry1() throws IOException {
        // Create the initial archive
        File arc = File.createTempFile("ziputils", ".zip");
        ZipOutputStream out = new ZipOutputStream(
            new FileOutputStream(arc));
        ZipEntry entry = new ZipEntry("Entry 1");
        out.putNextEntry(entry);
        PrintWriter writer = new PrintWriter(out);
        writer.println("test 1");
        writer.close();
        assertTrue("Could not create initial archive.", arc.exists());
        // Append an entry to it
        entry = new ZipEntry("Entry 2");
        out = ZipUtils.appendEntry(arc.getAbsolutePath(), entry);
        writer = new PrintWriter(out);
        writer.println("test 2");
        writer.close();
        // Check the archive
        ZipFile zip = new ZipFile(arc.getAbsolutePath());
        assertNotNull("Could not find 'Entry 1'", zip.getEntry("Entry 1"));
        assertNotNull("Could not find 'Entry 2'", zip.getEntry("Entry 2"));
        arc.delete();
    }

    /**
     * Tests the appendToEntry method with a single entry.
     * @throws IOException should not happen
     */
    public void testAppendToEntry1() throws IOException {
        // Create the initial archive
        File arc = File.createTempFile("ziputils", ".zip");
        ZipOutputStream out = new ZipOutputStream(
            new FileOutputStream(arc));
        ZipEntry entry = new ZipEntry("Entry 1");
        out.putNextEntry(entry);
        PrintWriter writer = new PrintWriter(out);
        writer.println("test 1");
        writer.close();
        assertTrue("Could not create initial archive.", arc.exists());
        // Append a line to this entry
        out = ZipUtils.appendToEntry(arc.getAbsolutePath(), "Entry 1");
        writer = new PrintWriter(out);
        writer.println("test 2");
        writer.close();
        // Check the archive
        ZipFile zip = new ZipFile(arc.getAbsolutePath());
        entry = zip.getEntry("Entry 1");
        assertNotNull("Could not find 'Entry 1'", entry);
        BufferedReader in = 
            new BufferedReader(
                new InputStreamReader(zip.getInputStream(entry)));
        String line = in.readLine();
        assertNotNull("Could not read the first line", line);
        assertEquals("Unexpected first line.", "test 1", line);
        line = in.readLine();
        assertNotNull("Could not read the second line", line);
        assertEquals("Unexpected first line.", "test 2", line);
        line = in.readLine();
        assertNull("There are more than two lines", line);
        in.close();
        zip.close();
        arc.delete();
    }

    /**
     * Tests the appendToEntry method with a two entries.
     * Test the dynamic access at the same time.
     * @throws IOException should not happen
     */
    public void testAppendToEntry2() throws IOException {
        // Create the instance
        ZipUtils utils = new ZipUtils();

        // Create the initial archive
        File arc = File.createTempFile("ziputils", ".zip");
        ZipOutputStream out = new ZipOutputStream(
            new FileOutputStream(arc));
        ZipEntry entry = new ZipEntry("Entry 1");
        out.putNextEntry(entry);
        PrintWriter writer = new PrintWriter(out);
        writer.println("test 1");
        writer.flush();
        out.closeEntry();
        entry = new ZipEntry("Entry 2");
        out.putNextEntry(entry);
        writer.println("test 3");
        writer.flush();
        out.closeEntry();
        writer.close();
        assertTrue("Could not create initial archive.", arc.exists());
        // Append a line to the first entry
        out = ZipUtils.appendToEntry(arc.getAbsolutePath(), "Entry 1");
        writer = new PrintWriter(out);
        writer.println("test 2");
        writer.close();
        // Check the archive
        ZipFile zip = new ZipFile(arc.getAbsolutePath());
        entry = zip.getEntry("Entry 1");
        assertNotNull("Could not find 'Entry 1'", entry);
        BufferedReader in = 
            new BufferedReader(
                new InputStreamReader(zip.getInputStream(entry)));
        String line = in.readLine();
        assertNotNull("Could not read the first line", line);
        assertEquals("Unexpected first line.", "test 1", line);
        line = in.readLine();
        assertNotNull("Could not read the second line", line);
        assertEquals("Unexpected first line.", "test 2", line);
        line = in.readLine();
        assertNull("There are more than two lines", line);
        in.close();
        entry = zip.getEntry("Entry 2");
        in = 
            new BufferedReader(
                new InputStreamReader(zip.getInputStream(entry)));
        line = in.readLine();
        assertNotNull("Could not read the first line", line);
        assertEquals("Unexpected first line.", "test 3", line);
        line = in.readLine();
        assertNull("There are more than two lines", line);
        in.close();
        zip.close();
        arc.delete();
    }

    /**
     * Tests the removeEntry method.
     * @throws IOException should not happen
     */
    public void testRemoveEntry1() throws IOException {
        // Create the initial archive
        File arc = File.createTempFile("ziputils", ".zip");
        ZipOutputStream out = new ZipOutputStream(
            new FileOutputStream(arc));
        ZipEntry entry = new ZipEntry("Entry 1");
        out.putNextEntry(entry);
        PrintWriter writer = new PrintWriter(out);
        writer.println("test 1");
        writer.close();
        assertTrue("Could not create initial archive.", arc.exists());
        // Append an entry to it
        entry = new ZipEntry("Entry 2");
        out = ZipUtils.appendEntry(arc.getAbsolutePath(), entry);
        writer = new PrintWriter(out);
        writer.println("test 2");
        writer.close();
        // Check the archive
        ZipFile zip = new ZipFile(arc.getAbsolutePath());
        assertNotNull("Could not find 'Entry 1'", zip.getEntry("Entry 1"));
        assertNotNull("Could not find 'Entry 2'", zip.getEntry("Entry 2"));
        zip.close();
        // Remove entry 1
        ZipUtils.removeEntry(arc.getAbsolutePath(), "Entry 1");
        // Check the archive
        zip = new ZipFile(arc.getAbsolutePath());
        assertNull("'Entry 1' still in archive", zip.getEntry("Entry 1"));
        assertNotNull("Could not find 'Entry 2'", zip.getEntry("Entry 2"));
        zip.close();
        arc.delete();
    }

    /**
     * Tests the removeEntry method with an almost empty archive.
     * @throws IOException should not happen
     */
    public void testBug21088() throws IOException {
        // Create the initial archive
        File arc = File.createTempFile("ziputils", ".zip");
        ZipOutputStream out = new ZipOutputStream(
            new FileOutputStream(arc));
        ZipEntry entry = new ZipEntry("Entry 1");
        out.putNextEntry(entry);
        PrintWriter writer = new PrintWriter(out);
        writer.println("test 1");
        writer.close();
        assertTrue("Could not create initial archive.", arc.exists());
        // Check the archive
        ZipFile zip = new ZipFile(arc.getAbsolutePath());
        assertNotNull("Could not find 'Entry 1'", zip.getEntry("Entry 1"));
        zip.close();
        // Remove entry 1
        ZipUtils.removeEntry(arc.getAbsolutePath(), "Entry 1");
        // Check the archive
        assertTrue("The archive was not deleted.", !arc.exists());
    }

    /**
     * Tests the appendEntry method when the archive is new or does not exist.
     * @throws IOException should not happen
     */
    public void testBug21089() throws IOException {
        // Create the initial empty archive
        File arc = File.createTempFile("ziputils", ".zip");
        assertTrue("Could not create initial empty archive.", arc.exists());
        // Append an entry to it
        ZipEntry entry = new ZipEntry("Entry 1");
        ZipOutputStream out =
            ZipUtils.appendEntry(arc.getAbsolutePath(), entry);
        PrintWriter writer = new PrintWriter(out);
        writer.println("test 1");
        writer.close();
        // Check the archive
        ZipFile zip = new ZipFile(arc.getAbsolutePath());
        assertNotNull("Could not find 'Entry 1'", zip.getEntry("Entry 1"));
        zip.close();
        String filename = arc.getAbsolutePath();
        arc.delete();
        assertTrue("Could not delete the archive.", !arc.exists());
        // Append an entry to the non existing archive
        entry = new ZipEntry("Entry 2");
        out = ZipUtils.appendEntry(filename, entry);
        writer = new PrintWriter(out);
        writer.println("test 2");
        writer.close();
        // Check the archive
        zip = new ZipFile(filename);
        assertNotNull("Could not find 'Entry 2'", zip.getEntry("Entry 2"));
        zip.close();
        arc.delete();
    }

    /**
     * Test the rename entry method.
     * @throws IOException should not happen
     */
    public void testRename1() throws IOException {
        // Create the initial archive
        File arc = File.createTempFile("ziputils", ".zip");
        ZipOutputStream out = new ZipOutputStream(
            new FileOutputStream(arc));
        ZipEntry entry = new ZipEntry("Entry 1");
        out.putNextEntry(entry);
        PrintWriter writer = new PrintWriter(out);
        writer.println("test 1");
        writer.close();
        assertTrue("Could not create initial archive.", arc.exists());
        // Check the archive
        ZipFile zip = new ZipFile(arc.getAbsolutePath());
        assertNotNull("Could not find 'Entry 1'", zip.getEntry("Entry 1"));
        zip.close();
        // Remove entry 1
        ZipUtils.renameEntry(arc.getAbsolutePath(), "Entry 1", "Entry 2");
        // Check the archive
        zip = new ZipFile(arc.getAbsolutePath());
        assertNull("Could find 'Entry 1'", zip.getEntry("Entry 1"));
        assertNotNull("Could not find 'Entry 2'", zip.getEntry("Entry 2"));
        zip.close();
    }
}
/*
 * $Log$
 * Revision 1.9  2004/08/25 14:08:52  mat
 * Fixed style.
 *
 * Revision 1.8  2003/03/18 16:58:28  yan
 * fixed style
 *
 * Revision 1.7  2002/12/12 14:20:19  mphilip
 * Added the rename method test case.
 *
 * Revision 1.6  2002/11/27 11:05:22  mphilip
 * Added tests for bugs 21088 and 21089.
 *
 * Revision 1.5  2002/10/11 20:57:40  mphilip
 * Added a test.
 *
 * Revision 1.4  2002/09/27 11:25:48  mphilip
 * Fixed bug: a zip file was not closed.
 *
 * Revision 1.3  2002/07/10 11:43:48  mphilip
 * Added the remove entry method test.
 *
 * Revision 1.2  2002/07/09 22:56:08  mphilip
 * Added the append to entry test.
 *
 * Revision 1.1  2002/06/24 15:12:39  mphilip
 * Initial revision.
 *
 */
