package com.koalog.util.swing.action;

import com.koalog.util.I18N;

import com.koalog.util.swing.AutoApplication;

import java.awt.event.ActionEvent;

import java.util.Locale;

import javax.swing.JOptionPane;

/**
 * A simple action usually invoked when closing an application to get the
 * user confirmation. The confirmation message can be overriden using the
 * <TT>MES_CONFIRM_EXIT</TT> parameter.
 *
 * <P>The default behaviour is not to exit when the user confirmation is
 * positive. However, this can be done by setting the <TT>EXIT_ON_YES</TT>
 * parameter to <TT>true</TT>.</P>
 *
 * @author Matthieu Philip
 */
public class ExitConfirmation extends I18NAbstractAction {
//////////////////////////////////////////////////////////////////////////
//
// Class members
//
//////////////////////////////////////////////////////////////////////////
    /** The parameter used to store the confirmation message key. */
    static String _MessageParam = "MES_CONFIRM_EXIT";
    /** The parameter used to force exit on yes answers. */
    static String _ExitOnYesParam = "EXIT_ON_YES";

    /** 
     * The default key of the confirmation message.
     */
    static String _DefaultMessageKey = "MES_CONFIRM_EXIT";

    /*
     * Non variable labels and message keys.
     */
    static final String KEY_LAB_CONFIRMATION = "LAB_CONFIRMATION";

//////////////////////////////////////////////////////////////////////////
//
// Constructors
//
//////////////////////////////////////////////////////////////////////////
    /**
     * Default constructor.
     */
    public ExitConfirmation() {
        _translatables.add(_MessageParam);
        // default value for the confirmation message
        putValue(_MessageParam, _DefaultMessageKey);
    }

//////////////////////////////////////////////////////////////////////////
//
// Instance methods
//
//////////////////////////////////////////////////////////////////////////
    /** @see com.koalog.util.swing.action.I18NAbstractAction */
    public ActionEvent perform(ActionEvent e) {
        AutoApplication app = (AutoApplication)getValue(
            AutoApplication.KEY_AUTO_APP);
        int result = JOptionPane.showConfirmDialog(
            app.getMainFrame(),
            (String)getValue(_MessageParam),
            I18N.getString(app.getBundleName(),
                           KEY_LAB_CONFIRMATION, 
                           Locale.getDefault()),
            JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.NO_OPTION 
            || result == JOptionPane.CLOSED_OPTION) {
            return null;
        }
        // Do we have an exit on yes case?
        String exitOnYes = (String)getValue(_ExitOnYesParam);
        if (exitOnYes != null 
            && exitOnYes.equalsIgnoreCase(String.valueOf(true))) {
            app.getMainFrame().dispose();
            System.exit(0);
        }
        // create a new action event
        e = new ActionEvent(
            this,
            ActionEvent.ACTION_PERFORMED,
            (String)getRawValue(NAME));
        return e;
    }
}
/*
 * $Log$
 * Revision 1.6  2003/03/18 16:57:12  yan
 * fixed style
 *
 * Revision 1.5  2002/10/30 16:28:26  mphilip
 * Main frame is now disposed on exit.
 *
 * Revision 1.4  2002/08/08 12:20:06  mphilip
 * Fixed bug: missing title.
 *
 * Revision 1.3  2002/05/02 16:05:51  yan
 * moved
 *
 * Revision 1.2  2002/04/12 13:33:16  mphilip
 * Fixed bug: closing the dialog was closing the application.
 *
 * Revision 1.1  2002/04/12 10:48:07  mphilip
 * Initial revision.
 *
 */


