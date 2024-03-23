package com.koalog.util.swing.action;

import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

/**
 * A simple action that display a message on the screen. It is mainly used
 * for testing purposes, or to replace actions while they are under
 * construction.
 *
 * @author Matthieu Philip
 */
public class MessageAction extends I18NAbstractAction {
//////////////////////////////////////////////////////////////////////////
//
// Class members
//
//////////////////////////////////////////////////////////////////////////
    /** 
     * The key of the message to be displayed. By default, the Hello World
     * message will be displayed.
     */
    String _messageKey = "MES_HELLO";

//////////////////////////////////////////////////////////////////////////
//
// Constructors
//
//////////////////////////////////////////////////////////////////////////
    /**
     * Default constructor.
     */
    public MessageAction() {
        _translatables.add(_messageKey);
    }

//////////////////////////////////////////////////////////////////////////
//
// Instance methods
//
//////////////////////////////////////////////////////////////////////////
    /** @see com.koalog.util.swing.action.I18NAbstractAction */
    public ActionEvent perform(ActionEvent e) {
        JOptionPane.showMessageDialog(null, getValue(_messageKey));
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
 * Revision 1.4  2003/03/18 16:57:12  yan
 * fixed style
 *
 * Revision 1.3  2002/05/02 16:05:51  yan
 * moved
 *
 * Revision 1.2  2002/04/12 11:44:33  mphilip
 * Updated the interface.
 *
 * Revision 1.1  2002/04/11 22:01:56  mphilip
 * Initial revision.
 *
 */
