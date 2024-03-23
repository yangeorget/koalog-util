package com.koalog.util.swing.control;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTree;

import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreeCellRenderer;

import org.w3c.dom.Element;

/**
 * A base class for XML DOM element peers. To create a peer class for
 * an element type, subclass this class, and override, at least,
 * <CODE>toString()</CODE>. To display different icons, also override
 * <CODE>getRenderer()</CODE> so that it returns a class shared instance
 * of <CODE>TreeCellRenderer</CODE>.
 *
 * @author Matthieu Philip
 */
public class ElementPeer implements TreeCellRenderer {
    /** This class shared instance of <CODE>TreeCellRenderer</CODE>. */
    private static TreeCellRenderer _Renderer = new DefaultTreeCellRenderer();

    /** The peer element. */
    protected Element _element;

    /** A backpointer to the tree. */
    JTree _lastTree;

    /** The row number in the tree. */
    int _lastRow;

    /** The open icon. */
    protected Icon _openIcon;
    /** The closed icon. */
    protected Icon _closedIcon;
    /** Leaf icon. */
    protected Icon _leafIcon;

    /**
     * Default constructor. It requires the peer element.
     * @param element The peer element.
     */
    public ElementPeer(Object element) {
        _openIcon = null;
        _closedIcon = null;
        _leafIcon = null;
        _element = (Element)element;
    }
    
    /**
     * Get the peer element.
     * @return The associated <CODE>Element</CODE>.
     */
    public Element getElement() {
        return _element;
    }

    /**
     * Method called by the standard tree cell renderers to get the
     * string displayed in the representation of the tree.
     * @return The string representation of this object.
     */
    public String toString() {
        return _element.getTagName() + ": " + _element.getAttribute("Name");
    }

    /**
     * Get the tree cell renderer that should render this object. It should
     * return a class shared instance for better performances.
     * @return The cell renderer to use for this object.
     */
    public TreeCellRenderer getRenderer() {
        return _Renderer;
    }

    /** @see javax.swing.tree.TreeCellRenderer */
    public synchronized Component 
        getTreeCellRendererComponent(JTree tree, 
                                     Object value, 
                                     boolean selected, 
                                     boolean expanded,
                                     boolean leaf, 
                                     int row, 
                                     boolean hasFocus) {
        Component c = getRenderer().getTreeCellRendererComponent(tree, 
                                                                 value, 
                                                                 selected, 
                                                                 expanded, 
                                                                 leaf, 
                                                                 row, 
                                                                 hasFocus);
        if (c instanceof JComponent) {
            ((JComponent)c).setToolTipText(toString());
        }
        if (c instanceof JLabel) {
            JLabel label = (JLabel)c;
            if (expanded && _openIcon != null) {
                label.setIcon(_openIcon);
            } else if (!expanded && !leaf && _closedIcon != null) {
                label.setIcon(_closedIcon);
            } else if (leaf && _leafIcon != null) {
                label.setIcon(_leafIcon);
            }
        }
        _lastTree = tree;
        _lastRow = row;
        return c;
    }

    /**
     * Invoked by editors to update the UI.
     */
    public synchronized void update() {
        if (_lastTree != null 
            && _lastTree.getModel() instanceof DefaultTreeModel) {
            TreeNode thisNode = (TreeNode)
                _lastTree.getPathForRow(_lastRow).getLastPathComponent();
            ((DefaultTreeModel)_lastTree.getModel()).nodeChanged(thisNode);
        }
    }
}
/*
 * $Log$
 * Revision 1.6  2003/03/18 16:57:51  yan
 * fixed style
 *
 * Revision 1.5  2002/07/22 12:06:48  mphilip
 * Added support for custom icons.
 *
 * Revision 1.4  2002/05/25 12:23:25  mphilip
 * Fixed bug: tree not updated when element attributes involved in the
 * element representation are modified.
 *
 * Revision 1.3  2002/05/02 16:05:51  yan
 * moved
 *
 * Revision 1.2  2002/04/17 16:52:44  mphilip
 * Add tooltop handling.
 *
 * Revision 1.1  2002/04/15 14:31:14  mphilip
 * Initial revision.
 *
 */

