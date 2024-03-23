package com.koalog.util;

import java.util.jar.Manifest;
import java.util.jar.JarFile;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Locale;
import java.util.StringTokenizer;
import org.apache.log4j.Category;

/**
 * Gives informations about a product from its jar file.
 * @author Yan Georget
 */
public class Infos {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(Infos.class);
    private static final String I18N_RESOURCE = "com.koalog.util.Messages";

    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    /** The version of the product. */
    private String productVersion;
    /** The name of the product. */
    private String productName;
    
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param jar the jar file name without the .jar extension
     */
    public Infos(String jar) {
        productVersion = I18N.getString(I18N_RESOURCE, 
                                        "VERSION_NOT_DEFINED", 
                                        Locale.getDefault());
        productName = I18N.getString(I18N_RESOURCE, 
                                        "PRODUCT_NOT_DEFINED", 
                                        Locale.getDefault());
        String jarPath = getJarPath(jar + ".jar");
        // cat.debug(jarPath);
        if (jarPath == null) {
            cat.error(I18N.getString(I18N_RESOURCE, 
                                     "JAR_NOT_FOUND", 
                                     Locale.getDefault()));
        } else {
            try {
                Manifest manifest = new JarFile(jarPath).getManifest();
                Iterator i = manifest.getMainAttributes().entrySet().iterator();
                while (i.hasNext()) {
                    Map.Entry entry = (Map.Entry) i.next();
                    String key = entry.getKey().toString();
                    // cat.debug(key);
                    if ("Implementation-Version".equals(key)) {
                        productVersion = entry.getValue().toString();
                    } else if ("Implementation-Title".equals(key)) {
                        productName = entry.getValue().toString();
                    }
                }
            } catch (IOException ioe) {
                cat.error(ioe);
            }
        }
    }
        
    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------
    /** 
     * Gets the path for a jar file.
     * @param jarName the name of the jar file
     * @return a string
     */
    private String getJarPath(String jarName) {
        String classPath = System.getProperty("java.class.path");
        cat.debug("searching " + jarName + " in " +classPath);
        StringTokenizer st = 
            new StringTokenizer(classPath,
                                System.getProperty("path.separator"));
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (token.indexOf(jarName) >= 0) {
                return token;
            }
        }
        return null;
    }

    //------------------------------------------------------------------------
    // STATIC METHODS
    //------------------------------------------------------------------------
    /** 
     * Gets the copyright string.
     * @return a string
     */
    public static String getCopyright() {
        return "Copyright 2002-2006 - Koalog";
    }

    //------------------------------------------------------------------------
    // ACCESSORS
    //------------------------------------------------------------------------
    /**
     * Gets the name of the product.
     * @return a string
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Gets the version of the product.
     * @return a string
     */
    public String getProductVersion() {
        return productVersion;
    }

    /**
     * Returns a message to display.
     * @param ri a dynamic piece of information
     * @return a string
     */
    public String getFullMessage(String ri) {
        StringBuffer message = new StringBuffer(getFullMessage());
        message.append("\n"); 
        message.append(ri);
        return message.toString();
    }

    public String getFullMessage() {
        StringBuffer message = new StringBuffer();
        message.append(getProductName());
        message.append(" (");
        message.append(getProductVersion());
        message.append(") - "); 
        message.append(getCopyright());
        return message.toString();
    }
}
/*
 * $Log$
 * Revision 1.12  2005/02/17 17:23:26  yan
 * removed debug logs
 *
 * Revision 1.11  2004/07/06 13:00:13  yan
 * added method
 *
 * Revision 1.10  2004/07/06 12:01:51  yan
 * fixed copyright
 *
 * Revision 1.9  2004/06/23 09:45:33  yan
 * added back year in copyright
 *
 * Revision 1.8  2003/03/18 18:10:25  yan
 * removed year from Copyright
 *
 * Revision 1.7  2003/03/18 16:54:21  yan
 * fixed style
 *
 * Revision 1.6  2002/11/13 14:18:34  yan
 * addec copyright
 *
 * Revision 1.5  2002/10/25 08:37:59  yan
 * *** empty log message ***
 *
 * Revision 1.4  2002/10/18 10:06:38  mphilip
 * Removed class.getName() for obfuscation.
 *
 * Revision 1.3  2002/10/14 17:18:27  yan
 * localized
 *
 * Revision 1.2  2002/10/14 17:11:39  yan
 * no exception is thrown when unable to read manifest
 *
 * Revision 1.1  2002/10/14 17:01:47  yan
 * initial revision
 *
 */
