> This guide shows you how to work directly with InputActionAssets and InputActions without using the PlayerInput component. Everything PlayerInput does for you, you'll do manually — giving you full control.

---

## Table of Contents

- [[#Why Skip PlayerInput?]]
- [[#The Manual Approach Overview]]
- [[#Complete Example Code]]
- [[#Breaking Down The Code]]
- [[#Comparison With PlayerInput]]
- [[#Advanced Patterns]]
- [[#Best Practices]]

---

# Why Skip PlayerInput?

## What PlayerInput Does For You

PlayerInput is a convenience wrapper that:

1. Automatically loads your InputActionAsset
2. Enables/disables action maps for you
3. Routes callbacks to your methods automatically
4. Handles device management in multiplayer
5. Provides visual Inspector configuration

## Why You Might NOT Want It

**Reason 1 — More Control** You want precise control over when actions are enabled/disabled, or need complex state management that PlayerInput's automatic behavior interferes with.

**Reason 2 — Performance** PlayerInput uses SendMessage (reflection) by default, which is slower than direct subscriptions. For performance-critical games with many inputs, direct subscription is faster.

**Reason 3 — Architecture** Your codebase uses a custom input manager architecture, event bus, or command pattern that PlayerInput doesn't fit into.

**Reason 4 — Multiplayer** You're building custom multiplayer input handling where PlayerInput's assumptions don't match your needs.

**Reason 5 — Learning** You want to understand the Input System deeply, not rely on magic.

---

# The Manual Approach Overview

Without PlayerInput, here's what you manage yourself:

```
┌─────────────────────────────────────────────────────────┐
│  YOUR RESPONSIBILITY (what PlayerInput normally does)    │
├─────────────────────────────────────────────────────────┤
│  1. Load the InputActionAsset                           │
│  2. Find specific InputActionMaps                        │
│  3. Find specific InputActions                           │
│  4. Subscribe to action callbacks (+= operator)          │
│  5. Enable actions/maps at the right time                │
│  6. Disable actions/maps at the right time               │
│  7. Unsubscribe from callbacks (-= operator)             │
│  8. Handle object lifecycle (Awake/Enable/Disable)       │
└─────────────────────────────────────────────────────────┘
```

**The benefit:** You see exactly what's happening. No magic.

**The cost:** More boilerplate code to write and maintain.

---

# Complete Example Code

## Scenario

We'll recreate the same functionality as the original `InputManager.cs` and `InputEventHandler.cs`, but without PlayerInput.

We have:

- **Move** action (Vector2, WASD keys)
- **Jump** action (Button, Spacebar)
- **Link** action (Button, F key)

---

## InputManager.cs (Without PlayerInput)

```csharp
using UnityEngine;
using UnityEngine.InputSystem;

public class InputManager : MonoBehaviour
{
    public static InputManager Instance { get; private set; }

    [Header("Input Action Asset")]
    [SerializeField] private InputActionAsset inputActions;

    // References to action maps and actions
    private InputActionMap playerActionMap;
    private InputAction moveAction;
    private InputAction jumpAction;
    private InputAction linkAction;

    // Public properties for other scripts to read
    public Vector2 MoveInput { get; private set; }
    public bool IsJumping { get; private set; }
    public bool IsLinkActive { get; private set; }

    // Track current action map name
    private string currentMapName = "Player";

    private void Awake()
    {
        // Singleton setup
        if (Instance != null && Instance != this)
        {
            Destroy(gameObject);
            return;
        }
        Instance = this;
        DontDestroyOnLoad(gameObject);

        // Initialize the input system manually
        InitializeInputActions();
    }

    private void InitializeInputActions()
    {
        // Step 1: Find the action map by name
        playerActionMap = inputActions.FindActionMap("Player");

        if (playerActionMap == null)
        {
            Debug.LogError("Player action map not found in InputActionAsset!");
            return;
        }

        // Step 2: Find individual actions within the map
        moveAction = playerActionMap.FindAction("Move");
        jumpAction = playerActionMap.FindAction("Jump");
        linkAction = playerActionMap.FindAction("Link");

        // Step 3: Validate that actions exist
        if (moveAction == null) Debug.LogError("Move action not found!");
        if (jumpAction == null) Debug.LogError("Jump action not found!");
        if (linkAction == null) Debug.LogError("Link action not found!");

        // Step 4: Subscribe to action callbacks
        // Using lambda expressions for cleaner inline handling
        moveAction.performed += OnMovePerformed;
        moveAction.canceled += OnMoveCanceled;

        jumpAction.started += OnJumpStarted;
        jumpAction.canceled += OnJumpCanceled;

        linkAction.started += OnLinkStarted;
        linkAction.canceled += OnLinkCanceled;
    }

    private void OnEnable()
    {
        // Enable the action map when this script becomes active
        playerActionMap?.Enable();
    }

    private void OnDisable()
    {
        // Disable the action map when this script is disabled
        playerActionMap?.Disable();
    }

    private void OnDestroy()
    {
        // CRITICAL: Unsubscribe from all callbacks to prevent memory leaks
        if (moveAction != null)
        {
            moveAction.performed -= OnMovePerformed;
            moveAction.canceled -= OnMoveCanceled;
        }

        if (jumpAction != null)
        {
            jumpAction.started -= OnJumpStarted;
            jumpAction.canceled -= OnJumpCanceled;
        }

        if (linkAction != null)
        {
            linkAction.started -= OnLinkStarted;
            linkAction.canceled -= OnLinkCanceled;
        }
    }

    // ========== MOVE ACTION CALLBACKS ==========
    private void OnMovePerformed(InputAction.CallbackContext context)
    {
        MoveInput = context.ReadValue<Vector2>();
    }

    private void OnMoveCanceled(InputAction.CallbackContext context)
    {
        MoveInput = Vector2.zero;
    }

    // ========== JUMP ACTION CALLBACKS ==========
    private void OnJumpStarted(InputAction.CallbackContext context)
    {
        IsJumping = true;
    }

    private void OnJumpCanceled(InputAction.CallbackContext context)
    {
        IsJumping = false;
    }

    // ========== LINK ACTION CALLBACKS ==========
    private void OnLinkStarted(InputAction.CallbackContext context)
    {
        IsLinkActive = true;
    }

    private void OnLinkCanceled(InputAction.CallbackContext context)
    {
        IsLinkActive = false;
    }

    // ========== PUBLIC API ==========
    public void SwitchToMap(string mapName)
    {
        // Disable current map
        inputActions.FindActionMap(currentMapName)?.Disable();

        // Enable new map
        var newMap = inputActions.FindActionMap(mapName);
        if (newMap != null)
        {
            newMap.Enable();
            currentMapName = mapName;
            Debug.Log($"Switched to action map: {mapName}");
        }
        else
        {
            Debug.LogError($"Action map '{mapName}' not found!");
        }
    }

    public void EnableActions()
    {
        playerActionMap?.Enable();
    }

    public void DisableActions()
    {
        playerActionMap?.Disable();
    }
}
```

---

## InputEventHandler.cs (No changes needed)

```csharp
using UnityEngine;

public class InputEventHandler : MonoBehaviour
{
    [SerializeField] private float moveSpeed = 5f;
    private Rigidbody2D rb;

    private void Awake()
    {
        rb = GetComponent<Rigidbody2D>();
    }

    private void Update()
    {
        HandleMovement();
        HandleJump();
        HandleLink();
    }

    private void HandleMovement()
    {
        // Read from InputManager singleton
        Vector2 moveDir = InputManager.Instance.MoveInput;
        rb.linearVelocity = new Vector2(moveDir.x * moveSpeed, rb.linearVelocity.y);
    }

    private void HandleJump()
    {
        if (InputManager.Instance.IsJumping)
        {
            // Jump logic here
            Debug.Log("Jumping!");
        }
    }

    private void HandleLink()
    {
        if (InputManager.Instance.IsLinkActive)
        {
            // Link ability logic here
            Debug.Log("Link ability activated!");
        }
    }
}
```

---

## Alternative: Using the Generated C# Class

Unity can generate a C# wrapper class for your InputActionAsset. This provides type-safe access without string lookups.

### Setup

1. Select your `.inputactions` asset in the Project window
2. In Inspector, check "Generate C# Class"
3. Click "Apply"
4. Unity generates `PlayerControls.cs` (or whatever you named it)

### Code With Generated Class

```csharp
using UnityEngine;

public class InputManagerWithGeneratedClass : MonoBehaviour
{
    public static InputManagerWithGeneratedClass Instance { get; private set; }

    // The generated class (instead of InputActionAsset)
    private PlayerControls controls;

    // Public properties
    public Vector2 MoveInput { get; private set; }
    public bool IsJumping { get; private set; }
    public bool IsLinkActive { get; private set; }

    private void Awake()
    {
        if (Instance != null && Instance != this)
        {
            Destroy(gameObject);
            return;
        }
        Instance = this;
        DontDestroyOnLoad(gameObject);

        // Instantiate the generated class
        // This loads the InputActionAsset internally
        controls = new PlayerControls();

        // Subscribe to actions using type-safe properties
        controls.Player.Move.performed += ctx => MoveInput = ctx.ReadValue<Vector2>();
        controls.Player.Move.canceled += ctx => MoveInput = Vector2.zero;

        controls.Player.Jump.started += ctx => IsJumping = true;
        controls.Player.Jump.canceled += ctx => IsJumping = false;

        controls.Player.Link.started += ctx => IsLinkActive = true;
        controls.Player.Link.canceled += ctx => IsLinkActive = false;
    }

    private void OnEnable()
    {
        // Enable all actions in the Player map
        controls.Player.Enable();
    }

    private void OnDisable()
    {
        // Disable all actions
        controls.Player.Disable();
    }

    private void OnDestroy()
    {
        // Dispose of the controls (unsubscribes everything)
        controls?.Dispose();
    }

    public void SwitchToMap(string mapName)
    {
        // With generated class, you have direct access to maps
        if (mapName == "Player")
        {
            controls.UI.Disable();
            controls.Player.Enable();
        }
        else if (mapName == "UI")
        {
            controls.Player.Disable();
            controls.UI.Enable();
        }
    }
}
```

**Advantages of Generated Class:**

- **Type safety:** `controls.Player.Jump` instead of `FindAction("Jump")`
- **IntelliSense:** Auto-completion works
- **Compile-time errors:** Misspellings are caught by compiler
- **Cleaner code:** No string literals everywhere
- **IDisposable:** `Dispose()` unsubscribes all callbacks automatically

**Disadvantage:**

- **Manual regeneration:** Every time you change the `.inputactions` file, you must click "Apply" to regenerate the C# class

---

# Breaking Down The Code

## Step-by-Step: What's Happening

### 1. Loading the Asset

```csharp
[SerializeField] private InputActionAsset inputActions;
```

You drag your `.inputactions` file into this field in the Inspector. This is the same field PlayerInput has, but now YOU control when and how it's used.

---

### 2. Finding Action Maps

```csharp
playerActionMap = inputActions.FindActionMap("Player");
```

**What `FindActionMap()` does internally:**

```csharp
// Simplified Unity source
public InputActionMap FindActionMap(string name)
{
    foreach (var map in actionMaps)
    {
        if (map.name == name)
            return map;
    }
    return null;
}
```

It's a simple string search through the asset's action maps. If misspelled → returns null → your game breaks silently.

**PlayerInput does this for you** based on the "Default Map" field in Inspector.

---

### 3. Finding Actions

```csharp
moveAction = playerActionMap.FindAction("Move");
```

**What `FindAction()` does internally:**

```csharp
// Simplified Unity source
public InputAction FindAction(string name)
{
    foreach (var action in actions)
    {
        if (action.name == name)
            return action;
    }
    return null;
}
```

Again, it's a string search. **PlayerInput does this automatically** when it routes callbacks via SendMessage.

---

### 4. Subscribing to Callbacks

```csharp
moveAction.performed += OnMovePerformed;
```

This is C#'s event subscription syntax. `+=` adds your method to the action's callback list.

**Under the hood:**

```csharp
// Inside InputAction source (simplified)
public event Action<CallbackContext> performed;

// When you write +=
performed += OnMovePerformed;

// Unity adds your method to an internal delegate list
// When the action fires, Unity calls: performed?.Invoke(context)
```

**PlayerInput with SendMessages** does NOT use `+=` — it uses reflection to find methods by name.

---

### 5. Enabling the Action Map

```csharp
playerActionMap.Enable();
```

**What this does:**

1. Marks the map as "active"
2. Tells the Input System: "Start checking bindings for actions in this map"
3. Each frame, Unity's C++ layer polls hardware and fires callbacks if bindings match

**Without calling Enable(), your actions never fire.**

PlayerInput calls `Enable()` automatically in its `OnEnable()`.

---

### 6. Disabling the Action Map

```csharp
playerActionMap.Disable();
```

**What this does:**

1. Marks the map as "inactive"
2. Input System stops checking bindings for this map
3. Callbacks won't fire even if keys are pressed

**You must disable in `OnDisable()`** to stop callbacks when the GameObject is inactive.

PlayerInput does this automatically.

---

### 7. Unsubscribing from Callbacks

```csharp
moveAction.performed -= OnMovePerformed;
```

**Why this is critical:** If you subscribe with `+=` but never unsubscribe with `-=`, the callback reference persists even after your GameObject is destroyed.

This causes:

- **Memory leaks:** The object can't be garbage collected
- **Null reference exceptions:** Callback tries to access destroyed objects
- **Duplicate callbacks:** If you reload a scene, you stack up multiple subscriptions

**PlayerInput manages this for you** when it's destroyed.

---

## The Lifecycle Flow

```
AWAKE
  ├─ Load InputActionAsset
  ├─ Find action maps and actions
  └─ Subscribe to callbacks (+=)

ON ENABLE
  └─ Enable the action map
      → Input System starts checking bindings
      → Callbacks can now fire

[GAME RUNNING]
  → Player presses Space
  → Input System detects: "<Keyboard>/space" matches Jump binding
  → Fires jumpAction.started
  → Your OnJumpStarted() callback runs
  → IsJumping = true

ON DISABLE
  └─ Disable the action map
      → Input System stops checking bindings
      → Callbacks stop firing

ON DESTROY
  └─ Unsubscribe from callbacks (-=)
      → Prevents memory leaks
      → Prevents null reference errors
```

**With PlayerInput:** All of this is automated.

**Without PlayerInput:** You write all of this explicitly.

---

# Comparison With PlayerInput

## Code Size

|Approach|Lines of Code|Boilerplate|Flexibility|
|---|---|---|---|
|**With PlayerInput**|~40 lines|Minimal|Low|
|**Without PlayerInput**|~120 lines|High|High|
|**Generated C# Class**|~50 lines|Low|High|

---

## Feature Comparison

|Feature|With PlayerInput|Without PlayerInput|
|---|---|---|
|**Setup time**|Fast (drag asset to component)|Slower (write subscription code)|
|**Inspector visibility**|All actions visible|Only asset reference visible|
|**Callback routing**|Automatic (SendMessage/UnityEvents)|Manual (explicit += subscriptions)|
|**Multiplayer support**|Built-in (PlayerInputManager)|You implement it|
|**Control scheme switching**|Automatic device detection|You implement it|
|**Performance**|Reflection overhead (SendMessage)|Direct delegates (faster)|
|**Flexibility**|Limited by component design|Full control|
|**Type safety**|Low (string-based)|High (if using generated class)|

---

## When To Use Each Approach

### Use PlayerInput when:

- You're prototyping or learning
- Your game has simple input needs
- You want visual Inspector configuration
- You're using built-in multiplayer features
- You don't need micro-optimizations

### Skip PlayerInput when:

- You need precise control over action lifecycle
- You're building a custom input framework
- Performance is critical (avoiding reflection)
- You have complex state-dependent input logic
- Your architecture doesn't fit PlayerInput's assumptions

---

# Advanced Patterns

## Pattern 1: Composite Actions with Value Types

```csharp
// Setup for WASD composite binding that produces Vector2
private void InitializeMoveAction()
{
    var moveAction = playerActionMap.FindAction("Move");

    // Subscribe to all three phases for full control
    moveAction.started += ctx =>
    {
        Debug.Log("Player started moving");
    };

    moveAction.performed += ctx =>
    {
        Vector2 input = ctx.ReadValue<Vector2>();
        MoveInput = input;
        Debug.Log($"Move value: {input}");
    };

    moveAction.canceled += ctx =>
    {
        MoveInput = Vector2.zero;
        Debug.Log("Player stopped moving");
    };
}
```

---

## Pattern 2: Action Map Switching With State Machine

```csharp
private enum GameState
{
    Playing,
    Menu,
    Cutscene
}

private GameState currentState = GameState.Playing;

public void SetGameState(GameState newState)
{
    // Disable current map
    switch (currentState)
    {
        case GameState.Playing:
            controls.Player.Disable();
            break;
        case GameState.Menu:
            controls.UI.Disable();
            break;
        case GameState.Cutscene:
            // All input disabled
            break;
    }

    // Enable new map
    switch (newState)
    {
        case GameState.Playing:
            controls.Player.Enable();
            break;
        case GameState.Menu:
            controls.UI.Enable();
            break;
        case GameState.Cutscene:
            // Keep all input disabled
            break;
    }

    currentState = newState;
}
```

---

## Pattern 3: Dynamic Action Rebinding

```csharp
public void RebindJumpKey(Key newKey)
{
    // Find the binding for Jump action
    var jumpAction = controls.Player.Jump;

    // Disable while rebinding
    jumpAction.Disable();

    // Remove old binding
    if (jumpAction.bindings.Count > 0)
    {
        jumpAction.RemoveBinding(0);
    }

    // Add new binding
    jumpAction.AddBinding($"<Keyboard>/{newKey}");

    // Re-enable
    jumpAction.Enable();

    Debug.Log($"Jump rebound to: {newKey}");
}
```

---

## Pattern 4: Multiple Players Without PlayerInputManager

```csharp
public class ManualMultiplayerInput : MonoBehaviour
{
    [SerializeField] private InputActionAsset player1Actions;
    [SerializeField] private InputActionAsset player2Actions;

    private PlayerControls player1Controls;
    private PlayerControls player2Controls;

    private void Awake()
    {
        // Player 1 uses keyboard
        player1Controls = new PlayerControls();
        player1Controls.Player.Move.performed += ctx =>
        {
            // Route to player 1's character
            Player1Controller.Instance.SetMoveInput(ctx.ReadValue<Vector2>());
        };

        // Player 2 uses gamepad
        player2Controls = new PlayerControls();
        player2Controls.Player.Move.performed += ctx =>
        {
            // Route to player 2's character
            Player2Controller.Instance.SetMoveInput(ctx.ReadValue<Vector2>());
        };
    }

    private void OnEnable()
    {
        player1Controls.Enable();
        player2Controls.Enable();
    }

    private void OnDisable()
    {
        player1Controls.Disable();
        player2Controls.Disable();
    }
}
```

---

## Pattern 5: Querying Device Information

```csharp
private void OnJumpStarted(InputAction.CallbackContext context)
{
    // Which device triggered this action?
    var device = context.control.device;

    if (device is Keyboard)
    {
        Debug.Log("Jump pressed on keyboard");
    }
    else if (device is Gamepad gamepad)
    {
        Debug.Log($"Jump pressed on gamepad: {gamepad.displayName}");
    }

    // Which specific control?
    Debug.Log($"Exact control: {context.control.path}");
    // Output example: "<Keyboard>/space"
}
```

---

## Pattern 6: Hold Detection Without Interactions

```csharp
private float jumpHoldTime = 0f;
private const float maxJumpHoldTime = 0.5f;

private void OnJumpStarted(InputAction.CallbackContext context)
{
    jumpHoldTime = 0f;
}

private void Update()
{
    if (IsJumping)
    {
        jumpHoldTime += Time.deltaTime;
        float chargePercent = Mathf.Clamp01(jumpHoldTime / maxJumpHoldTime);
        // Use chargePercent to scale jump power
    }
}

private void OnJumpCanceled(InputAction.CallbackContext context)
{
    // Execute the jump with the charged power
    float jumpPower = Mathf.Lerp(minJumpForce, maxJumpForce, chargePercent);
    Jump(jumpPower);
}
```

---

# Best Practices

## 1. Always Unsubscribe

```csharp
// ❌ BAD — Memory leak waiting to happen
private void Awake()
{
    jumpAction.performed += OnJump;
}

// ✅ GOOD — Proper lifecycle management
private void OnEnable()
{
    jumpAction.performed += OnJump;
}

private void OnDisable()
{
    jumpAction.performed -= OnJump;  // Critical!
}
```

---

## 2. Null-Check Everything

```csharp
// ❌ BAD — Will crash if action doesn't exist
var jumpAction = playerActionMap.FindAction("Jump");
jumpAction.performed += OnJump;  // NullReferenceException if "Jump" not found

// ✅ GOOD — Defensive programming
var jumpAction = playerActionMap.FindAction("Jump");
if (jumpAction != null)
{
    jumpAction.performed += OnJump;
}
else
{
    Debug.LogError("Jump action not found in Player action map!");
}
```

---

## 3. Enable/Disable in OnEnable/OnDisable

```csharp
// ❌ BAD — Actions stay enabled when GameObject is inactive
private void Awake()
{
    playerActionMap.Enable();
}

// ✅ GOOD — Follows Unity lifecycle
private void OnEnable()
{
    playerActionMap.Enable();
}

private void OnDisable()
{
    playerActionMap.Disable();
}
```

---

## 4. Use Generated C# Class for Type Safety

```csharp
// ❌ BAD — Typos cause silent failures
var jumpAction = controls.FindAction("Jmup");  // oops, typo!
if (jumpAction != null)  // will be null, but you might not notice immediately
{
    jumpAction.performed += OnJump;
}

// ✅ GOOD — Compile-time error catches typos
controls.Player.Jmup.performed += OnJump;  // Compiler error: "Jmup" doesn't exist
controls.Player.Jump.performed += OnJump;  // Correct
```

---

## 5. Disable Before Rebinding

```csharp
// ❌ BAD — Can cause race conditions
jumpAction.RemoveBinding(0);
jumpAction.AddBinding("<Keyboard>/e");

// ✅ GOOD — Disable during structural changes
jumpAction.Disable();
jumpAction.RemoveBinding(0);
jumpAction.AddBinding("<Keyboard>/e");
jumpAction.Enable();
```

---

## 6. Don't Subscribe in Update()

```csharp
// ❌ BAD — Subscribes 60+ times per second
private void Update()
{
    jumpAction.performed += OnJump;  // Memory leak horror show
}

// ✅ GOOD — Subscribe once
private void OnEnable()
{
    jumpAction.performed += OnJump;
}
```

---

## 7. Use Disposed Pattern for Generated Classes

```csharp
// ✅ BEST PRACTICE — Proper cleanup
private PlayerControls controls;

private void Awake()
{
    controls = new PlayerControls();
    // ... subscriptions ...
}

private void OnDestroy()
{
    controls?.Dispose();  // Unsubscribes all callbacks automatically
}
```

---

# Summary: The Manual vs Automatic Choice

## Manual Approach (Without PlayerInput)

**You write:**

```csharp
private InputActionAsset asset;
private InputActionMap map;
private InputAction action;

void Awake()
{
    map = asset.FindActionMap("Player");
    action = map.FindAction("Jump");
    action.performed += OnJump;
}

void OnEnable() { map.Enable(); }
void OnDisable() { map.Disable(); }
void OnDestroy() { action.performed -= OnJump; }

void OnJump(InputAction.CallbackContext ctx) { }
```

**Pros:** Full control, no magic, faster (no reflection), flexible architecture **Cons:** More boilerplate, easier to make mistakes, more to maintain

---

## Automatic Approach (With PlayerInput)

**You write:**

```csharp
public void OnJump(InputValue value)
{
    // That's it. PlayerInput handles everything else.
}
```

**Pros:** Fast to set up, less code, visual Inspector, multiplayer built-in **Cons:** Less control, SendMessage overhead, opaque behavior, harder to customize

---

## The Hybrid: Generated Class

**You write:**

```csharp
private PlayerControls controls;

void Awake()
{
    controls = new PlayerControls();
    controls.Player.Jump.performed += ctx => Jump();
}

void OnEnable() { controls.Enable(); }
void OnDisable() { controls.Disable(); }
void OnDestroy() { controls.Dispose(); }
```

**Pros:** Type-safe, IntelliSense, flexible, no reflection **Cons:** Must regenerate when asset changes

---

## Recommendation

|Project Type|Recommended Approach|
|---|---|
|**Learning / Prototyping**|Use PlayerInput|
|**Small game**|Use PlayerInput|
|**Medium game**|Generated C# class|
|**Large game / Team**|Generated C# class|
|**Performance-critical**|Manual without PlayerInput|
|**Custom framework**|Manual without PlayerInput|

---

_This guide complements the New Input System reference documentation and shows the full manual approach._