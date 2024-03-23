package com.koalog.util.swing.control;

import java.util.Enumeration;
import java.util.HashMap;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import org.apache.log4j.Category;
import org.w3c.dom.Element;

/**
 * A tree that represent a XML document.
 *
 * @author Matthieu Philip
 */
public abstract class XMLTree extends Tree {
//////////////////////////////////////////////////////////////////////////
//
// Class members
//
//////////////////////////////////////////////////////////////////////////
    /*
     * Message keys.
     */
    /** The peer mapping creation message key. */
    static final String KEY_MES_CREATE_PEER_MAP = "MES_CREATE_PEER_MAP";
    /**
     * Category used for logging this class events.
     */
    private static Category cat = Category.getInstance(XMLTree.class);
    /**
     * Default properties resource bundle for the class-renderer and
     * the (tag name)-(peer class) maps.
     */
    static String _ResBdl = "com.koalog.util.swing.control.XMLTree";
    /** Prefix used for peer map entries in the properties file. */
    static String _PeerPrefix = "PEER_";

//////////////////////////////////////////////////////////////////////////
//
// Instance members
//
//////////////////////////////////////////////////////////////////////////
    /** The (tag name)-(peer class) map. */
    protected HashMap _peerMap;

//////////////////////////////////////////////////////////////////////////
//
// Constructors
//
//////////////////////////////////////////////////////////////////////////
    /**
     * Default constructor. Requires the resource bundle name and
     * a DOM element.
     * @param resBdl The name of the properties resource bundle.
     * @param root The root element of the tree.
     */
    public XMLTree(String resBdl, Element root) {
        //
        // TODO: Element is DOM - fix that... we could be SAX here
        //
        super(resBdl);
        _peerMap = new HashMap(5); // low initial capacity for memory
        // process each tag-peer entry
        Enumeration keys = _propsResBdl.getKeys();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            if (key.startsWith(_PeerPrefix)) {
                addPeer(
                    key.substring(_PeerPrefix.length()),
                    _propsResBdl.getString(key));
            }
        }
        DefaultMutableTreeNode treeRoot = createTreeNode(root);
        DefaultTreeModel model = new DefaultTreeModel(treeRoot);
        setModel(model);
    }

    /**
     * Construct a tree using the default resource bundle.
     * Requires a DOM element.
     * @param root The root element of the tree.
     */
    public XMLTree(Element root) {
        this(_ResBdl, root);
    }

    /**
     * Add a (tag name)-(peer class) association to the map.
     * @param tagName The name of the tag to map.
     * @param peerClassName The name of the peer class to map the tag to.
     */
    private void addPeer(String tagName, 
                         String peerClassName) {
        cat.debug(getString(KEY_MES_CREATE_PEER_MAP) 
                  + tagName 
                  + "->" 
                  + peerClassName);
        try {
            Class peerClass = Class.forName(peerClassName);
            _peerMap.put(tagName, peerClass);
        } catch(ClassNotFoundException cnfe) {
            cat.error(getString(KEY_ERR_CREATE_MAP) + cnfe);
        }
    }

    /**
     * Creates a tree node.
     * @param xmlNode an xml node
     * @return a tree node
     */
    public abstract DefaultMutableTreeNode createTreeNode(Element xmlNode);
}
/*
 * $Log$
 * Revision 1.5  2003/03/18 16:57:51  yan
 * fixed style
 *
 * Revision 1.4  2002/05/02 16:05:51  yan
 * moved
 *
 * Revision 1.3  2002/04/25 21:03:57  mphilip
 * Modified the access level of the node creatoin method.
 *
 * Revision 1.2  2002/04/17 16:53:39  mphilip
 * Modified the constructor.
 *
 * Revision 1.1  2002/04/15 14:50:48  mphilip
 * Initial revision.
 *
 */
