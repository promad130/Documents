# Core Mechanics
## Normal Movement
- Player will move left and right when the assigned keys are pressed and are kept pressing.
- The movement is handled by setting player velocity to the speed given in the program 
- Player speed and movement keys are to be made public for enemy script to be able to access it.

## Player Jump
- When the player presses the jump key, i.e., when the key is registered and if the player `canJump` variable is true(a variable that checks if the player is on the ground), then we add a force in the Y-direction using `Vector2`
- But a frame exists for a given amount of time, and player might get the jump key command in one frame, and the execution i.e., the player may leave the ground on another frame, so the code might register the jump command several times and allows player to jump a several times.
- To fix that, a child gameObject with a collider with trigger on is created, and then placed such that only a small portion of it overlaps with the ground, and then we use `onTrigger` to check if the layer of the object in trigger is ground, if yes, then we can jump. This eliminates the register of multiple jumps as the time duration during which the player remains on the ground  
- When the player is jumping, and is not crouched, then I want to tweak gravity(i.e., the gravity scale) when it jumps and fall differently and when it is crouched and do that, then I want to tweak the gravity scale just like before.

## Player Crouch
- When player is on the ground, we want player to "crouch" when the "crouch key"(public just like other keys for enemy to mess with) is pressed
- When the crouch key is pressed, the crouch collider will get active, and idle collider is turned off.
- Also when crouched, check if there is something above us, i.e., another child will be created, and then we create a overlap detection area using `Physics2D`'s static method called `OverlapCircle()` 

## Player Dash
When player is airborne, if we detect a double space, then we want player to 'dash' in the direction where the mouse is facing.
- There would be a threshold limit, i.e., the interval within which both spaces should be pressed
- If the time at which the second space is pressed minus the time at which the first space was pressed is less than the threshold, then we set the `canDash` variable true, and set the time at which last space was pressed as `-1`
- If canDash is true, then I want to tweak gravity, and turn it off, so in the gravity function that we used before to tweak gravity when player jumps, we would add a parent `if` conditional, i.e., when `canDash == false`, then tweak gravity like mentioned, else set gravity scale to 0. 
- Now when the player is dashing, I want it to continue for a certain amount of time, i.e., set a variable for it.
- During that time, we would add velocity in the direction of the mouse that was recorded when the player clicked the dash button, which brings up the point of recording the position of the mouse when the dash condition of double space is met. So create a function that records the mouse position, and execute it when the dash condition is met.

## [[Player dash Boost Bar and player duplication]]
When player dashes, boostbar decreases, and if the boostbar is empty and then the player dashes, then a player duplicate is created with the velocity is the opposite direction as that of player.

## [[Enemy logic and movement, along with player kill mechanism]]
There are a total of 2 enemy type, one being the one that will have a fixed region of existanace, and other when locating player follows it around to hunt like a madman.
Both of them have different purpose.

## Player Weapons

## Enemy Weapons

# Core Loop
What player will go through roughly again and again throughout the game
Include a canvas view in this

# Player Abilities

Player powerup and consequence of using them