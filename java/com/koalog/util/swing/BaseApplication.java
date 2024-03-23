package com.koalog.util.swing;

import com.koalog.util.I18N;
import com.koalog.util.Infos;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;

import java.io.IOException;

import java.net.URL;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Category;

import org.xml.sax.SAXException;

/**
 * A base application class.
 * As an AutoApplication, each application extending this class can be
 * partially generated from an XML file.
 *
 * @author Matthieu Philip
 */
public class BaseApplication implements AutoApplication {
//////////////////////////////////////////////////////////////////////////
//
// Class members
//
//////////////////////////////////////////////////////////////////////////
    /**
     * Category used for logging this class events.
     */
    private static Category _Cat = Category.getInstance(BaseApplication.class);
    /*
     * Message and label keys.
     */
    static final String KEY_LAB_ERROR = "LAB_ERROR";
    static final String KEY_LAB_WARNING = "LAB_WARNING";
    static final String KEY_LAB_INFO = "LAB_INFO";

//////////////////////////////////////////////////////////////////////////
//
// Instance members
//
//////////////////////////////////////////////////////////////////////////
    /** The application main frame. */
    protected JFrame _mainFrame;

    /** The application actions. */
    HashMap _actions;

    /** The application menus. */
    HashMap _menus;

    /** The application context. */
    HashMap _values;

    /**
     * The name of the resource bundle storing this application strings.
     */
    String _bundleName = "com.koalog.util.swing.Messages";

    /** The key of the localized title. */
    String _titleKey;

    /** The number of busy actions. */
    int _busyCard;

    /** The application desktop. */
    protected JDesktopPane _desktop;

    /** 
     *  An Infos object encapsulating informations about the application. 
     */
    private Infos infos;

//////////////////////////////////////////////////////////////////////////
//
// Constructors
//
//////////////////////////////////////////////////////////////////////////

    /**
     * Default constructor.
     */
    public BaseApplication() {
        _mainFrame = new JFrame();
        _actions = new HashMap();
        _menus = new HashMap();
        _values = new HashMap();
        _busyCard = 0;
    }

//////////////////////////////////////////////////////////////////////////
//
// Instance methods
//
//////////////////////////////////////////////////////////////////////////
    /** @see com.koalog.util.swing.AutoApplication */
    public void setInfos(Infos infos) {
        this.infos = infos;
    }

    /** @see com.koalog.util.swing.AutoApplication */
    public Infos getInfos() {
        return infos;
    }

    /**
     * Build the application from the specified XML file.
     * @param filename The name of the XML file.
     * @throws SAXException should not happen
     * @throws IOException should not happen
     */
    public void build(String filename)
        throws SAXException, IOException {
        ApplicationLoader loader = new ApplicationLoader(filename);
        loader.build(this);
        _mainFrame.pack();
        
        // center the frame
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        _mainFrame.setLocation((d.width - _mainFrame.getSize().width) / 2,
                               (d.height - _mainFrame.getSize().height) / 2);
    }

    /**
     * Run the application.
     */
    public void run() {
        _mainFrame.show();
    }

    /**
     * Display an error message.
     * @param mes The message to display.
     */
    public void showError(String mes) {
        JOptionPane.showMessageDialog(getMainFrame(),
                                      mes,
                                      I18N.getString(_bundleName, 
                                                     KEY_LAB_ERROR, 
                                                     Locale.getDefault()), 
                                      JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Display a warning message.
     * @param mes The message to display.
     */
    public void showWarning(String mes) {
        JOptionPane.showMessageDialog(getMainFrame(),
                                      mes,
                                      I18N.getString(_bundleName, 
                                                     KEY_LAB_WARNING, 
                                                     Locale.getDefault()), 
                                      JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Display an information message.
     * @param mes The message to display.
     */
    public void showInfo(String mes) {
        JOptionPane.showMessageDialog(getMainFrame(),
                                      mes,
                                      I18N.getString(_bundleName, 
                                                     KEY_LAB_INFO, 
                                                     Locale.getDefault()), 
                                      JOptionPane.INFORMATION_MESSAGE);
    }

/*
 *
 * Implementation of AutoApplication - start
 *
 */

    /** @see com.koalog.util.swing.AutoApplication */
    public JFrame getMainFrame() {
        return _mainFrame;
    }

    /** @see com.koalog.util.swing.AutoApplication */
    public Map getActions() {
        return _actions;
    }

    /** @see com.koalog.util.swing.AutoApplication */
    public Action getAction(String key) {
        return (Action) getActions().get(key);
    }


    /** @see com.koalog.util.swing.AutoApplication */
    public Map getMenus() {
        return _menus;
    }

    /** @see com.koalog.util.swing.AutoApplication */
    public Object getValue(String key) {
        return _values.get(key);
    }

    /** @see com.koalog.util.swing.AutoApplication */
    public void putValue(String key, Object value) {
        _values.put(key, value);
    }

    /** @see com.koalog.util.swing.AutoApplication */
    public void setTitle(String title) {
        _titleKey = title;
        _mainFrame.setTitle(I18N.getString(_bundleName, 
                                           title, 
                                           Locale.getDefault()));
    }

    /** @see com.koalog.util.swing.AutoApplication */
    public void setIcon(String icon) {
        URL iconUrl = ClassLoader.getSystemResource(icon);
        if (iconUrl == null) {
            _Cat.error("Could not find application icon: " + icon);
            return;
        }
        ImageIcon iconImg = new ImageIcon(iconUrl);
        _mainFrame.setIconImage(iconImg.getImage());
    }

    /** @see com.koalog.util.swing.AutoApplication */
    public String getBundleName() {
        return _bundleName;
    }

    /** @see com.koalog.util.swing.AutoApplication */
    public void setBundleName(String bundle) {
        _bundleName = bundle;
    }

    /** @see com.koalog.util.swing.AutoApplication */
    public synchronized void addBusyAction() {
        _busyCard++;
        if (_busyCard == 1) {
            getMainFrame().setCursor(Cursor.getPredefinedCursor(
                Cursor.WAIT_CURSOR
                ));
            // also modify internal frames because of a JDK bug
            JInternalFrame[] frames = _desktop.getAllFrames();
            for (int i = 0; i < frames.length; i++) {
                frames[i].setCursor(Cursor.getPredefinedCursor(
                    Cursor.WAIT_CURSOR
                    ));
            }
        }
    }

    /** @see com.koalog.util.swing.AutoApplication */
    public synchronized void removeBusyAction() {
        _busyCard--;
        if (_busyCard == 0) {
            getMainFrame().setCursor(Cursor.getPredefinedCursor(
                Cursor.DEFAULT_CURSOR
                ));
            // also modify internal frames because of a JDK bug
            JInternalFrame[] frames = _desktop.getAllFrames();
            for (int i = 0; i < frames.length; i++) {
                frames[i].setCursor(Cursor.getPredefinedCursor(
                    Cursor.DEFAULT_CURSOR
                    ));
            }
        }
    }

/*
 *
 * Implementation of AutoApplication - stop
 *
 */

//////////////////////////////////////////////////////////////////////////
//
// Class methods
//
//////////////////////////////////////////////////////////////////////////

    /**
     * The main method executes the application.
     * @param args the command-line arguments
     * @throws SAXException should not happen
     * @throws IOException should not happen
     */
    public static void main(String[] args)
        throws SAXException, IOException {
        BasicConfigurator.configure();
        BaseApplication app = new BaseApplication();
        app.build(args[0]);
        app.run();
    }
}
/*
 * $Log$
 * Revision 1.12  2004/07/01 13:40:35  yan
 * useful signature
 *
 * Revision 1.11  2003/03/18 16:56:29  yan
 * fixed style
 *
 * Revision 1.10  2002/11/13 14:18:15  yan
 * added help menu
 *
 * Revision 1.9  2002/11/05 21:34:59  mphilip
 * Implemented the interface to set the application icon.
 *
 * Revision 1.8  2002/08/08 12:20:56  mphilip
 * Fixed bug: missing message dialog titles.
 *
 * Revision 1.7  2002/06/26 22:05:58  mphilip
 * Fixed bug: cursor not always waiting as expected over internal frames.
 *
 * Revision 1.6  2002/06/15 19:54:15  mphilip
 * Added message display methods.
 *
 * Revision 1.5  2002/05/02 16:05:51  yan
 * moved
 *
 * Revision 1.4  2002/04/25 23:30:18  mphilip
 * Added menu bar element.
 *
 * Revision 1.3  2002/04/17 16:50:18  mphilip
 * Added the busy mechanism.
 *
 * Revision 1.2  2002/04/12 12:40:53  mphilip
 * Modified the access rights to the main frame.
 *
 * Revision 1.1  2002/04/11 22:02:26  mphilip
 * Initial revision.
 *
 */




