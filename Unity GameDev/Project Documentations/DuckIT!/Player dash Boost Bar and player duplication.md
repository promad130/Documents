## Player Dash Boost Bar
- A UI canvas is created inside the player object, and a boost bar that will roam over the player head.
- Once the player has stopped using the dash for a certain amount of time, the boost bar will start to refill one at a time.
- I.e., create a bar for the remaining time until the next time, and when the time is up, the dash bar increments by a value, and then the timer starts again.
- Once, the bar is out and player still tries to use it, the duplicate of a player is created and the max amt of boosts is decreased, and the timer for it is decreased as well by a certain amount.
- Once the max amount is 0, then player can no longer dash, so remove the dash functionality.

## Player Duplication
- When the boost bar is out, and player tries to access it again, the player duplicate is created in the opposite directional velocity. 
- The duplicate player will be an instance of the original player object, and the movement and all depends if it is active player or not:
	- First, turn the player into a prefab. 
	- Now, in player movement script, have a variable that will check if the given player is active player or not.
	- Change things in player movement script, create functions for movement, jump and dash, and then the key press for a particular movement is met, call the function
	- If the given player is active player, then update the movement part in `update` to work, else follow player ai logic.
	- Ai logic would work with some circles, i.e.., if it detects an enemy near that circle, it will run with a slow speed(`playerSpeed*Some_Function_That_Gives_Out_Constant_With_Min0_Max1_Depending_Upon_Enemy_Position_In_Circle`), and if the enemy is near, it will run away, and if no enemy near, then it will stay idle, like a retard. 
	- Check for enemy using layers, and for now just create sqaures with enemy layer on them to test this
	- If it faces a wall it will try to jump if the height is achievable, else it will try to jump and dash while running away, but they are retard, so player has to save them.
	- They cannot travel in the Z to get away if faced by enemy on both sides.

- Now time to add a player camera, and attach active player logic script to it, i.e., the camera will be given a starting instance to follow, and in the player movement, access the prefab that the camera is following, if that prefab is the given instance, then set `activePlayer = true`,and add a playerShift script, that stores all the instances made of the player prefab and their respective distance from the enemy in the circle that the ai has and the level boundary, and create a player switch key, and if that key is pressed, then set the object that camera will follow to the player closest to an enemy or the level wall.
- If the active player does create a duplicate, then that will be addeed to the list.
- If the active player dies(for now, just create  a bool called `playerDie`. Create a playerdie script that will destroy the player instance when `playerDie == true`.), the camera will then follow the closest player to the active player:
	- Create a script that checks for the closest player instance to the active player, and when player dies, the active player will shift to it.
## Once both enemy are created
- If the current player instance is player duplication, then for:
- Enemy 1:
	- Check if player enters the enemy trigger region, if yes, then run away from enemy until it goes out of enemy's provided valid region. 
	- 