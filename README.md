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

- implement dq button -- disable robot
- config tab ui impl
- score tab ui impl
- import match schedule  
- export radio keys
- implement test mode frame
- what does DS send when estopped
- set fms ip periodically and not only at startup
- support for E-stop, does setting high bit in command state disable robot?
- setting bypass does not stop updating robot
- clearing bypass does not clear robot link
- team management 

## Issues
- cannot create pane in tabbed panel larger than space available even with scroll bars (Score)

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
