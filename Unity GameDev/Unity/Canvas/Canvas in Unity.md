The **Canvas** is the root component for all UI elements in Unity.
There are three main **Render Modes** for Canvas (in Canvas Component):

- **Screen Space - Overlay**: UI renders on top of everything, based on screen pixels.
- **Screen Space - Camera**: UI renders through a specified camera.
- **World Space**: UI is part of the 3D world.

# Canvas Scaler:
This component controls how UI scales across different resolutions.


# UI Elements within a canvas
Unity's anchoring system by default calculates the position of elements from the center of the canvas, if set to `Screen Space - Overlay`, then from the center of the screen itself. However this can be changed using `RectTransform`, but the position we set initially becomes permanent, 
i.e., UI elements will then be placed on that particular pixel coordinates, irrespective of the screen resolution.
Hence this causes troubles like these:
![[Pasted image 20250819182313.png]]
![[Pasted image 20250819182327.png]]

So it is important to know how does the placement of UI elements works?

## 1. RectTransform and Scale
Whenever a UI element is created, `RectTransfrom` component is always attached to it. This is what determines how the element is actually placed or scaled within the canvas.
- UI elements use a **RectTransform** component instead of a regular [Transform](Transform%20in%20unity).
- RectTransform manages position, size (width & height), [anchors](Anchors%20in%20Unity%20Canvas.md), and [pivots](Pivots%20in%20Unity%20Canvas).
- The **Scale** property in RectTransform behaves like a 3D object scale but is usually kept at (1,1,1) to avoid distortion.
- Changing the **scale** of UI elements is possible but not recommended for resizing UI; use width and height instead.


# Render Modes:
## Screen space - overlay, Camera, 
## World Space