<img src="https://r2cdn.perplexity.ai/pplx-full-logo-primary-dark%402x.png" style="height:64px;margin-right:32px"/>

# Java GUI Classes and Their Basic Functions

## Overview

Java provides two main toolkits for creating graphical user interfaces:

- **AWT (Abstract Window Toolkit)** - `java.awt` package - heavyweight, platform-dependent components
- **Swing** - `javax.swing` package - lightweight, platform-independent components


## Core Class Hierarchy

### 1. **Component (java.awt.Component)**

Base class for all GUI elements

- `setSize(int width, int height)` - Sets component size
- `setVisible(boolean b)` - Controls visibility
- `setBackground(Color c)` - Sets background color
- `setForeground(Color c)` - Sets text/foreground color
- `addMouseListener()` - Handles mouse events
- `addKeyListener()` - Handles keyboard events
- `repaint()` - Redraws the component
- `paint(Graphics g)` - Custom drawing method


### 2. **Container (java.awt.Container)**

Extends Component, can hold other components

- `add(Component c)` - Adds a component
- `remove(Component c)` - Removes a component
- `setLayout(LayoutManager mgr)` - Sets layout manager
- `getComponents()` - Returns array of child components


### 3. **JFrame (javax.swing.JFrame)**

Main window for GUI applications

- `setTitle(String title)` - Sets window title
- `setDefaultCloseOperation(int operation)` - Sets close behavior
- `setSize(int width, int height)` - Sets window size
- `setResizable(boolean resizable)` - Controls resizing
- `pack()` - Sizes window to fit components
- `setContentPane(Container pane)` - Sets main content area
- `getContentPane()` - Gets content pane


## Swing Components (javax.swing)

### **JComponent (javax.swing.JComponent)**

Base for all Swing components

- `setPreferredSize(Dimension d)` - Sets preferred size
- `setBorder(Border border)` - Sets component border
- `setToolTipText(String text)` - Sets tooltip
- `setEnabled(boolean enabled)` - Enables/disables component


### **JPanel (javax.swing.JPanel)**

Container for grouping components

- Inherits Container and JComponent functions
- Used for organizing layouts


### **JButton (javax.swing.JButton)**

Clickable button component

- `setText(String text)` - Sets button text
- `addActionListener(ActionListener l)` - Handles clicks
- `setIcon(Icon icon)` - Sets button icon


### **JLabel (javax.swing.JLabel)**

Displays text or images

- `setText(String text)` - Sets label text
- `setIcon(Icon icon)` - Sets label icon
- `setHorizontalAlignment(int alignment)` - Sets text alignment


### **JTextField (javax.swing.JTextField)**

Single-line text input

- `getText()` - Gets entered text
- `setText(String text)` - Sets text
- `setColumns(int columns)` - Sets field width


### **Other Swing Components:**

- **JTextArea** - Multi-line text input/display
- **JCheckBox** - Checkbox for boolean input
- **JRadioButton** - Radio button for exclusive selection
- **JComboBox** - Dropdown list selection
- **JList** - Scrollable list of items


## AWT Components (java.awt)

### **Canvas (java.awt.Canvas)**

Drawing surface for custom graphics

- `paint(Graphics g)` - Override for custom drawing
- `getGraphics()` - Gets Graphics object


### **Button, Label, TextField**

AWT versions (heavyweight) with similar functions to Swing counterparts

## Helper Classes

### **Dimension (java.awt.Dimension)**

Represents width and height

- **Constructors:**
    - `Dimension()` - Creates 0x0 dimension
    - `Dimension(int width, int height)` - Creates with specified size
    - `Dimension(Dimension d)` - Copy constructor
- **Fields:** `int width`, `int height`


## Mermaid Diagram


```mermaid

classDiagram
    Object <|-- Component
    Component <|-- Container
    Container <|-- Window
    Window <|-- Frame
    Frame <|-- JFrame
    Container <|-- Panel
    Panel <|-- JPanel
    Component <|-- Canvas
    Container <|-- JComponent
    JComponent <|-- JButton
    JComponent <|-- JLabel
    JComponent <|-- JTextField
    JComponent <|-- JTextArea
    JComponent <|-- JCheckBox
    JComponent <|-- JRadioButton
    JComponent <|-- JComboBox
    JComponent <|-- JList
    JComponent <|-- JTable
    JComponent <|-- JScrollPane
    JComponent <|-- JPanel
    Window <|-- JWindow
    Window <|-- JDialog
    Object <|-- Dimension
    Object <|-- Graphics
    Object <|-- LayoutManager
    LayoutManager <|-- FlowLayout
    LayoutManager <|-- BorderLayout
    LayoutManager <|-- GridLayout

    class Component {
        +setSize(int width, int height)
        +setVisible(boolean b)
        +setBackground(Color c)
        +setForeground(Color c)
        +addMouseListener()
        +addKeyListener()
        +repaint()
        +paint(Graphics g)
    }

    class Container {
        +add(Component c)
        +remove(Component c)
        +setLayout(LayoutManager mgr)
        +getComponents()
    }

    class JFrame {
        +setTitle(String title)
        +setDefaultCloseOperation(int operation)
        +setResizable(boolean resizable)
        +pack()
        +setMenuBar(MenuBar mb)
    }

    class JComponent {
        +setPreferredSize(Dimension d)
        +setBorder(Border border)
        +setToolTipText(String text)
        +addActionListener()
        +setEnabled(boolean enabled)
    }

    class Dimension {
        +int width
        +int height
        +Dimension()
        +Dimension(int width, int height)
        +Dimension(Dimension d)
    }

```


## Class Diagram

![Comprehensive Java GUI Classes Hierarchy and Relationships](https://ppl-ai-code-interpreter-files.s3.amazonaws.com/web/direct-files/82c42b0d928bb98de957fbac2abd84cb/bfb57020-c6e0-43b9-a6a5-d62afc17d622/dfcf6fee.png)

Comprehensive Java GUI Classes Hierarchy and Relationships

## Simple GUI Application Example
![[SimpleGUIApp.java]]
Here's a complete example demonstrating these classes:

## Key Relationships

1. **Inheritance:** JFrame → Frame → Window → Container → Component
2. **Composition:** JFrame contains JPanel, JPanel contains other JComponents
3. **Helper:** Dimension used by components for size specification
4. **Event Handling:** Components use ActionListener, MouseListener, etc.
5. **Layout:** LayoutManager arranges components in containers

## Layout Managers

- **FlowLayout** - Left-to-right flow
- **BorderLayout** - North, South, East, West, Center regions
- **GridLayout** - Grid of rows and columns
- **GridBagLayout** - Flexible grid layout

This hierarchy allows for building complex GUI applications by combining containers and components with proper event handling and layout management.
<span style="display:none">[^1][^10][^11][^12][^13][^14][^15][^16][^17][^18][^19][^2][^20][^21][^3][^4][^5][^6][^7][^8][^9]</span>

<div style="text-align: center">⁂</div>
