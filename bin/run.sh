#
# Script messages
#
MES_JAVA_NOT_FOUND="ERROR: Could not find a java executable. Please set your \
JAVA_HOME variable appopriately, or modify the PATH variable \
such that it contains a java executable."
MES_JAVA_EXEC_NOT_FOUND="ERROR: Could not find a java executable in the \
expected location. It could be that your JAVA_HOME variable is not set to \
a correct value, or your JAVA_HOME directory is missing sub-directories, or \
your JAVA_HOME is not set and your java executable is not under a directory \
that matches the JAVA_HOME standard structure. Please set your \
JAVA_HOME variable appopriately, or modify the PATH variable \
so that the first java executable it contains is under a standard JAVA_HOME \
directory structure."
MES_JAVA_HOME_SET_TO="INFO: JAVA_HOME is set to "
MES_CP_ENTRY_MISSING="WARNING: Could not find classpath entry: "
MES_APP_CP_ENTRY_MISSING="ERROR: Could not find application file: "

#
# Construct the real application classpath
#
APP_HOME=`dirname $0`/..
TMP_APP_CP=$APP_CLASSPATH
APP_CLASSPATH=
IFS=:
for ENTRY in $TMP_APP_CP; do
    if [ -z "$APP_CLASSPATH" ]; then
        APP_CLASSPATH=$APP_HOME/lib/$ENTRY
    else
        APP_CLASSPATH=$APP_CLASSPATH:$APP_HOME/lib/$ENTRY
    fi
done
IFS=
TMP_APP_CP=

#
# Find the java home directory and set the java environment
#

# set the Java home directory
if [ -z "$JAVA_HOME" ]; then
    JAVA=`which java`
    if [ ! -x "$JAVA" ]; then
        echo
        echo $MES_JAVA_NOT_FOUND
        echo
        exit 1
    fi
    JAVA_HOME=`dirname ${JAVA}`/..
fi
echo $MES_JAVA_HOME_SET_TO$JAVA_HOME

# set the Java executable and check its validity
JAVA_EXEC=$JAVA_HOME/bin/java
if [ ! -x "$JAVA_EXEC" ]; then
    echo
    echo $MES_JAVA_EXEC_NOT_FOUND
    echo
    exit 1
fi

# add the Java tools to the classpath
if [ -z "$APP_CLASSPATH" ]; then
    APP_CLASSPATH=$JAVA_HOME/lib/tools.jar
else
    APP_CLASSPATH=$APP_CLASSPATH:$JAVA_HOME/lib/tools.jar
fi

#
# Check the classpath
#
IFS=:
for ENTRY in $CLASSPATH; do
    IFS=
    if [ ! -r "$ENTRY" ]; then
        echo $MES_CP_ENTRY_MISSING$ENTRY
    fi
done
IFS=:
for ENTRY in $APP_CLASSPATH; do
    IFS=
    if [ ! -r "$ENTRY" ]; then
        echo $MES_APP_CP_ENTRY_MISSING$ENTRY
        exit 1
    fi
done
IFS=

#
# Application execution
#
exec $JAVA_EXEC $JAVA_OPTS -classpath "$APP_CLASSPATH:$CLASSPATH" $MAIN_CLASS "$@"
