rem Run the image stitching algorithm on 2 specified images found in ./input/
@echo off
set PractArg1=%1.png
set PractArg2=%2.png

IF NOT "%PractArg1%"=="" (
	IF NOT "%PractArg2%"=="" (
		@echo on
		ant run -Dimg1=./input/%PractArg1% -Dimg2=./input/%PractArg2%
	)
	IF "%PractArg2%"=="" (
		echo ERROR: Insufficient Arguments!
	)
)
IF "%PractArg1%"=="" (
	echo ERROR: Insufficient Arguments!
)
