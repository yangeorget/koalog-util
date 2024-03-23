package com.koalog.util.swing.action;

import com.koalog.util.swing.AutoApplication;
import com.koalog.util.swing.control.DOMTree;
import com.koalog.util.swing.control.ElementPeer;

import java.awt.event.ActionEvent;

import java.util.StringTokenizer;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.log4j.Category;
import org.w3c.dom.Element;

/**
 * An action that add an element from a DOM. Mandatory options:
 * <UL>
 * <LI><CODE>ELEMENT_TYPE</CODE>: the type of element to insert.</LI>
 * <LI><CODE>ELEMENT_ATTRS</CODE>: the comma-separated list of the
 * initial element attributes.</LI>
 * </UL>
 *
 * @author Matthieu Philip
 */
public class DOMAddElement extends I18NAbstractAction {
//////////////////////////////////////////////////////////////////////////
//
// Class members
//
//////////////////////////////////////////////////////////////////////////
    /** 
     * The key of the element type.
     */
    static final String KEY_ELEMENT_TYPE = "ELEMENT_TYPE";
    /** 
     * The key of the element attribute list.
     */
    static final String KEY_ELEMENT_ATTRS = "ELEMENT_ATTRS";

//////////////////////////////////////////////////////////////////////////
//
// Constructors
//
//////////////////////////////////////////////////////////////////////////
    /**
     * Default constructor.
     */
    public DOMAddElement() {
    }

//////////////////////////////////////////////////////////////////////////
//
// Instance methods
//
//////////////////////////////////////////////////////////////////////////
    /** @see com.koalog.util.swing.action.I18NAbstractAction */
    public ActionEvent perform(ActionEvent e) {
        cat.debug("Adding an element...");
        AutoApplication app = (AutoApplication)getValue(
            AutoApplication.KEY_AUTO_APP);
        DOMTree tree = (DOMTree)app.getValue(DOMTree.KEY_CUR_TREE);
        DefaultMutableTreeNode node =
            (DefaultMutableTreeNode)app.getValue(DOMTree.KEY_CUR_ELEMENT);
        String type = (String)getValue(KEY_ELEMENT_TYPE);
        String attrs = (String)getValue(KEY_ELEMENT_ATTRS);
        cat.debug("Element type is " + type);
        cat.debug("Element attributes are " + attrs);
        Element el = ((ElementPeer)node.getUserObject()).getElement();
        Element newElement = el.getOwnerDocument().createElement(type);
        if (attrs != null) {
            StringTokenizer st = new StringTokenizer(attrs, ",");
            while (st.hasMoreTokens()) {
                newElement.setAttribute(st.nextToken(), "");
            }
        }
        el.appendChild(newElement);
        DefaultTreeModel model = (DefaultTreeModel)tree.getModel();
        model.insertNodeInto(
            tree.createTreeNode(newElement), node, node.getChildCount());
        DOMTree.dirty(el);
        cat.debug("Done.");
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
    private static Category cat = Category.getInstance(
        DOMRemoveElement.class);

//------------------------------------------------//
// Logging declarations and implementation - stop //
//------------------------------------------------//
}
/*
 * $Log$
 * Revision 1.5  2004/07/02 20:44:13  mat
 * Added a system that let detect modifications to the DOM.
 *
 * Revision 1.4  2003/03/18 16:57:12  yan
 * fixed style
 *
 * Revision 1.3  2002/05/24 17:32:04  mphilip
 * Added initial attributes creation option.
 *
 * Revision 1.2  2002/05/02 16:05:51  yan
 * moved
 *
 * Revision 1.1  2002/04/25 21:05:39  mphilip
 * Initial revision.
 *
 */
