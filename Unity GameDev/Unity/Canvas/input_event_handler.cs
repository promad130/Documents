using UnityEngine;
using UnityEngine.InputSystem;

/// <summary>
/// Handles player movement and actions by reading input from InputManager.
/// This script does NOT directly touch the Input System — it just reads
/// the clean state values that InputManager exposes.
///
/// Attach this to your Player GameObject.
/// InputManager lives on a separate persistent GameObject (singleton).
/// </summary>
public class InputEventHandler : MonoBehaviour
{
    [Header("Movement Settings")]
    [SerializeField] private float moveSpeed = 5f;
    [SerializeField] private float jumpForce = 10f;

    private Rigidbody2D rb;
    private bool isGrounded;

    private void Awake()
    {
        rb = GetComponent<Rigidbody2D>();
    }

    private void Update()
    {
        HandleMovement();
        HandleLink();
    }

    private void HandleMovement()
    {
        // Read the current move input from the InputManager singleton
        // InputManager.Instance.MoveInput is a Vector2 set by OnMove()
        Vector2 moveDir = InputManager.Instance.MoveInput;

        // Apply horizontal movement
        rb.linearVelocity = new Vector2(moveDir.x * moveSpeed, rb.linearVelocity.y);
    }

    private void HandleLink()
    {
        // Check if Link action was activated this frame
        if (InputManager.Instance.IsLinkActive)
        {
            ActivateLink();
        }
    }

    private void ActivateLink()
    {
        Debug.Log("Link ability activated!");
        // Your link mechanic logic here
    }

    // ----------------------------------------------------------------
    // This method is wired up through UnityEvents on the PlayerInput
    // component (if using UnityEvents notification mode instead of
    // SendMessage). It would be dragged in via the Inspector.
    // ----------------------------------------------------------------
    public void OnLinkPerformed(InputAction.CallbackContext context)
    {
        // context.performed is true here (this fires at the "performed" phase)
        if (context.performed)
        {
            ActivateLink();
        }
    }

    // ----------------------------------------------------------------
    // Jump is handled through an event callback directly subscribed in
    // this script (manual subscription pattern — no PlayerInput needed)
    // ----------------------------------------------------------------
    private InputAction jumpActionDirect;

    private void OnEnable()
    {
        // If you want to subscribe directly (without PlayerInput),
        // you can get the action from InputManager's playerInput.
        // This is shown here for reference only — in this project we
        // use InputManager.Instance.IsJumping instead.
    }

    private void OnCollisionEnter2D(Collision2D collision)
    {
        if (collision.gameObject.CompareTag("Ground"))
            isGrounded = true;
    }

    private void OnCollisionExit2D(Collision2D collision)
    {
        if (collision.gameObject.CompareTag("Ground"))
            isGrounded = false;
    }
}
