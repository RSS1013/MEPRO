@echo off
setlocal

rem --- Usar el JDK 24 ---
set "JAVA_HOME=C:\java\jdk-24.0.2"
set "PATH=%JAVA_HOME%\bin;%PATH%"

echo Usando:
java -version
echo.

java -classpath ".\bin1;.\lib\*" escoba.AplicacionEscoba

echo.
pause
