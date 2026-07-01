@echo off

REM Change directory to the location where this script is saved
cd /d "%~dp0"

REM Check if the jar file exists
if not exist "bin\frontend.jar" (
    echo Error: frontend.jar not found in bin directory. Please verify your installation
    exit /b 1
)

REM Run the Java application and pass all arguments perfectly
java -jar bin\frontend.jar %*