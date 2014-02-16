A java implementation of the FRC (FIRST Robotics) Field Management System.

Based on screen shots of the fms-lite program, found in `doc`.

## Build

- ant compile

## Deploy

- ant dist-jar

## Run 

- java -jar build/fms.jar
- java -cp build/fms.jar fms/fms

## TODO

- export radio keys
- import match schedule  
- team management 

- implement dq button -- disable robot
- config tab ui impl
- implement test mode frame
- set fms ip periodically and not only at startup
- setting bypass does not stop updating robot
- clearing bypass does not clear robot link
- log tab ui impl
- fix team number input weirdness requiring leading zero

## Issues
- Score panel grows beyond allocated space and the scroll bar doesn't work 

## Completed
- raise fcui panel location from center to top
- fetch and display fms ip-addr
- decode robot/ds return packet state
- detect missing packets from DS
- reset/clear station status fields when clearing or resetting team number
- Red/Green 'Alliance Ready' graphic
- implement bypass button
- A/T inside graphic robot enabled/disabled
- Red 3 at top shows up as Red 1 at bottom of status page
- compute and display ds avg rtt
- implemented end of auto and start of tele-op cycle, untested
- what does DS send when estopped
- support for E-stop, does setting high bit in command state disable robot?
- score tab ui impl
- audience display proxy config 
- audience display send info
