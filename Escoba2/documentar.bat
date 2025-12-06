@echo off
setlocal EnableDelayedExpansion
set "SRC=src"
set "DOC=doc"
set "LIB=lib"

if exist "%DOC%" rmdir /s /q "%DOC%"
mkdir "%DOC%" 2>nul

set "CP="
for %%F in ("%LIB%\*.jar") do (
  if defined CP (set "CP=!CP!;%%~fF") else (set "CP=%%~fF")
)

javadoc -d "%DOC%" -sourcepath "%SRC%" -subpackages escoba -encoding UTF-8 -charset UTF-8 -author -version -classpath "!CP!" --ignore-source-errors
pause
