package com.koalog.util.swing;

import java.util.Map;
import javax.swing.Action;
import javax.swing.JFrame;

import com.koalog.util.Infos;

/**
 * An interface to access an application so that it can be partially
 * automatically generated from an XML file.
 * @author Matthieu Philip
 */
public interface AutoApplication {
    /** 
     * Gets infos about the application.
     * @return an Infos object
     */
    public Infos getInfos();

    /** 
     * Sets infos about the application.
     * @param infos an Infos object
     */
    public void setInfos(Infos infos);

    /** 
     * The key used to store the application on the actions. 
     * Every action instantiated by the application loader will have
     * the application among its values, accessible by this key. It thus
     * possible by such an action to access the application values by name,
     * so that the data can easily be shared between the actions, and the
     * actions are reusable in other applications 
     * (not final because of obfuscation).
     */
    public static String KEY_AUTO_APP = "AUTO_APPLICATION";

    /**
     * Get the main frame of the application. The different application
     * items can be accessed from there.
     * For instance, the menu bar
     * can be modified to add menus and menu items.
     * @return The application main frame.
     */
    public JFrame getMainFrame();

    /**
     * Get the application actions. Each action is uniquely identified
     * by its name, used as its key in the returned Map instance.
     * @return A name to action map.
     */
    public Map getActions();

    public Action getAction(String key);

    /**
     * Get the application menus. Each menu is uniquely identified
     * by its name, used as its key in the returned Map instance.
     * @return A name to menu map.
     */
    public Map getMenus();

    /**
     * Get a value from the application context.
     * @param key The key used to store the value.
     * @return The object corresponding to the key, or <CODE>null</CODE>
     * if the object can not be found.
     */
    public Object getValue(String key);

    /**
     * Add a value to the application context.
     * @param key The key used to store the value.
     * @param value The object to add to the application context.
     */
    public void putValue(String key, Object value);

    /**
     * Set the application title.
     * @param title The key of the localized title.
     */
    public void setTitle(String title);

    /**
     * Set the application icon.
     * @param icon The location of the icon resource.
     */
    public void setIcon(String icon);

    /**
     * Get the application resource bundle name.
     * @return The name of the application resource bundle.
     */
    public String getBundleName();

    /**
     * Set the application resource bundle name.
     * @param bundle The name of the application resource bundle.
     */
    public void setBundleName(String bundle);

    /**
     * Notify the application that it is busy. This method should be
     * called by any action while it is doing something in the event
     * thread.
     */
    public void addBusyAction();

    /**
     * Notify the application that an action has finished its work.
     */
    public void removeBusyAction();
}
/*
 * $Log$
 * Revision 1.9  2004/07/01 13:40:27  yan
 * useful signature
 *
 * Revision 1.8  2003/03/18 17:34:30  yan
 * obfuscation related fixes
 *
 * Revision 1.7  2003/03/18 16:56:29  yan
 * fixed style
 *
 * Revision 1.6  2002/11/13 14:18:15  yan
 * added help menu
 *
 * Revision 1.5  2002/11/05 21:34:34  mphilip
 * Added an interface to set the application icon.
 *
 * Revision 1.4  2002/05/02 16:05:51  yan
 * moved
 *
 * Revision 1.3  2002/04/25 23:30:18  mphilip
 * Added menu bar element.
 *
 * Revision 1.2  2002/04/17 16:49:56  mphilip
 * Added the busy mechanism.
 *
 * Revision 1.1  2002/04/11 22:02:26  mphilip
 * Initial revision.
 *
 */
