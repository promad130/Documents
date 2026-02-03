**`OnConnectedToMaster()`** is a callback method in Photon PUN that you override in a class derived from **`MonoBehaviourPunCallbacks`**. Unity calls this method automatically once your client has successfully connected to the [[Photon Master Server]], which is the entry point for all Photon Cloud services.

---

## When It Is Invoked

- You call
    
    csharp
    
    `PhotonNetwork.ConnectUsingSettings();`
    
    to begin connecting.
    
- Photon negotiates with regional Master Servers.
    
- Upon successful handshake and authentication with the Master Server, PUN invokes
    
    csharp
    
    `public override void OnConnectedToMaster() { … }`
    
    on any active **`MonoBehaviourPunCallbacks`** instances.
    

---

## Typical Use Cases

- **Join a Lobby**  
    Immediately call
    
    csharp
    
    `PhotonNetwork.JoinLobby();`
    
    to get room listings and enable matchmaking UI.
    
- **Log Connection Status**  
    Confirm in logs that the network connection is live.
    
- **Initialize User Data**  
    Set custom player properties or send analytics events once connected.
    

---

## Example
```csharp
using Photon.Pun;
using Photon.Realtime;
using UnityEngine;

public class PhotonConnector : MonoBehaviourPunCallbacks
{
    void Start()
    {
        PhotonNetwork.ConnectUsingSettings();
    }

    public override void OnConnectedToMaster()
    {
        Debug.Log("✅ Connected to Photon Master Server");
        // Now safe to join a lobby or create/join rooms
        PhotonNetwork.JoinLobby();
    }

    public override void OnDisconnected(DisconnectCause cause)
    {
        Debug.LogError("Disconnected: " + cause);
    }
}

```

1. **Start()** initiates the connection.
    
2. **OnConnectedToMaster()** confirms the client is ready for lobby and room operations.
    
3. **OnDisconnected(...)** handles any connection losses.
    

This pattern ensures your multiplayer workflow (joining lobbies, creating rooms, syncing players) only proceeds after a successful network connection.