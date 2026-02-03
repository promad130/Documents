React Native uses Flexbox layout by default. Think of it like organizing items in a box:

- **Main axis** = direction items flow (`flexDirection`)  
- **Cross axis** = perpendicular direction

# Padding
## Padding Horizontal

# Margin


# Aligning Item

## alignItems
`alignItems` controls how child elements align along the **cross axis** of **their** container. 
The cross axis is perpendicular to the main axis—
	if your content flows vertically (column), `alignItems` controls horizontal alignment; 
	if content flows horizontally (row), it controls vertical alignment.

###  Values

When the main axis is Horizontal:

![[Pasted image 20260110021731.png]]


## justifyContent

`justifyContent` controls how child elements align along the **main axis** of **their** container. 
This is the opposite of `alignItems`—
	while `alignItems` works on the cross axis, `justifyContent` works on the main axis
	while `alignItems` works on the main axis, `justifyContent` works on the cross axis

### Values
When the `alignItem` works on cross axis, i.e., vertical axis.

![[Pasted image 20260110021848.png]]

- **`space-between`** - Distributes items evenly with the first item at the start and last item at the end, with equal space between items
- **`space-around`** - Distributes items with equal space around each item (half-size space at the edges)
- **`space-evenly`** - Distributes items with completely equal spacing everywhere, including edges


# Colors

![[Pasted image 20260110021800.png]]


