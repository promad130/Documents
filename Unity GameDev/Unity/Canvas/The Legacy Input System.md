Unity's legacy input system works on polling-mechanism. 
It has two main components:
- [[#The Input class]]
- [[#The Input Manager]]
# The Input class
The `Input` class is part of `UnityEngine` namespace.

## Methods
### `GetAxis(String axisName)`
`GetAxis()` checks for input strength on **each frame** and returns a value representing the input direction/magnitude.
The `axisName` parameter is the name of the virtual axis you configured in the Input Manager, such as "Horizontal", "Vertical", "Mouse X", or any custom axis you've created.

#### Return Values
For **keyboard and joystick inputs**, `GetAxis` returns a value between **-1 and 1**:
	**1** when the positive button is pressed (e.g., right arrow, D key)    
	**-1** when the negative button is pressed (e.g., left arrow, A key)
	**0** when no input is detected
Values smoothly interpolate between these extremes

For **mouse movement** axes ("Mouse X", "Mouse Y", "Mouse ScrollWheel"), the return value is **not limited to -1 to 1**. Instead, it returns the mouse delta (how much the mouse moved) multiplied by the axis sensitivity setting.



# The Input Manager
The Input Manager is a user interface window that you access through **Edit > Project Settings > Input Manager**. 
It's essentially a configuration file with a visual editor where you define virtual input axes (like "Horizontal", "Vertical", "Fire1") and map them to physical hardware inputs (keyboard keys, mouse buttons, gamepad buttons).

# The Mechanism
The core of this system used to read input stream 