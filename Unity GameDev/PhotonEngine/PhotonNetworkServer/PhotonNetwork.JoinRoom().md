**PhotonNetwork.JoinRoom()** is the method you call to join an existing multiplayer room by specifying its name.

## Key Points:

- **Purpose:** Join a specific room that already exists on the Photon server.
    
- **Parameter:**
    
    - `roomName` (string) — the exact name of the room you want to join.
        
- **Success callback:**
    
    - If joining succeeds, `OnJoinedRoom()` is called automatically on your `MonoBehaviourPunCallbacks` class.
        
- **Failure callback:**
    
    - If the room does not exist, is full, or is closed, `OnJoinRoomFailed(short returnCode, string message)` is called (if overridden).
        

---

## How to Use
```Csharp
using Photon.Pun;
using Photon.Realtime;
using UnityEngine;

public class RoomManager : MonoBehaviourPunCallbacks
{
    public void JoinRoomByName(string roomName)
    {
        bool joinStarted = PhotonNetwork.JoinRoom(roomName);
        if (!joinStarted)
        {
            Debug.LogError("Failed to start joining room: " + roomName);
        }
    }

    public override void OnJoinedRoom()
    {
        Debug.Log("Successfully joined room: " + PhotonNetwork.CurrentRoom.Name);
        // Load your multiplayer game scene here
        PhotonNetwork.LoadLevel("ChessGameScene");
    }

    public override void OnJoinRoomFailed(short returnCode, string message)
    {
        Debug.LogError($"Join room failed: {message} (Code: {returnCode})");
        // Handle failure (show UI message, create room, etc.)
    }
}
```
---

## Important Notes:

- You **must be connected to Photon** (connected to Master Server and in Lobby) before calling `JoinRoom()`.
    
- Room names are case-sensitive and must exactly match the target room.
    
- If the room is private, closed, or full, joining will fail.
    
- When you create a room via `PhotonNetwork.CreateRoom()`, you automatically join it.
    

---

## Summary:

- Use `PhotonNetwork.JoinRoom("roomName")` to join known rooms by name.
    
- Handle success in `OnJoinedRoom()` to proceed with your game.
    
- Handle failure in `OnJoinRoomFailed()` to inform the user or take alternate action.
    

That’s the complete straight-to-the-point beginner’s guide to `JoinRoom()`.
