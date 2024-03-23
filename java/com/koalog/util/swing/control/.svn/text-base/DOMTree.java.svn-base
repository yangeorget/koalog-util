package com.koalog.util.swing.control;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.log4j.Category;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * A tree that represent a XML file as a DOM document. Using the DOM 
 * representation for makes it easy to perform
 * a save operation that does not destruct the unknown elements.
 * However, keep in mind that it is not adapted to very large documents.
 *
 * <P>Available options:
 * <UL>
 * <LI><CODE>IGNORE_UNKNOWN_ELEMENTS</CODE>: by setting this option to
 * <CODE>true</CODE>, the unknown tags are not displayed on the screen. The
 * default value is <CODE>false</CODE>.</LI>
 * </UL>
 * </P>
 *
 * @author Matthieu Philip
 */
public class DOMTree extends XMLTree {
//////////////////////////////////////////////////////////////////////////
//
// Class members
//
//////////////////////////////////////////////////////////////////////////
    /**
     * Category used for logging this class events.
     */
    private static Category cat = Category.getInstance(DOMTree.class);

    /**
     * A table used to detect unsaved trees.
     */
    private static Map _docStates = new WeakHashMap();

    /** The ignore unknown elements key. */
    static final String KEY_UNKNOWNS = "IGNORE_UNKNOWN_ELEMENTS";

    /** 
     * The key used to store the current tree in the application
     * context (not final because of obfuscation).
     */
    public static String KEY_CUR_TREE = "CUR_TREE";

    /** 
     * The key used to store the current element in the application
     * context (not final because of obfuscation).
     */
    public static String KEY_CUR_ELEMENT = "CUR_ELEMENT";

//////////////////////////////////////////////////////////////////////////
//
// Instance members
//
//////////////////////////////////////////////////////////////////////////
    /** A map to get a tree node from an element. */
    HashMap _treeNodesMap;

//////////////////////////////////////////////////////////////////////////
//
// Constructors
//
//////////////////////////////////////////////////////////////////////////
    /**
     * Default constructor. Requires a DOM element.
     * @param resBdl The properties resource bundle of the tree.
     * @param root The root element of the tree to represent.
     */
    public DOMTree(String resBdl, Element root) {
        super(resBdl, root);
    }

    /**
     * Construct a tree with the default resource bundle.
     * Requires a DOM element.
     * @param root The root element of the tree to represent.
     */
    public DOMTree(Element root) {
        super(root);
    }

//////////////////////////////////////////////////////////////////////////
//
// Instance methods
//
//////////////////////////////////////////////////////////////////////////
    /**
     * Construct a tree hierarchy from the specified element.
     * @param xmlNode The root of the XML hierarchy to represent.
     * @return The root of the built tree hierarchy.
     */
    public DefaultMutableTreeNode createTreeNode(Element xmlNode) {
        Object userObject = xmlNode;
        Class userObjectClass = (Class)_peerMap.get(xmlNode.getTagName());
        // get the unknown elements option
        String unknowns = _propsResBdl.getString(KEY_UNKNOWNS);
        boolean unknownsOpt = unknowns != null 
            && unknowns.equalsIgnoreCase(String.valueOf(true));
        // if no peer class is specified, then the user object is the
        // node itself. Otherwise the peer is built.
        if (userObjectClass != null) {
            try {
                // get the constructor - required that takes an object as
                // its argument
                Constructor c = userObjectClass.getConstructor(new Class[] {
                    Object.class
                });
                userObject = c.newInstance(new Object[] {xmlNode});
                cat.debug("Peer instance created for " + xmlNode.getTagName());
            } catch (NoSuchMethodException nsme) {
                cat.error(nsme);
            } catch (InstantiationException ie) {
                cat.error(ie);
            } catch (IllegalAccessException iae) {
                cat.error(iae);
            } catch (InvocationTargetException ite) {
                cat.error(ite.getTargetException());
            }
        } else if (unknownsOpt) {
            return null;
        }
        DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(
            userObject);
        synchronized(this) {
            if (_treeNodesMap == null) {
                _treeNodesMap = new HashMap();
            }
        }
        _treeNodesMap.put(xmlNode, treeNode);
        NodeList children = xmlNode.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i) instanceof Element) {
                DefaultMutableTreeNode childNode = createTreeNode(
                    (Element)children.item(i));
                if (childNode != null) {
                    treeNode.add(childNode);
                }
            }
        }
        return treeNode;
    }

    /**
     * Get a tree node from a DOM element.
     * @param element The DOM element.
     * @return The associated tree node, or null if none was found.
     */
    public DefaultMutableTreeNode getTreeNodeForElement(Element element) {
        return (DefaultMutableTreeNode)_treeNodesMap.get(element);
    }

//////////////////////////////////////////////////////////////////////////
//
// Class methods
//
//////////////////////////////////////////////////////////////////////////
    /**
     * Reset the state of a document to <em>saved</em>.
     *
     * @param doc The document to mark as saved.
     */
    public static synchronized void saved(Document doc) {
        _docStates.put(doc, Boolean.TRUE);
    }

    /**
     * Get the state of a document.
     *
     * @param doc The document to check.
     * @return <code>true</code> if the document has been marked as
     * <em>saved</em>, <code>false</code> otherwise (and in particular if
     * the document has not been registered before).
     */
    public static synchronized boolean isSaved(Document doc) {
        Boolean b = (Boolean)_docStates.get(doc);
        if (b == null) {
            // The document has never been registered as saved: it is safer
            // to assume that it needs to be saved.
            return false;
        }
        return b.booleanValue();
    }

    /**
     * Mark a document as dirty.
     *
     * @param element An element of the document to mark.
     */
    public static synchronized void dirty(Element element) {
        Document doc = element.getOwnerDocument();
        if (doc != null) {
            _docStates.put(doc, Boolean.FALSE);
        }
    }

    /** 
     * Temporary method.
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
        app.getMainFrame().getContentPane().add(tree);
        app.run();
    }

//-------------------------------------------------//
// Logging declarations and implementation - start //
//-------------------------------------------------//

//------------------------------------------------//
// Logging declarations and implementation - stop //
//------------------------------------------------//
}
/*
 * $Log$
 * Revision 1.11  2004/07/07 14:16:33  mat
 * Fixed jdoc bug.
 *
 * Revision 1.10  2004/07/02 20:44:13  mat
 * Added a system that let detect modifications to the DOM.
 *
 * Revision 1.9  2003/03/18 17:35:04  yan
 * obfuscation related fixes
 *
 * Revision 1.8  2003/03/18 16:57:51  yan
 * fixed style
 *
 * Revision 1.7  2002/05/27 21:27:57  mphilip
 * Fixed bug: hash map not initialized.
 *
 * Revision 1.6  2002/05/27 18:12:33  mphilip
 * Added a method to get a tree node from a DOM element.
 *
 * Revision 1.5  2002/05/02 16:05:51  yan
 * moved
 *
 * Revision 1.4  2002/04/25 21:04:39  mphilip
 * Modified the access level of the node creation method.
 *
 * Revision 1.3  2002/04/21 17:12:34  mphilip
 * Added the option to ignore unknown elements.
 *
 * Revision 1.2  2002/04/17 16:51:51  mphilip
 * Changed the constructor.
 *
 * Revision 1.1  2002/04/15 14:53:51  mphilip
 * Initial revision.
 *
 */
