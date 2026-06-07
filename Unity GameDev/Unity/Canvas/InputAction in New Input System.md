
**What it is:** Represents a single game input event like "Jump", "Fire", or "Move". It's the core class that connects bindings to your code.

## Key Properties

| Property | Type            | Purpose                             |
| :------- | :-------------- | :---------------------------------- |
| `name`   | string          | Action name (e.g., "Jump") [^1][^3] |
| `type`   | InputActionType | Button, Value, or PassThrough       |


## Methods
### Enable/Disable
- `Enable()` - Start listening for input
- `Disable()` - Stop listening

### Read Values
- `ReadValue<T>()` - Reads the current value from an InputAction in Unity's Input System. (e.g., `ReadValue<Vector2>()`, `ReadValue<float>()`)
- `ReadValueAsObject()` - Get value as object

### Polling Checks
- `WasPressedThisFrame()` - True if button pressed this frame
- `WasReleasedThisFrame()` - True if button released this frame
- `IsPressed()` - True if button currently held
- `triggered` - Same as WasPerformedThisFrame


### Binding Management
- `AddBinding(string path)` - Add new binding (e.g., `AddBinding("<Keyboard>/space")`)[^1]
- `ChangeBinding(int index)` - Modify existing binding[^1]
- `RemoveBinding(int index)` - Remove binding[^1]

## Key Events
- `started` - Input begins[^3][^1]
- `performed` - Interaction completes[^3][^1]
- `canceled` - Input stops[^3][^1]

**Usage:**

```csharp
jumpAction.performed += ctx => Jump();
jumpAction.canceled += ctx => StopJump();
```


## Common Usage Patterns
**Event-driven:**
```csharp
jumpAction.performed += OnJump;
jumpAction.Enable();

void OnJump(InputAction.CallbackContext ctx) 
{
    player.Jump();
}
```

**Polling:**
```csharp
void Update() 
{
    if (jumpAction.WasPressedThisFrame())
        player.Jump();
    
    Vector2 move = moveAction.ReadValue<Vector2>();
}
```

**Create in code:**
```csharp
var action = new InputAction(
    name: "Jump",
    type: InputActionType.Button,
    binding: "<Keyboard>/space"
);
action.Enable();
```