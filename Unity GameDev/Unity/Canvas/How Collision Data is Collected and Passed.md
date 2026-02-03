## Step 1: Physics Simulation (C++ Engine)

Unity's physics engine (PhysX - written in C++) runs during the physics step. It:
1. Detects all overlapping non-trigger colliders in the scene
2. Calculates collision geometry (contact points, normals, penetration depth)
3. Applies physics forces to resolve the collision (impulses, friction)
4. Stores all this collision data in internal C++ structures

## Step 2: Collision Object Creation (C++ to C# Bridge)
After physics simulation completes, Unity's engine:
1. **Creates a Collision object** for each collision pair
2. **Populates it** with data from the C++ physics simulation:
    - References to the colliders involved
        
    - Relative velocity calculated by physics
        
    - Impulse forces that were applied
        
    - Contact point geometry (positions, normals, depths)
        
3. **Wraps C++ data** in C# Collision class instances

## Step 3: Callback Invocation (MonoBehaviour Magic Methods)

Unity then automatically calls OnCollision methods on **MonoBehaviour scripts** attached to both GameObjects involved in the collision:[](https://stackoverflow.com/questions/67553565/what-decides-the-calling-order-of-oncollisionenterother-in-unity)​
```csharp
void OnCollisionEnter(Collision collision)  
{     
	// Unity passes the Collision object as parameter    
	// You receive all the data collected by physics engine 
}
```

The physics engine scans all MonoBehaviour components on the colliding GameObjects, and if it finds OnCollisionEnter/Stay/Exit methods, it invokes them with the Collision object as the parameter.​