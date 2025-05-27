import princeton.StdDraw;
public class Main {
    public static void main(String[] args) {
        StdDraw.setCanvasSize(1600, 900); // Large window size
        StdDraw.enableDoubleBuffering();

        while (true) { // Replay loop
            int DIFFICULTY_LEVEL = 1;
            int pauseTime = 0;

            switch (DIFFICULTY_LEVEL) {
                case 1:
                    pauseTime = 200;
                    break;
                case 2:
                    pauseTime = 100;
                    break;
                case 3:
                    pauseTime = 50;
                    break;
                default:
                    pauseTime = 200;
                    System.out.println("Invalid level choice. Defaulting to level 1.");
                    break;
            }

            // Create a GameScene instance
            GameScene gameScene = new GameScene();
            gameScene.loadMap("src/scene.txt");

            // Draw the initial game scene
            gameScene.drawScene();

            // Create a Pacman instance
            Pacman pacman = new Pacman(10.5, 14.5, 0.9);

            // Create an array to store monsters
            Monster[] monsters = new Monster[4]; // Change the number of monsters here

            // Initialize monsters with their positions and colors
            for (int i = 0; i < monsters.length; i++) {
                String color;
                if (i < Monster.monsterColors.length) {
                    color = Monster.monsterColors[i];
                } else {
                    // If i exceeds the length of monsterColors, revert back to blue
                    color = Monster.monsterColors[0];
                }
                monsters[i] = new Monster(10.0 + i, 10.0 + i, 0.9, gameScene, color);
            }

            boolean gameOver = false;
            boolean win = false;

            // Main game loop
            while (!gameOver) {
                pacman.handleInput(gameScene.getEnvironment());
                for (Monster monster : monsters) {
                    monster.handleInput(gameScene.getEnvironment());
                }
                pacman.draw();
                for (Monster monster : monsters) {
                    monster.draw();
                }
                if (pacman.getDotsEaten() == gameScene.getTotalFood()) {
                    gameScene.displayText("YOU WIN!");
                    System.out.println("You win!");
                    StdDraw.show();
                    win = true;
                    break;
                }
                for (Monster monster : monsters) {
                    if (checkCollision(pacman, monster)) {
                        if (pacman.hasSpecialPower()) {
                            monster.eraseMonster();
                            pacman.setSpecialPower(false);
                        } else {
                            gameScene.displayText("GAME OVER!");
                            System.out.println("Pacman collided with a monster! Game Over!");
                            gameOver = true;
                        }
                    }
                }
                StdDraw.show();
                StdDraw.pause(pauseTime);
            }

            // Draw Replay button
            double btnX = 10.5, btnY = 7, btnW = 3, btnH = 1.5;
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.filledRectangle(btnX, btnY, btnW, btnH);
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.rectangle(btnX, btnY, btnW, btnH);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 40));
            StdDraw.text(btnX, btnY, "Replay");
            StdDraw.show();

            // Wait for click on Replay button
            boolean waitingReplay = true;
            while (waitingReplay) {
                if (StdDraw.isMousePressed()) {
                    double mx = StdDraw.mouseX();
                    double my = StdDraw.mouseY();
                    if (mx >= btnX - btnW && mx <= btnX + btnW && my >= btnY - btnH && my <= btnY + btnH) {
                        // Wait for mouse release to avoid multiple triggers
                        while (StdDraw.isMousePressed()) {
                            StdDraw.pause(10);
                        }
                        waitingReplay = false; // Restart the game
                    }
                }
                StdDraw.pause(10);
            }
        }
    }

    // Method to check collision between Pacman and a monster
    private static boolean checkCollision(Pacman pacman, Monster monster) {
        // Get Pacman's position
        int pacmanCol = (int) Math.floor(pacman.getX());
        int pacmanRow = GameScene.HEIGHT - 1 - (int) Math.floor(pacman.getY());

        // Get Monster's position
        int monsterCol = (int) Math.floor(monster.getX());
        int monsterRow = GameScene.HEIGHT - 1 - (int) Math.floor(monster.getY());

        // Check if Pacman and the monster are in the same cell
        return pacmanCol == monsterCol && pacmanRow == monsterRow;
    }
}