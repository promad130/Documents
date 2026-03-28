# How does Unity work?
In unity, there are two different hierarchies, i.e, **Levels of Organization** at work:
- **The Composition Hierarchy:** How things are built in the Scene (GameObjects holding Components).
- **The Class Hierarchy:** How the code is written in C# (Scripts inheriting from parent classes and how those scripts work together, interact and execute).

---
## NOTE: Other way to look at it:
### 1. The Class Hierarchy (Inheritance) = "Is-a"
This is what the code defines.
- A `PlayerController` **is a** `MonoBehaviour`.
- A `MonoBehaviour` **is a** `Component`.    
- A `Component` **is an** `Object`.

This hierarchy gives your script the _capability_ to exist in Unity. It defines the "DNA" of the script.

### 2. The Composition Model (GameObjects) = "Has-a"
This is how you build things in the scene.
- A `GameObject` **has a** `PlayerController`.
- A `GameObject` **has a** `Rigidbody`.
- A `GameObject` **has a** `BoxCollider`.
---

In **Composition Hierarchy**, we use the composition model, which has three parts:
- GameObjects:
	- *Everything in a game is a GameObject*. The lights, player, sound, bullets, particles, etc,.
	- Think of this as an empty cardboard box. By itself, it does nothing. It has no shape, no physics, and no behavior. It is just a container that holds a position, rotation and scale in the world.
- Component:
	- These are the items that have purpose in their lives. These can make things happen and do stuff.
	- These connect to the known GameObjects to actually add meaning to that empty piece of cardboard with no hope just like your life.
- Script:
	- These are the custom components you create by yourself when your greedy ass was not happy with all the hard work of unity devs and wanted to create stuff on your own.

In our dear **Class Hierarchy**, we write the code, organize it, make it all work together like a one big happy family definitely not forced to live and smile together.
This is where the cool stuff begins, and unity scripting is introduced. For your script to work as a Component (so it can be attached to a GameObject), it has to inherit from specific Unity classes.
Here is the "Family Tree" of a standard Unity script:
1. **`Object`:** The base of everything in Unity.
2. **`GameObject`** & **`Component`:** Both inherit from `Object`, but they are siblings. One is the container, the other is the content.
3. **`Behaviour`:** Adds the ability to be enabled or disabled.
4. **`MonoBehaviour`:** This is the most important one for you. It adds the "Magic Methods" that connect your script to the game loop (like `Start()` and `Update()`).
(and yes all this is in order of inheritance, 5 inherits 4, 4 inherits 3, 3 inherits 2, and so on....) 

## The Class Hierarchy
Here is the lineage of a typical Unity script:

1. **`Object` (The Ancestor):** This is the base for everything Unity handles (textures, meshes, game objects). It handles basic things like naming the object (`.name`) and destroying it (`Destroy()`).
    
2. **`Component` (The Child):** This inherits from `Object`. A `Component` is specifically something that can attach to a GameObject. It stores who its parent GameObject is (`.gameObject`) and its position (`.transform`). Hence, it knows all about where it is attached.
    
3. **`Behaviour` (The Grandchild):** This inherits from `Component`. It adds the ability to be **enabled** or **disabled**. (Think of the little checkbox next to a component in the Inspector).
    
4. **`MonoBehaviour` (The Great-Grandchild):** This is where the magic happens. It inherits from `Behaviour` and adds the connection to the Unity Engine's internal clock. It allows you to use event functions like `Start()`, `Update()`, and `OnCollisionEnter()`.

When you write `public class MyScript : MonoBehaviour`, you are telling Unity: _"This script is a Component, it can be enabled/disabled, and it needs to listen to the game loop."_

# Add gameObject in this as well.TODO: Add gameObject in this as well.

### Entry of the GameObject
Think of the **GameObject** as the central hub or the "manager" of the box.

1. **The Request:** Your Script (MonoBehaviour) asks, "Hey Manager (GameObject), do you have a `Rigidbody` attached to you?"
2. **The Search:** The GameObject looks through its list of contents (fucker finally has some hope).
3. **The Handshake:** If it finds one, it gives your script a reference to it. Now your script can control gravity!

It looks like this in C#: `Rigidbody myBody = GetComponent<Rigidbody>();`

## Connecting to the Engine
Unity doesn't just read your code from top to bottom once and stop. It uses a **Game Loop**. Think of it like a flipbook animation. To create the illusion of movement, Unity draws a picture, then another, then another, very fast (usually 60 times a second). Each of these pictures is called a **Frame**.

Your `MonoBehaviour` scripts hook into this loop using specific "magic methods."

Here are the two most critical ones:
1. **`Start()`:** This runs **once** when the object first comes into existence (or the scene starts). This is where you do your setup, like getting that Rigidbody component we talked about.
2. **`Update()`:** This runs **every single frame**. If your game runs at 60 frames per second, this function triggers 60 times every second. This is where you listen for inputs and move things.

So, whenever you enter the play mode, unity scans all the components attached to all the GameObjects in the game scene, and that is when it starts connecting it all to the Game Engine.

## The Scripting Languages used in Unity and how does it all work with the engine?
Unity uses C# for scripting, and the engine itself is built on C++.
But then how does it work with engine? 
Here is exactly how inheritance connects your text file to the C++ engine.

### 1. The Two Worlds (Native vs. Managed)
This is the root of everything.
- **The Engine (Native):** Unity’s core is written in **C++**. It handles the physics, graphics, and the actual game loop.
- **Your Script (Managed):** You write in **C#**, which runs on the .NET framework (specifically Mono/IL2CPP).

These two languages cannot talk to each other directly. They are in separate memory spaces. **`MonoBehaviour` is the bridge.**

### 2. The Hidden Pointer (The "Binding")
When you inherit from `MonoBehaviour` (and eventually `UnityEngine.Object`), your script inherits a specific, hidden variable often referred to as `m_CachedPtr` (or an `IntPtr`).
This is what happens when you click "Play":
1. **Creation:** Unity (C++) creates the actual component in its native memory.
2. **Instance:** Unity creates an instance of your C# class.
3. **The Link:** Unity takes the memory address of the C++ object and saves it into that `m_CachedPtr` variable inside your C# object.

**Answer:** Inheriting `MonoBehaviour` is required because that base class contains the code to hold this pointer. Without it, your C# class is just floating in .NET memory with no link to the C++ game world.

### 3. How "Update()" Actually Gets Called
This is the part that confuses everyone because `Update()` is **not** an `override`. You don't write `override void Update()`. You just write `void Update()`.
So how does the C++ engine call a C# function it doesn't know exists?

**The Mechanism: Scripting Backend (Reflection)**
1. **Scanning:** When the scene loads, Unity inspects your C# class using **Reflection**. It literally scans the text of your class looking for specific method names like "Start", "Update", "OnCollisionEnter".
2. **Caching:** If it finds a method named "Update", it saves a pointer to that specific function in a special internal list (a C++ list).
3. **Execution:** Every frame, the C++ engine iterates through this list. It doesn't look at your script file; it just fires the function pointers it saved earlier.

**Why this matters:** If you inherit `MonoBehaviour` but _don't_ write an `Update` function, Unity sees that during the scan and doesn't add you to the list. This saves performance.

# Attributes in `UnityEngine;` namespace
## `[SerializableField]`
`[SerializeField]` is an **attribute** that forces Unity to serialize a private field, making it visible and editable in the Inspector.

# MonoBehaviour
It is a script in `UnityEngine` Namespace. Has properties and methods, and is used by all the object that are / can be used in the game scene/ actual game in unity.

## The Magic Methods in MonoBehaviour:
### What are Magic Methods?
MonoBehaviour methods are actually "magic methods" or messages that Unity calls via reflection if they exist in your script, rather than virtual methods you override.
Unity's message system checks at runtime whether a script contains specific method names like `OnCollisionEnter2D`, `Start()`, or `Update()`. 
If the method exists, Unity adds it to internal lists and calls it at the appropriate time.

### `start()`

The `Start` method is called **only once** in the lifetime of a script instance. It is triggered when:

1. The GameObject is **Active**.
    
2. The Script (Component) is **Enabled**.
    
3. The frame starts, but **before** the first `Update` call.    

**It is not necessarily at the start of the game.** If you instantiate a prefab or activate a GameObject halfway through your game, its `Start` method will run at that moment (specifically, right before its first frame update).

once a script has called its `Start` or `Awake` methods, they are "checked off" the list for that specific instance. Flipping the GameObject from inactive to active (or toggling the checkbox on the script) will **not** make them run a second time.

If you need code to run every single time an object is toggled back on, you’ll want to use `OnEnable()`.

### `update()`
Gets executed at the start of each frame. 
For the first frame, get executed after running the start() method.

### `OnEnable()`
**What:** Called automatically every time the script component or its GameObject becomes enabled.​

**When called:**
- GameObject activated
- Script component enabled
- Scene loads (if object already enabled)​
- **Can be called multiple times** unlike Awake/Start​

**Execution order:**
```text
Awake() → OnEnable() → Start()
```

### Invoke()
**Invoke** is a method in `MonoBehaviour` that lets you call a function after a delay.[^1][^2][^4]
Invoke is a **magic method** like `Start()` and `Update()` - it's built into `MonoBehaviour`. You don't see its implementation, but Unity calls it automatically based on time.[^6][^4][^1][^2]

```csharp
// These are ALL methods in MonoBehaviour
void Start() { }        // Magic method - Unity calls automatically
void Update() { }       // Magic method - Unity calls automatically  
Invoke("Method", 1f);   // Magic method - Unity handles timing
```

#### Syntax

```csharp
Invoke(string methodName, float delay);
```

- **methodName**: Name of method to call (as string)[^2][^1][^4]
- **delay**: Time in seconds before calling[^1][^2][^4]

**Important**: Only methods with **no parameters** and **void return type** can be invoked.[^2][^4]

##### Example - Spawn After Delay

```csharp
using UnityEngine;

public class SpawnExample : MonoBehaviour 
{
    public GameObject enemy;
    
    void Start() 
    {
        // Call SpawnEnemy after 2 seconds
        Invoke("SpawnEnemy", 2f);
    }
    
    void SpawnEnemy() 
    {
        Instantiate(enemy, Vector3.zero, Quaternion.identity);
    }
}
```


#### InvokeRepeating - Repeat Function

```csharp
InvokeRepeating(string methodName, float startDelay, float repeatRate);
```

- **startDelay**: Initial delay before first call[^1][^2]
- **repeatRate**: Time between each repeat[^2][^1]

```csharp
using UnityEngine;

public class SpawnRepeating : MonoBehaviour 
{
    public GameObject coin;
    
    void Start() 
    {
        // Call SpawnCoin after 2 seconds, then every 1 second
        InvokeRepeating("SpawnCoin", 2f, 1f);
    }
    
    void SpawnCoin() 
    {
        float x = Random.Range(-5f, 5f);
        Instantiate(coin, new Vector3(x, 2f, 0), Quaternion.identity);
    }
}
```


#### CancelInvoke - Stop Invokes

```csharp
CancelInvoke();              // Cancel all invokes
CancelInvoke("MethodName");  // Cancel specific method
```

```csharp
using UnityEngine;

public class StopSpawning : MonoBehaviour 
{
    void Start() 
    {
        InvokeRepeating("SpawnEnemy", 0f, 2f);
    }
    
    void Update() 
    {
        if (Input.GetKeyDown(KeyCode.Space)) 
        {
            // Stop spawning
            CancelInvoke("SpawnEnemy");
        }
    }
    
    void SpawnEnemy() 
    {
        Debug.Log("Enemy spawned!");
    }
}
```


#### IsInvoking - Check if Invoking

```csharp
bool isActive = IsInvoking("MethodName");
```

```csharp
void Update() 
{
    if (IsInvoking("SpawnEnemy")) 
    {
        Debug.Log("Still spawning enemies");
    }
}
```


#### Avoiding String Errors - Use nameof

Using strings is error-prone if you rename methods. Use `nameof()`:[^5][^8]

```csharp
using UnityEngine;

public class SafeInvoke : MonoBehaviour 
{
    void Start() 
    {
        // Safe - updates automatically if you rename PrintMessage
        Invoke(nameof(PrintMessage), 2f);
        
        // NOT SAFE - breaks if you rename PrintMessage
        // Invoke("PrintMessage", 2f);
    }
    
    void PrintMessage() 
    {
        Debug.Log("Hello!");
    }
}
```


#### Complete Example - Game Over Delay

```csharp
using UnityEngine;
using UnityEngine.SceneManagement;

public class PlayerDeath : MonoBehaviour 
{
    void OnCollisionEnter(Collision collision) 
    {
        if (collision.gameObject.tag == "Enemy") 
        {
            Debug.Log("Player died!");
            
            // Restart scene after 2 seconds
            Invoke(nameof(RestartLevel), 2f);
        }
    }
    
    void RestartLevel() 
    {
        SceneManager.LoadScene(SceneManager.GetActiveScene().buildIndex);
    }
}
```


### The Collision and Trigger Methods
All the Collision and Trigger callbacks are magic methods:
- [[#Collider Event Methods]]
- [[#Trigger Event Methods]]
#### How Unity Connects Them
Here's the flow:
1. **Collider components** detect overlaps/collisions through Unity's physics engine
2. **Unity's physics engine** determines which GameObjects are involved in the collision/trigger
3. **Unity scans all MonoBehaviour components** attached to those GameObjects
4. **If any MonoBehaviour has OnCollision/OnTrigger methods**, Unity automatically calls them

The Collider component provides the **data** (collision geometry, trigger status), but the **MonoBehaviour provides the callbacks** where you write your response logic.

### So do we have regular methods? Other methods we write, what about them?
Other than that, we also have regular methods, and those are executed as normal C# code.
So if I use a method of transform, then it has to be relayed to render engine right?
So is that transform method executed as a normal C# and then we have the toll calls in the actual defination of that method to the render engine.
Also, consider this:
```csharp
public class MyScript : MonoBehaviour 
{
    void Start() // Magic method - Unity calls automatically
    {
        CalculateDamage(10); // Your custom method - you call it
    }

    void CalculateDamage(int amount) // Regular C# method
    {
        // Only runs when YOU call it
    }
}
```
So here, only start is a magic method, hence what happens is that When you call `CalculateDamage()` from inside `Start()`, it doesn't become a "magic method". Here's what actually occurs at runtime:
- **Unity's C++ engine calls `Start()`** because it's a recognized lifecycle method
- **Your C# code inside `Start()` executes normally**, including the call to `CalculateDamage(10)`
- **`CalculateDamage()` executes as a regular method call** on the call stack—it's just normal C# execution at this point
The "magic" only applies to Unity's engine knowing when to invoke `Start()` in the first place. Once you're inside `Start()`, the rest is standard C# code execution—method calls, loops, conditionals all work exactly like normal C#.


# GameObject 
This is a class that all the things that are in the game has. in other words, it is the thing that player will see. It holds all the scripts that are attached to this game object, like transform, MeshRenderer, collider, your custom scripts, etc.

## Attributes
### transform
A reference to the Transform script attached to the GameObject

### `activeInHierarchy` 
whether it's active

### tag
`tag` is a read/write string property that allows you to identify and categorize GameObjects. Tags must be declared in Unity's Tags and Layers manager before you can use them.
**Type:** String property

```csharp
void Start()
{
	gameObject.tag = "This";
}
```

## Methods
### `GameObject.GetComponent<>()`
Searches that specific GameObject's components

```csharp
GameObject player = GameObject.Find("Player");
Rigidbody2D rb = player.GetComponent<Rigidbody2D>();
```

**If component is found:**  
Returns a reference to the component of type `T`
```csharp
Rigidbody rb = GetComponent<Rigidbody>(); 
// rb now contains a reference to the Rigidbody component
```
**If component is NOT found:**  
Returns `null`
```csharp
Rigidbody rb = GetComponent<Rigidbody>(); 
// rb will be null if no Rigidbody exists on the GameObject
```

### `GameObject.AddComponent<>()`
This is a method of GameObject class, and it adds the given component to the specified game object, and returns the newly created component instance.

```csharp
// Create a new GameObject
GameObject player = new GameObject("Player");

// Add components to it
Rigidbody2D rb = player.AddComponent<Rigidbody2D>();
SpriteRenderer sprite = player.AddComponent<SpriteRenderer>();
PlayerController controller = player.AddComponent<PlayerController>();
```

### `GameObject.CompareTag(String Tag)`
`CompareTag` is a method that checks if a GameObject has a specific tag.
`CompareTag` returns `true` if the GameObject has the specified tag, `false` otherwise. It's the recommended way to check tags instead of using string comparison.

```csharp
void OnCollisionEnter2D(Collision2D collision) 
{
    // Using Component.CompareTag (on Collider2D component)
    if (collision.collider.CompareTag("Enemy")) 
    {
        Debug.Log("Hit enemy!");
    }
    
    // Using GameObject.CompareTag (equivalent)
    if (collision.gameObject.CompareTag("Enemy")) 
    {
        Debug.Log("Hit enemy!");
    }
}

```
`CompareTag` is **significantly faster** than using `gameObject.tag ==`. The `tag` property allocates memory each time it's accessed because it copies the string from Unity's native code to managed C#. `CompareTag` avoids this allocation by doing the comparison in native code.

### `GameObject.Find(String)`
Returns the **first active GameObject** with the matching name, or **`null`** if none is found. Only active GameObjects are searched—inactive objects are ignored.
```csharp
`GameObject player = GameObject.Find("Player");`
```
```csharp
// Find Hand which is child of Arm > Monster
GameObject hand = GameObject.Find("Monster/Arm/Hand");

// Absolute path (Monster has no parent)
GameObject hand = GameObject.Find("/Monster/Arm/Hand");
```

### `GameObject.SetActive(bool)`
Sets the given object either active or inactive
```csharp
gameObject.SetActive(false);
```
- Disables **every component** on the GameObject (renderers, colliders, rigidbodies, scripts, etc.)
- Stops all **lifecycle methods** like `Update()`, `FixedUpdate()`, `LateUpdate()` from being called
- Deactivates **all child GameObjects** in the hierarchy
- Triggers **`OnDisable()`** on all attached MonoBehaviour scripts (if the GameObject was previously active)

```csharp
gameObject.SetActive(true);
```
- Enables the GameObject and its components
- Allows lifecycle methods to resume
- Triggers **`OnEnable()`** on all attached MonoBehaviour scripts (if this changes `activeInHierarchy`)

# Component

## `this` keyword
In Unity, the `this` keyword refers to the **Script instance itself**. While you can use it, it’s usually optional unless you are trying to resolve a naming conflict (like if a function parameter has the same name as a class variable).

In C#, any property or method belonging to the class you are currently writing in can be accessed without the `this.` prefix. Because your script inherits from `MonoBehaviour`, it automatically "owns" certain properties like `gameObject`, `transform`, and `enabled`.

---

### When to use (or skip) the `this` keyword

#### 1. The "Shortcut" Properties

Unity provides built-in shortcuts for the most common things. You can use these directly anywhere in your script:

- **`gameObject`**: The GameObject this script is attached to.
    
- **`transform`**: The Transform component of that GameObject.
    
- **`name`**: The name of the GameObject.
    

#### 2. When `this` is actually required (Disambiguation)

The only time you **must** use `this` is when a local variable (like a function parameter) has the same name as a class variable. This tells the computer, "I mean the one belonging to the class, not the one I just passed into the function."

```C#
public class Player : MonoBehaviour {
    int health;

    public void SetHealth(int health) {
        // 'health' refers to the parameter above.
        // 'this.health' refers to the variable at the top of the script.
        this.health = health; 
    }
}
```

#### 3. Accessing Components

As you noticed, you can call `GetComponent<T>()` directly. Internally, the computer understands this as `this.GetComponent<T>()`.


### The Hierarchy of Access

It helps to visualize what you are actually touching when you type these words:

|**Code**|**What it refers to**|
|---|---|
|**`this`**|The specific **C# Script** (e.g., "PlayerMovement.cs").|
|**`gameObject`**|The **Entity** in the Hierarchy that holds the script.|
|**`transform`**|The **Position/Rotation/Scale** component of that entity.|
|**`this.enabled`**|Whether the **Script** is checked "on" or "off".|
|**`gameObject.SetActive()`**|Whether the **Entire Object** is "on" or "off".|


### A Note on Modern Unity (Performance)

In very old versions of Unity (years ago), using `this.transform` was slightly faster than `gameObject.GetComponent<Transform>()`. However, in modern Unity, `transform` and `gameObject` are highly optimized properties. You don't need to worry about performance differences between `this.gameObject` and `gameObject`—**it is purely a matter of your personal coding style.**

Most Unity developers omit `this.` to keep the code cleaner and easier to read.

## Attributes in Component
### `gameObject` - reference to the GameObject this component is attached to
### `transform` - reference to the Transform component (which is also just accessing `gameObject.transform`)

## Methods in Component
### `GetComponent<>()`
Searches the GameObject that the component (your script) is attached to.

```csharp
public class PlayerController : MonoBehaviour
{
	void Start()
	{
		Rigidbody2D rb = GetComponent<Rigidbody2D>();
	}
}
```

**If component is found:**  
Returns a reference to the component of type `T`
```csharp
Rigidbody rb = GetComponent<Rigidbody>(); 
// rb now contains a reference to the Rigidbody component
```
**If component is NOT found:**  
Returns `null`
```csharp
Rigidbody rb = GetComponent<Rigidbody>(); 
// rb will be null if no Rigidbody exists on the GameObject
```

### `CompareTag(String Tag)`
`Component.CompareTag()` internally just calls `GameObject.CompareTag()`:
```csharp
// Inside Component class
public bool CompareTag(string tag)
{
    return gameObject.CompareTag(tag);  // Calls GameObject's version
}
```
`CompareTag` returns `true` if the GameObject has the specified tag, `false` otherwise. It's the recommended way to check tags instead of using string comparison.


# Object

## Methods in Object
### Destroy()
`Destroy` removes a GameObject, component, or asset from the scene. The actual destruction happens **at the end of the current frame**, not immediately.
Has two signatures:
- `Destroy(obj)` - Destroys the object immediately (after the current Update loop)
- `Destroy(obj, t)` - Destroys the object after `t` seconds delay

### Instantiate()
`Instantiate()` is defined in the **Object base class**. *Returns a reference* to the newly created clone. Remember the inheritance chain:


- **Object** ← `Instantiate()` is defined HERE as a static method
    - **Component**
        - **Behaviour**
            - **MonoBehaviour**

Since it's a **static method**, you don't need an instance to call it—you can call it directly:​
```csharp
GameObject clone = Instantiate(prefab);
```
Or explicitly:
```csharp
GameObject clone = Object.Instantiate(prefab);
```
You can call `Object.Instantiate()` without typing `Object.` because C# allows you to access static members of base classes through derived class names. But under the hood, it's **always calling `Object.Instantiate()`**.

#### Method signatures:
```csharp
// Basic - clones at original's position/rotation
Object Instantiate(Object original)

// With position and rotation
Object Instantiate(Object original, Vector3 position, Quaternion rotation)

// With parent transform
Object Instantiate(Object original, Transform parent)

// With position, rotation, and parent
Object Instantiate(Object original, Vector3 position, Quaternion rotation, Transform parent)
```

# Components in `UnityEngine` namespace
## Transform
Inherits MonoBehaviour Class.
Stores and controls info of game Object position, rotation and scale

### Fields / Properties
#### localScale
You control the scale of a GameObject using the `transform.localScale` property. This property is a `Vector3` that represents the scale on the X, Y, and Z axes relative to the GameObject's parent.

`transform.localScale` is relative to the parent GameObject's scale. If the parent has a scale of (2, 2, 2) and the child has localScale of (0.5, 0.5, 0.5), the child appears at normal size in world space, i.e., (1,1,1).

```csharp
transform.localScale = new Vector3(-1,1,1);
```

### Methods in Transform:
#### Translate()
| Parameter   | Description                                              |
| ----------- | -------------------------------------------------------- |
| translation | The amount by which to move the Transform.               |
| relativeTo  | The coordinate system in which to apply the translation. |
If `relativeTo` is left out (or set to [Space.Self](https://docs.unity3d.com/ScriptReference/Space.Self.html)) the movement is applied relative to the transform's local axes. 
(the x, y and z axes shown when selecting the object inside the Scene View.) 

If `relativeTo` is [Space.World](https://docs.unity3d.com/ScriptReference/Space.World.html) the movement is applied relative to the world coordinate system.

```csharp
using UnityEngine;
using System.Collections;

public class ExampleClass : MonoBehaviour
{
	void update()
	{
		// Move object forward in Z axis by 1 unit
		transform.Translate(new Vector3(0,0,1) * Time.deltaTime);
		//this one will be relative to the local axis
		//OR can write:
		/*
			transform.Translate(0,0,1* Time.deltaTime);
		*/ 
		
		//Move the object realtive to world coordinates:
		transform.Translate(new Vector3(0,0,1)* Time.deltaTime, Space.World);
	}
}
```
(`Space` is an enum in Unity. It's part of the `UnityEngine` namespace and defines which coordinate system to use for operations like translation and rotation.)
#### Types of overloads/ parameters
- **Vector3-based overloads**
	- **`Translate(Vector3 translation)`** - Moves the transform by the amounts specified in the Vector3 (x, y, z components), relative to its own local space by default
	- **`Translate(Vector3 translation, Space relativeTo)`** - Same as above but you specify the coordinate system: `Space.Self` (local space, default) or `Space.World` (world space)
	- **`Translate(Vector3 translation, Transform relativeTo)`** - Moves relative to another GameObject's transform coordinate system, or if you pass `null`, it moves in world space
	
- **Float-based overloads**
	- **`Translate(float x, float y, float z)`** - Moves by individual x, y, and z amounts in local space​
	- **`Translate(float x, float y, float z, Space relativeTo)`** - Same as above but you specify `Space.Self` or `Space.World`
	- **`Translate(float x, float y, float z, Transform relativeTo)`** - Moves relative to another GameObject's transform or world space if `null`

#### Rotate()
`Transform.Rotate` is a method that applies rotation to a GameObject by adding degrees of rotation to its current rotation. 
*It rotates the object incrementally rather than setting an absolute rotation value.*

```csharp
transform.Rotate(0, 1, 0); // (X, Y, Z)
//Can Also use Vector3 in this 
Vector3 rotation = new Vector3(0, 90, 0); 
transform.Rotate(rotation * Time.deltaTime);
```

The rotation is applied in a specific order: Z axis first, then X axis, then Y axis. This order matters for 3D rotations due to gimbal lock considerations.
##### Space.Self vs Space.World
You can specify whether rotation happens in local space (relative to the object's own axes) or world space (relative to the scene's axes):

**Local Space (default):**
```csharp

// Rotates around the object's own local axes 
transform.Rotate(0, 90, 0, Space.Self);
```

**World Space:**
```csharp
// Rotates around the world's axes 
transform.Rotate(0, 90, 0, Space.World);
```
## Time
The `Time` class in Unity is a **static class** that provides an interface to access **time-related information** from the Unity engine.
It can provide info like time since the game was running, time between two frame, or any other time related info needed for the game

### Fields
#### deltaTime
`Time.deltaTime` is a property that returns the interval in seconds from the last frame to the current one.
I.e., it gives info on time between two frames.

### Methods

## Colliders in Unity
**Colliders** are components in Unity that define the physical shape of GameObjects for collision detection and physics interactions.

Types of Colliders in Unity:
- **3D Primitive Colliders (most efficient):**
	- **Box Collider**: Cube-shaped, ideal for walls, crates, rectangular objects
	- **Sphere Collider**: Ball-shaped, most efficient for round objects
	- **Capsule Collider**: Pill-shaped, commonly used for characters

- **2D Colliders:**
	- **Box Collider 2D**: Rectangular shape for 2D games
	- **Circle Collider 2D**: Round shape for 2D games
	- **Capsule Collider 2D**: Rounded rectangle for 2D characters
	- **Polygon Collider 2D**: Custom shapes following sprite outlines
	- **Edge Collider 2D**: Line-based colliders for platforms

- **Complex Colliders (more expensive):**
	- **Mesh Collider**: Matches exact mesh geometry, but computationally expensive—avoid when possible

### The `Collider` Class
The **Collider** and **Collider2D** classes are the base classes for all collider types in Unity, providing the core functionality that all specific colliders inherit from.

- `Object` → `Component` → `Collider` (for 3D)
- `Object` → `Component` → `Collider2D` (for 2D)

Specific collider types like BoxCollider, SphereCollider, and MeshCollider inherit from the `Collider` base class. Similarly, BoxCollider2D, CircleCollider2D, etc., inherit from `Collider2D`.

### Attributes in `Collider` (for 3D)
- **`isTrigger`**: Boolean that configures whether the collider acts as a trigger (no physical collision) or a solid collider

- **`enabled`**: Whether the collider is active—disabled colliders don't participate in collisions

### Methods in `Collider` (for 3D)
#### `ClosestPoint(Vector3 position)`
Returns the closest point on the collider to a given position​

#### **`ClosestPointOnBounds(Vector3 position)`**
Returns the closest point on the bounding box

#### **`Raycast(Ray ray, out RaycastHit hitInfo, float maxDistance)`**
Casts a ray that only detects this specific collider​

#### **`GetGeometry()`** 
Returns the geometric shape data of the collider

### Compound Colliders

You can combine multiple primitive colliders to approximate complex shapes more efficiently than using a single Mesh Collider. 
Create child GameObjects with individual colliders, then add a Rigidbody to the parent. This approach balances accuracy and performance.

### Collider Setup

Colliders behave differently based on Rigidbody setup:
- **Static Collider**: Collider without Rigidbody—used for immovable level geometry like walls and floors
- **Rigidbody Collider**: Collider with Rigidbody—moves and responds to physics forces
- **Kinematic Rigidbody Collider**: Collider with kinematic Rigidbody—controlled by scripts, not physics

### Collider Event Methods

```csharp
void OnCollisionEnter(Collision collision) {
    // Called when collision starts
    Debug.Log("Hit: " + collision.collider.name);
}

void OnCollisionStay(Collision collision) {
    // Called every frame during collision
}

void OnCollisionExit(Collision collision) {
    // Called when collision ends
}
```
```csharp
//For 2D
void OnCollisionEnter2D(Collision2D collision) {
    // Called when collision starts
    Debug.Log("Hit: " + collision.collider.name);
}

void OnCollisionStay2D(Collision2D collision) {
    // Called every frame during collision
}

void OnCollisionExit2D(Collision2D collision) {
    // Called when collision ends
}
```

These are also [[#The Magic Methods in MonoBehaviour]].

### `Collision` / `Collision2D`

**Collision** (and **Collision2D**) is a data container class that Unity creates to describe collision details. It's **not a Component**—it's a plain C# class that exists only to hold information about a specific collision event.

Summary:
- Inherits from: Nothing (it's a standalone class, not part of the Unity Object hierarchy)
- Purpose: Package collision data to pass to your OnCollision callbacks

#### Structure of Collision Class
The Collision/Collision2D class contains:

**References to the colliding objects:**
- `gameObject` - The GameObject you collided with
- `collider` - The specific Collider component involved
- `rigidbody` - The Rigidbody of the other object (null if none)
- `transform` - The Transform of the other object

**Physics data:**
- `relativeVelocity` - The velocity difference between the two objects
- `impulse` - The total impulse applied to resolve the collision​
- `contactCount` - Number of contact points​
- `contacts` - Array of ContactPoint structs with detailed geometry

A curious question: [[How Collision Data is Collected and Passed]]

## Trigger in Unity (Part of [[#Colliders in Unity]]):
A **trigger** in Unity is a special type of collider that detects when objects enter, stay in, or exit its volume without causing physical collisions.

**To create a trigger**, add a Collider component to a GameObject and check the **"Is Trigger"** checkbox.

**Requirements for trigger detection:**​
- At least one of the objects must have a **Rigidbody** component
- Both objects must have **Collider** components
- One collider must have "Is Trigger" enabled

### Trigger Event Methods
Whenever a trigger happens, we have three methods just like how we have for colliders:
```csharp
void OnTriggerEnter(Collider other) {
    // Called when another object enters the trigger
    Debug.Log(other.gameObject.name + " entered");
}

void OnTriggerStay(Collider other) {
    // Called every frame while object remains inside
}

void OnTriggerExit(Collider other) {
    // Called when the object leaves the trigger
}
```
```csharp
// For 2D
void OnTriggerEnter2D(Collider2D other) {
    // Called when another object enters the trigger
    Debug.Log(other.gameObject.name + " entered");
}

void OnTriggerStay2D(Collider2D other) {
    // Called every frame while object remains inside
}

void OnTriggerExit2D(Collider2D other) {
    // Called when the object leaves the trigger
}
```
These are [[#The Magic Methods in MonoBehaviour]]

## Rigidbody
Add physics in unity to the gameObject. 

### Attributes

| Syntax               | Type  | Working                                                                                          |
| -------------------- | ----- | ------------------------------------------------------------------------------------------------ |
| `rb.angularVelocity` | float | **property** (not a method) that controls rotational speed of a Rigidbody in radians per second. |


### Method:

#### AddRelativeForce()
`AddRelativeForce` applies force to a Rigidbody in its **local coordinate system** instead of world space.[^1][^2]

##### Syntax
```csharp
// Vector3 version
rb.AddRelativeForce(Vector3 force);
rb.AddRelativeForce(Vector3 force, ForceMode mode);

// Individual components
rb.AddRelativeForce(float x, float y, float z);
rb.AddRelativeForce(float x, float y, float z, ForceMode mode);
```

##### Example - Rocket/Vehicle Movement
```csharp
using UnityEngine;

public class RocketMovement : MonoBehaviour 
{
    public float thrust = 10f;
    private Rigidbody rb;
    
    void Start() 
    {
        rb = GetComponent<Rigidbody>();
    }
    
    void FixedUpdate() 
    {
        // Push forward in local space (always moves in object's forward direction)
        rb.AddRelativeForce(Vector3.forward * thrust);
        
        // Push upward in local space
        rb.AddRelativeForce(Vector3.up * thrust);
        
        // Or use individual components
        rb.AddRelativeForce(0, 0, thrust); // x, y, z in local space
    }
}
```

##### Key Difference from AddForce
- `AddForce`: Uses **world space** - `Vector3.forward` always means global Z-axis[^2]
- `AddRelativeForce`: Uses **local space** - `Vector3.forward` means object's forward direction regardless of rotation

### Fields:
#### useGravity
A boolean value to turn gravity on or off in rigidbody3D

# Structs in `UnityEngine` Namespace
## Vector3
`Vector3` is a **struct** in the `UnityEngine` namespace that represents 3D vectors and points.
`Vector3` is a **value type** (struct, not a class) containing three float components: `x`, `y`, and `z`. This means it's stored on the stack and copied by value rather than by reference.

```csharp
Vector3 position = new Vector3(5f, 2f, 10f);  // x=5, y=2, z=10
Vector3 flat = new Vector3(3f, 0f);  // x=3, y=0, z=0 (z defaults to 0)
```
### Attributes
```csharp
float xValue = position.x;
float yValue = position.y;
float zValue = position.z;

// Can also use indexer
float xValue = position[0];  // x
float yValue = position[1];  // y
float zValue = position[2];  // z

Vector3.zero       // (0, 0, 0)
Vector3.one        // (1, 1, 1)
Vector3.up         // (0, 1, 0)
Vector3.down       // (0, -1, 0)
Vector3.left       // (-1, 0, 0)
Vector3.right      // (1, 0, 0)
Vector3.forward    // (0, 0, 1)
Vector3.back       // (0, 0, -1)

//magnitude of a vector
Vector3 vec = new Vector3(3, 4, 0);
float length = vec.magnitude;  // Returns 5

//Normalized Vector: Unity vector in the same direction
Vector3 direction = new Vector3(10, 0, 0);
Vector3 unit = direction.normalized;  // (1, 0, 0)


```
### Methods
#### Distance between points:
```csharp
fload distance = Vector3.Distance(pointA, pointB);
```

#### Lerp(linear interpolation)
```csharp
Vector3 current = Vector3.Lerp(start, end, 0.5f);  // 50% between start and end
```

#### MoveTowards
Returns a vector that is closer to the given target (`target.position`) from the starting point (`transform.position`) by given units (`speed * Time.deltaTime`).
```csharp
void Update() 
{
    transform.position = Vector3.MoveTowards(
        transform.position, 
        target.position, 
        speed * Time.deltaTime
    );
}
```

# Ways to do some stuff:
## Managing movements and collision

## Taking user input (Both 2D and 3D)
There are two ways of taking input in unity:
- [[The Legacy Input System]]
- [[The New Input System]]

### The Difference?
**Both the old and new Input Systems rely on device polling at the C++ engine level**. The hardware still needs to be checked every frame—there's no way around this physical requirement. 
The difference is in **how your C# code interacts with that polled data**.​

NOTE: Device polling is a method used in computing and system design where a central controller or computer periodically checks the status of connected devices to determine if they have data to report or require attention

#### Old Input System (Poll-Based in C#)
With the old system, **you** had to poll in your `Update()` method:
```csharp
void Update() 
{     
	if (Input.GetKeyDown(KeyCode.Space)) 
	// You poll every frame    
	{        
		Jump();    
	} 
}
```
This means your code is **actively checking** the input state every frame, even when nothing is happening.​

#### New Input System (Event-Driven Option)
The new Input System **still polls devices at the hardware level**, but it gives you an **event-driven approach** as an option. The system checks for state changes and only calls your methods when input actually occurs:​
```csharp
void Awake() 
{     
	jumpAction.performed += OnJump; // Subscribe once 
} 

void OnJump(InputAction.CallbackContext context) 
{     
	Jump(); // Only called when jump actually happens 
}
```

**However**, the new Input System **also supports polling** if you prefer it:[unity3d+1](https://docs.unity3d.com/Packages/com.unity.inputsystem@1.4/manual/Actions.html)​
```csharp
void Update() 
{     
	if (jumpAction.WasPressedThisFrame()) 
	// New system polling    
	{        
		Jump();    
	}         	
	// Or continuous value reading    
	Vector2 moveValue = moveAction.ReadValue<Vector2>(); 
}
```

#### The Key Difference
**Old System:** You must poll in `Update()`[stackoverflow+1](https://stackoverflow.com/questions/79330015/unity-new-input-system-generating-multiple-events)​
**New System:** You can choose:[unity3d+1](https://docs.unity3d.com/Packages/com.unity.inputsystem@1.8/manual/RespondingToActions.html)​
- **Event-driven**: Subscribe to callbacks, no polling in your code (better performance)[zerotomastery+1](https://zerotomastery.io/blog/unity-new-input-system/)​
- **Polling**: Use `WasPressedThisFrame()`, `ReadValue<T>()` in `Update()` (simpler for continuous input like movement)[unity3d+1](https://docs.unity3d.com/Packages/com.unity.inputsystem@1.4/manual/Actions.html)​

The Checking Still Happens - But Not In Your Code
**Both approaches check every frame**, but here's the critical difference:
- **Event-Driven (New Input System):**
```text
Unity's Input Update (before Update()):
├─ Input System checks ALL enabled InputActions once
├─ Compares device states to previous frame
├─ If changed → Fire callbacks for affected actions only
└─ Your callback runs only if something changed

Your Update():
└─ (nothing related to input here)
```

- **Polling (Old System or New System Polling):**
```text
Unity's Input Update:
└─ Device states updated (same as above)

Your Update():
├─ Check if jump pressed
├─ Check if fire pressed  
├─ Check if reload pressed
├─ Check if crouch pressed
└─ (repeat for every possible input)
```

The term "event-based instead of poll-based" in the documentation means the new system gives you **event-driven C# code as an option**, not that it eliminated hardware polling entirely. 
The C++ engine still polls devices every frame in both systems—that's unavoidable—but the new system lets you write **event-driven game logic** that consumes fewer resources because your code only runs when input changes.​



## Making player jump

## Making player crouch 

## Making player slide 

## Making player stick to walls

---
# Utilities in Unity
## Cinemachine
**Controls camera** in unity. Gives ways to add stuff like camera shakes, camera order control, aim assist, etc,. 
Cinemachine doesn't create new physical cameras—instead, it uses **virtual cameras** (CinemachineCameras) that control your scene's single Unity Camera. 
Think of it like a TV director managing multiple camera angles.

### Classes:
#### Cinemachine Camera (`CinemachineCamera`)
Inherits `CinemachineVirtualCameraBase`, and `CinemachineVirtualCameraBase` inherits `MonoBehaviour, ICinemachineCamera`.

##### Fields 
# **Procedural Components:**

	Position Control and Rotation Control are **separate systems** that each have their own **algorithm** (method) for controlling their part of the camera transform.[^1][^5]

## How It Works

Each algorithm has a **specific way** of calculating what value to set:

### Position Control Algorithms (Body)

| Algorithm               | Method                                                               |
| :---------------------- | :------------------------------------------------------------------- |
| **Position Composer**   | Keeps target in frame using **screen position rules + damping** [^4] |
| **Orbital Follow**      | Maintains **fixed distance + orbit angle** around target [^7]        |
| **Follow**              | Maintains **fixed offset** from target in world/local space          |
| **Hard Lock to Target** | Sets position = target position (no offset) [^10]                    |
| **Spline Dolly**        | Position based on **spline path percentage**                         |

### Rotation Control Algorithms (Aim)

| Algorithm                 | Method                                                                         |
| :------------------------ | :----------------------------------------------------------------------------- |
| **Rotation Composer**     | Calculates rotation to keep target in **screen space zone + damping** [^4][^6] |
| **Hard Look At**          | Calculates rotation to **point directly at target** [^1]                       |
| **Pan Tilt**              | Sets rotation based on **user input values** [^1]                              |
| **Same As Follow Target** | Copies **target's rotation quaternion** [^1]                                   |
| **None**                  | Does nothing - rotation set by **external source** [^1]                        |

## The Pattern

Each algorithm answers the question: **"How do I calculate the transform value I control?"**

- **Hard Look At**: "Calculate the rotation quaternion that points from camera to target"[^1]
- **Rotation Composer**: "Calculate rotation to keep target in screen zone with smoothing"[^4][^6]
- **Orbital Follow**: "Calculate position at radius R, angle θ from target"[^7]
- **Hard Lock**: "Just use target's position"[^10]

Position algorithms compute **Vector3 position**, rotation algorithms compute **Quaternion rotation**.

and then each mode has a component that it requires to get added for these modes to work as intended:
#### Cinemachine Follow
It makes the main camera follow the tracking target given in [[#Cinemachine Camera (`CinemachineCamera`)]].

#### Cinemachine Hard Lock To Target
When we want the camera to be inside the tracking target given in [[#Cinemachine Camera (`CinemachineCamera`)]]



##### Methods 

---
# Stuff in Unity
# Audio System in Unity

Unity's audio system has **two main components**: AudioListener (the ear) and AudioSource (the speaker).

## Core Concept

- **AudioListener** = Your ears (microphone) - receives sound
- **[AudioSource](AudioSource%20in%20Unity)** = Speaker - plays sound
- **AudioClip** = The actual sound file (mp3, wav, ogg)

Only **1 AudioListener** per scene, usually on Main Camera. Multiple AudioSources can exist.

## Class Hierarchy

```csharp
// Both inherit from Behaviour → Component → Object
UnityEngine.Object
    └── Component
        └── Behaviour
            ├── AudioListener    // Component class
            └── AudioSource      // Component class

// AudioClip is separate - it's an asset, not a component
UnityEngine.Object
    └── AudioClip               // Asset class (not a component)
```

**AudioListener** and **AudioSource** are **Components** (inherit from `Behaviour`) that attach to GameObjects. **AudioClip** is an **asset** you assign to AudioSource.[^1][^3]


## AudioSource Component Properties

```csharp
// Common properties
audioSource.clip = myAudioClip;       // The sound to play
audioSource.volume = 0.5f;            // 0 to 1
audioSource.pitch = 1.0f;             // Speed/pitch
audioSource.loop = true;              // Repeat sound
audioSource.playOnAwake = false;      // Auto-play on start
audioSource.spatialBlend = 1.0f;      // 0 = 2D, 1 = 3D spatial
audioSource.isPlaying;                // Tells if the audio is playing

// Play methods
audioSource.Play();                   // Play clip
audioSource.PlayOneShot(clip);        // Play without interrupting current
audioSource.Stop();                   // Stop playing
audioSource.Pause();                  // Pause
```


### Basic Setup

```csharp
using UnityEngine;

public class SoundExample : MonoBehaviour 
{
    public AudioClip jumpSound;      // Assign in Inspector
    private AudioSource audioSource;
    
    void Start() 
    {
        // Get AudioSource component on this GameObject
        audioSource = GetComponent<AudioSource>();
        
        // Or add one if missing
        if (audioSource == null) 
        {
            audioSource = gameObject.AddComponent<AudioSource>();
        }
    }
    
    void Update() 
    {
        if (Input.GetKeyDown(KeyCode.Space)) 
        {
            // Play the sound
            audioSource.PlayOneShot(jumpSound);
        }
    }
}
```



### Simple Example - Background Music

```csharp
using UnityEngine;

public class MusicPlayer : MonoBehaviour 
{
    void Start() 
    {
        AudioSource music = GetComponent<AudioSource>();
        music.loop = true;           // Keep playing
        music.playOnAwake = true;    // Auto-start
        music.volume = 0.3f;         // Quiet background
        music.spatialBlend = 0f;     // 2D sound (no 3D positioning)
    }
}
```


### Example - 3D Sound Effect

```csharp
using UnityEngine;

public class Footsteps : MonoBehaviour 
{
    public AudioClip walkSound;
    private AudioSource audioSource;
    
    void Start() 
    {
        audioSource = gameObject.AddComponent<AudioSource>();
        audioSource.clip = walkSound;
        audioSource.spatialBlend = 1f;        // Full 3D
        audioSource.minDistance = 1f;         // Full volume within 1 unit
        audioSource.maxDistance = 20f;        // Silent beyond 20 units
        audioSource.loop = true;
    }
}
```


## AudioListener

```csharp
// Automatically on Main Camera - usually don't touch it
// Only 1 per scene or you get errors

// If needed, move it:
AudioListener listener = Camera.main.GetComponent<AudioListener>();
```

AudioListener has **no properties** - just add it and it works. It "listens" for all AudioSources and plays them through speakers based on distance.

# SceneManager

**SceneManager** is a **static class** in Unity that controls scene loading, unloading, and management at runtime.

## Namespace \& Class Structure

```csharp
// Namespace
UnityEngine.SceneManagement

// Class hierarchy
System.Object
    └── SceneManager    // Static class (NOT a Component)
```

**SceneManager is NOT a Component** - it's a **static utility class**. You don't attach it to GameObjects or inherit from it. All methods are **static** so you call them directly.

## Required Namespace

```csharp
using UnityEngine;               // GameObject, MonoBehaviour, etc.
using UnityEngine.SceneManagement;  // SceneManager

public class MyScript : MonoBehaviour 
{
    // Now you can use SceneManager
}
```

Must add `using UnityEngine.SceneManagement;` at the top.

## Methods

```csharp
// Load scene (replaces current scene)
SceneManager.LoadScene("SceneName");
SceneManager.LoadScene(0);  // By build index

// Load scene additively (keeps current scene)
SceneManager.LoadScene("SceneName", LoadSceneMode.Additive);

// Unload scene
SceneManager.UnloadSceneAsync("SceneName");

// Get active scene
Scene currentScene = SceneManager.GetActiveScene();

// Get scene count
int sceneCount = SceneManager.sceneCount;
```

| Type       | Name                              | Description                             |
| ---------- | --------------------------------- | --------------------------------------- |
| **Method** | `LoadScene(string name)`          | Load scene by name ​                    |
| **Method** | `LoadScene(int index)`            | Load scene by build index ​             |
| **Method** | `LoadSceneAsync(string name)`     | Load scene asynchronously ​             |
| **Method** | `UnloadSceneAsync(string name)`   | Unload scene asynchronously ​           |
| **Method** | `GetActiveScene()`                | Returns currently active Scene ​        |
| **Method** | `SetActiveScene(Scene scene)`     | Set which Scene is active ​             |
| **Method** | `GetSceneAt(int index)`           | Get Scene at index from loaded scenes ​ |
| **Method** | `GetSceneByName(string name)`     | Find Scene by name ​                    |
| **Method** | `GetSceneByPath(string path)`     | Find Scene by file path ​               |
| **Method** | `GetSceneByBuildIndex(int index)` | Find Scene by build index ​             |
| **Method** | `CreateScene(string name)`        | Create new empty Scene at runtime       |

## Attributes

| Type         | Name                        | Description                         |
| ------------ | --------------------------- | ----------------------------------- |
| **Property** | `sceneCount`                | Number of currently loaded scenes ​ |
| **Property** | `sceneCountInBuildSettings` | Total scenes in Build Settings      |

## Example

Example - Main Menu to Game

```csharp
using UnityEngine;
using UnityEngine.SceneManagement;

public class MainMenu : MonoBehaviour 
{
    // Call from button
    public void PlayGame() 
    {
        SceneManager.LoadScene("GameScene");
    }
    
    public void QuitGame() 
    {
        Application.Quit();
    }
}
```

Example - Restart Level

```csharp
using UnityEngine;
using UnityEngine.SceneManagement;

public class GameManager : MonoBehaviour 
{
    void Update() 
    {
        if (Input.GetKeyDown(KeyCode.R)) 
        {
            // Reload current scene
            SceneManager.LoadScene(SceneManager.GetActiveScene().name);
            
            // Or by index
            SceneManager.LoadScene(SceneManager.GetActiveScene().buildIndex);
        }
    }
}
```

Example - Load Next Scene

```csharp
using UnityEngine;
using UnityEngine.SceneManagement;

public class LevelComplete : MonoBehaviour 
{
    public void LoadNextLevel() 
    {
        // Get current scene index
        int currentIndex = SceneManager.GetActiveScene().buildIndex;
        
        // Load next scene
        SceneManager.LoadScene(currentIndex + 1);
    }
}
```


## LoadSceneMode Options

```csharp
// Single mode (default) - replaces current scene
SceneManager.LoadScene("Level2", LoadSceneMode.Single);

// Additive mode - keeps current scene, adds new one
SceneManager.LoadScene("UIScene", LoadSceneMode.Additive);
```


## Important Setup

**Must add scenes to Build Settings**:

1. File → Build Settings
2. Drag scenes from Project window to "Scenes In Build" list
3. Each scene gets an index number (0, 1, 2...)

Without this, `LoadScene` throws errors.[^5]

## Scene Class

```csharp
using UnityEngine;
using UnityEngine.SceneManagement;

public class SceneInfo : MonoBehaviour 
{
    void Start() 
    {
        Scene currentScene = SceneManager.GetActiveScene();
        
        Debug.Log("Scene name: " + currentScene.name);
        Debug.Log("Scene index: " + currentScene.buildIndex);
        Debug.Log("Scene path: " + currentScene.path);
        Debug.Log("Root objects: " + currentScene.rootCount);
    }
}
```

### Attributes
|Type|Name|Description|
|---|---|---|
|**Property**|`name`|Name of the Scene ​|
|**Property**|`path`|File path of the Scene asset ​|
|**Property**|`buildIndex`|Index in Build Settings (-1 if not in build) ​|
|**Property**|`rootCount`|Number of root GameObjects in Scene ​|
|**Property**|`isLoaded`|Whether Scene is loaded ​|
|**Property**|`isDirty`|Whether Scene has unsaved changes

### Methods 
| Type       | Name                   | Description                         |
| ---------- | ---------------------- | ----------------------------------- |
| **Method** | `GetRootGameObjects()` | Returns array of root GameObjects ​ |
| **Method** | `IsValid()`            | Whether Scene reference is valid    |

## Async Loading (Advanced)

```csharp
using UnityEngine;
using UnityEngine.SceneManagement;
using System.Collections;

public class LoadSceneAsync : MonoBehaviour 
{
    public void LoadLevel() 
    {
        StartCoroutine(LoadAsyncScene());
    }
    
    IEnumerator LoadAsyncScene() 
    {
        AsyncOperation asyncLoad = SceneManager.LoadSceneAsync("HeavyScene");
        
        // Wait until scene fully loaded
        while (!asyncLoad.isDone) 
        {
            Debug.Log("Loading: " + asyncLoad.progress);
            yield return null;
        }
    }
}
```


# Particle

**Particle System** is a Component in Unity that creates visual effects like fire, smoke, explosions, magic spells, and more.

## Namespace & Class Hierarchy

```csharp
// Namespace
UnityEngine

// Class hierarchy
System.Object
    └── UnityEngine.Object
        └── Component
            └── ParticleSystem  // Component (NOT MonoBehaviour)
```

**ParticleSystem is a Component** that inherits directly from `Component`, not `MonoBehaviour`. You attach it to GameObjects like any other component.

## Attributes
|Property|Type|Description|
|---|---|---|
|`main.startLifetime`|float|How long each particle lives in seconds unity3d+1​|
|`main.startSpeed`|float|Initial speed of particles unity3d+1​|
|`main.startSize`|float|Initial size of particles unity3d+1​|
|`main.startRotation`|float|Initial rotation angle of particles unity3d+1​|
|`main.startColor`|Color|Initial color of particles unity3d+1​|
|`main.gravityModifier`|float|Scales gravity effect on particles unity3d+1​|
|`main.maxParticles`|int|Maximum number of particles allowed unity3d+1​|
|`main.loop`|bool|Whether system loops continuously unity3d+1​|
|`main.playOnAwake`|bool|Auto-play when scene starts unity3d+1​|
|`main.duration`|float|Length of one cycle in seconds [unity3d](https://docs.unity3d.com/ScriptReference/ParticleSystem.html)​|
|`main.simulationSpace`|ParticleSystemSimulationSpace|Local or world space simulation unity3d+1​|
|`emission.enabled`|bool|Enable/disable particle emission unity3d+1​|
|`emission.rateOverTime`|float|Particles emitted per second unity3d+1​|
|`shape.shapeType`|ParticleSystemShapeType|Shape of emission volume (Sphere, Cone, Box, etc.) learn.unity+2​|
|`shape.radius`|float|Radius for sphere/cone shapes learn.unity+1​|
|`shape.angle`|float|Cone angle at its point learn.unity+1​|
|`isPlaying`|bool|Whether system is currently playing [unity3d](https://docs.unity3d.com/6000.3/Documentation/ScriptReference/ParticleSystem.html)​|
|`isPaused`|bool|Whether system is paused [unity3d](https://docs.unity3d.com/6000.3/Documentation/ScriptReference/ParticleSystem.html)​|
|`particleCount`|int|Current number of particles [unity3d](https://docs.unity3d.com/6000.3/Documentation/ScriptReference/ParticleSystem.html)​|
|`time`|float|Playback position in seconds [unity3d](https://docs.unity3d.com/6000.3/Documentation/ScriptReference/ParticleSystem.html)​|

## Methods 
| Method                                                             | Return Type | Description                                                                                                                               |
| ------------------------------------------------------------------ | ----------- | ----------------------------------------------------------------------------------------------------------------------------------------- |
| `Play()`                                                           | void        | Start emitting particles                                                                                                                  |
| `Play(bool withChildren)`                                          | void        | Start with option to include child systems                                                                                                |
| `Stop()`                                                           | void        | Stop emitting particles ​                                                                                                                 |
| `Stop(bool withChildren, ParticleSystemStopBehavior stopBehavior)` | void        | Stop with control over children and behavior ​                                                                                            |
| `Pause()`                                                          | void        | Pause the particle system ​                                                                                                               |
| `Pause(bool withChildren)`                                         | void        | Pause with option for children [unity3d](https://docs.unity3d.com/6000.3/Documentation/ScriptReference/ParticleSystem.html)​              |
| `Clear()`                                                          | void        | Remove all particles immediately reddit+1​                                                                                                |
| `Clear(bool withChildren)`                                         | void        | Clear with option for children [unity3d](https://docs.unity3d.com/6000.3/Documentation/ScriptReference/ParticleSystem.html)​              |
| `Emit(int count)`                                                  | void        | Emit specific number of particles instantly [unity3d](https://docs.unity3d.com/6000.3/Documentation/ScriptReference/ParticleSystem.html)​ |
| `Simulate(float t)`                                                | void        | Fast-forward particle system by t seconds [unity3d](https://docs.unity3d.com/6000.3/Documentation/ScriptReference/ParticleSystem.html)​   |
| `IsAlive()`                                                        | bool        | Check if system has any active particles [unity3d](https://docs.unity3d.com/6000.3/Documentation/ScriptReference/ParticleSystem.html)​    |
| `IsAlive(bool withChildren)`                                       | bool        | Check if system or children have particles [unity3d](https://docs.unity3d.com/6000.3/Documentation/ScriptReference/ParticleSystem.html)​  |
| `GetParticles(ParticleSystem.Particle[] particles)`                | int         | Get current particle array for manipulation [unity3d](https://docs.unity3d.com/6000.3/Documentation/ScriptReference/ParticleSystem.html)​ |
| `SetParticles(ParticleSystem.Particle[] particles, int size)`      | void        | Set particle array after manipulation [unity3d](https://docs.unity3d.com/6000.3/Documentation/ScriptReference/ParticleSystem.html)​       |
| `TriggerSubEmitter(int index)`                                     | void        | Manually trigger a sub-emitter [unity3d](https://docs.unity3d.com/6000.3/Documentation/ScriptReference/ParticleSystem.html)​              |