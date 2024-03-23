package com.koalog.util;

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.Locale;
import org.apache.log4j.Category;

/** 
 * A container for a license string.
 * 
 * <P>When constructed, it tries to get the license string in a license file 
 * of the form <CODE>USER_HOME/.product/license.lic</CODE>.</P>
 * 
 * <P>If the license file is not found, prompts the user for a license file 
 * (which will be copied at the location described above).</P>
 * 
 * @author Yan Georget
 */
public class License {
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    private static Category cat = Category.getInstance(License.class);
    private static final String I18N_RESOURCE = "com.koalog.util.Messages";

    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------
    private String license;
    
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------
    /**
     * Sole constructor.
     * @param name the name of the product
     */
    public License(String name) {
        File productDir = new File(System.getProperty("user.home")  
                                   + System.getProperty("file.separator") 
                                   + "."  
                                   + name);
        // if necessary the product directory is created
        if (!productDir.exists()) {
            productDir.mkdir();
        } 
        File licenseFile = new File(productDir.getPath()  
                                    + System.getProperty("file.separator") 
                                    + "license.lic");
        // if necessary the license file is copied
        if (!licenseFile.exists()) {
            System.out.print(I18N.getString(I18N_RESOURCE, 
                                            "ASK_LICENSE_FILE",
                                            Locale.getDefault()));
            System.out.print(": ");
            try {
                String licensePath = 
                    new BufferedReader(new InputStreamReader(System.in))
                        .readLine();
                FileInputStream in = 
                    new FileInputStream(new File(licensePath));
                FileOutputStream out = new FileOutputStream(licenseFile);
                // file copy
                byte[] buf = new byte[1024]; 
                int n;
                while ((n = in.read(buf, 0, buf.length)) > 0) {
                    out.write(buf, 0, n);
                }
                in.close();
                out.close();
            } catch (IOException ioe) {
                cat.error(ioe.getMessage(), ioe);
            }
        }
        // reading the license file
        try {
            license = new BufferedReader(new FileReader(licenseFile))
                .readLine();
        } catch (IOException ioe) {
            cat.fatal(ioe.getMessage(), ioe);
        }
    }
        
    //------------------------------------------------------------------------
    // ACCESSORS
    //------------------------------------------------------------------------
    /**
     * Gets the license string. 
     * @return a string 
     * (or null when it was not possible to get the license string)
     */
    public String getLicense() {
        return license;
    }
}
/*
 * $Log$
 * Revision 1.3  2003/03/18 16:54:21  yan
 * fixed style
 *
 * Revision 1.2  2002/10/21 11:10:53  yan
 * added javadoc
 *
 * Revision 1.1  2002/10/21 11:02:09  yan
 * initial revision
 *
 */
