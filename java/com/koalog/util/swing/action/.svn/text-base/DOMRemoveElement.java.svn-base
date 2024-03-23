package com.koalog.util.swing.action;

import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import org.apache.log4j.Category;
import org.w3c.dom.Element;
import com.koalog.util.swing.AutoApplication;
import com.koalog.util.swing.control.DOMTree;
import com.koalog.util.swing.control.ElementPeer;

/**
 * An action that remove an element from a DOM.
 *
 * @author Matthieu Philip
 */
public class DOMRemoveElement extends I18NAbstractAction {
//////////////////////////////////////////////////////////////////////////
//
// Class members
//
//////////////////////////////////////////////////////////////////////////
    /** 
     * The key of the removal confirmation message.
     */
    String _messageKey = "MES_CONFIRM_REMOVAL";

//////////////////////////////////////////////////////////////////////////
//
// Constructors
//
//////////////////////////////////////////////////////////////////////////
    /**
     * Default constructor.
     */
    public DOMRemoveElement() {
        putValue(_messageKey, _messageKey);
        _translatables.add(_messageKey);
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
        DOMTree tree = (DOMTree)app.getValue(DOMTree.KEY_CUR_TREE);
        DefaultMutableTreeNode node =
            (DefaultMutableTreeNode)app.getValue(DOMTree.KEY_CUR_ELEMENT);
        int res = JOptionPane.showConfirmDialog(
            app.getMainFrame(), getValue(_messageKey));
        if (res == JOptionPane.YES_OPTION) {
            Element el = ((ElementPeer)node.getUserObject()).getElement();
            DOMTree.dirty(el);
            el.getParentNode().removeChild(el);
            ((DefaultTreeModel)tree.getModel()).removeNodeFromParent(node);
        }
        return null;
    }

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
        Category.getInstance(DOMRemoveElement.class);

//------------------------------------------------//
// Logging declarations and implementation - stop //
//------------------------------------------------//
}
/*
 * $Log$
 * Revision 1.4  2004/07/02 20:44:13  mat
 * Added a system that let detect modifications to the DOM.
 *
 * Revision 1.3  2003/03/18 16:57:12  yan
 * fixed style
 *
 * Revision 1.2  2002/05/02 16:05:51  yan
 * moved
 *
 * Revision 1.1  2002/04/25 21:06:01  mphilip
 * Initial revision.
 *
 */
