**PhotonNetwork** is the primary static class provided by Photon PUN that serves as the interface between your Unity game and the Photon Cloud (or a self-hosted Photon Server). It exposes methods, properties, and events for connecting, creating or joining rooms, sending networked RPCs, synchronizing object states, and managing players.

Key responsibilities of **PhotonNetwork** include:

- **Connection Management**  
    Establishing and maintaining a connection to Photon Cloud servers.
    
- **Room & Lobby Control**  
    Creating, joining, listing, and leaving rooms and lobbies.
    
- **Player & Team Management**  
    Accessing the local player (`PhotonNetwork.LocalPlayer`), the list of connected players (`PhotonNetwork.PlayerList`), and setting custom player properties.
    
- **Object Instantiation & Synchronization**  
    Instantiating networked prefabs (`PhotonNetwork.Instantiate`) and handling automatic state updates via `PhotonView` components.
    
- **RPC Calls**  
    Invoking remote procedure calls across clients with `PhotonView.RPC`.
    
- **Network Settings**  
    Adjusting send rates, serialization rates, and caching options through static properties.

Has [[PhotonNetwork.ConnectUsingSettings()]]: In summary, **PhotonNetwork** is the central hub for all Photon PUN operations, and **ConnectUsingSettings()** is its convenient one-line method for establishing the initial network connection using your configured server settings.

Has [[PhotonNetwork.JoinLobby()]]: With `PhotonNetwork.JoinLobby()`, you enable a smooth, interactive matchmaking experience—letting players browse, select, and join games before entering real-time multiplayer sessions.

Has [[PhotonNetwork.JoinRoom()]]: Use `PhotonNetwork.JoinRoom("roomName")` to join known rooms by name. Handle success in [[OnJoinedRoom()]] to proceed with your game. Handle failure in [[OnJoinRoomFailed()]] to inform the user or take alternate action.

Has [[PhotonNetwork.CreateRoom()]]: **PhotonNetwork.CreateRoom()** creates a new multiplayer room on the Photon server with a specified name and optional settings.
	- **Purpose:** Create a uniquely named room where players can join and play together.
	- **Parameters:**
	    - `roomName` (string): Name of the room to create (must be unique).
	    - `RoomOptions` (optional): Settings like max players, visibility, open/closed status.
	- **Success:** Automatically joins the room after creation, triggering [[OnJoinedRoom()]].
	- **Failure:** If room exists or creation fails, [[OnCreateRoomFailed()]] is called.