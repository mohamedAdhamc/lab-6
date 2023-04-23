#!/bin/bash

# Compile Java source code
javac ExceptionHandlingAssiignment.java

# Normal case
java ExceptionHandlingAssiignment.java input.arxml

# Not valid Autosar file case
java ExceptionHandlingAssiignment.java invalid_file.xml

# Empty file case
touch empty.arxml
java ExceptionHandlingAssiignment.java empty.arxml

# Clean up
rm *.class
rm empty.arxml
