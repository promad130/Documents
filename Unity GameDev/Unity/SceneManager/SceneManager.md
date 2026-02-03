**`SceneManager`** (in `UnityEngine.SceneManagement`) handles loading and unloading scenes at runtime.

Key methods:

csharp

`using UnityEngine.SceneManagement;`

1. [[SceneManager.LoadScene()]]  
    Replaces current scene.
```csharp
SceneManager.LoadScene("SceneName");
```
    
2. **LoadSceneAsync**  
    Loads without freezing.
    
    csharp
    
    `SceneManager.LoadSceneAsync("SceneName");`
    
3. **LoadScene (Additive)**  
    Adds a scene on top.
    
    csharp
    
    `SceneManager.LoadScene("OverlayUI", LoadSceneMode.Additive);`
    
4. **UnloadSceneAsync**  
    Removes a scene.
    
    csharp
    
    `SceneManager.UnloadSceneAsync("SceneName");`
    
5. **GetActiveScene** / **SetActiveScene**
    
    csharp
    
    `var active = SceneManager.GetActiveScene(); SceneManager.SetActiveScene(active);`
    

Requirements:

- Scenes must be in **File → Build Settings**.
    

That’s it—control scene transitions with these simple calls.