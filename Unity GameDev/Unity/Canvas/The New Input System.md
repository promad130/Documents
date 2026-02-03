
## The Foundation: What Is The New Input System?
The **New Input System** is a Unity **Package** (not part of core Unity) that replaces the old `Input.GetKey()` system. It consists of:​

1. **C++ Native Layer** (Hidden): Low-level device polling in Unity's engine
2. **C# Package Layer** (Visible): `UnityEngine.InputSystem` namespace with all the classes
3. **JSON Configuration** (Data): `.inputactions` files storing your input setup
4. **Custom Editor UI** (Tooling): Visual editor for creating/editing JSON data

## Layer 1: The Hidden C++ Core
Unity's **native C++ engine** continuously polls physical input devices every frame. This happens in Unity's core update loop and detects:
- Keyboard key states (pressed/released)
- Mouse position and button states
- Gamepad analog stick values and button states
- Touch screen inputs

This raw hardware data flows into C# through Unity's internal bindings. You never interact with this directly—the Input System package handles it.

## Layer 2: The C# Package Architecture
### Core Classes in `UnityEngine.InputSystem` Namespace
**`InputDevice` Class**  
Represents physical hardware (keyboard, mouse, gamepad). Each device has **Controls** (buttons, axes, sticks).​

**`InputControl` Class**  
Represents individual inputs on a device, like `<Keyboard>/space` or `<Gamepad>/leftStick`. Each control has a **path** that identifies it uniquely.

**`InputAction` Class**  
The heart of the system—represents a **logical game event** like "Jump" or "Move". You can create actions in code:​
```csharp
var jumpAction = new InputAction(binding: "<Keyboard>/space"); 
jumpAction.AddBinding("<Gamepad>/buttonSouth"); // Add gamepad A button
```

**`InputBinding` Struct**  
Connects an `InputAction` to one or more `InputControl` paths. Each binding stores:
- `path`: The control path (e.g., `"<Keyboard>/space"`)
- `action`: Which action this binding triggers
- `groups`: Which control schemes it belongs to

**`InputActionMap` Class**  
Container for related `InputAction` objects. Represents contexts like "Player", "Vehicle", "UI​

**`InputActionAsset` Class**  
Container for multiple `InputActionMap` objects. This is the ScriptableObject that stores everything as JSON.

## Layer 3: How The JSON File Works
When you create an Input Action Asset via UI, Unity creates a `.inputactions` file that looks like this internally:
```json
{
  "name": "PlayerControls",
  "maps": [
    {
      "name": "Player",
      "actions": [
        {
          "name": "Move",
          "type": "Value",
          "expectedControlType": "Vector2"
        },
        {
          "name": "Jump",
          "type": "Button"
        }
      ],
      "bindings": [
        {
          "path": "<Keyboard>/space",
          "action": "Jump",
          "groups": "Keyboard&Mouse"
        },
        {
          "path": "<Keyboard>/w",
          "action": "Move/up",
          "groups": "Keyboard&Mouse"
        }
      ]
    }
  ],
  "controlSchemes": [
    {
      "name": "Keyboard&Mouse",
      "devices": [
        {"devicePath": "<Keyboard>"},
        {"devicePath": "<Mouse>"}
      ]
    }
  ]
}

```
*This JSON is loaded at runtime and converted into C# objects* (`InputActionMap`, `InputAction`, `InputBinding`).

## Layer 4: How The UI Editor Was Built
The visual editor you see is **custom C# Editor code** that Unity wrote using `UnityEditor` namespace classes. It works like this:

**Custom Inspector for `.inputactions` Files**  
Unity registered a custom editor window that:
1. Reads the JSON from your `.inputactions` file
2. Deserializes it into C# objects
3. Displays these objects as a three-column UI
4. When you edit UI fields, it updates the C# objects
5. Serializes them back to JSON and saves the file

This is similar to how Unity's Animator window is just C# code rendering a visual interface for `.controller` files.

## The Complete Flow: How Bindings Actually Work
### Setup Phase (Design Time)
1. **You create Input Action Asset via UI**
    - Unity creates `.inputactions` JSON file
    - You add Action Maps → Actions → Bindings through the visual editor    
    - Each binding stores a **path string** like `"<Keyboard>/space"`
    
2. **Unity loads the asset**
    - When your scene loads, if you reference the asset, Unity:
        - Parses the JSON
        - Creates `InputActionMap` objects
        - Creates `InputAction` objects inside those maps
        - Creates `InputBinding` structs and associates them with actions

### Runtime Phase (Game Loop)
Here's what happens **every frame**:

**Frame Start: Device Polling (C++ Layer)**
```text
Unity Engine C++ → Polls keyboard hardware → Detects Space key pressed
                → Polls gamepad hardware → Detects button states
                → Stores all device states in memory
```

**Input Update Phase (Before Update())**
```text
1. Input System queries all InputDevice objects for state changes
2. For each device, it gets control values (e.g., Keyboard.space.isPressed = true)
3. Input System iterates through all ENABLED InputAction objects
4. For each action, it checks all its bindings:
   
   Example for "Jump" action:
   - Binding 1: path = "<Keyboard>/space"
   - Binding 2: path = "<Gamepad>/buttonSouth"
   
5. Input System uses path to find matching controls:
   - "<Keyboard>/space" → Resolves to Keyboard.current.spaceKey control
   - Checks if spaceKey.wasPressedThisFrame == true
   
6. If ANY binding's control is active → Trigger the action!
7. Call action.started/performed/canceled callbacks based on interaction phase
```

**Your Update() Runs**
```text
Your code in Update() can now read action values or has already received callbacks
```

## How Path Resolution Works
The binding path `"<Keyboard>/space"` is a **string-based query**. At runtime, the Input System:​
1. Parses `"<Keyboard>"` → Finds the active `Keyboard` device object
2. Parses `"/space"` → Queries the device for a control named "space"
3. Returns the `ButtonControl` object representing the spacebar
4. Reads its current state (`.isPressed`, `.wasPressedThisFrame`, etc.)

This happens through **reflection and control hierarchies**—each `InputDevice` maintains a tree of `InputControl` objects that can be queried by name.

## How It Connects To Your Player

### Method 1: PlayerInput Component (Automatic)
**You add `PlayerInput` component to GameObject**
- Component has a reference to your `InputActionAsset`
- `OnEnable()`: Component calls `asset.Enable()` which enables all action maps
- Component subscribes to all action callbacks internally
- When actions trigger, component uses `SendMessage()` or UnityEvents to call your methods
```csharp
// Unity automatically calls this because PlayerInput uses SendMessage() 
public void OnJump(InputValue value) { }
```

### Method 2: Generated C# Class (Manual)
**You click "Generate C# Class" in asset inspector**
- Unity reads your JSON structure
- Generates a wrapper class with strongly-typed properties
- You instantiate this class in your code
- You manually enable/disable and subscribe to events
```csharp
private PlayerControls controls; // Generated class 
void Awake() 
{     
	controls = new PlayerControls(); // Loads the JSON, creates InputAction objects    
	controls.Player.Jump.performed += OnJump; // Subscribe to Jump action 
} 
void OnEnable() 
{     
	controls.Enable(); // Tells Input System to start checking bindings 
}
```

### Method 3: Direct Reference (Manual Low-Level)
**You hold a direct reference to the InputActionAsset**
```csharp
public InputActionAsset asset; 

void Start() 
{     
	var jumpAction = asset.FindAction("Jump"); // String-based lookup    
	jumpAction.performed += OnJump;    
	jumpAction.Enable(); 
}
```

## Namespace Classes & Methods
## `UnityEngine.InputSystem` Namespace
**Core Classes:**
- [InputSystem](InputSystem%20in%20UnityEngine.InputSystem): Static class with global settings and callbacks
    
- `InputDevice`: Base for all devices (Keyboard, Mouse, Gamepad)
    
- `Keyboard.current`, `Mouse.current`, `Gamepad.current`: Access current devices
    
- [InputAction](InputAction%20in%20UnityEngine.InputSystem): Represents a game action
    
- `InputActionMap`: Container for actions
    
- `InputActionAsset`: ScriptableObject holding everything
    
- `InputBinding`: Struct connecting actions to controls
    
- `InputActionReference`: ScriptableObject reference to a single action
    

**Key Methods:**

On `InputAction`:

- `Enable()`: Start listening for input
    
- `Disable()`: Stop listening
    
- `ReadValue<T>()`: Get current value (e.g., `ReadValue<Vector2>()`)
    
- `triggered`: Boolean property, true if action triggered this frame
    
- Callbacks: `started`, `performed`, `canceled` events
    

On `InputActionMap`:

- `Enable()`: Enable all actions in map
    
- `Disable()`: Disable all actions
    
- `FindAction(string)`: Get action by name
    

On `InputActionAsset`:

- `Enable()`: Enable all maps
    
- `Disable()`: Disable all maps
    
- `FindActionMap(string)`: Get map by name
    

## The Complete Timeline

**Design Time:**

1. Install Input System package → C# code added to project
    
2. Create Input Action Asset → `.inputactions` JSON file created
    
3. Edit in visual editor → UI (built with C#) modifies JSON
    
4. Optionally generate C# class → Code generation reads JSON, outputs `.cs` file
    

**Runtime:**

1. Scene loads → Unity deserializes JSON into C# objects (`InputActionAsset` → `InputActionMap` → `InputAction` → `InputBinding`)
    
2. Your script runs → Either PlayerInput component or your code enables actions
    
3. Every frame (Input Update phase):
    
    - C++ polls devices → Updates `InputDevice` states
        
    - Input System resolves binding paths → Finds matching `InputControl` objects
        
    - Checks control states → Triggers actions if bindings match
        
    - Fires C# events → Your callbacks execute
        
4. Your Update() runs → Read values or respond to callbacks
    

## Why This Architecture?

**Separation of Concerns:**

- JSON defines **what** inputs exist (data)
    
- C# classes handle **how** inputs are processed (logic)
    
- UI makes editing JSON easy (tooling)
    
- Generated classes provide type safety (optional convenience)
    

The binding paths stored as strings in JSON allow **runtime rebinding**—you can change `"<Keyboard>/space"` to `"<Keyboard>/e"` without recompiling code. The Input System just resolves the new path to a different control!youtube​

This hybrid approach gives you flexibility: quick prototyping with UI, full control with scripting, and runtime customization through path manipulation.