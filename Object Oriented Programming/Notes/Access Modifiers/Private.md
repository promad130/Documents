The private access modifier means something(Like a variable/ function/ Method) that will be kept inaccessible for the other block of code, and is accessible only to that block in which it was created.

In Classes, the members of a class aka variables of our classes are by default set to private.

The key point is: **Private instance variables in Java are private to the class, not the object.**

```java
'package wrath_of_god_game;

  

import javax.swing.JFrame;

import java.awt.Canvas;

import java.awt.Dimension;

  

public class Game extends Canvas implements Runnable{

//screen resolution

public static int width = 300;

public static int height = (width / 16) * 9;

// a scale for the game, i.e., the size to which the game is scaled to?

public static final int scale = 3;

private Thread thread;

private JFrame frame;

private boolean running = false;

public Game() {

Dimension size = new Dimension(width*scale, height*scale);

setPreferredSize(size);

frame = new JFrame();

}

public synchronized void start()

{

running = true;

thread = new Thread(this, "Display");

thread.start();

}

public synchronized void stop()

{

running = false;

try {

thread.join();

}catch(InterruptedException err) {

err.printStackTrace();

}

}

  

@Override

public void run() {

// TODO Auto-generated method stub

while(running) {

}

}

public static void main(String[] args)

{

Game game = new Game();

game.frame.setResizable(false);

game.frame.setTitle("Wrath of GOD!");

game.frame.add(game);

game.frame.pack();

}

}'
```