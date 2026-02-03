**MonoBehaviourPunCallbacks** is a base class provided by Photon PUN that extends Unity’s **[[MonoBehaviour]]** to include a suite of virtual callback methods related to network events. By deriving your own classes from **MonoBehaviourPunCallbacks**, you can override these callbacks to respond to Photon events without writing boilerplate event-registration code.

## Key Features

1. **Automatic Event Hookup**  
    When a GameObject with a **MonoBehaviourPunCallbacks**-derived component is active, PUN automatically registers and unregisters it to receive network event callbacks.
    
2. **Comprehensive Callback Methods**  
    The class exposes numerous network lifecycle and player/room events as overridable methods, including but not limited to:
    
    - **Connection Events**
        
        - `OnConnected()` – called once the client connects to any server.
            
        - [[OnConnectedToMaster()]] – called after connecting to the Master Server.
            
        - `OnDisconnected(DisconnectCause cause)` – called upon disconnection.
            
    - **Lobby & Room Events**
        
        - [[OnJoinedLobby()]] / `OnLeftLobby()`
            
        - [[OnCreatedRoom()]] / [[OnCreateRoomFailed()]]
            
        - [[OnJoinedRoom()]] / [[OnJoinRoomFailed()]]
            
        - `OnLeftRoom()`
            
    - **Player Events**
        
        - `OnPlayerEnteredRoom(Player newPlayer)`
            
        - `OnPlayerLeftRoom(Player otherPlayer)`
            
        - `OnPlayerPropertiesUpdate(Player targetPlayer, Hashtable changedProps)`
            
    - **Room Properties & Master Client**
        
        - `OnRoomPropertiesUpdate(Hashtable propertiesThatChanged)`
            
        - `OnMasterClientSwitched(Player newMasterClient)`
            
3. **Ease of Use**  
    Instead of manually subscribing to `PhotonNetwork.NetworkingClient.EventReceived` or other low-level events, you simply override the callbacks you need.
    

## Example Usage
```Csharp
using Photon.Pun;
using Photon.Realtime;
using UnityEngine;

public class LobbyManager : MonoBehaviourPunCallbacks
{
    void Start()
    {
        PhotonNetwork.ConnectUsingSettings();
    }

    public override void OnConnectedToMaster()
    {
        Debug.Log("Connected to Master Server. Joining lobby...");
        PhotonNetwork.JoinLobby();
    }

    public override void OnJoinedLobby()
    {
        Debug.Log("Lobby joined. Ready to create or join rooms.");
    }

    public override void OnCreateRoomFailed(short returnCode, string message)
    {
        Debug.LogError($"Room creation failed: {message} (Code {returnCode})");
    }

    public override void OnJoinedRoom()
    {
        Debug.Log($"Joined room: {PhotonNetwork.CurrentRoom.Name}");
        // Load or enable the game scene
    }

    public override void OnPlayerEnteredRoom(Player newPlayer)
    {
        Debug.Log($"Player joined: {newPlayer.NickName}");
    }
}

```

## When to Use MonoBehaviourPunCallbacks

- **Scene Management**: Automatically handle network events for lobby, room, or in-game scenes.
    
- **Gameplay Logic**: React to other players joining/leaving, property updates, or master client changes.
    
- **Error Handling**: Gracefully manage failures in creation, joining, or authentication by overriding failure callbacks.