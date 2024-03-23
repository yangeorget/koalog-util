@echo off
REM
REM Script messages
REM
set MES_JAVA_NOT_FOUND=ERROR: Could not find a java executable. Please set your JAVA_HOME variable appopriately, or modify the PATH variable such that it contains a java executable.
set MES_JAVA_EXEC_NOT_FOUND="ERROR: Could not find a java executable in the expected location. It could be that your JAVA_HOME variable is not set to a correct value, or your JAVA_HOME directory is missing sub-directories, or your JAVA_HOME is not set and your java executable is not under a directory that matches the JAVA_HOME standard structure. Please set your JAVA_HOME variable appopriately, or modify the PATH variable so that the first java executable it contains is under a standard JAVA_HOME directory structure."
set MES_JAVA_HOME_SET_TO=INFO: JAVA_HOME is set to 
set MES_CP_ENTRY_MISSING=WARNING: Could not find classpath entry: 
set MES_APP_CP_ENTRY_MISSING=ERROR: Could not find application file: 

REM
REM Construct the real application classpath
REM
set APP_HOME=%~dp0..
set TMP_APP_CP=%APP_CLASSPATH%
set APP_CLASSPATH=
for %%f in (%TMP_APP_CP%) do call :update_app_cp %%f
set TMP_APP_CP=

REM
REM Find the java home directory and set the java environment
REM

REM set the Java home directory
if defined JAVA_HOME goto JHOK
call :which java
if exist %JAVA_HOME% goto JHOK
echo
echo %MES_JAVA_NOT_FOUND%
echo
goto eof
:JHOK
echo %MES_JAVA_HOME_SET_TO%%JAVA_HOME%

REM set the Java executable and check its validity
set JAVA_EXEC="%JAVA_HOME%\bin\java.exe"
if not exist %JAVA_EXEC% echo %MES_JAVA_EXEC_NOT_FOUND%
if not exist %JAVA_EXEC% goto eof

REM add the Java tools to the classpath
set APP_CLASSPATH=%APP_CLASSPATH%;"%JAVA_HOME%\lib\tools.jar"

REM
REM Check the classpath
REM
for %%f in (%CLASSPATH%) do if not exist %%f echo %MES_CP_ENTRY_MISSING%%%f
for %%f in (%APP_CLASSPATH%) do call :check_app_file %%f

REM
REM Application execution
REM
%JAVA_EXEC% %JAVA_OPTS% -classpath %APP_CLASSPATH%;%CLASSPATH% %MAIN_CLASS% %*
goto eof

REM
REM Utility functions
REM

:update_app_cp
if defined APP_CLASSPATH goto app_cp_defined
set APP_CLASSPATH="%APP_HOME%\lib\%1"
goto eof
:app_cp_defined
set APP_CLASSPATH=%APP_CLASSPATH%;"%APP_HOME%\lib\%1"
goto eof

:check_app_file
if exist %1 goto eof
echo %MES_APP_CP_ENTRY_MISSING%%1
goto eof

:which
set JAVA_HOME=%~dp$PATH:1..
goto eof

:eof
