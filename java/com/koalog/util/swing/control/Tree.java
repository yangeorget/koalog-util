package com.koalog.util.swing.control;

import java.awt.Component;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import org.apache.log4j.Category;
import com.koalog.util.I18N;

/**
 * An encapsulation of <CODE>JTree</CODE> that add some functionalty to it.
 * <P><CODE>Tree</CODE> associates  tree cell renderers to objects depending on
 * their class. The map has to be provided in the properties resource
 * bundle of the <CODE>Tree</CODE> instance. In the resource bundle, a map
 * entry has the following format:
 *
 * <P>
 * <CENTER>
 * <CODE>RENDER_&lt;classname&gt;=&lt;renderer classname&gt;</CODE>
 * </CENTER>
 * </P>
 *
 * @author Matthieu Philip
 */
public abstract class Tree extends JTree {
//////////////////////////////////////////////////////////////////////////
//
// Class members
//
//////////////////////////////////////////////////////////////////////////
    /** Name of the i18n bundle. */
    static String _I18NBundle = "com.koalog.util.swing.control.Messages";
    /*
     * Message keys.
     */
    /** Creation of a class-renderer mapping message key. */
    static final String KEY_MES_CREATE_CL_RD_MAP = "MES_CREATE_CL_RD_MAP";
    /** Mapping creation error message key. */
    static final String KEY_ERR_CREATE_MAP = "ERR_CREATE_MAP";
    /**
     * Category used for logging this class events.
     */
    private static Category cat = Category.getInstance(Tree.class);
    /** Prefix used for render map entries in the properties file. */
    static String _RenderPrefix = "RENDER_";

//////////////////////////////////////////////////////////////////////////
//
// Instance members
//
//////////////////////////////////////////////////////////////////////////
    /** 
     * The resource bundle storing the properties that define
     * the behaviour of this tree.
     */
    ResourceBundle _propsResBdl;

    /** The I18N instance attached to this tree. */
    protected I18N _i18n;

    /** The class-renderer map. */
    protected HashMap _rendererMap;

//////////////////////////////////////////////////////////////////////////
//
// Constructors
//
//////////////////////////////////////////////////////////////////////////
    /**
     * Default constructor. Requires the name of the properties resource
     * bundle. The resource bundle is parsed to generate the class-renderer
     * map.
     * @param propsResBdl The name of the resource bundle storing this tree
     * properties.
     */
    public Tree(String propsResBdl) {
        _i18n = I18N.getInstance();
        _propsResBdl = ResourceBundle.getBundle(propsResBdl);
        _rendererMap = new HashMap(5); // low initial capacity for memory
        // process each class-renderer entry
        Enumeration keys = _propsResBdl.getKeys();
        while (keys.hasMoreElements()) {
            String key = (String) keys.nextElement();
            if (key.startsWith(_RenderPrefix)) {
                addRenderer(
                    key.substring(_RenderPrefix.length()),
                    _propsResBdl.getString(key));
            }
        }
        // change the tree cell renderer
        setCellRenderer(new MappedRenderer(
            getCellRenderer(), _rendererMap));
    }

    /**
     * Add a class-renderer association to the map.
     * @param className The name of the class to map.
     * @param rendererClassName The name of the renderer class.
     */
    private void addRenderer(String className, 
                             String rendererClassName) {
        cat.debug(getString(KEY_MES_CREATE_CL_RD_MAP) 
                  + className 
                  + "->" 
                  + rendererClassName);
        try {
            Class key = Class.forName(className);
            Class valClass = Class.forName(rendererClassName);
            TreeCellRenderer val = (TreeCellRenderer)valClass.newInstance();
            _rendererMap.put(key, val);
        } catch(ClassNotFoundException cnfe) {
            cat.error(getString(KEY_ERR_CREATE_MAP), cnfe);
        } catch(InstantiationException ie) {
            cat.error(getString(KEY_ERR_CREATE_MAP), ie);
        } catch(IllegalAccessException iae) {
            cat.error(getString(KEY_ERR_CREATE_MAP), iae);
        } catch(ClassCastException cce) {
            cat.error(getString(KEY_ERR_CREATE_MAP), cce);
        }
    }

    /**
     * Convenience method to get a localized string.
     * @param key The key of the string to localize.
     * @return a string
     */
    protected String getString(String key) {
        return _i18n.getString(_I18NBundle, key);
    }

//////////////////////////////////////////////////////////////////////////
//
// Class methods
//
//////////////////////////////////////////////////////////////////////////

/**
 * The tree cell renderer that maps classes to registered tree cell
 * renderers. Each type an object has to be rendered, the
 * <CODE>MappedRenderer</CODE> checked whether there is a renderer associated
 * to the object class. If that is the case, the found renderer is used to
 * render the object. Otherwise, the <CODE>MappedRenderer</CODE> checks whether
 * the object is itself a <CODE>TreeCellRenderer</CODE>, in which case the
 * object is used to render itself. In any other case, the object is
 * rendered using the <CODE>MappedRenderer</CODE> parent renderer.
 *
 * @author Matthieu Philip
 */
    class MappedRenderer implements TreeCellRenderer {
        /** The parent renderer, 
            used as the default when no mapping is found. */
        TreeCellRenderer _parent;
        /** The class-renderer map. */
        Map _map;
        
        /**
         * Default constructor. It requires a parent renderer and a map.
         * @param parent The parent renderer, used when no mapping is found.
         * @param map The class-renderer map.
         */
        public MappedRenderer(TreeCellRenderer parent, Map map) {
            _parent = parent;
            _map = map;
        }
        
        // inherit javadoc
        public Component getTreeCellRendererComponent(
            JTree tree, Object value, boolean selected, boolean expanded,
            boolean leaf, int row, boolean hasFocus) {
            // in the case of a default mutable tree node, the user object
            // is used
            if (value instanceof DefaultMutableTreeNode) {
                value = ((DefaultMutableTreeNode)value).getUserObject();
            }
            // get the renderer from the map
            TreeCellRenderer renderer = (TreeCellRenderer)_map.get(
                value.getClass());
            // if none was found, use the default one, except if the value is
            // a renderer itself, in which case it would be used.
            if (renderer == null) {
                if (value instanceof TreeCellRenderer) {
                    renderer = (TreeCellRenderer)value;
                } else {
                    renderer = _parent;
                }
            }
            return renderer.getTreeCellRendererComponent(
                tree, value, selected, expanded, leaf, row, hasFocus);
        }
    }
}
/*
 * $Log$
 * Revision 1.5  2003/03/18 16:57:51  yan
 * fixed style
 *
 * Revision 1.4  2002/09/26 11:11:58  mphilip
 * Non declared class changed to inner class.
 *
 * Revision 1.3  2002/09/16 17:21:10  mphilip
 * Fixed bug: bundle name not ported to the new package.
 *
 * Revision 1.2  2002/05/02 16:05:51  yan
 * moved
 *
 * Revision 1.1  2002/04/15 14:43:59  mphilip
 * Initial revision.
 *
 */
