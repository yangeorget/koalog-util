package com.koalog.util.swing.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Locale;
import javax.swing.AbstractAction;
import com.koalog.util.I18N;
import com.koalog.util.swing.AutoApplication;

/**
 * An internationalizable version of the AbstractAction.
 *
 * <P>By using this class as the super class for your actions, you make
 * your action I18N. The way it works is by using keys instead of
 * strings in all action methods that require a string. No all string values
 * are localized.</P>
 *
 * <P>It is recommanded to specify a resource bundle and a locale at the
 * construction time.</P>
 *
 * @author Matthieu Philip
 */
public abstract class I18NAbstractAction extends AbstractAction {
//////////////////////////////////////////////////////////////////////////
//
// Instance members
//
//////////////////////////////////////////////////////////////////////////
    /** The set containing the keys of the translatable strings. */
    protected HashSet _translatables;

    /** The action locale. */
    Locale _locale = Locale.getDefault();

    /**
     * The name of the resource bundle storing this action strings.
     */
    String _bundleName = "com.koalog.util.swing.action.Messages";

//////////////////////////////////////////////////////////////////////////
//
// Constructors
//
//////////////////////////////////////////////////////////////////////////
    /**
     * Default constructor.
     */
    public I18NAbstractAction() {
        if (_translatables == null) {
            _translatables = new HashSet();
            _translatables.add(NAME);
            _translatables.add(SHORT_DESCRIPTION);
            _translatables.add(LONG_DESCRIPTION);
        }
    }

//////////////////////////////////////////////////////////////////////////
//
// Instance methods
//
//////////////////////////////////////////////////////////////////////////
    /** @see javax.swing.AbstractAction */
    public void actionPerformed(ActionEvent e) {
        AutoApplication app = (AutoApplication)getValue(
            AutoApplication.KEY_AUTO_APP);
        app.addBusyAction();
        try {
            ActionEvent result = perform(e);
            // if the action was performed successfully, notify the flow
            // controller, which is the auto application.
            if (result != null) {
                if (app instanceof ActionListener) {
                    ((ActionListener)app).actionPerformed(result);
                }
            }
        } finally {
            app.removeBusyAction();
        }
    }

    /**
     * Perform the action. That's where all the action logic goes.
     * But no action flow logic is expected to be here. The flow logic
     * takes place at a higher level and is based on the returned action
     * events.
     * @param e an action event
     * @return An action event representing the action performed, or null
     * if no action was performed or if the notification of the action
     * performed should not be propagated.
     */
    public abstract ActionEvent perform(ActionEvent e);

    /** 
     * Set the name of the resource bundle to use for string translations.
     * @param name The name of the resource bundle.
     */
    public void setBundleName(String name) {
        _bundleName = name;
    }

    /**
     * Get the internationalized value associated to the specified key.
     * The internationalization is done only if the value is of type string.
     * If the value is a string but does not yield to any other string, then
     * it is returned unchanged.
     * @param key The key of the object to retrieve.
     * @return The object associate to the given key, translated if it is a
     * String.
     */
    public Object getValue(String key) {
        Object res = super.getValue(key);
        if (res == null || !_translatables.contains(key)) {
            // the returned value is not a string
            return res;
        }
        key = (String)res;
        return I18N.getString(_bundleName, key, _locale);
    }

    /**
     * Get the value associated to the specified key without performing any
     * translation.
     * @param key a string
     * @return The value associated to the key.
     */
    public Object getRawValue(String key) {
        return super.getValue(key);
    }

}
/*
 * $Log$
 * Revision 1.6  2003/03/18 16:57:12  yan
 * fixed style
 *
 * Revision 1.5  2002/11/13 14:37:16  yan
 * removed unecessary synchronization
 *
 * Revision 1.4  2002/05/02 16:05:51  yan
 * moved
 *
 * Revision 1.3  2002/04/17 16:51:02  mphilip
 * Added the busy mechanism.
 *
 * Revision 1.2  2002/04/12 11:43:37  mphilip
 * Modified the interface for better flow control.
 *
 * Revision 1.1  2002/04/11 22:01:56  mphilip
 * Initial revision.
 *
 */







