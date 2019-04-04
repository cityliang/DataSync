@echo off
setlocal
chcp 65001

cd ..
del /a /f /s /q %cd%\log\*.*

java -Dfile.encoding=utf-8 -jar bgk.jar --server.port=8085 --DDS_DATE=00000000 --DDS_NAME=YLWS_JBXX

endlocal
pause