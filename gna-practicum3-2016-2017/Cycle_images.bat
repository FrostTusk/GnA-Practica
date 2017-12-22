@echo off
IF NOT EXIST "temp_data.txt" (
	type NUL > temp_data.txt
	echo ZEE> temp_data.txt
)
set /p PractValue=<temp_data.txt
IF "%PractValue%"=="ZEE" (
	echo COLOR> temp_data.txt
	@echo on
	ant run -Dimg1=./images/zee1.png -Dimg2=./images/zee2.png -Doffsetx=318 -Doffsety=-7
)
IF "%PractValue%"=="COLOR" (
	echo SPIRAAL> temp_data.txt
	@echo on
	ant run -Dimg1=./images/color1.png -Dimg2=./images/color2.png
)
IF "%PractValue%"=="SPIRAAL" (
	echo ZEE> temp_data.txt
	@echo on
	ant run -Dimg1=./images/spiraal1.png -Dimg2=./images/spiraal2.png
)
