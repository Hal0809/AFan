# AFan by Hal0809. 

A running fan animation. It was used in my java training course. The detailed information and requirements are shown after.

Experiment 5 Animation – Display a running fan

Course:  Java Programming 2022-2023-2  
Name: Hal_G
Date:   2023 / 6 / 2 

1.	Experimental Purpose
1.1.	Learn to develop UI using JavaFX.
1.2.	Master layout panes, setting style for control components, event handling (including ActionEvent, MouseEvent, and KeyEvent), and Timeline animation.
2.	Experimental Task
2.1	Program
  Write a program that displays a running fan. Use the > and || buttons to start and pause fan running. Use the ← and → buttons to left reverse and right reverse fan running. Use the ↑ and ↓ buttons to increase and decrease speed of fan running. Use animation to show the current time no matter fan is running or paused. Also control fan running with MouseEvent and KeyEvent.

2.2	Requirements
1.	Define three classes
Define TimePane class, FanPane class, FanAnimation class to (1) display animation of the current time, (2) display fan running animation, and (3) control fan running using buttons.
2.	ActionEvent on buttons
(1)	When cursor is on the button, change cursor into hand;
(2)	Click > button to start fan running;
(3)	Click || button to pause fan running;
(4)	Click ← button to make fan running turn left;
(5)	Click → button to make fan running turn right;
(6)	Click ↑ button to speed up fan running;
(7)	Click ↓ button to speed down fan running;
3.	MouseEvent on fan
Click mouse on the fan to change its current status. For example, 
(1)	If the fan is running and click mouse on the fan, change it into paused; 
(2)	If the fan is paused and click mouse on the fan, change it into running;
4.	KeyEvent on FanPane
(1)	Press Enter on keyboard to start and pause fan running;
(2)	Press Up on keyboard to speed up fan running;
(3)	Press Down on keyboard to speed up fan running;
5.	Binding properties
(1)	Bind the position of the fan to its outer pane properties. So that no matter how we change the window’s size, the position of fan is always in the center of the window.
