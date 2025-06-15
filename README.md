# Snake Game 🐍

A classic Snake game built using Java and Swing.

## 🎮 Features

- **Real-time Gameplay**: Smooth snake movement and food consumption.
- **Grid-based System**: Logical and visual grid layout using Java's `Graphics` API.
- **Food Mechanics**: Randomly spawning food that increases the snake’s length.
- **Game Over Detection**:
  - Collision with walls
  - Collision with snake body
- **High Score Tracking**:
  - Displays current score and high score
- **Persistent Data Storage**:
  - High score persists across sessions using file I/O
- **Restart Mechanism**:
  - Press `SPACE` after game over to restart the game
- **Polished UI**:
  - Snake and food rendered with 3D effect using `fill3DRect`
  - Custom game window icon

![image](https://github.com/user-attachments/assets/0659b91f-daf9-44e5-97d8-ec5fc650825c)
![image](https://github.com/user-attachments/assets/a4d2b929-d9b3-40fd-bc36-4f858e7f8e44)

## 💡 Programming Concepts Used

- **Object-Oriented Programming (OOP)**:
  - Encapsulation via `Tile` class for snake segments and food
- **Swing Framework**:
  - UI elements and game rendering with `JPanel`, `JFrame`, and `Timer`
- **Event Handling**:
  - Keyboard inputs with `KeyListener`
  - Game loop with `ActionListener` and `javax.swing.Timer`
- **Graphics Programming**:
  - Custom rendering with `paintComponent` and `Graphics`
- **File I/O**:
  - Persistent high score saved to `highscore.txt` using `BufferedReader` and `PrintWriter`
- **Randomization**:
  - Food spawns randomly using `java.util.Random`
- **Game State Management**:
  - Control flow based on game state (`gameOver`, `velocity`, etc.)
