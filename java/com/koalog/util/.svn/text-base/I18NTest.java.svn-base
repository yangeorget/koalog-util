package com.koalog.util;

import java.util.Locale;

import junit.framework.TestCase;

/**
 * Do not use constants such as Locale.FRENCH: they are not properly
 * defined on all JVMs.
 *
 * @author Matthieu Philip
 */
public class I18NTest extends TestCase {
    /**
     * Sole constructor.
     * @param name a name
     */
    public I18NTest(String name) {
        super(name);
    }

    /**
     * Does nothing.
     */
    protected void setUp() {
    }

    /**
     * Test the instantiation of i18n.
     */
    public void testInstantiation() {
        Locale.setDefault(new Locale("en","US"));
        Locale loc = null;
        I18N i18n = null;
        // default locale
        i18n = I18N.getInstance();
        assertNotNull(i18n);
        // check unicity
        assertEquals(i18n, I18N.getInstance());
        // another locale
        i18n = I18N.getInstance(new Locale("fr","FR"));
        assertNotNull(i18n);
        assertTrue(i18n != I18N.getInstance());
        // check unicity
        assertEquals(i18n, I18N.getInstance(new Locale("fr","FR")));
    }

    /**
     * Test string retrieval of i18n.
     */
    public void testGet() {
        Locale.setDefault(new Locale("en","US"));
        Locale loc = null;
        I18N i18n = null;
        // default locale
        String s = I18N.getString("i18n.i18n", "LABEL", new Locale("fr","FR"));
        // no bundle for fr, make sure we got the default
        assertEquals(I18N.getString("i18n.i18n", "LABEL",
                                    Locale.getDefault()), s);
        // en
        s = I18N.getString("i18n.i18n", "LABEL", new Locale("en",""));
        // no bundle for fr, make sure we got the default
        assertEquals("en", s);
        // en_US
        s = I18N.getString("i18n.i18n", "LABEL", new Locale("en","US"));
        // no bundle for fr, make sure we got the default
        assertEquals("en_US", s);
        // en
        s = I18N.getString("i18n.i18n", "LABEL", new Locale("en","UK"));
        // no bundle for fr, make sure we got the default
        assertEquals("en", s);
        // now, change the default to french
        Locale.setDefault(new Locale("fr","FR"));
        //s = I18N.getString("i18n.i18n", "LABEL", Locale.GERMAN);
        assertEquals(Locale.getDefault(),new Locale("fr","FR"));
        /*
         * Test removed because of a bug in versions 1.3 of the JVMs
         *
        s = I18N.getString("i18n.i18n", "LABEL", new Locale("de","DE"));
        assertEquals("Default", s);
         */
        Locale.setDefault(new Locale("en","US"));
    }
}
/*
 * $Log$
 * Revision 1.5  2003/03/18 16:54:21  yan
 * fixed style
 *
 * Revision 1.4  2002/09/27 11:11:09  mphilip
 * Fixed JVM related issues.
 *
 * Revision 1.3  2002/05/02 16:02:06  yan
 * moved
 *
 * Revision 1.2  2002/03/13 13:14:07  mphilip
 * Modified to pass most checkstyle tests.
 *
 * Revision 1.1  2002/02/25 15:13:50  mphilip
 * Initial revision.
 *
 */
