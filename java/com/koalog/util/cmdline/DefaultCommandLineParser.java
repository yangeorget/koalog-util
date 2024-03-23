package com.koalog.util.cmdline;

import java.util.Locale;
import java.util.Comparator;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import com.koalog.util.I18N;

/**
 * Default command line parser.
 * 
 * <P>Understands the options:</P> 
 * <UL>
 * <LI><CODE>-l (--log4j) FILE</CODE> 
 * where <CODE>FILE</CODE> is a log4j xml or properties file,</LI>
 * <LI><CODE>-v (--version)</CODE>, 
 * <LI><CODE>-h (--help)</CODE>.</LI>
 * </UL>
 *
 * @author Yan Georget
 */
public class DefaultCommandLineParser extends CommandLineParser {
    //------------------------------------------------------------------------
    // PROPERTIES
    //------------------------------------------------------------------------ 
    private String name;
    private String header;
    private String footer;
    private String i18n;
    /** The list of default options. */
    protected List defaultOptions;
    private List sortedOptions;

    //------------------------------------------------------------------------
    // STATIC PROPERTIES
    //------------------------------------------------------------------------ 
    private static final String I18N_RESOURCE = 
        "com.koalog.util.cmdline.Messages";
    private static final int USAGE_MAX_START = 30;

    //------------------------------------------------------------------------
    // CONSTRUCTORS
    //------------------------------------------------------------------------ 
    /** 
     * Auxilliary constructor. 
     * @param name the name of the script
     * @param i18n the name of the i18n resource
     * @param options an array of options
     */
    public DefaultCommandLineParser(String name,
                                    String i18n,
                                    CommandLineOption[] options) {
        this(name, i18n);
        addOptions(options);
    }

    /** 
     * Main constructor. 
     * @param name the name of the script
     * @param i18n the name of the i18n resource
     */
    public DefaultCommandLineParser(String name, String i18n) {
        super();
        this.name = name;
        this.i18n = i18n;
        defaultOptions = new ArrayList();
        defaultOptions.add(new CommandLineOption("SYNTAX_LOG4J",
                                                 "USAGE_LOG4J", 
                                                 'l', 
                                                 "log4j", 
                                                 true));
        defaultOptions.add(new CommandLineOption("SYNTAX_HELP",
                                                 "USAGE_HELP", 
                                                 'h', 
                                                 "help", 
                                                 false));
        defaultOptions.add(new CommandLineOption("SYNTAX_VERSION",
                                                 "USAGE_VERSION", 
                                                 'v', 
                                                 "version", 
                                                 false));
        addOptions(defaultOptions);
    }

    //------------------------------------------------------------------------
    // ACCESSORS
    //------------------------------------------------------------------------ 
    /**
     * Sets the header string.
     * @param header a string
     */
    public void setHeader(String header) {
        this.header = header;
    }

    /**
     * Sets the footer string.
     * @param footer a string
     */
    public void setFooter(String footer) {
        this.footer = footer;
    }

    //------------------------------------------------------------------------
    // METHODS
    //------------------------------------------------------------------------ 
    /**
     * Returns the usage.
     * @return a string
     */
    public String getUsage() {
        sortedOptions = new ArrayList(shortNames.values());
        Collections.sort(sortedOptions, new Comparator() {
                public int compare(Object o1, Object o2) {
                    return (((CommandLineOption) o1).getKey()
                            .compareTo(((CommandLineOption) o2).getKey()));
                }
            });
        StringBuffer buffer = new StringBuffer();
        // HEADER
        if (header != null) {
            buffer.append(header);
            buffer.append("\n");
        }
        // USAGE
        buffer.append(I18N.getString(I18N_RESOURCE, 
                                     "USAGE", 
                                     Locale.getDefault()));
        buffer.append(": ");
        buffer.append(name);
        buffer.append(" [");
        buffer.append(I18N.getString(I18N_RESOURCE, 
                                     "OPTIONS", 
                                     Locale.getDefault()));
        buffer.append("] ");
        buffer.append(I18N.getString(I18N_RESOURCE, 
                                     "ARGUMENTS", 
                                     Locale.getDefault()));
        buffer.append(" \n\n");
        // OPTIONS
        buffer.append(I18N.getString(I18N_RESOURCE, 
                                     "OPTIONS", 
                                     Locale.getDefault()));
        buffer.append(":\n");
        Iterator i = sortedOptions.iterator();
        while (i.hasNext()) {
            CommandLineOption option = (CommandLineOption) i.next();
            String usage = "USAGE";
            String syntax = "SYNTAX";
            if (option.getSyntaxKey() != null 
                && option.getSyntaxKey() != null) {
                if (defaultOptions.contains(option)) {
                    syntax = I18N.getString(I18N_RESOURCE, 
                                            option.getSyntaxKey(), 
                                            Locale.getDefault());
                    usage = I18N.getString(I18N_RESOURCE, 
                                           option.getUsageKey(), 
                                           Locale.getDefault());
                } else {
                    syntax = I18N.getString(i18n, 
                                            option.getSyntaxKey(), 
                                            Locale.getDefault());
                    usage = I18N.getString(i18n, 
                                           option.getUsageKey(), 
                                           Locale.getDefault());
                }
            }
            buffer.append(syntax);
            int c = syntax.length();
            if (c > USAGE_MAX_START) {
                buffer.append("\n");
                for (int j=0; j<USAGE_MAX_START; j++) {
                    buffer.append(" ");   
                }
            } else {
                for (int j=0; j<USAGE_MAX_START-c; j++) {
                    buffer.append(" ");
                }
            }  
            buffer.append(usage);
            buffer.append("\n");
        }
        buffer.append(I18N.getString(I18N_RESOURCE, 
                                     "USAGE_OPTIONS", 
                                     Locale.getDefault()));
        buffer.append("\n\n");
        // ARGUMENTS
        buffer.append(I18N.getString(I18N_RESOURCE, 
                                     "ARGUMENTS", 
                                     Locale.getDefault()));
        buffer.append(":\n");
        buffer.append(I18N.getString(I18N_RESOURCE, 
                                     "USAGE_ARGUMENTS", 
                                     Locale.getDefault()));
        // FOOTER
        if (footer != null) {
            buffer.append(footer);
            buffer.append("\n");
        }
        return buffer.toString();
    }
}
/*
 * $Log$
 * Revision 1.10  2003/03/18 16:54:21  yan
 * fixed style
 *
 * Revision 1.9  2002/10/21 14:22:14  yan
 * removed getInfos method
 *
 * Revision 1.8  2002/10/21 11:01:08  yan
 * made getInfos method static
 *
 * Revision 1.7  2002/10/14 17:02:18  yan
 * added version option
 *
 * Revision 1.6  2002/10/07 13:52:12  yan
 * added support for -w option
 *
 * Revision 1.5  2002/10/03 09:51:53  yan
 * options are now sorted
 *
 * Revision 1.4  2002/10/02 16:11:09  yan
 * added usage
 *
 * Revision 1.3  2002/06/26 14:47:14  yan
 * fixed bug
 *
 * Revision 1.2  2002/06/24 08:56:54  yan
 * log4j not used anymore
 *
 * Revision 1.1  2002/06/19 15:42:19  yan
 * initial revision
 *
 */

