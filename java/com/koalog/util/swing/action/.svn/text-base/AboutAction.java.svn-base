package com.koalog.util.swing.action;

import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

import com.koalog.util.Infos;
import com.koalog.util.swing.AutoApplication;

/**
 * An action that displays an "about" message on the screen. 
 *
 * @author Yan Georget
 */
public class AboutAction extends I18NAbstractAction {
    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------  
    /** @see com.koalog.util.swing.action.I18NAbstractAction */
    public ActionEvent perform(ActionEvent e) {
        Infos infos = 
            ((AutoApplication) getValue(AutoApplication.KEY_AUTO_APP))
            .getInfos();
        StringBuffer message = new StringBuffer();
        message.append(infos.getProductName());
        message.append(" (");
        message.append(infos.getProductVersion());
        message.append(")\n");
        message.append(Infos.getCopyright());
        JOptionPane.showMessageDialog(null, message.toString());
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
 * Revision 1.3  2004/08/25 14:08:52  mat
 * Fixed style.
 *
 * Revision 1.2  2003/03/18 16:57:12  yan
 * fixed style
 *
 * Revision 1.1  2002/11/13 14:40:26  yan
 * initial revision
 *
 */

