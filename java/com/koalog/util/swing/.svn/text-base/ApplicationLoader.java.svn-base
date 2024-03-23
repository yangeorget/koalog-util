package com.koalog.util.swing;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.InputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Stack;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.MenuElement;
import org.apache.log4j.Category;
import org.apache.xerces.parsers.SAXParser;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import com.koalog.util.I18N;
import com.koalog.util.swing.action.I18NAbstractAction;
import com.koalog.util.swing.action.I18NMenu;

/**
 * An application generator. From an XML file, this utility generate the most
 * common elements of an application.
 *
 * @author Matthieu Philip
 */
public class ApplicationLoader implements ContentHandler {
//////////////////////////////////////////////////////////////////////////
//
// Class members
//
//////////////////////////////////////////////////////////////////////////
    /** 
     * The name of the resource bundle storing the messages. 
     */
    static final String RESBDL = "com.koalog.util.swing.Messages";

    /** The key to the sax error message. */
    static final String KEY_ERR_SAX = "ERR_SAX_ERROR";
    /** The key to the sax underlying exception message. */
    static final String KEY_ERR_SAX_UNDER = "ERR_SAX_UNDER";
    /** The key to the parse IO exception message. */
    static final String KEY_ERR_PARSE_IO = "ERR_PARSE_IO";
    /** The key to the null application message. */
    static final String KEY_ERR_APP_NULL = "ERR_APP_NULL";
    /** The key to the null application message. */
    static final String KEY_ERR_TITLE_NULL = "ERR_TITLE_NULL";
    /** The key to the null action name message. */
    static final String KEY_ERR_NULL_ACTION_NAME = "ERR_NULL_ACTION_NAME";
    /** The key to the null action class message. */
    static final String KEY_ERR_NULL_ACTION_CLASS = "ERR_NULL_ACTION_CLASS";
    /** The key to the null parameter key message. */
    static final String KEY_ERR_NULL_PARAM_KEY = "ERR_NULL_PARAM_KEY";
    /** The key to the null parameter value message. */
    static final String KEY_ERR_NULL_PARAM_VAL = "ERR_NULL_PARAM_VAL";
    /** The key to the null menu name message. */
    static final String KEY_ERR_NULL_MENU_NAME = "ERR_NULL_MENU_NAME";
    /** The key to the null menu action message. */
    static final String KEY_ERR_NULL_MENU_ACTION = "ERR_NULL_MENU_ACTION";
    /** The key to the null hook action message. */
    static final String KEY_ERR_NULL_HOOK_ACTION = "ERR_NULL_HOOK_ACTION";
    /** The key to the element processing message. */
    static final String KEY_MES_PROCESSING = "MES_PROCESSING";
    /** The key to the unknown tag message. */
    static final String KEY_MES_UNKNOWN_TAG = "MES_UNKNOWN_TAG";
    /** The key to the menu bar created message. */
    static final String KEY_MES_MENUBAR_CREATED = "MES_MENUBAR_CREATED";
    /** Tag name to action map. */
    protected static HashMap _ActionMap;

    /*
     *
     * Constants to identify actions taken for XML elements.
     *
     */

    /** Index of the application creation. */
    protected static final int APPLICATION = 0;
    /** Index of the action creation. */
    protected static final int ACTION = 1;
    /** Index of the action parameter creation. */
    protected static final int ACTION_PARAM = 2;
    /** Index of the menu bar creation. */
    protected static final int MENU_BAR = 3;
    /** Index of the menu creation. */
    protected static final int MENU = 4;
    /** Index of the menu item creation. */
    protected static final int MENU_ITEM = 5;
    /** Index of the menu separator creation. */
    protected static final int MENU_SEPARATOR = 6;
    /** Index of the close button hook creation. */
    protected static final int CLOSE_HOOK = 7;

    /*
     *
     * Constants identifying the element names.
     *
     */

    /** Element for the application creation. */
    protected static final String APPLICATION_EL = "AUTOAPP";
    /** Element for the action creation. */
    protected static final String ACTION_EL = "ACTION";
    /** Element for the action parameter creation. */
    protected static final String ACTION_PARAM_EL = "PARAM";
    /** Element for the menu bar creation. */
    protected static final String MENU_BAR_EL = "MENUBAR";
    /** Element for the menu creation. */
    protected static final String MENU_EL = "MENU";
    /** Element for the menu item creation. */
    protected static final String MENU_ITEM_EL = "MENUITEM";
    /** Element for the menu separator creation. */
    protected static final String MENU_SEPARATOR_EL = "SEPARATOR";
    /** Element for the close button hook creation. */
    protected static final String CLOSE_HOOK_EL = "CLOSEHOOK";

    /*
     *
     * Constants identifying the attribute names.
     *
     */

    /** Application title attribute. */
    protected static final String APPLICATION_TITLE_ATTR = "Title";
    /** Application bundle attribute. */
    protected static final String APPLICATION_BUNDLE_ATTR = "Bundle";
    /** Application icon attribute. */
    protected static final String APPLICATION_ICON_ATTR = "Icon";
    /** Action name attribute. */
    protected static final String ACTION_NAME_ATTR = "Name";
    /** Action class attribute. */
    protected static final String ACTION_CLASS_ATTR = "Class";
    /** Action mnemonic attribute. */
    protected static final String ACTION_MNEMO_ATTR = "Mnemonic";
    /** Action enabled attribute. */
    protected static final String ACTION_ENABLED_ATTR = "Enabled";
    /** Action accelerator attribute. */
    protected static final String ACTION_ACC_ATTR = "Accelerator";
    /** Action parameter key attribute. */
    protected static final String ACTION_PARAM_KEY_ATTR = "Key";
    /** Action parameter value attribute. */
    protected static final String ACTION_PARAM_VAL_ATTR = "Value";
    /** Menu name attribute. */
    protected static final String MENU_NAME_ATTR = "Name";
    /** Menu item action attribute. */
    protected static final String MENU_ITEM_ACTION_ATTR = "Action";

//////////////////////////////////////////////////////////////////////////
//
// Instance members
//
//////////////////////////////////////////////////////////////////////////

    /**
     * The location of the XML file.
     */
    String _filename;

    /** 
     * Internal counter indicating which pass the parser is in.
     */
    int _pass;

    /** The application to populate from the XML file. */
    AutoApplication _app;

    /** The stack of the menu elements. */
    Stack _currentMenu;

    /** The currently processed action. */
    Action _currentAction;

//////////////////////////////////////////////////////////////////////////
//
// Constructors
//
//////////////////////////////////////////////////////////////////////////

    /**
     * Default constructor. It requires the name of the XML file from which
     * to build the application.
     * @param filename the name of the XML file
     */
    public ApplicationLoader(String filename) {
        _filename = filename;
        _currentMenu = new Stack();

        // if the action map has not been constructed, do it.
        // better doing it here than in a static block for
        // exception handling.
        synchronized(ApplicationLoader.class) {
            if (_ActionMap == null) {
                _ActionMap = new HashMap(20);
                _ActionMap.put(APPLICATION_EL, new Integer(APPLICATION));
                _ActionMap.put(ACTION_EL, new Integer(ACTION));
                _ActionMap.put(ACTION_PARAM_EL, new Integer(ACTION_PARAM));
                _ActionMap.put(MENU_BAR_EL, new Integer(MENU_BAR));
                _ActionMap.put(MENU_EL, new Integer(MENU));
                _ActionMap.put(MENU_ITEM_EL, new Integer(MENU_ITEM));
                _ActionMap.put(MENU_SEPARATOR_EL, new Integer(MENU_SEPARATOR));
                _ActionMap.put(CLOSE_HOOK_EL, new Integer(CLOSE_HOOK));
            }
        }

    }

//////////////////////////////////////////////////////////////////////////
//
// Instance methods
//
//////////////////////////////////////////////////////////////////////////
    /**
     * Construct an input source to access the XML file.
     * @return A new available InputSource instance.
     */
    InputSource getInputSource() {
        // First, try to look for the file into the classpath
        InputStream is = ClassLoader.getSystemResourceAsStream(_filename);
        // If not found in the classpath, get it from the filesystem
        if (is == null) {
        }
        return new InputSource(is);
    }

    /**
     * Build the application from the specified XML file.
     * @param app The application to populate.
     * @throws SAXException should not happen
     * @throws IOException should not happen
     */
    public synchronized void build(AutoApplication app) 
        throws SAXException, IOException {
        if (app == null) {
            throw new IllegalArgumentException(getString(KEY_ERR_APP_NULL));
        }
        _app = app;
        SAXParser parser = new SAXParser();
        parser.setContentHandler(this);

        try {
            // First pass create the action references
            _pass = 1;
            _currentAction = null;
            parser.parse(getInputSource());
            // Second pass create the other objects
            _pass = 2;
            _currentMenu.empty();
            parser.parse(getInputSource());
        } catch(SAXException saxe) {
            cat.fatal(getString(KEY_ERR_SAX), saxe);
            cat.fatal(getString(KEY_ERR_SAX_UNDER), saxe.getException());
            throw saxe;
        } catch(IOException ioe) {
            cat.fatal(getString(KEY_ERR_PARSE_IO), ioe);
            throw ioe;
        }
    }

    /**
     * Internationalization method. Uses the I18N class to get the correct
     * translations.
     * @param key The key of the string to retrieve.
     * @return The localized string.
     */
    public String getString(String key) {
        return I18N.getString(RESBDL, key, Locale.getDefault());
    }

    /**
     * Create or update an application. The following attributes are mandatory:
     * <UL>
     * <LI>Title: the title key of the application.
     * </UL>
     * The following attributes are optional:
     * <UL>
     * <LI>Bundle: the name of the resource bundle used by the application.
     * <LI>Icon: the icon of the application.
     * </UL>
     * @param atts The XML element attributes.
     * @throws SAXException should not happen
     */
    public void createApplication(Attributes atts) throws SAXException {
        String title = atts.getValue(APPLICATION_TITLE_ATTR);
        // make sure it has a title
        if (title == null) {
            throw new SAXException(getString(KEY_ERR_TITLE_NULL));
        }
        // First set the bundle name if it is available
        String bundle = atts.getValue(APPLICATION_BUNDLE_ATTR);
        if (bundle != null) {
            _app.setBundleName(bundle);
        }
        // Set the title
        _app.setTitle(title);
        // Set the icon
        String icon = atts.getValue(APPLICATION_ICON_ATTR);
        if (icon != null) {
            _app.setIcon(icon);
        }
    }

    /**
     * Create or update an action. The following attributes are mandatory:
     * <UL>
     * <LI>Name: the unique identifier of the action, also used as the key
     * for the action name;
     * <LI>Class: the action class name.
     * </UL>
     * The following attributes are optional:
     * <UL>
     * <LI>Mnemonic: the key used to access the action from its context.
     * The code key is built using the KeyStroke class from the specified
     * string. It is thus case sensitive and upper case letters should be used
     * to refer to the letter keys;
     * <LI>Accelerator: a key combination to invoke the action from anywhere
     * in the application. The syntax for the key combination is the one
     * defined in the KeyStroke class. In particular, letters should be upper
     * case and the name of the control keys should be lower case;
     * <LI>Enabled: indicate the initial state of the action - enabled or
     * disabled. Use the values<TT>true</TT> and <TT>false</TT>.
     * </UL>
     * @param atts The XML element attributes.
     * @throws SAXException should not happen
     */
    public void createAction(Attributes atts) throws SAXException {
        String name = atts.getValue(ACTION_NAME_ATTR);
        // make sure it has an identifier
        if (name == null) {
            throw new SAXException(getString(KEY_ERR_NULL_ACTION_NAME));
        }
        // TODO: handle the update case

        String className = atts.getValue(ACTION_CLASS_ATTR);
        // make sure it has a class name
        if (className == null) {
            throw new SAXException(getString(KEY_ERR_NULL_ACTION_CLASS));
        }

        try {
            // get the class
            Class actionClass = Class.forName(className);

            // instantiate the action
            Action action = (Action)actionClass.newInstance();
            action.putValue(Action.NAME, name);
            // add it to the application
            _app.getActions().put(name, action);
            // add the application to the action
            action.putValue(AutoApplication.KEY_AUTO_APP, _app);
            // if the action has a mnemonic, set it - only works with JDK 1.3+
            String mnemo = atts.getValue(ACTION_MNEMO_ATTR);
            if (mnemo != null) {
                //Object keyCode = KeyEvent.class.getField(mnemo).get(null);
                int keyCode = KeyStroke.getKeyStroke(mnemo).getKeyCode();
                action.putValue(Action.MNEMONIC_KEY, new Integer(keyCode));
            }
            // if the action has an accelerator, set it
            // only works with JDK 1.3+
            String acc = atts.getValue(ACTION_ACC_ATTR);
            if (acc != null) {
                KeyStroke keyStroke = KeyStroke.getKeyStroke(acc);
                action.putValue(Action.ACCELERATOR_KEY, keyStroke);
            }
            // if the enabled option is specified, apply it
            String enabled = atts.getValue(ACTION_ENABLED_ATTR);
            if (enabled != null 
                && enabled.equalsIgnoreCase(String.valueOf(false))) {
                action.setEnabled(false);
            }
            // if the action is translatable, set its resource bundle to the
            // application's.
            if (action instanceof I18NAbstractAction) {
                ((I18NAbstractAction)action).setBundleName(
                    _app.getBundleName());
            }
            _currentAction = action;
        } catch (Exception e) {
            throw new SAXException(e);
        }
    }

    /**
     * Create or update an action. The following attributes are mandatory:
     * <UL>
     * <LI>Key: the key of the action parameter to set;
     * <LI>Value: the value of the parameter.
     * </UL>
     * @param atts The XML element attributes.
     * @throws SAXException should not happen
     */
    public void createActionParam(Attributes atts) throws SAXException {
        String key = atts.getValue(ACTION_PARAM_KEY_ATTR);
        // make sure it has a key
        if (key == null) {
            throw new SAXException(getString(KEY_ERR_NULL_PARAM_KEY));
        }
        String value = atts.getValue(ACTION_PARAM_VAL_ATTR);
        // make sure it has a value
        if (value == null) {
            throw new SAXException(getString(KEY_ERR_NULL_PARAM_VAL));
        }
        _currentAction.putValue(key, value);
    }

    /**
     * Create or update a menu bar.
     *
     * @param atts The XML element attributes
     * @throws SAXException should not happen
     */
    public void createMenuBar(Attributes atts) throws SAXException {
        JMenuBar menuBar = new JMenuBar();
        _app.getMainFrame().setJMenuBar(menuBar);
        _currentMenu.push(menuBar);
        cat.debug(getString(KEY_MES_MENUBAR_CREATED));
    }

    /**
     * Create or update a menu. The following attributes are mandatory:
     * <UL>
     * <LI>Name: the key
     * for the menu name;
     * </UL>
     * The following attributes are optional:
     * <UL>
     * <LI>Mnemonic: the key used to access the action from its context.
     * The code key is built using the KeyStroke class from the specified
     * string. It is thus case sensitive and upper case letters should be used
     * to refer to the letter keys.
     * </UL>
     * @param atts The XML element attributes.
     * @throws SAXException should not happen
     */
    public void createMenu(Attributes atts) throws SAXException {
        String name = atts.getValue(MENU_NAME_ATTR);
        // make sure it has an identifier
        if (name == null) {
            throw new SAXException(getString(KEY_ERR_NULL_MENU_NAME));
        }
        // TODO: handle the update case

        // Create the menu
        I18NMenu menuAction = new I18NMenu();
        menuAction.putValue(Action.NAME, name);
        menuAction.setBundleName(_app.getBundleName());
        // If a mnemonic is specified, set it
        String mnemo = atts.getValue(ACTION_MNEMO_ATTR);
        if (mnemo != null) {
            int keyCode = KeyStroke.getKeyStroke(mnemo).getKeyCode();
            menuAction.putValue(Action.MNEMONIC_KEY, new Integer(keyCode));
        }

        JMenu menu = new JMenu(menuAction);
        menu.setName(name);
        _app.getMenus().put(name, menu);
        if (!_currentMenu.isEmpty()) {
            MenuElement parent = (MenuElement)_currentMenu.peek();
            if (parent instanceof JMenuBar) {
                ((JMenuBar)parent).add(menu);
            } else {
                ((JMenu)parent).add(menu);
            }
        }
        _currentMenu.push(menu);
    }

    /**
     * Create or update a menu item. The following attributes are mandatory:
     * <UL>
     * <LI>Action: the key of the action associated to this menu item.
     * It is the action that holds all the information for the menu item
     * representation on the screen.
     * </UL>
     *
     * @param atts The XML element attributes.
     * @throws SAXException should not happen
     */
    public void createMenuItem(Attributes atts) throws SAXException {
        String name = atts.getValue(MENU_ITEM_ACTION_ATTR);
        // make sure it has an identifier
        if (name == null) {
            throw new SAXException(getString(KEY_ERR_NULL_MENU_ACTION));
        }
        // TODO: handle the update case

        // Create the menu item
        Action action = (Action)_app.getActions().get(name);
        JMenuItem menuItem = new JMenuItem(action);
        menuItem.setName(name);
        JMenu parent = (JMenu)_currentMenu.peek();
        parent.add(menuItem);
    }

    /**
     * Create a menu separator.
     * @param atts The XML element attributes.
     * @throws SAXException should not happen
     */
    public void createMenuSeparator(Attributes atts) throws SAXException {
        // Create the menu separator
        JMenu parent = (JMenu)_currentMenu.peek();
        parent.addSeparator();
    }

    /**
     * Create a hook to the window close icon. The following attributes are 
     * mandatory:
     * <UL>
     * <LI>Action: the key of the action executed when closing the window.
     * </UL>
     * <P>Usually, the window close hook will be an action that will issue
     * an action event commanding the application to exit. It seems unsecure
     * to exit from the hook itself, except if it's an application specific
     * hook.
     * @param atts the attributes
     * @throws SAXException should not happen
     */
    public void createCloseHook(Attributes atts) throws SAXException {
        JFrame frame = _app.getMainFrame();
        String name = atts.getValue(MENU_ITEM_ACTION_ATTR);
        // make sure it has an identifier
        if (name == null) {
            throw new SAXException(getString(KEY_ERR_NULL_HOOK_ACTION));
        }
        // get the action
        Action action = (Action)_app.getActions().get(name);
        frame.addWindowListener(new ClosingHook(action));
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }
//---------------------------------------//
// ContentHandler implementation - start //
//---------------------------------------//

    /** @see org.xml.sax.ContentHandler */
    public void startDocument() {
    }

    /** @see org.xml.sax.ContentHandler */
    public void endDocument() {
    }

    /** @see org.xml.sax.ContentHandler */
    public void startElement(String namespaceURI, 
                             String localName, 
                             String qName, 
                             Attributes atts)
        throws SAXException {
        cat.debug(getString(KEY_MES_PROCESSING) + localName);

        // get the action index
        Integer action = (Integer)_ActionMap.get(localName);

        // ignore unknown actions
        if (action == null) {
            cat.warn(getString(KEY_MES_UNKNOWN_TAG) + localName);
            return;
        }

        if (_pass == 1) {
            switch (action.intValue()) {
                case APPLICATION: createApplication(atts);
                    break;
                case ACTION: createAction(atts);
                    break;
                case ACTION_PARAM: createActionParam(atts);
                    break;
            }
        }
        if (_pass == 2) {
            switch (action.intValue()) {
                case MENU_BAR: createMenuBar(atts);
                    break;
                case MENU: createMenu(atts);
                    break;
                case MENU_ITEM: createMenuItem(atts);
                    break;
                case MENU_SEPARATOR: createMenuSeparator(atts);
                    break;
                case CLOSE_HOOK: createCloseHook(atts);
                    break;
            }
        }
    }

    /** @see org.xml.sax.ContentHandler */
    public void endElement(String namespaceURI, 
                           String localName, 
                           String qName) {
        // get the action index
        Integer action = (Integer)_ActionMap.get(localName);

        // ignore unknown actions
        if (action == null) {
            return;
        }
        if (_pass == 1) {
            switch (action.intValue()) {
                case ACTION: _currentAction = null;
                    break;
            }
        }
        if (_pass == 2) {
            switch (action.intValue()) {
                case MENU_BAR: _currentMenu.pop();
                    break;
                case MENU: _currentMenu.pop();
                    break;
            }
        }
    }

    /** @see org.xml.sax.ContentHandler */
    public void skippedEntity(String name) {
    }

    /** @see org.xml.sax.ContentHandler */
    public void characters(char[] ch, 
                           int start, 
                           int length) {
    }

    /** @see org.xml.sax.ContentHandler */
    public void ignorableWhitespace(char[] ch, 
                                    int start, 
                                    int length) {
    }

    /** @see org.xml.sax.ContentHandler */
    public void processingInstruction(String target, 
                                      String data) {
    }

    /** @see org.xml.sax.ContentHandler */
    public void startPrefixMapping(String prefix, 
                                   String uri) {
    }

    /** @see org.xml.sax.ContentHandler */
    public void endPrefixMapping(String prefix) {
    }

    /** @see org.xml.sax.ContentHandler */
    public void setDocumentLocator(Locator locator) {
    }

//--------------------------------------//
// ContentHandler implementation - stop //
//--------------------------------------//


//////////////////////////////////////////////////////////////////////////
//
// Class methods
//
//////////////////////////////////////////////////////////////////////////

//-------------------------------------------------//
// Logging declarations and implementation - start //
//-------------------------------------------------//
    /**
     * Category used for logging this class events.
     */
    private static Category cat = 
        Category.getInstance(ApplicationLoader.class);

//------------------------------------------------//
// Logging declarations and implementation - stop //
//------------------------------------------------//

/**
 * A class that forwards window closing events to an action.
 *
 * @author Matthieu Philip
 */
    class ClosingHook extends WindowAdapter {
        /** The action to forward the closing events to. */
        Action _action;
        
        /**
         * Default constructor. Requires the action to forward the events to.
         */
        ClosingHook(Action action) {
            _action = action;
        }
        
        /** @see java.awt.event.WindowAdapter */
        public void windowClosing(WindowEvent e) {
            _action
                .actionPerformed(new ActionEvent(e.getSource(), 
                                                 ActionEvent.ACTION_PERFORMED, 
                                                 getClass().getName()));
        }
    }
}
 
/*
 * $Log$
 * Revision 1.9  2003/03/19 11:52:15  yan
 * fixed style
 *
 * Revision 1.8  2003/03/18 16:56:28  yan
 * fixed style
 *
 * Revision 1.7  2002/11/05 21:34:13  mphilip
 * Added the application icon load.
 *
 * Revision 1.6  2002/09/26 11:12:29  mphilip
 * Non declared class changed to inner class.
 *
 * Revision 1.5  2002/05/02 16:05:51  yan
 * moved
 *
 * Revision 1.4  2002/04/25 23:30:18  mphilip
 * Added menu bar element.
 *
 * Revision 1.3  2002/04/12 12:41:35  mphilip
 * Internationalized the menus.
 *
 * Revision 1.2  2002/04/12 11:42:23  mphilip
 * Added the closing event hook.
 *
 * Revision 1.1  2002/04/11 22:02:26  mphilip
 * Initial revision.
 *
 */
