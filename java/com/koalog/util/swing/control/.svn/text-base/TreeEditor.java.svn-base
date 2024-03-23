package com.koalog.util.swing.control;

import java.awt.Component;
import java.awt.Dimension;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import org.apache.log4j.Category;

/**
 * A tree editor that can be configured to handle any type of tree structure.
 * The configuration consists in specifying the tree and the
 * class-editor map.
 * <P>A possible way to use this class is by subclassing it and give the
 * subclass a constructor that only takes the tree as an argument, the map
 * being generated from class constants inside the constructor.
 *
 * @author Matthieu Philip
 */
public class TreeEditor extends JPanel implements TreeSelectionListener {
//////////////////////////////////////////////////////////////////////////
//
// Class members
//
//////////////////////////////////////////////////////////////////////////
    /**
     * Category used for logging this class events.
     */
    private static Category cat = Category.getInstance(TreeEditor.class);
    /** The default tree width. */
    static final int DEFAULT_TREE_WIDTH = 200;
    /** The default panel width. */
    static final int DEFAULT_PANEL_WIDTH = 400;
    /** The default height. */
    static final int DEFAULT_HEIGHT = 400;

//////////////////////////////////////////////////////////////////////////
//
// Instance members
//
//////////////////////////////////////////////////////////////////////////
    /** The edited tree. */
    Tree _tree;

    /** The class-editor map. */
    Map _map;

    /** The currently edited object. */
    Object _currentObject;

    /** The split pane. */
    JSplitPane _split;

    /** The empty right pane. */
    JPanel _empty;

//////////////////////////////////////////////////////////////////////////
//
// Constructors
//
//////////////////////////////////////////////////////////////////////////
    /**
     * Default constructor. Requires a <TT>Tree</TT> and a class-editor map.
     * Each value in the map must of a type that is a subclass of <CODE>
     * Component</CODE>, and must implement
     * <CODE>ObjectEditor</CODE>.
     * @param tree The tree of the editable objects.
     * @param map The map between classes and editor instances.
     */
    public TreeEditor(Tree tree, Map map) {
        // if the tree name is unique, the following should also be unique...
        setName("TreeEditor:" + tree.getName());
        _tree = tree;
        _map = map;
        // make sure that each element in the map is an ObjectEditor and
        // a Component
        setLayout(new java.awt.CardLayout());
        _split = new JSplitPane();
        _split.setLeftComponent(new JScrollPane(tree) {
                public Dimension getPreferredSize() {
                    return new Dimension(DEFAULT_TREE_WIDTH, DEFAULT_HEIGHT);
                }
            });
        _empty = new JPanel();
        _split.setRightComponent(new JScrollPane(_empty) {
                public Dimension getPreferredSize() {
                    return new Dimension(DEFAULT_PANEL_WIDTH, DEFAULT_HEIGHT);
                }
            });
        tree.addTreeSelectionListener(this);
        // The constraint is not used, but it does not hurt to give
        // a meaningful one
        add(_split, "TreeEditor:split");
    }


//////////////////////////////////////////////////////////////////////////
//
// Instance methods
//
//////////////////////////////////////////////////////////////////////////

    /**
     * Handles the tree selection events. Each type an object is selected,
     * the RHS panel is populated with an editor that can handle the
     * selected object.
     * @param e The selection event.
     */
    public void valueChanged(TreeSelectionEvent e) {
        TreePath[] paths = _tree.getSelectionPaths();
        // If there is no selection or more than one object selected
        // then show nothing
        if (paths == null || paths.length == 0 || paths.length > 1) {
            ((JScrollPane)_split.getRightComponent()).setViewportView(_empty);
            return;
        }
        try {
            // First get the node and make sure it's a mutable tree node
            DefaultMutableTreeNode node = 
                (DefaultMutableTreeNode)paths[0].getLastPathComponent();
            _currentObject = node.getUserObject();
            // Get object class associated editor
            ObjectEditor editor = 
                (ObjectEditor)_map.get(_currentObject.getClass());
            if (editor == null) {
                cat.warn("Could not find an editor for " 
                         + _currentObject.getClass());
                // editor = new ObjectEditor();
            } else {
                editor.setObject(_currentObject);
            }
            ((JScrollPane)_split.getRightComponent()).setViewportView(
                (Component)editor);
        } catch(ClassCastException cce) {
            // The node is not a mutable tree node
            cat.error(cce);
        }
    }

//////////////////////////////////////////////////////////////////////////
//
// Class methods
//
//////////////////////////////////////////////////////////////////////////
    
    /**
     * Temporary - will disapear.
     * @param args the command-line arguments
     * @throws Exception should not happen
     */
    public static void main(String[] args) throws Exception {
        org.apache.log4j.BasicConfigurator.configure();
        com.koalog.util.swing.BaseApplication app = 
            new com.koalog.util.swing.BaseApplication();
        org.w3c.dom.Document doc = 
            javax.xml.parsers.DocumentBuilderFactory
            .newInstance()
            .newDocumentBuilder()
            .parse
            ("/home/mphilip/koalog/util/tests/applications/loader_test.xml");
        DOMTree tree = new DOMTree(doc.getDocumentElement());
        app.getMainFrame().getContentPane().add(
            new TreeEditor(tree, new java.util.HashMap()));
        app.getMainFrame().pack();
        app.run();
    }
}
/*
 * $Log$
 * Revision 1.7  2003/03/18 16:57:51  yan
 * fixed style
 *
 * Revision 1.6  2002/06/18 19:50:13  mphilip
 * Fixed bug: unstable split divider.
 *
 * Revision 1.5  2002/05/02 16:05:51  yan
 * moved
 *
 * Revision 1.4  2002/04/25 23:14:05  mphilip
 * Fixed bug: initial divider location was incorrect.
 *
 * Revision 1.3  2002/04/17 22:50:50  mphilip
 * Modified error handling for null editors.
 *
 * Revision 1.2  2002/04/17 16:53:05  mphilip
 * Added a warning.
 *
 * Revision 1.1  2002/04/15 14:02:43  mphilip
 * Initial revision.
 *
 */
