@ECHO off


ECHO Usage: %~nx0 jar "[-d|-f]" name numIteration tolerance

ECHO The %~nx0 script args are:

ECHO JAR : %1
set jar=%1

ECHO DIR or File : %2
set isDir=%2

ECHO DIR or File Name: %3
set name=%3

ECHO MAX Number of Iterations : %4
set numIteration=%4

ECHO Tolerance : %5
set tolerance=%5

IF "%isDir%" == "-d" (
    for %%f in (.\%name%\*.in) do (
        ECHO Processing File: %%f
        java -jar %jar% %%f %numIteration% %tolerance%
    )
) ELSE (
        ECHO Processing File: %3
        java -jar %jar% .\%name% %numIteration% %tolerance%
)
ECHO %~nx0 finihed!
pause
