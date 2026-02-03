
The **Photon Master Server** is a core component of Photon’s cloud-based multiplayer architecture. It acts as the _entry point_ for every client (player) that wants to play a multiplayer game. Think of it as the front desk of a hotel: it doesn’t host your room (the game itself), but it checks you in, finds you a room, and directs you to it.

---

## 1. Role and Responsibilities

1. **Client Connection & Authentication**
    
    - When you call [[PhotonNetwork.ConnectUsingSettings()]], your game client establishes a connection to the Master Server.
        
    - The Master Server verifies your App ID and region, ensuring you’re authorized to use the service.
        
2. **Matchmaking & Lobby Management**
    
    - After connecting, you typically call [[PhotonNetwork.JoinLobby()]] or [[PhotonNetwork.JoinRandomRoom()]].
        
    - The Master Server maintains lists of available “lobbies” (waiting areas) and “rooms” (active games).
        
    - It matches you with existing rooms or creates a new one based on your request.
        
3. **Room Directory Service**
    
    - It keeps track of all active rooms: who created them, how many players are inside, custom room properties, and whether they’re open or closed.
        
    - It sends you lists of rooms so you can display available games to the player.
        
4. **Redirection to Game Servers**
    
    - Once you successfully join or create a room, the Master Server doesn’t host your game.
        
    - It tells your client which **Game Server** instance holds that room.
        
    - Your client then disconnects from the Master Server and reconnects to that dedicated Game Server for real-time gameplay.
        

---

## 2. Workflow Breakdown

1. **Startup & Connect**
    
    - Game calls `PhotonNetwork.ConnectUsingSettings()`
        
    - Master Server authenticates the client
        
2. **Join Lobby**
    
    - Client calls [[PhotonNetwork.JoinLobby()]]
        
    - Master Server confirms lobby entry and can send room lists
        
3. **Matchmaking**
    
    - Client requests [[JoinRandomRoom()]] or [[CreateRoom("RoomName")]]
        
    - Master Server either places the client in an existing room or creates a new one
        
4. **Redirect to Game Server**
    
    - Master Server sends the address of the Game Server hosting your room
        
    - Client switches connection to that Game Server
        
5. **Gameplay**
    
    - All in-game messaging (RPCs, state sync, instantiation) happens with the Game Server
        
6. **End Session**
    
    - When the last player leaves a room, the Game Server closes that room
        
    - Disconnected clients may reconnect to the Master Server for new matchmaking
        

---

## 3. Why This Design?

- **Scalability**: Separating matchmaking (Master Server) from game session hosting (Game Servers) lets Photon scale to millions of players without overloading one server.
    
- **Reliability**: The Master Server remains available to handle new connections, even if individual Game Servers go down.
    
- **Flexibility**: You can configure regions and data centers; the Master Server directs players to the best Game Server based on latency or capacity.
    

---

## 4. Simple Analogy

- **Hotel Front Desk (Master Server)**  
    Checks you in, finds an available room, and issues your keycard.
    
- **Hotel Room (Game Server Instance)**  
    Once you have your key, you go to your room for your private stay (gameplay).
    
- **Lobby (Waiting Area)**  
    A common area where guests gather while waiting for rooms or for other guests to arrive (player matchmaking).
    

---

By understanding the Photon Master Server, you can design your game’s connection flow so players reliably find and join game sessions, ensuring smooth matchmaking before diving into real-time multiplayer gameplay.