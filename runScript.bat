CHCP 65001
@echo off
echo Podaj ściężkę do katalogu głównego Tomcat
set /p tomcatPath=

set appPath=%cd%
set appPathTarget=%appPath%\target

call mvn clean install

set tomcatPathBin=%tomcatPath%\bin
cd %tomcatPathBin%
call shutdown.bat

echo %appPathTarget%
set tomcatPathWebapps=%tomcatPath%\webapps
cd %appPathTarget%
copy "TicketBookingSystem-0.0.1-SNAPSHOT.war" "%tomcatPathWebapps%"
cd %tomcatPathWebapps%

cd %tomcatPathBin%
call startup.bat

cd %appPath%