package com.koalog.util.swing.action;

import com.koalog.util.I18N;

import com.koalog.util.swing.AutoApplication;

import java.awt.event.ActionEvent;

import java.util.Locale;

import javax.swing.JOptionPane;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

/**
 * An action that let the user choose a Look and Feel.The following optional
 * parameters are available:
 * <UL>
 * <LI><CODE>LIST</CODE>: a comma-separated list of available look and feel
 * classes;</LI>
 * </UL>
 *
 * @author Matthieu Philip
 */
public class LnFChooser extends I18NAbstractAction {
//////////////////////////////////////////////////////////////////////////
//
// Class members
//
//////////////////////////////////////////////////////////////////////////
    /** The list parameter. */
    static final String PAR_LIST = "LIST";

//////////////////////////////////////////////////////////////////////////
//
// Instance members
//
//////////////////////////////////////////////////////////////////////////
    /** The list of installed Look and Feels. */
    LnFWrapper[] _lnfs;

    /** The lnf chooser dialog. */

//////////////////////////////////////////////////////////////////////////
//
// Constructor
//
//////////////////////////////////////////////////////////////////////////
    /**
     * Default constructor.
     */
    public LnFChooser() {
        // check for skinlf
        try {
            Class.forName("com.l2fprod.gui.plaf.skin.SkinLookAndFeel");
            UIManager.installLookAndFeel(
                "SkinLF", "com.l2fprod.gui.plaf.skin.SkinLookAndFeel");
        } catch(ClassNotFoundException cnfe) {
            // we could log sth, but that's not crucial
        }
        LookAndFeelInfo[] lnfs = UIManager.getInstalledLookAndFeels();
        _lnfs = new LnFWrapper[lnfs.length];
        for (int i = 0; i < lnfs.length; i++) {
            _lnfs[i] = new LnFWrapper(lnfs[i]);
        }
        putValue(PAR_LIST, "javax.swing.plaf.metal.MetalLookAndFeel,");
        //_chooser = new JFileChooser();
    }

//////////////////////////////////////////////////////////////////////////
//
// Instance methods
//
//////////////////////////////////////////////////////////////////////////
    /** @see com.koalog.util.swing.action.I18NAbstractAction */
    public ActionEvent perform(ActionEvent e) {
        // get the application
        AutoApplication app = (AutoApplication)getValue(
            AutoApplication.KEY_AUTO_APP);
        
        // Get the chosen lnf
        Object chosen = JOptionPane.showInputDialog(
            app.getMainFrame(),
            I18N.getString("com.koalog.util.swing.action.Messages",
                           "LNF_TXT",
                           Locale.getDefault()),
            I18N.getString("com.koalog.util.swing.action.Messages",
                           "LNF_TITLE",
                           Locale.getDefault()),
            JOptionPane.QUESTION_MESSAGE,
            null,
            _lnfs,
            _lnfs[0]
            );
        if (chosen == null) {
            return null;
        }
        // apply the lnf
        try {
            LookAndFeel lnf = ((LnFWrapper)chosen).getLnF();
            UIManager.setLookAndFeel(lnf);
            SwingUtilities.updateComponentTreeUI(app.getMainFrame());
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(app.getMainFrame(), ex.getMessage());
        }
        return null;
    }

/**
 * A wrapper for look and feels.
 * @author Matthieu Philip
 */
    class LnFWrapper {
        /** The wrapped Look and Feel Info. */
        LookAndFeelInfo _lnf;
        
        /**
         * Default constructor.
         * @param lnf The wrapped Look and Feel Info object.
         */
        LnFWrapper(LookAndFeelInfo lnf) {
            _lnf = lnf;
        }

        /**
         * Get the underlying Look and Feel instance.
         * @return A <CODE>LookAndFeel</CODE> instance.
         */
        public LookAndFeel getLnF() {
            try {
                Class lnfClass = Class.forName(_lnf.getClassName());
                LookAndFeel lnf = (LookAndFeel)lnfClass.newInstance();
                return lnf;
            } catch(Exception ignored) {
                // ignored
            }
            return UIManager.getLookAndFeel();
        }
        
        // inherit javadoc
        public String toString() {
            return _lnf.getName();
        }
    }
}

/*
 * $Log$
 * Revision 1.4  2003/03/18 16:57:12  yan
 * fixed style
 *
 * Revision 1.3  2002/09/26 11:12:59  mphilip
 * Non declared class changed to inner class.
 *
 * Revision 1.2  2002/07/23 17:55:40  mphilip
 * Fixed bug: LnF was not I18N.
 *
 * Revision 1.1  2002/05/23 08:43:42  mphilip
 * Initial revision.
 *
 */
