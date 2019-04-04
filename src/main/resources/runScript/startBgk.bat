@echo off
setlocal
chcp 65001

cd ../
del /a /f /s /q %cd%\log\*.*
java -Dfile.encoding=utf-8 -jar bgk.jar --server.port=8082 --DDS_DATE=00000000 --DDS_NAME=XXWS_JBXX

endlocal
pause