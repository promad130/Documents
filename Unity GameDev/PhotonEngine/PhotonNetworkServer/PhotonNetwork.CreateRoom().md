**PhotonNetwork.CreateRoom()** is the method to create a new multiplayer room on the Photon server.

---

## Key Points:

- **Purpose:** Creates a uniquely named room where players can join and play together.
    
- **Parameters:**
    
    - `roomName` (string): The name of the room you want to create. Must be unique.
        
    - Optional **[[RoomOptions]]** to set max players, visibility, and other room settings.
        // TODO: Cover RoomOptions, and player being in same room diff server depending upon the region
- **Success:** Automatically joins the room after creation, triggering [[OnJoinedRoom()]] callback.
    
- **Failure:** If the room name already exists or creation fails, [[OnCreateRoomFailed()]] is called.
    

---

## Basic Usage Example:

```csharp
using Photon.Pun;
using Photon.Realtime;
using UnityEngine;

public class RoomCreator : MonoBehaviourPunCallbacks
{
    public void CreateNewRoom(string roomName)
    {
        RoomOptions options = new RoomOptions
        {
            MaxPlayers = 2,
            IsVisible = true,
            IsOpen = true
        };

        bool created = PhotonNetwork.CreateRoom(roomName, options);
        if (!created)
        {
            Debug.LogError("CreateRoom request failed to start.");
        }
    }

    public override void OnJoinedRoom()
    {
        Debug.Log("Room created and joined: " + PhotonNetwork.CurrentRoom.Name);
        // Load multiplayer game scene here
        PhotonNetwork.LoadLevel("ChessGameScene");
    }

    public override void OnCreateRoomFailed(short returnCode, string message)
    {
        Debug.LogError($"Create room failed: {message} (Code: {returnCode})");
        // Handle failure (e.g. try a different name)
    }
}

```

---

## Important Notes:

- **Unique Room Name:** Creating a room with an existing name will fail. Use unique names or GUIDs if needed.
    
- **RoomOptions:** Control maximum players, visibility, open/closed state, custom properties.
    
- **Auto-Join:** Upon successful creation, Photon automatically joins the room for you.
    
- **Must be Connected:** Ensure you are connected and joined to a lobby or Master Server before creating rooms.
    

---

## What if something goes wrong?
if anything goes wrong, then we 

## Summary:

- Use [[PhotonNetwork.CreateRoom()]] to create a new multiplayer room.
- Handle callbacks [[OnJoinedRoom()]] and [[OnCreateRoomFailed()]] for success/failure.
- Always verify your network connection before calling this method.