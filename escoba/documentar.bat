@echo off
echo Generando documentación Javadoc...
javadoc -d doc -encoding UTF-8 -sourcepath src -subpackages escoba
echo Documentación generada en la carpeta /doc
pause
