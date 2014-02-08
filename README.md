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

- Red/Green 'Alliance Ready' graphic
- implement bypass button
- implement dq button
- decode robot/ds return packet state
- config tab ui impl
- score tab ui impl
- reset/clear station status fields when clearing or resetting team number
- import match schedule  
- export radio keys
- implement test mode frame
- A/T inside graphic robot enabled/disabled
- what does DS send when estopped
- set fms ip continuously and not at startup
- Red 3 at top shows up as Red 1 at bottom of status page
- compute and display ds avg rtt


## Completed
- raise fcui panel location from center to top
- fetch and display fms ip-addr
- detect missing packets from DS
