package com.koalog.util.swing.action;

import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JFileChooser;
import com.koalog.util.swing.AutoApplication;

/**
 * An action that let the user choose a file.The following optional
 * parameters are available:
 * <UL>
 * <LI><TT>TYPE</TT>: used to specify whether it is an open or a save dialog.
 * The possible values are <TT>open</TT> and <TT>save</TT>. The default value
 * is <TT>open</TT>;</LI>
 * <LI><TT>APPROVE_LABEL</TT>: the approve button label;</LI>
 * <LI><TT>CURRENT_DIR</TT>: set the current directory. When you set the
 * value of this parameter, it is consumed at the following invocation, so
 * that the user can modify the current directory;</LI>
 * </UL>
 *
 * @author Matthieu Philip
 */
public class FileChooser extends I18NAbstractAction {
//////////////////////////////////////////////////////////////////////////
//
// Class members
//
//////////////////////////////////////////////////////////////////////////
    /** The type parameter. */
    static final String PAR_TYPE = "TYPE";
    /** The "open" value for the type parameter. */
    static final String VAL_OPEN = "open";
    /** The "save" value for the type parameter. */
    static final String VAL_SAVE = "save";
    /** The approval button label parameter. */
    static final String PAR_APPROVAL_LABEL = "APPROVAL_LABEL";
    /** The current dir parameter. */
    static final String PAR_CURRENT_DIR = "CURRENT_DIR";

//////////////////////////////////////////////////////////////////////////
//
// Instance members
//
//////////////////////////////////////////////////////////////////////////
    /** The file chooser dialog. */
    JFileChooser _chooser;

//////////////////////////////////////////////////////////////////////////
//
// Constructor
//
//////////////////////////////////////////////////////////////////////////
    /**
     * Default constructor.
     */
    public FileChooser() {
        _translatables.add(PAR_APPROVAL_LABEL);
        putValue(PAR_TYPE, VAL_OPEN);
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
        // if the chooser was not created, create it now.
        synchronized(this) {
            if (_chooser == null) {
                _chooser = new JFileChooser();
            }
        }
        // if the current dir is specified, set it and remove the param
        String curDir = (String)getValue(PAR_CURRENT_DIR);
        if (curDir != null) {
            File curDirFile = new File(curDir);
            if (!curDirFile.isDirectory()) {
                curDirFile = curDirFile.getParentFile();
            }
            if (curDirFile == null) {
                // TODO: log an error message
            } else {
                _chooser.setCurrentDirectory(curDirFile);
            }
            putValue(PAR_CURRENT_DIR, null);
        }
        String approvalLabel = (String)getValue(PAR_APPROVAL_LABEL);
        int res;
        if (approvalLabel == null) {
            if (VAL_OPEN.equalsIgnoreCase((String)getValue(PAR_TYPE))) {
                res = _chooser.showOpenDialog(app.getMainFrame());
            } else {
                res = _chooser.showSaveDialog(app.getMainFrame());
            }
        } else {
            res = _chooser.showDialog(app.getMainFrame(), approvalLabel);
        }
        if (res == JFileChooser.APPROVE_OPTION) {
            return new ActionEvent(
                _chooser.getSelectedFile(),
                ActionEvent.ACTION_PERFORMED,
                (String)getRawValue(NAME)
                );
        }
        return null;
    }

//////////////////////////////////////////////////////////////////////////
//
// Accessors
//
//////////////////////////////////////////////////////////////////////////
    /** 
     * Sets the working/current directory.
     * @param workingDir a string
     */
    public void setWorkingDir(String workingDir) {
        putValue(FileChooser.PAR_CURRENT_DIR, 
                 workingDir);
    }
}
/*
 * $Log$
 * Revision 1.6  2003/03/18 16:57:12  yan
 * fixed style
 *
 * Revision 1.5  2002/11/01 22:16:29  mphilip
 * Delayed the creation of the chooser to make it possible to change the
 * look and feel before it is created.
 *
 * Revision 1.4  2002/10/07 13:51:32  yan
 * added support for -w option
 *
 * Revision 1.3  2002/05/02 16:05:51  yan
 * moved
 *
 * Revision 1.2  2002/04/19 16:12:07  mphilip
 * Fixed bug: setCurrentDirectory(a file that is not a directory) does
 * not default to the parent directory on all jdks.
 *
 * Revision 1.1  2002/04/13 16:52:54  mphilip
 * Initial revision.
 *
 */
