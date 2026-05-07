# Unity's New Input System — Complete Beginner Guide

> This guide explains Unity's New Input System from scratch.
> It walks through core concepts, your actual code, and answers every common question.
> Read it top to bottom the first time, then use it as a reference.

---

## Table of Contents

- [Part 1: Core Concepts](#part-1-core-concepts)
- [Part 2: The PlayerInput Component](#part-2-the-playerinput-component)
- [Part 3: How Your Code Works](#part-3-how-your-code-works)
- [Part 4: Common Questions Answered](#part-4-common-questions-answered)
- [Part 5: Quick Reference](#part-5-quick-reference)

---

# Part 1: Core Concepts

## What Is The New Input System?

The New Input System is a **Unity Package** — it is not built into the Unity engine core.
You install it from the Package Manager.
It replaces the old `Input.GetKey()` approach.

It has four layers:

```
┌─────────────────────────────────────────────────────────┐
│  LAYER 4 — Visual Editor (UI)                           │
│  The three-column editor you use to set up inputs       │
├─────────────────────────────────────────────────────────┤
│  LAYER 3 — JSON Data (.inputactions file)               │
│  Stores your action maps, actions, and bindings         │
├─────────────────────────────────────────────────────────┤
│  LAYER 2 — C# Package (UnityEngine.InputSystem)         │
│  All the classes you work with in code                  │
├─────────────────────────────────────────────────────────┤
│  LAYER 1 — C++ Native Engine (Hidden)                   │
│  Polls the physical hardware every frame                │
└─────────────────────────────────────────────────────────┘
```

You only ever touch **Layers 2, 3, and 4**.
Layer 1 is invisible and automatic.

---

## The Class Hierarchy

Everything in the Input System follows this containment hierarchy:

```
InputActionAsset          ← ScriptableObject, the .inputactions file
  └── InputActionMap      ← Groups actions by context ("Player", "UI")
        └── InputAction   ← One logical game event ("Jump", "Move", "Link")
              └── InputBinding  ← Links the action to a physical key/button
                    └── InputControl  ← The actual key/button on the device
```

**Plain English:**
- An **Asset** holds everything.
- A **Map** is a group of related actions (like a "mode" of your game).
- An **Action** is something your player can do ("Jump").
- A **Binding** says which physical button triggers that action (`<Keyboard>/space`).
- A **Control** is the runtime representation of that physical button.

---

## The Core Classes Explained

### `InputActionAsset`
**What it is:** A `ScriptableObject` that stores your entire input configuration.
**Where it lives:** As a `.inputactions` file in your Project Assets.
**What it contains:** Multiple `InputActionMap` objects.

```csharp
// Reference it in Inspector or load it
public InputActionAsset controls;

// Enable/disable all maps at once
controls.Enable();
controls.Disable();

// Find a specific map
InputActionMap playerMap = controls.FindActionMap("Player");
```

---

### `InputActionMap`
**What it is:** A named container for a group of `InputAction` objects.
**Why it exists:** Different game states need different inputs.
  - "Player" map: Move, Jump, Attack
  - "UI" map: Navigate, Submit, Cancel
  - "Vehicle" map: Accelerate, Steer, Brake

Only **one map should be active** at a time in most games.

```csharp
InputActionMap playerMap = controls.FindActionMap("Player");
playerMap.Enable();   // activate all actions in this group
playerMap.Disable();  // deactivate all actions in this group
```

---

### `InputAction`
**What it is:** Represents one logical thing a player can do.
**Three types:**

| Type | Use case | Example |
|---|---|---|
| `Button` | Press/release events | Jump, Fire |
| `Value` | Continuous value with change | Move (Vector2), Aim |
| `PassThrough` | Like Value but allows multiple devices at once | Multiplayer |

**Three phases of an action:**

```
started   → The input begins    (e.g., key starts being held)
performed → The interaction completes (e.g., key is pressed fully)
canceled  → The input ends      (e.g., key released)
```

**You subscribe to phases:**
```csharp
jumpAction.performed += OnJump;   // subscribe
jumpAction.performed -= OnJump;   // unsubscribe (important in OnDisable!)
```

---

### `InputBinding`
**What it is:** A struct that stores the **path string** linking an action to a control.

```
Action "Jump"
  ├── Binding: "<Keyboard>/space"      ← keyboard spacebar
  └── Binding: "<Gamepad>/buttonSouth" ← gamepad A button
```

The path `"<Keyboard>/space"` is just a string at design time.
At runtime, Unity resolves it into an actual `InputControl` object.

---

### `InputControl`
**What it is:** The runtime object representing one physical input (one key, one stick axis, one button).

You rarely use `InputControl` directly.
It is what the Input System queries to read hardware state.

Examples:
- `Keyboard.current.spaceKey` → the spacebar's `ButtonControl`
- `Gamepad.current.leftStick` → the left stick's `StickControl`
- `Mouse.current.position` → mouse position `Vector2Control`

---

### `InputDevice`
**What it is:** Represents a physical hardware device.

```csharp
// Access current devices directly
Keyboard keyboard = Keyboard.current;
Mouse mouse = Mouse.current;
Gamepad gamepad = Gamepad.current;

// Check a specific key without InputAction
if (Keyboard.current.spaceKey.wasPressedThisFrame)
    Debug.Log("Space pressed!");

// List all connected devices
foreach (InputDevice device in InputSystem.devices)
    Debug.Log(device.name);
```

`InputDevice` is the parent class. `Keyboard`, `Mouse`, `Gamepad` are subclasses.

---

### `InputSystem` (static class)
**What it is:** The global manager — think of it as the control room.
**It is static** — you call it like `InputSystem.AddDevice(...)`.

Key things you can do:
```csharp
// Get all connected devices
InputSystem.devices;

// React when any device connects or disconnects
InputSystem.onDeviceChange += (device, change) => {
    Debug.Log($"Device {device.name}: {change}");
};

// Add a virtual device (useful for testing)
var keyboard = InputSystem.AddDevice<Keyboard>();

// Remove a device
InputSystem.RemoveDevice(device);
```

In most games, you **never call `InputSystem` directly** — the package manages it for you.

---

## How Path Resolution Works

When you write `"<Keyboard>/space"` as a binding, here is exactly what Unity does at runtime:

```
Step 1: Parse "<Keyboard>"
        → Search InputSystem.devices for a device of type Keyboard
        → Found: Keyboard.current

Step 2: Parse "/space"
        → Query Keyboard.current for a child control named "space"
        → Found: ButtonControl (the spacebar)

Step 3: Each frame, read the ButtonControl's state
        → isPressed? wasPressedThisFrame? → trigger the action
```

This means:
- Bindings are **data** (strings), not hardcoded control references.
- You can **rebind at runtime** by changing the path string — no code change needed.
- If no matching device exists, the binding simply doesn't fire.

---

# Part 2: The PlayerInput Component

## What Is PlayerInput?

`PlayerInput` is a **Unity Component** — you add it to a `GameObject` in the Inspector.

In Unity's object hierarchy:
```
Object                ← Everything in Unity
  └── Component       ← Attached to GameObjects
        └── Behaviour ← Components that can be enabled/disabled
              └── MonoBehaviour     ← Standard Unity script base
                    └── PlayerInput ← THIS — a built-in Unity component
```

It is **not** a GameObject. It is **not** a Manager script you write.
It is a pre-built component from the Input System package.

---

## What Does PlayerInput Do?

`PlayerInput` is the **bridge** between your `.inputactions` asset and your code.

Without it, you must:
1. Load the asset manually
2. Find the right action
3. Subscribe to callbacks
4. Enable/disable everything yourself

With it, you just:
1. Drag the asset into the `Actions` field in the Inspector
2. PlayerInput handles enabling, subscribing, and routing callbacks

---

## PlayerInput Key Properties

| Property | Type | What It Does |
|---|---|---|
| `actions` | `InputActionAsset` | The .inputactions asset assigned in Inspector |
| `currentActionMap` | `InputActionMap` | The currently active action map |
| `notificationBehavior` | `PlayerNotifications` | How callbacks are sent to your code |
| `camera` | `Camera` | Used for split-screen multiplayer |
| `playerIndex` | `int` | Index when using PlayerInputManager for multiplayer |
| `splitScreenIndex` | `int` | Split-screen area index |
| `controlScheme` | `string` | Name of the active control scheme |
| `hasMissingRequiredDevices` | `bool` | True if required device is not connected |

---

## PlayerInput Key Methods

```csharp
// Switch to a different action map by name
playerInput.SwitchCurrentActionMap("UI");

// Activate a specific control scheme
playerInput.SwitchCurrentControlScheme("Gamepad", Gamepad.current);

// Pause/resume all input processing
playerInput.DeactivateInput();
playerInput.ActivateInput();
```

---

## PlayerNotifications — How Callbacks Reach Your Code

This is the `notificationBehavior` property. It controls **how** PlayerInput tells your code that an action fired.

There are four modes:

### Mode 1: `SendMessages` (Default)
PlayerInput calls `SendMessage("OnActionName", value)` on the **same GameObject**.

```
PlayerInput fires "Jump" action
  → Calls SendMessage("OnJump", value)
  → Unity searches every component on THIS GameObject
  → Finds your method: public void OnJump(InputValue value)
  → Calls it
```

**Rule:** Your method must be `public`, on the **same GameObject**, named `On` + ActionName.

```csharp
// This is automatically called — you don't connect it anywhere
public void OnJump(InputValue value)
{
    if (value.isPressed) Jump();
}

public void OnMove(InputValue value)
{
    moveDir = value.Get<Vector2>();
}
```

### Mode 2: `BroadcastMessages`
Same as `SendMessages` but also searches **child GameObjects**.

Use this when your handler script is on a child object, not the same object as PlayerInput.

### Mode 3: `InvokeUnityEvents`
PlayerInput exposes **UnityEvents** in the Inspector.
You drag your script's method into the event slot manually.

```
In Inspector:
PlayerInput component
  └── Events
        └── Player
              └── Jump: [drag script here] → OnLinkPerformed()
```

Your callback signature must be:
```csharp
public void OnLinkPerformed(InputAction.CallbackContext context)
{
    if (context.performed) ActivateLink();
}
```

### Mode 4: `InvokeCSharpEvents`
PlayerInput exposes C# events you subscribe to in code.

```csharp
playerInput.onActionTriggered += OnAnyAction;

void OnAnyAction(InputAction.CallbackContext context)
{
    if (context.action.name == "Jump") Jump();
}
```

---

## Multiple InputActionAssets

Can you have multiple `.inputactions` assets?
**Yes.** But only one `PlayerInput` component can have one asset at a time.

Common patterns:

**Pattern A — One asset, multiple maps (recommended)**
```
PlayerControls.inputactions
  ├── Player map    (Movement, Jump, Attack)
  ├── UI map        (Navigate, Submit, Cancel)
  └── Vehicle map   (Accelerate, Steer, Brake)
```
Switch with: `playerInput.SwitchCurrentActionMap("Vehicle")`

**Pattern B — Multiple assets, one PlayerInput**
You can swap the entire `actions` asset at runtime:
```csharp
playerInput.actions = newAsset;  // replace the whole asset
```

**Pattern C — Multiple PlayerInput components (multiplayer)**
Each player GameObject gets its own `PlayerInput` with its own asset.
Use `PlayerInputManager` to manage this automatically.

---

## Switching Action Maps

```csharp
// Via PlayerInput component
GetComponent<PlayerInput>().SwitchCurrentActionMap("UI");

// Via InputActionAsset directly
controls.FindActionMap("Player").Disable();
controls.FindActionMap("UI").Enable();

// Via generated C# class
controls.Player.Disable();
controls.UI.Enable();
```

**Important rule:** When using `PlayerInput`, call `SwitchCurrentActionMap()` — it handles enabling/disabling cleanly. Don't manually enable/disable maps when PlayerInput is managing them.

---

# Part 3: How Your Code Works

## The Setup

Your project uses two scripts:

```
┌────────────────────────────────────────────┐
│ GameObject: "GameManager" (DontDestroyOnLoad) │
│   ├── InputManager.cs   (your script)      │
│   └── PlayerInput       (Unity component)  │
└────────────────────────────────────────────┘

┌────────────────────────────────────────────┐
│ GameObject: "Player"                        │
│   └── InputEventHandler.cs  (your script)  │
└────────────────────────────────────────────┘
```

`InputManager` sits alongside `PlayerInput` on the same GameObject.
`InputEventHandler` is on the player — it reads clean state from `InputManager`.

---

## InputManager.cs — Line by Line

```csharp
public class InputManager : MonoBehaviour
{
    // Singleton — only one InputManager exists across all scenes
    public static InputManager Instance { get; private set; }

    // These are the "clean" input states other scripts can read
    public Vector2 MoveInput { get; private set; }
    public bool IsJumping { get; private set; }
    public bool IsLinkActive { get; private set; }
```

**Why a singleton?**
Any script anywhere needs to read input.
A singleton means you just call `InputManager.Instance.MoveInput` from anywhere.

---

```csharp
    private void Awake()
    {
        if (Instance != null && Instance != this) { Destroy(gameObject); return; }
        Instance = this;
        DontDestroyOnLoad(gameObject);
        playerInput = GetComponent<PlayerInput>();
    }
```

**What happens here:**
1. First time Awake runs: `Instance` is null → this becomes the singleton.
2. If a second InputManager ever loads: it destroys itself.
3. `DontDestroyOnLoad` keeps this alive across scene changes.
4. `GetComponent<PlayerInput>()` grabs the PlayerInput on this same GameObject.

---

```csharp
    public void OnMove(InputValue value)
    {
        MoveInput = value.Get<Vector2>();
    }

    public void OnJump(InputValue value)
    {
        IsJumping = value.isPressed;
    }

    public void OnLink(InputValue value)
    {
        IsLinkActive = value.isPressed;
    }
```

**What happens here — the key magic:**

1. PlayerInput component fires the "Move" action (player pressed WASD).
2. PlayerInput's `notificationBehavior` is set to `SendMessages`.
3. `SendMessage("OnMove", inputValue)` is called on this GameObject.
4. Unity finds `public void OnMove(InputValue value)` — because the name matches.
5. The method stores the clean `Vector2` in `MoveInput`.
6. Any other script can now read `InputManager.Instance.MoveInput`.

**The naming rule is non-negotiable with SendMessage:**
Action name `"Move"` → method must be `OnMove`
Action name `"Link"` → method must be `OnLink`
Action name `"Jump"` → method must be `OnJump`

---

```csharp
    public void SwitchToMap(string mapName)
    {
        playerInput.SwitchCurrentActionMap(mapName);
        currentMapName = mapName;
    }
```

**What happens:** PlayerInput disables the current map, enables the named map.
After this call, only actions from the new map will fire.

---

## InputEventHandler.cs — Line by Line

```csharp
public class InputEventHandler : MonoBehaviour
{
    private void Update()
    {
        HandleMovement();
        HandleLink();
    }
```

**Why Update() here, not a callback?**
Movement is continuous — the player holds a key and the character keeps moving.
Polling `MoveInput` every frame is correct for this.
Callbacks are better for one-shot events like jump or attack.

---

```csharp
    private void HandleMovement()
    {
        Vector2 moveDir = InputManager.Instance.MoveInput;
        rb.linearVelocity = new Vector2(moveDir.x * moveSpeed, rb.linearVelocity.y);
    }
```

**What happens:**
1. Every frame, read the current `MoveInput` from `InputManager`.
2. `InputManager.MoveInput` was set by `OnMove()` when the action fired.
3. Apply it as velocity.

No direct contact with the Input System at all — clean separation.

---

```csharp
    public void OnLinkPerformed(InputAction.CallbackContext context)
    {
        if (context.performed) ActivateLink();
    }
```

**This method is wired via UnityEvents (InvokeUnityEvents mode).**

In the Inspector, on the PlayerInput component:
```
Events → Player → Link (performed): [drag InputEventHandler here] → OnLinkPerformed
```

This method takes `CallbackContext` — explained in detail in Part 4.

---

## The Full Flow — What Happens When Player Presses Space

```
Frame N: Player presses Spacebar
│
├─ C++ layer detects key change
│
├─ Input Update Phase (before Update()):
│    InputSystem checks "Jump" action bindings
│    Finds: "<Keyboard>/space" → space is pressed
│    Triggers "Jump" action → phase: performed
│
├─ PlayerInput (on GameManager) receives the trigger
│    notificationBehavior = SendMessages
│    Calls: SendMessage("OnJump", inputValue)
│
├─ InputManager.OnJump(value) runs:
│    IsJumping = value.isPressed  → IsJumping = true
│
└─ Your Update() runs:
     Can now read InputManager.Instance.IsJumping == true
```

---

# Part 4: Common Questions Answered

## Q1: How does generating a C# class work? Does it stay in sync?

**Short answer:** No, it does NOT auto-sync. You must regenerate it manually.

**What happens when you click "Generate C# Class":**
1. Unity reads your current `.inputactions` JSON.
2. It generates a `.cs` file with wrapper properties.
3. Example output for a "Jump" action in "Player" map:

```csharp
// This is the GENERATED class — you did not write this
public class PlayerControls : IInputActionCollection2, IDisposable
{
    public InputActionAsset asset { get; }

    // Strongly-typed access to the "Player" map
    public @Player @Player => new @Player(this);

    public struct @Player
    {
        // Strongly-typed access to the "Jump" action
        public InputAction @Jump => m_Wrapper.asset["Player/Jump"];
    }
}
```

**Usage in your code:**
```csharp
private PlayerControls controls;

void Awake()
{
    controls = new PlayerControls();            // loads the JSON
    controls.Player.Jump.performed += OnJump;  // type-safe, IntelliSense works!
}

void OnEnable()  { controls.Enable(); }
void OnDisable() { controls.Disable(); }
```

**Why use it?**
- Auto-complete works — `controls.Player.Jump` instead of `FindAction("Jump")`
- Compile-time error if you mistype a name
- Cleaner code

**The catch — it does NOT auto-update:**
```
You add new "Dash" action via UI editor
↓
The .inputactions JSON is updated
↓
The generated .cs file is NOT updated (it's stale)
↓
controls.Player.Dash → compile error! Property doesn't exist yet
↓
You must click "Generate C# Class" again to regenerate
```

**So is the generated class its own InputActionMap?**
No. It is a **wrapper/facade** over the same underlying asset.
`new PlayerControls()` loads the JSON and creates the same `InputActionMap`/`InputAction` objects internally.
The generated class just gives you typed access to them.

---

## Q2: What is CallbackContext?

`InputAction.CallbackContext` is a **struct** passed to your callback method.
It is a snapshot of everything about the input event at the moment it fired.

```csharp
void OnJump(InputAction.CallbackContext context)
{
    // Which phase fired?
    bool started   = context.started;    // key just began pressing
    bool performed = context.performed;  // key fully pressed (or threshold met)
    bool canceled  = context.canceled;   // key released

    // Which action triggered this?
    InputAction action = context.action;   // the Jump action object
    string actionName  = context.action.name;  // "Jump"

    // Which device/control fired it?
    InputControl control = context.control;    // e.g., Keyboard.space
    InputDevice device   = context.control.device; // the Keyboard device

    // Read the value
    float value = context.ReadValue<float>();    // 0 or 1 for a button
    Vector2 vec = context.ReadValue<Vector2>();  // for Move action
}
```

**Why does it exist?**
A single callback method can handle all three phases.
Without `context`, you wouldn't know if the input started, completed, or stopped.

**Typical usage:**
```csharp
// Button action — act on press only
jumpAction.performed += ctx => Jump();

// Value action — read the value
moveAction.performed += ctx => {
    moveDir = ctx.ReadValue<Vector2>();
};

// Handle all phases in one method
jumpAction.started   += ctx => Debug.Log("jump started");
jumpAction.performed += ctx => Jump();
jumpAction.canceled  += ctx => Debug.Log("jump canceled");
```

---

## Q3: How does Unity know when to call `OnLinkPerformed`? It's never referenced anywhere.

**This depends on which notification mode you use.**

### If using SendMessages:
Unity uses C#'s **reflection** system.

When the "Link" action fires, PlayerInput does this internally:
```csharp
// Inside PlayerInput source code (simplified):
gameObject.SendMessage("OnLink", inputValue, SendMessageOptions.DontRequireReceiver);
```

`SendMessage` scans every component on the GameObject.
It looks for any method named `"OnLink"` using reflection.
If found — calls it. If not found — silently skips (DontRequireReceiver).

Your method is "discovered" automatically at runtime, not at compile time.
This is why you never need to wire it up — Unity finds it by name.

### If using InvokeUnityEvents:
You explicitly drag your method into the Inspector.
```
PlayerInput Inspector
  └── Events
        └── Player
              └── Link (performed): InputEventHandler → OnLinkPerformed
```

PlayerInput stores this reference and calls it directly.
No reflection involved — it's a stored delegate.

**Why is InvokeUnityEvents more reliable?**
- Compile-time safety: if you rename the method, the Inspector reference breaks (you'll see it).
- With SendMessages: if you rename `OnLink` to something else, it silently stops working.

---

## Q4: What does `InputValue` vs `CallbackContext` mean?

These are two different signatures for input callbacks.

| | `InputValue` | `CallbackContext` |
|---|---|---|
| Used with | `SendMessages` mode | `InvokeUnityEvents` or direct subscription |
| Phase info | Only fires at `performed` | Fires at started/performed/canceled |
| Read value | `value.Get<T>()` | `context.ReadValue<T>()` |
| Example | `OnJump(InputValue v)` | `OnJump(CallbackContext ctx)` |

```csharp
// SendMessages signature (InputValue)
public void OnJump(InputValue value)
{
    if (value.isPressed) Jump();  // only fires when performed
}

// InvokeUnityEvents / direct subscription signature (CallbackContext)
public void OnJump(InputAction.CallbackContext context)
{
    if (context.performed) Jump();
}
```

---

## Q5: How to switch between different action maps?

```csharp
// Method 1 — Via PlayerInput component (recommended when using PlayerInput)
GetComponent<PlayerInput>().SwitchCurrentActionMap("UI");

// Method 2 — Via the InputActionAsset directly
playerInput.actions.FindActionMap("Player").Disable();
playerInput.actions.FindActionMap("UI").Enable();

// Method 3 — Via generated C# class
controls.Player.Disable();
controls.UI.Enable();
```

**Practical example — opening a menu:**
```csharp
void OpenMenu()
{
    playerInput.SwitchCurrentActionMap("UI");
    // Now WASD moves menu cursor, not character
    // Jump button submits, not jumps
}

void CloseMenu()
{
    playerInput.SwitchCurrentActionMap("Player");
    // Back to normal player controls
}
```

---

## Q6: How do multiple InputActionAssets work?

**One asset is usually enough** — use multiple maps within one asset.

If you truly need multiple assets:

```csharp
[SerializeField] private InputActionAsset playerAsset;
[SerializeField] private InputActionAsset vehicleAsset;

private PlayerInput playerInput;

void SwitchToVehicle()
{
    playerInput.actions = vehicleAsset;  // swap the entire asset
    // PlayerInput re-subscribes to the new asset automatically
}

void SwitchToPlayer()
{
    playerInput.actions = playerAsset;
}
```

**When would you have two assets?**
- When two team members work on input simultaneously (merge conflicts)
- When modular DLC adds entirely new control schemes
- Almost never in a typical game

---

# Part 5: Quick Reference

## Class Summary

| Class | Type | Purpose |
|---|---|---|
| `InputActionAsset` | ScriptableObject | Holds all maps/actions/bindings |
| `InputActionMap` | Class | Groups related actions by context |
| `InputAction` | Class | One game input event |
| `InputBinding` | Struct | Path string linking action to control |
| `InputControl` | Class | Runtime physical button/axis |
| `InputDevice` | Class | Physical hardware device |
| `InputSystem` | Static class | Global manager and utilities |
| `PlayerInput` | Component | Bridge between asset and your code |
| `CallbackContext` | Struct | Snapshot of input event data |
| `InputValue` | Class | Simplified value (used with SendMessages) |

---

## Notification Behavior Comparison

| Mode | Signature | Auto-wired | Best for |
|---|---|---|---|
| `SendMessages` | `OnAction(InputValue v)` | Yes (by name) | Quick prototyping |
| `BroadcastMessages` | `OnAction(InputValue v)` | Yes (by name) | Child component handlers |
| `InvokeUnityEvents` | `OnAction(CallbackContext ctx)` | Inspector drag | Visual wiring, clarity |
| `InvokeCSharpEvents` | `OnAction(CallbackContext ctx)` | Code subscription | Full code control |

---

## Binding Path Format

```
"<DeviceType>/controlName"

Examples:
"<Keyboard>/space"           spacebar
"<Keyboard>/w"               W key
"<Mouse>/leftButton"         left mouse button
"<Mouse>/delta"              mouse movement (Vector2)
"<Gamepad>/buttonSouth"      A button (Xbox) / X button (PlayStation)
"<Gamepad>/leftStick"        left analog stick (Vector2)
"<Gamepad>/leftTrigger"      left trigger (float 0-1)
"<Touchscreen>/primaryTouch/position"  finger position (Vector2)
```

---

## CallbackContext — What You Can Read

```csharp
void OnAnyAction(InputAction.CallbackContext ctx)
{
    ctx.started           // bool: input just began
    ctx.performed         // bool: input completed
    ctx.canceled          // bool: input ended

    ctx.action            // InputAction: which action fired
    ctx.action.name       // string: "Jump", "Move", etc.
    ctx.control           // InputControl: which button/key fired it
    ctx.control.device    // InputDevice: which device

    ctx.ReadValue<float>()    // read float (button: 0 or 1)
    ctx.ReadValue<Vector2>()  // read Vector2 (stick, WASD composite)
    ctx.ReadValue<bool>()     // read bool
    ctx.time                  // double: timestamp when this fired
}
```

---

## Action Map Switching Cheat Sheet

```csharp
// Recommended — PlayerInput handles cleanup
playerInput.SwitchCurrentActionMap("MapName");

// Low-level — if not using PlayerInput
asset.FindActionMap("Player").Disable();
asset.FindActionMap("UI").Enable();

// Generated class
controls.Player.Disable();
controls.UI.Enable();
```

---

## The Three Ways to Connect Input to Your Code

```
Option 1: PlayerInput + SendMessages
  Pro: Least code, just write OnMove() and it works
  Con: Silent failure if you misspell the method name

Option 2: PlayerInput + InvokeUnityEvents  
  Pro: Visual wiring, clear in Inspector, CallbackContext gives full info
  Con: Requires Inspector setup per action

Option 3: Generated C# Class (no PlayerInput)
  Pro: Full type safety, no magic strings, IntelliSense
  Con: Must regenerate when you change the asset, more boilerplate

Rule of thumb:
  Prototyping / learning     → SendMessages
  Mid-size game              → InvokeUnityEvents
  Large game / team project  → Generated C# class
```

---

## Best Practices

1. **Always unsubscribe in OnDisable.**
   ```csharp
   void OnEnable()  { jumpAction.performed += OnJump; }
   void OnDisable() { jumpAction.performed -= OnJump; }  // prevents memory leaks
   ```

2. **Don't subscribe in Update.** Subscribe once in Awake/OnEnable.

3. **Use SwitchCurrentActionMap instead of manually enabling/disabling** when using PlayerInput.

4. **Don't read input in FixedUpdate.** Input callbacks run before Update, not FixedUpdate.
   Store the value in a field in the callback, apply it in FixedUpdate.

5. **Re-generate the C# class after every change** to your .inputactions file.

6. **One InputActionMap active at a time** keeps logic clean and prevents conflicting inputs.

---

*This guide was written to companion the files: `input_manager.cs` and `input_event_handler.cs`*
*Base notes: `The New Input System.md`*
