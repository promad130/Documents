Comes under [[SceneManager]],

**`SceneManager.LoadScene`** loads a new scene by name or build index, replacing the current scene.

Usage:
```csharp
using UnityEngine.SceneManagement;

// Load by name
SceneManager.LoadScene("ChessGameScene");

// Load by build index
SceneManager.LoadScene(2);
```

How it works:
- Unloads all GameObjects from the current scene.
- Loads assets and GameObjects of the specified scene.
- Sets the newly loaded scene as active.

Requirements:
- The target scene must be added to **File → Build Settings**.

That’s it—one line to switch scenes.