@echo off
javac ExceptionHandlingAssiignment.java

rem Normal Case
java Main input.arxml

rem Not valid Autosar file case
java Main invalid_file.xml

rem Empty file case
echo "" > empty.arxml
java Main empty.arxml

rem Clean up
del *.class
del invalid_file.xml
