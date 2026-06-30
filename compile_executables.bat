@echo off
setlocal enabledelayedexpansion

if not exist "bin" (
  echo Creating bin directory
  mkdir bin
)

call mvn clean install -q -DskipTests
if errorlevel 1 (
  echo FATAL ERROR: Frontend compilation failed
  exit /b 1
)
echo INFO: Frontend compiled successfully

for /f "delims=" %%A in ('call mvn help:evaluate -Dexpression=project.artifactId -q -DforceStdout') do set frontendArtifact=%%A
for /f "delims=" %%A in ('call mvn help:evaluate -Dexpression=project.version -q -DforceStdout') do set frontendVersion=%%A
set frontendExecutable=%frontendArtifact%-%frontendVersion%.jar

echo INFO: Copying frontend executable
copy /Y "target\%frontendExecutable%" "bin\frontend.jar" >nul

cd stack-my-lang
call stack install

if errorlevel 1 (
  echo FATAL ERROR: Backend compilation failed
  cd ..
  exit /b 1
)
echo INFO: Backend compiled successfully
echo INFO: Copying backend executable
copy /Y "%USERPROFILE%\AppData\Roaming\local\bin\my-lang-exe.exe" "..\bin\backend.exe" >nul

cd ..
echo Exiting