In Photon PUN, **`PhotonNetwork.JoinLobby()`** is the call you make to enter a matchmaking lobby after you’ve connected to the Master Server. A lobby is a waiting area where you can retrieve lists of available game rooms and display them to players before they join or create a room.

---

## When to Call `JoinLobby()`

1. **Connect to Master Server**
    
    csharp
    
    `PhotonNetwork.ConnectUsingSettings();`
    
2. **Wait for Connection Callback**  
    Override:
    
    csharp
    
    `public override void OnConnectedToMaster() {     // Now you’re ready to join a lobby     PhotonNetwork.JoinLobby(); }`
    

---

## What Happens Internally

- **Lobby Registration**  
    Your client informs the Master Server that it wants to receive updates about a specific lobby (the default lobby or a named lobby).
    
- **Room List Updates**  
    Once in a lobby, Photon sends you callbacks whenever rooms are created, updated, or removed. This lets you maintain an up-to-date list of joinable rooms.
    

---

## Key Callbacks

To respond to lobby events, derive from `MonoBehaviourPunCallbacks` and override:

- **[[OnJoinedLobby()]]**
    
    csharp
    
    `public override void OnJoinedLobby() {     Debug.Log("✅ Joined the lobby. Ready to receive room listings.");     // Show your UI for available rooms here }`
    
- **OnRoomListUpdate(`List<RoomInfo> roomList`)**
    
    csharp
    
    `public override void OnRoomListUpdate(List<RoomInfo> roomList) {     // roomList contains all current rooms and their properties     foreach (var info in roomList)     {         Debug.Log($"Room: {info.Name} | Players: {info.PlayerCount}/{info.MaxPlayers}");     } }`
    
- **OnLeftLobby()**  
    Called if you leave the lobby (e.g., by joining a room or explicitly calling [[PhotonNetwork.LeaveLobby()]]).
    

---

## Example Usage

```csharp
using Photon.Pun;
using Photon.Realtime;
using UnityEngine;
using System.Collections.Generic;

public class LobbyController : MonoBehaviourPunCallbacks
{
    void Start()
    {
        // Step 1: Connect to Photon Master Server
        PhotonNetwork.ConnectUsingSettings();
    }

    public override void OnConnectedToMaster()
    {
        // Step 2: Upon connection, join the default lobby
        PhotonNetwork.JoinLobby();
    }

    public override void OnJoinedLobby()
    {
        Debug.Log("Joined lobby successfully.");
        // Initialize or show lobby UI here
    }

    public override void OnRoomListUpdate(List<RoomInfo> roomList)
    {
        // Update your lobby UI with current rooms
        foreach (RoomInfo room in roomList)
        {
            Debug.Log($"Room: {room.Name} | {room.PlayerCount}/{room.MaxPlayers}");
        }
    }

    public void CreateRoom(string roomName)
    {
        PhotonNetwork.CreateRoom(roomName, new RoomOptions { MaxPlayers = 2 });
    }

    public void JoinRoom(string roomName)
    {
        PhotonNetwork.JoinRoom(roomName);
    }
}

```

1. **Connect**: `ConnectUsingSettings()`
    
2. **Join Lobby**: `JoinLobby()` inside `OnConnectedToMaster()`
    
3. **Receive Room List**: `OnRoomListUpdate` updates UI
    
4. **Create/Join**: Use `CreateRoom` or `JoinRoom` based on UI input
    

---

## Why Use a Lobby?

- **Dynamic Room Discovery**: Players can see and select from active game rooms.
    
- **Custom Filters**: You can create multiple lobbies (named lobbies) to categorize rooms (e.g., skill levels, game modes).
    
- **Scalability**: Lobbies let you manage and display rooms without joining them, reducing unnecessary server load.
    

With `PhotonNetwork.JoinLobby()`, you enable a smooth, interactive matchmaking experience—letting players browse, select, and join games before entering real-time multiplayer sessions.