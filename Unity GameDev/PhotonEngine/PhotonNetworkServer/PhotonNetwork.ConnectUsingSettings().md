The `PhotonNetwork.ConnectUsingSettings()` method initializes the connection to Photon Cloud (or your configured Photon Server) using the settings defined in the **PhotonServerSettings** asset. You typically call this once (often in your main network manager or lobby controller) to begin the connection process.

When you invoke:

csharp

`PhotonNetwork.ConnectUsingSettings();`

it performs the following steps:

1. **Reads Configuration**  
    Fetches your application’s App ID, region, protocol (UDP/WebSockets), and other network settings from the `PhotonServerSettings` file in your project’s `Assets/Photon/PhotonUnityNetworking/Resources` folder.
    
2. **Connects to Master Server**  
    Initiates a handshake with the Photon Master Server in the selected region. Once connected, your client is ready to join lobbies and rooms.
    
3. **Triggers Callbacks**  
    As the connection progresses, PUN calls back into your `MonoBehaviourPunCallbacks` methods:
    
    - `OnConnectedToMaster()` when the initial connection succeeds.
        
    - `OnDisconnected(DisconnectCause cause)` if the connection fails or drops.
        

---

## Typical Usage Example

```C#
using Photon.Pun;
using Photon.Realtime;
using UnityEngine;

public class NetworkManager : MonoBehaviourPunCallbacks
{
    void Start()
    {
        // Begin connecting to Photon Cloud
        PhotonNetwork.ConnectUsingSettings();
    }

    public override void OnConnectedToMaster()
    {
        Debug.Log("Connected to Photon Master Server.");
        // Ready to join lobby or create/join rooms
    }

    public override void OnDisconnected(DisconnectCause cause)
    {
        Debug.LogError($"Photon disconnected: {cause}");
    }
}

```

In summary, **PhotonNetwork** is the central hub for all Photon PUN operations, and **ConnectUsingSettings()** is its convenient one-line method for establishing the initial network connection using your configured server settings.