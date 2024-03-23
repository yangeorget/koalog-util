package com.koalog.util.swing.control;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import org.apache.log4j.Category;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import com.koalog.util.I18N;

/**
 * An editor that can handle any type of tag. However, it is not
 * efficient and should only be used as a rescue solution when
 * no specific editor is available.
 * @author Matthieu Philip
 */
public class DOMElementEditor 
    extends JTable 
    implements ObjectEditor, ActionListener, MouseListener {
    //------------------------------------------------------------------------
    // CONSTANTS
    //------------------------------------------------------------------------
    /** The add attribute label key. */
    static final String KEY_ADD_ATT = "LAB_ADD_ATT";
    /** The remove attribute label key. */
    static final String KEY_REMOVE_ATT = "LAB_REMOVE_ATT";
    /** The attribute name question key. */
    static final String KEY_ATT_NAME_QUESTION = "MES_ATT_NAME_QUESTION";
    /** The name of the remove menu item. */
    static final String NAME_REMOVE = "DOMElementEditor:menu:remove";
    /** The name of the add menu item. */
    static final String NAME_ADD = "DOMElementEditor:menu:add";

    /*
     * Keys used to access labels and messages
     */
    /** The attribute name label key. */
    static final String KEY_ATT_NAME = "LAB_ATT_NAME";
    /** The attribute value label key. */
    static final String KEY_ATT_VALUE = "LAB_ATT_VALUE";

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    /** The resource bundle used for i18n. */
    static String _ResBdl = "com.koalog.util.swing.control.Messages";

    /**
     * Category used for logging this class events.
     */
    private static Category cat = 
        Category.getInstance(DOMElementEditor.class);
    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    /** The i18n helper. */
    I18N _i18n;

    /** The edited DOM element. */
    Element _element;

    /** The element peer object. */
    ElementPeer _peer;

    /** The contextual menu. */
    JMenu _menu;

    /** The row targeted by the contextual menu. */
    int _currentRow = -1;

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------
    /**
     * Default constructor.
     */
    public DOMElementEditor() {
        super();
        _i18n = I18N.getInstance();
        setModel(new DOMElementTableModel());
        // build the contextual menu
        _menu = new JMenu();
        _menu.setName("DOMElementEditor:menu");
        String label = _i18n.getString(_ResBdl, KEY_ADD_ATT);
        JMenuItem item = new JMenuItem(label);
        item.setName(NAME_ADD);
        item.addActionListener(this);
        _menu.add(item);
        label = _i18n.getString(_ResBdl, KEY_REMOVE_ATT);
        item = new JMenuItem(label);
        item.setName(NAME_REMOVE);
        item.addActionListener(this);
        _menu.add(item);
        addMouseListener(this);
    }


    //------------------------------------------------------------------------
    // METHODS 
    //------------------------------------------------------------------------       
    /** 
     * Checks an attribute.
     */
    public void checkAttribute(String name, String value) {
    }

    //------------------------------------------------------------------------
    // METHODS (ObjectEditor)
    //------------------------------------------------------------------------
    /** @see com.koalog.util.swing.control.ObjectEditor */
    public synchronized void setObject(Object obj) {
        removeAll();
        _peer = (ElementPeer)obj;
        _element = _peer._element;
    }

    //------------------------------------------------------------------------
    // METHODS (ActionListener)
    //------------------------------------------------------------------------
    /**
     * Handle the contextual menu actions.
     * @param event The encapsulation of the menu action.
     */
    public synchronized void actionPerformed(ActionEvent event) {
        if (_currentRow >= 0) {
            String name = ((JMenuItem)event.getSource()).getName();
            DOMElementTableModel model = (DOMElementTableModel)getModel();
            String att = (String) model.getValueAt(_currentRow, 0);
            if (NAME_REMOVE.equals(name)) {
                _element.removeAttribute(att);
                model.fireTableRowsDeleted(
                    _currentRow, _currentRow);
                cat.debug("Removed attribute " + att);
            } else if (NAME_ADD.equals(name)) {
                att = JOptionPane.showInputDialog(
                    this, _i18n.getString(_ResBdl, KEY_ATT_NAME_QUESTION));
                if (att != null && !att.trim().equals("")) {
                    _element.setAttribute(att, "");
                    model.fireTableRowsInserted(0, model.getRowCount());
                }
            }
            DOMTree.dirty(_element);
        } else {
            cat.error
                ("Current row is -1, which does not apply to menu actions.");
        }
        // reset the targeted row
        _currentRow = -1;
    }

    //------------------------------------------------------------------------
    // METHODS (MouseListener)
    //------------------------------------------------------------------------
    /** @see java.awt.event.MouseListener */
    public void mouseClicked(MouseEvent e) {
    }

    /** @see java.awt.event.MouseListener */
    public void mouseEntered(MouseEvent e) {
    }
    
    /** @see java.awt.event.MouseListener */
    public void mouseExited(MouseEvent e) {
    }
    
    /**
     * Listens to mouse events in order to display the contextual
     * menu when necessary.
     * @param e The tree selection event.
     */
    public void mousePressed(MouseEvent e) {
        if (!e.isPopupTrigger()) {
            return;
        }
        _currentRow = rowAtPoint(new Point(e.getX(), e.getY()));
        setRowSelectionInterval(_currentRow, _currentRow);
        _menu.getPopupMenu().show(this, e.getX(), e.getY());
    }

    /** @see java.awt.event.MouseListener */
    public void mouseReleased(MouseEvent e) {
    }

    //------------------------------------------------------------------------
    // INNER CLASSES
    //------------------------------------------------------------------------
    /**
     * A table model for this editor.
     * @author Matthieu Philip
     */
    class DOMElementTableModel extends AbstractTableModel {
        public DOMElementTableModel() {
            super();
        }

        /** @see javax.swing.table.AbstractTableModel */
        public int getRowCount() {
            if (_element == null) {
                return 0;
            }
            return _element.getAttributes().getLength();
        }

        /** @see javax.swing.table.AbstractTableModel */
        public int getColumnCount() {
            return 2;
        }

        /** @see javax.swing.table.AbstractTableModel */
        public String getColumnName(int column) {
            if (column == 0) {
                return _i18n.getString(_ResBdl, KEY_ATT_NAME);
            } else {
                return _i18n.getString(_ResBdl, KEY_ATT_VALUE);
            }
        }

        /** @see javax.swing.table.AbstractTableModel */
        public boolean isCellEditable(int row, int col) {
            return col > 0;
        }

        /** @see javax.swing.table.AbstractTableModel */
        public Object getValueAt(int row, int col) {
            Node att = _element.getAttributes().item(row);
            if (col == 0) {
                return att.getNodeName();
            } else {
                return att.getNodeValue();
            }
        }

        /** @see javax.swing.table.AbstractTableModel */
        public void setValueAt(Object value, int row, int col) {
            if (col == 0) {
                // So far, the attribute name can't be set
                return;
            } else {
                Node att = _element.getAttributes().item(row);
                att.setNodeValue(value.toString());
                _peer.update();
                checkAttribute(att.getNodeName(), att.getNodeValue());
                DOMTree.dirty(_element);
            }
        }
    }
}
/*
 * $Log$
 * Revision 1.8  2004/07/02 20:44:13  mat
 * Added a system that let detect modifications to the DOM.
 *
 * Revision 1.7  2004/07/02 16:02:42  yan
 * added a hook for checking attributes
 *
 * Revision 1.6  2003/03/18 16:57:51  yan
 * fixed style
 *
 * Revision 1.5  2002/05/25 12:20:53  mphilip
 * Fixed bug: tree not updated when modifying element attributes used for
 * the element representation in the tree.
 *
 * Revision 1.4  2002/05/24 17:31:02  mphilip
 * Added the row selection when popup menu is invoked.
 * Fixed bug: nullpointer exception when attribute creation is cancelled.
 *
 * Revision 1.3  2002/05/02 16:05:51  yan
 * moved
 *
 * Revision 1.2  2002/04/26 14:26:09  mphilip
 * Added contextual menu.
 *
 * Revision 1.1  2002/04/17 16:52:10  mphilip
 * Initial revision.
 *
 */






