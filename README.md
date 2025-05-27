# Pacman Java Game

A simple Pacman game implemented in Java using the Princeton StdDraw library for graphics.

## Features

- Classic Pacman gameplay
- Keyboard controls (arrow keys)
- Replay button after game over or win
- Uses only two Pacman images (left and right), with rotation for up/down
- Customizable map via `scene.txt`

## Setup

1. **Clone or download the repository.**
2. **Ensure you have Java installed (Java 8 or later).**
3. **Directory structure:**
   ```
   project-root/
   ├── src/
   │   ├── Main.java
   │   ├── GameScene.java
   │   ├── Pacman.java
   │   ├── Monster.java
   │   ├── GameCharacter.java
   │   └── scene.txt
   ├── princeton/
   │   └── StdDraw.java
   ├── PNG/
   │   ├── pacman_right.png
   │   ├── pacman_left.png
   │   └── (monster images...)
   └── README.md
   ```

## How to Run

1. **Compile:**
   ```sh
   javac -cp . src/*.java princeton/StdDraw.java
   ```
2. **Run:**
   ```sh
   java -cp . src.Main
   ```

## Controls

- **Arrow keys:** Move Pacman
- **Replay button:** Click after game over or win to restart

## Customization

- **Map:** Edit `src/scene.txt` to change the game layout.
- **Images:** Replace PNGs in the `PNG/` folder for custom graphics.

## Dependencies

- No external dependencies. Uses the included `princeton/StdDraw.java` for graphics.

## Credits

- StdDraw library by Robert Sedgewick and Kevin Wayne (Princeton University)
- Game logic and code by [Your Name]

---

Enjoy playing Pacman!
