## Understanding `OnJoinedLobby()`

**`OnJoinedLobby()`** is a callback method provided by Photon PUN that you override in a class derived from **`MonoBehaviourPunCallbacks`**. Unity invokes this method automatically when your client successfully enters a lobby after calling [[PhotonNetwork.JoinLobby()]].

---

## Purpose

- **Notification**: Tells you that you are now part of a lobby and ready to receive room listings.
    
- **UI Initialization**: Ideal place to enable or populate your lobby user interface.
    
- **Custom Logic**: You can trigger additional setup, such as filtering room lists or starting matchmaking timers.
    

---

## How It Works

1. **Connection Flow**
    
    - You call `PhotonNetwork.ConnectUsingSettings()` to connect to Photon’s Master Server.
        
    - In `OnConnectedToMaster()`, you invoke `PhotonNetwork.JoinLobby()`.
        
2. **Lobby Join**
    
    - Photon processes your lobby request.
        
    - When complete, it calls your overridden `OnJoinedLobby()` method.
        
3. **Room List Updates**
    
    - After joining, Photon sends `OnRoomListUpdate(List<RoomInfo> roomList)` whenever rooms are created, updated, or removed.
        

---

## Method Signature

csharp

`public override void OnJoinedLobby() {     // Your code here }`

- No parameters.
    
- Called only once per lobby join.
    
- Must be inside a class inheriting from `MonoBehaviourPunCallbacks`.
    

---

## Example Usage

```csharp
using Photon.Pun;
using Photon.Realtime;
using UnityEngine;
using System.Collections.Generic;

public class LobbyUIManager : MonoBehaviourPunCallbacks
{
    // Called when connected to Master Server
    public override void OnConnectedToMaster()
    {
        PhotonNetwork.JoinLobby();
    }

    // Called when lobby join is successful
    public override void OnJoinedLobby()
    {
        Debug.Log("✅ Successfully joined the lobby.");
        ShowLobbyUI();
        RequestRoomList(); // Optional: manually request current rooms
    }

    // Handle updated room lists
    public override void OnRoomListUpdate(List<RoomInfo> roomList)
    {
        UpdateRoomListUI(roomList);
    }

    void ShowLobbyUI()
    {
        // Enable lobby panel, buttons, etc.
    }

    void RequestRoomList()
    {
        // Photon automatically sends initial room list after OnJoinedLobby
    }

    void UpdateRoomListUI(List<RoomInfo> roomList)
    {
        // Populate your UI elements with roomList data
        foreach (var room in roomList)
        {
            Debug.Log($"Room: {room.Name} | Players: {room.PlayerCount}/{room.MaxPlayers}");
        }
    }
}

```

---

## Common Pitfalls

- **Not Deriving from `MonoBehaviourPunCallbacks`**  
    Ensure your class inherits from `MonoBehaviourPunCallbacks` so Photon can route events to it.
    
- **Multiple Instances**  
    If you have several lobby managers in the scene, all active ones will receive the callback. Use one dedicated manager to avoid duplicate UI updates.
    
- **Calling `JoinLobby()` Too Early**  
    Always call `PhotonNetwork.JoinLobby()` **after** `OnConnectedToMaster()` to ensure you’re connected.
    

---

By implementing `OnJoinedLobby()`, you gain control over the moment your client enters the matchmaking lobby, allowing you to set up and display room listings, initialize UI, and guide players to create or join games.