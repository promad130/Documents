using UnityEngine;
using UnityEngine.InputSystem;

/// <summary>
/// Singleton that manages all input for the game.
/// Holds references and provides input state to other systems.
/// Uses the PlayerInput component's SendMessage notification mode.
/// </summary>
public class InputManager : MonoBehaviour
{
    // --- Singleton Pattern ---
    public static InputManager Instance { get; private set; }

    // --- Public Input State (read by other scripts) ---
    public Vector2 MoveInput { get; private set; }
    public bool IsJumping { get; private set; }
    public bool IsLinkActive { get; private set; }

    // --- Reference to the PlayerInput component on this GameObject ---
    private PlayerInput playerInput;

    // --- Which action map is currently active ---
    private string currentMapName = "Player";

    private void Awake()
    {
        // Enforce singleton
        if (Instance != null && Instance != this)
        {
            Destroy(gameObject);
            return;
        }

        Instance = this;
        DontDestroyOnLoad(gameObject);

        // Grab the PlayerInput component attached to this same GameObject
        playerInput = GetComponent<PlayerInput>();
    }

    // ----------------------------------------------------------------
    // These methods are called automatically by PlayerInput via
    // SendMessage() when the matching action fires.
    // The naming rule: "On" + ActionName  (e.g., action "Move" → OnMove)
    // ----------------------------------------------------------------

    // Called when the "Move" action fires
    public void OnMove(InputValue value)
    {
        MoveInput = value.Get<Vector2>();
    }

    // Called when the "Jump" action fires
    public void OnJump(InputValue value)
    {
        IsJumping = value.isPressed;
    }

    // Called when the "Link" action is performed
    // (SendMessage looks for a method called "OnLink" on every component)
    public void OnLink(InputValue value)
    {
        IsLinkActive = value.isPressed;
        Debug.Log($"Link input received: {IsLinkActive}");
    }

    // ----------------------------------------------------------------
    // Action Map Switching
    // ----------------------------------------------------------------

    /// <summary>
    /// Switch to a different action map by name.
    /// Only one map can be active at a time with PlayerInput by default.
    /// Example maps: "Player", "UI", "Vehicle"
    /// </summary>
    public void SwitchToMap(string mapName)
    {
        if (playerInput == null) return;

        playerInput.SwitchCurrentActionMap(mapName);
        currentMapName = mapName;
        Debug.Log($"Switched input map to: {mapName}");
    }

    public string GetCurrentMap() => currentMapName;
}
