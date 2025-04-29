@echo off
javac *.java
java Main %1 > %~n1.key
echo Translation saved to %~n1.key 