package com.koalog.util;

import java.util.HashMap;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Category;

/**
 * A utility classed used for internationalization purposes.
 * Each instance of <TT>I18N</TT> is bound to a locale, and maps
 * <EM>(resource bundle, key)</EM> pairs to strings.
 * <P>The class also provides static methods avoiding to you the management of
 * multiple <TT>I18N</TT> instances.
 * </P>
 * @author Matthieu Philip
 */
public class I18N {
//////////////////////////////////////////////////////////////////////////
//
// Instance members
//
//////////////////////////////////////////////////////////////////////////
    /** 
     * The instance locale. All mappings will be done using that locale.
     */
    protected Locale _locale;

    /** A map of the already loaded resource bundles, keyed by name. */
    protected HashMap _bundles;

//////////////////////////////////////////////////////////////////////////
//
// Class members
//
//////////////////////////////////////////////////////////////////////////
    /**
     * Category used for logging this class events.
     */
    private static Category _cat = Category.getInstance(I18N.class);

    /** A map between locales and <TT>I18N</TT> instances. */
    private static HashMap _i18n;

//////////////////////////////////////////////////////////////////////////
//
// Constructors
//
//////////////////////////////////////////////////////////////////////////

    /**
     * Construct an <TT>I18N</TT> instance for the specified locale.
     */
    private I18N(Locale locale) {
        // reject null locales
        if (locale == null) {
            throw new IllegalArgumentException(
                "Cannot create an I18N instance for a null locale.");
        }
        _locale = locale;
        _bundles = new HashMap();
    }

//////////////////////////////////////////////////////////////////////////
//
// Instance methods
//
//////////////////////////////////////////////////////////////////////////
    /**
     * Get the specified bundle. Load it if required.
     * @param bundleName Name of the resource bundle to get.
     * @return A resource bundle.
     */
    protected ResourceBundle getBundle(String bundleName) {
        ResourceBundle res;
        synchronized(_bundles) {
            res = (ResourceBundle)_bundles.get(bundleName);
            if (res == null) {
                // bundle not in cache: load it
                _cat.debug("Loading bundle " 
                           + bundleName 
                           + " for locale " 
                           + _locale 
                           + "...");
                try {
                    res = ResourceBundle.getBundle(bundleName, _locale);
                    _cat.debug("Loaded bundle " 
                               + bundleName 
                               + " for locale " 
                               + _locale 
                               + ".");
                } catch (MissingResourceException mre) {
                    _cat.debug("Could not find bundle " 
                               + bundleName 
                               + " for locale " 
                               + _locale 
                               + ".");
                }
                _bundles.put(bundleName, res);
            }
        }
        return res;
    }

    /**
     * Get the string mapped to the specified <EM>(resource bundle, key)</EM>
     * pair.
     * @param bundleName Name of the resource bundle to look into.
     * @param key Key of the desired string in the specified resource bundle.
     * @return The string representation of the key, according to the bound
     * locale.
     */
    public String getString(String bundleName, String key) {
        // get the bundle
        ResourceBundle res = getBundle(bundleName);
        String val = null;
        // get the string
        if (res != null) {
            try {
                val = res.getString(key);
            } catch (MissingResourceException mre) {
                _cat.debug("Missing key " + key + " in " + bundleName + ".");
            }
        }
        // if the string was not found, construct an error string
        if (val == null) {
            val = "[Missing " + bundleName + "." + key + "]";
        }
        return val;
    }

//////////////////////////////////////////////////////////////////////////
//
// Class methods
//
//////////////////////////////////////////////////////////////////////////

    /**
     * Get an instance of <TT>I18N</TT> bound to the default locale.
     * The default locale can safely be changed to new values, this method
     * will take the changes into account.
     * @return A valid I18N instance.
     */
    public static synchronized I18N getInstance() {
        return getInstance(Locale.getDefault());
    }


    /**
     * Get an instance of <TT>I18N</TT> bound to the specified locale.
     * @param locale The locale to bind the <TT>I18N</TT> instance to.
     * @return A valid I18N instance.
     */
    public static synchronized I18N getInstance(Locale locale) {
        // construct the locale - i18n map if needed
        if (_i18n == null) {
            _i18n = new HashMap();
        }
        
        // can we find an i18n for the default locale?
        I18N res = (I18N)_i18n.get(locale);

        // if not, create an instance for that default locale
        if (res == null) {
            res = new I18N(locale);
            _cat.debug("Created an I18N instance for locale " + locale);
        }
        _i18n.put(locale, res);
        return res;
    }

    /**
     * Get the string mapped to the specified <EM>(resource bundle, key)</EM>
     * pair using the given locale.
     * @param bundleName Name of the resource bundle to look into.
     * @param key Key of the desired string in the specified resource bundle.
     * @param locale The locale to use for the mapping.
     * @return The string representation of the key, according to the given
     * locale.
     */
    public static String getString(String bundleName, 
                                   String key, 
                                   Locale locale) {
        return getInstance(locale).getString(bundleName, key);
    }
}
/*
 * $Log$
 * Revision 1.5  2003/03/18 16:54:20  yan
 * fixed style
 *
 * Revision 1.4  2002/10/11 15:24:14  mphilip
 * Switch logging method to Yan's.
 *
 * Revision 1.3  2002/05/02 16:02:06  yan
 * moved
 *
 * Revision 1.2  2002/04/05 16:21:09  mphilip
 * Modified to pass most checkstyle tests.
 *
 * Revision 1.1  2002/02/25 13:53:47  mphilip
 * initial revision
 *
 */
