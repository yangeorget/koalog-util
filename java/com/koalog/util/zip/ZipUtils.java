package com.koalog.util.zip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.Enumeration;

import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * A set of utilities to manipulate zip files.
 *
 * @author Matthieu Philip
 */
public class ZipUtils {
    /** The default buffer size. */
    private static final int BUFFER_SIZE = 32768;
    
    /**
     * Copy a file.
     * @param source The source file.
     * @param target The target file.
     * @throws IOException TODO: say when
     */
    public static void copy(File source, File target) throws IOException {
        if (source.equals(target)) {
            return;
        }
        int length;
        byte[] buf = new byte[BUFFER_SIZE];
        BufferedInputStream in = new BufferedInputStream(
            new FileInputStream(source), BUFFER_SIZE);
        try {
            BufferedOutputStream out = new BufferedOutputStream(
                new FileOutputStream(target), BUFFER_SIZE);
            try {
                while ((length = in.read(buf)) >= 0) {
                    out.write(buf, 0, length);
                }
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        } finally {
            in.close();
        }
    }

    /**
     * This method simulate the addition of an entry to an existing archive.
     * The standard library does not indeed allow such operation. To add more
     * than one entry, only invoke this method for the first entry.
     * @param filename The archive filename.
     * @param entry The entry to append.
     * @return A <CODE>ZipOutputStream</CODE> ready for the specified entry.
     * @throws IOException TODO: say when
     */
    public static ZipOutputStream appendEntry(String filename, ZipEntry entry)
        throws IOException {
        // Copy the archive to a tmp file
        File arc = new File(filename);
        File tmp = File.createTempFile("codeCoverage", ".zip");
        if (arc.exists()) {
            copy(arc, tmp);
        }
        // Recreate the archive if it was not empty
        int length;
        ZipOutputStream result = new ZipOutputStream(
            new BufferedOutputStream(new FileOutputStream(filename), 
                                     BUFFER_SIZE));
        try {
            if (tmp.length() > 0) {
                ZipFile oldZip = new ZipFile(tmp);
                try {
                    byte[] buf = new byte[BUFFER_SIZE];
                    Enumeration entries = oldZip.entries();
                    while (entries.hasMoreElements()) {
                        ZipEntry oldEntry = (ZipEntry)entries.nextElement();
                        BufferedInputStream in = new BufferedInputStream(
                            oldZip.getInputStream(oldEntry), BUFFER_SIZE);
                        try {
                            ZipEntry newEntry = new ZipEntry(oldEntry);
                            result.putNextEntry(newEntry);
                            try {
                                while ((length = in.read(buf)) >= 0) {
                                    result.write(buf, 0, length);
                                }
                            } finally {
                                result.closeEntry();
                            }
                        } finally {
                            in.close();
                        }
                    }
                } finally {
                    oldZip.close();
                }
            }
            // Delete the tmp file only if successful so far
            // => not in a finally clause
            tmp.delete();
            // add the given entry
            result.putNextEntry(entry);
        } catch(IOException ioe) {
            // only close the created result if an error occurs
            try {
                result.close();
            } catch (IOException ignored) {
                // the initial exception is thrown
            }
            throw ioe;
        }
        return result;
    }

    /**
     * This method simulate the extension of an entry in an existing archive.
     * The standard library does not indeed allow such operation. If the entry
     * does not exist, it is created.
     * @param filename The archive filename.
     * @param entry The name of the entry to extend.
     * @return A <CODE>ZipOutputStream</CODE> ready for the specified entry.
     * @throws IOException TODO: say when
     */
    public static ZipOutputStream appendToEntry(String filename, String entry)
        throws IOException {
        // Copy the archive to a tmp file
        File arc = new File(filename);
        File tmp = File.createTempFile("codeCoverage", ".zip");
        copy(arc, tmp);
        // Recreate the archive
        int length;
        ZipOutputStream result = new ZipOutputStream(
            new BufferedOutputStream(new FileOutputStream(filename), 
                                     BUFFER_SIZE));
        try {
            ZipFile oldZip = new ZipFile(tmp);
            try {
                byte[] buf = new byte[BUFFER_SIZE];
                Enumeration entries = oldZip.entries();
                while (entries.hasMoreElements()) {
                    ZipEntry oldEntry = (ZipEntry)entries.nextElement();
                    if (!entry.equals(oldEntry.getName())) {
                        BufferedInputStream in = new BufferedInputStream(
                            oldZip.getInputStream(oldEntry), BUFFER_SIZE);
                        try {
                            ZipEntry newEntry = new ZipEntry(oldEntry);
                            result.putNextEntry(newEntry);
                            try {
                                while ((length = in.read(buf)) >= 0) {
                                    result.write(buf, 0, length);
                                }
                            } finally {
                                result.closeEntry();
                            }
                        } finally {
                            in.close();
                        }
                    }
                }
                // now process the target entry
                ZipEntry newEntry = new ZipEntry(entry);
                result.putNextEntry(newEntry);
                ZipEntry oldEntry = oldZip.getEntry(entry);
                if (oldEntry != null) {
                    BufferedInputStream in = new BufferedInputStream(
                        oldZip.getInputStream(oldEntry), BUFFER_SIZE);
                    while ((length = in.read(buf)) >= 0) {
                        result.write(buf, 0, length);
                    }
                    in.close();
                }
            } finally {
                oldZip.close();
            }
            // Delete the tmp file only if everything was ok this far
            // => not in a finally clause
            tmp.delete();
        } catch(IOException ioe) {
            // only close the created result if something happens
            result.close();
            throw ioe;
        }
        return result;
    }

    /**
     * This method removes an entry from an existing archive.
     * @param filename The archive filename.
     * @param entry The name of the entry to remove.
     * @exception IOException TODO: say when
     */
    public static void removeEntry(String filename, String entry)
        throws IOException {
        // Make sure the entry exists and is not the last. In the latter
        // case the archive should be deleted.
        File arc = new File(filename);
        ZipFile zip = new ZipFile(arc);
        boolean delete = false;
        try {
            if (zip.getEntry(entry) == null) {
                return;
            }
            delete = (zip.size() <= 1);
        } finally {
            zip.close();
        }
        if (delete) {
            arc.delete();
            return;
        }
        // Copy the archive to a tmp file
        File tmp = File.createTempFile("codeCoverage", ".zip");
        copy(arc, tmp);
        // Recreate the archive
        int length;
        ZipOutputStream result = new ZipOutputStream(
            new BufferedOutputStream(new FileOutputStream(filename), 
                                     BUFFER_SIZE));
        try {
            ZipFile oldZip = new ZipFile(tmp);
            try {
                byte[] buf = new byte[BUFFER_SIZE];
                Enumeration entries = oldZip.entries();
                while (entries.hasMoreElements()) {
                    ZipEntry oldEntry = (ZipEntry)entries.nextElement();
                    if (!entry.equals(oldEntry.getName())) {
                        BufferedInputStream in = new BufferedInputStream(
                            oldZip.getInputStream(oldEntry), BUFFER_SIZE);
                        try {
                            ZipEntry newEntry = new ZipEntry(oldEntry);
                            result.putNextEntry(newEntry);
                            try {
                                while ((length = in.read(buf)) >= 0) {
                                    result.write(buf, 0, length);
                                }
                            } finally {
                                result.closeEntry();
                            }
                        } finally {
                            in.close();
                        }
                    }
                }
            } finally {
                oldZip.close();
            }
            // Delete the tmp file only if everything went fine
            // => not in a finally clause
            tmp.delete();
        } finally {
            result.close();
        }
    }

    /**
     * This method renames an entry in an existing archive.
     * @param filename The archive filename.
     * @param oldEntryName The name of the entry to rename.
     * @param newEntryName The new name for the entry.
     * @throws IOException TODO: say when
     */
    public static void renameEntry(String filename,
                                   String oldEntryName,
                                   String newEntryName)
        throws IOException {
        // Make sure the entry exists. If not, just return.
        File arc = new File(filename);
        ZipFile zip = new ZipFile(arc);
        try {
            if (zip.getEntry(oldEntryName) == null) {
                return;
            }
        } finally {
            zip.close();
        }
        // Copy the archive to a tmp file
        File tmp = File.createTempFile("ZipUtilsRename", ".zip");
        copy(arc, tmp);
        // Recreate the archive
        int length;
        ZipOutputStream result = new ZipOutputStream(
            new BufferedOutputStream(new FileOutputStream(filename), 
                                     BUFFER_SIZE));
        try {
            ZipFile oldZip = new ZipFile(tmp);
            try {
                byte[] buf = new byte[BUFFER_SIZE];
                Enumeration entries = oldZip.entries();
                while (entries.hasMoreElements()) {
                    ZipEntry oldEntry = (ZipEntry)entries.nextElement();
                    BufferedInputStream in = new BufferedInputStream(
                        oldZip.getInputStream(oldEntry), BUFFER_SIZE);
                    try {
                        ZipEntry newEntry;
                        if (!oldEntryName.equals(oldEntry.getName())) {
                            newEntry = new ZipEntry(oldEntry);
                        } else {
                            newEntry = new ZipEntry(newEntryName);
                        }
                        result.putNextEntry(newEntry);
                        try {
                            while ((length = in.read(buf)) >= 0) {
                                result.write(buf, 0, length);
                            }
                        } finally {
                            result.closeEntry();
                        }
                    } finally {
                        in.close();
                    }
                }
            } finally {
                oldZip.close();
            }
            // Delete the tmp file only if everything went fine
            // => not in a finally clause
            tmp.delete();
        } finally {
            result.close();
        }
    }
}
/*
 * $Log$
 * Revision 1.11  2003/03/18 16:58:28  yan
 * fixed style
 *
 * Revision 1.10  2002/12/12 14:20:38  mphilip
 * Added the rename method.
 *
 * Revision 1.9  2002/12/07 15:26:19  mphilip
 * Improved performances for large files.
 *
 * Revision 1.8  2002/11/27 11:06:18  mphilip
 * Fixed bugs: 21088 and 21089. These bugs were related to the
 * manipulation of (almost) empty archives.
 *
 * Revision 1.7  2002/11/15 11:03:55  mphilip
 * Fixed bug #21074. appendToEntry() was failing when the given entry did
 * not exist in the source archive.
 *
 * Revision 1.6  2002/11/14 21:59:11  mphilip
 * Fixed bug: copy was allowing copying a file over itself.
 *
 * Revision 1.5  2002/11/14 16:12:56  mphilip
 * Added a factorized copy() method.
 *
 * Revision 1.4  2002/10/11 10:16:24  mphilip
 * Ironed the usage of streams.
 *
 * Revision 1.3  2002/07/10 11:43:25  mphilip
 * Added the remove entry method.
 *
 * Revision 1.2  2002/07/09 22:55:40  mphilip
 * Added an appent to entry method.
 *
 * Revision 1.1  2002/06/24 15:13:41  mphilip
 * Initial revision.
 *
 */
