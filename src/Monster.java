import java.util.Random;
import princeton.StdDraw;

public class Monster extends GameCharacter {
    protected static final String[] monsterColors = {"Blue", "Green", "Orange", "Red"};
    private static final String[] monsterImages = {
            "PNG/Monster_Blue_Left.png",
            "PNG/Monster_Green_Left.png",
            "PNG/Monster_Orange_Left.png",
            "PNG/Monster_Red_Right.png"
    };

    private Random random;
    private GameScene gameScene; // Instance of GameScene to access environment
    private String color; // Store the color of the monster

    public Monster(double x, double y, double size, GameScene gameScene, String color) {
        super(x, y, null, size); // Image will be loaded when drawing
        this.gameScene = gameScene;
        this.random = new Random();
        this.color = color; // Assign the color passed as parameter
    }

    @Override
    public void draw() {
        // Calculate the index based on the stored color
        int index = getIndexByColor(color);

        // Assign the corresponding image path based on the calculated index
        String imagePath = monsterImages[index];

        // Center the monster in each cell
        double offsetX = 0.5;
        double offsetY = 0.5;
        StdDraw.picture(x + offsetX, y + offsetY, imagePath, size * 0.7, size * 0.7);
    }

    @Override
    public void handleInput(int[][] environment) {
        // Generate random directions for both horizontal and vertical axes
        int horizontalDirection = random.nextInt(3) - 1; // -1 for left, 0 for no movement, 1 for right
        int verticalDirection = random.nextInt(3) - 1; // -1 for down, 0 for no movement, 1 for up

        // Calculate the new position based on the current position and movement step size
        double newX = x + horizontalDirection;
        double newY = y + verticalDirection;

        // Check if the monster can move to the new position
        if (canMove(newX, newY, environment)) {
            // Check if the previous cell was food
            int prevCol = (int) Math.floor(x);
            int prevRow = environment.length - 1 - (int) Math.floor(y);
            boolean prevCellWasFoodWall = (environment[prevRow][prevCol] == GameScene.FOOD ||
                    environment[prevRow][prevCol] == GameScene.BIG_FOOD ||
                    environment[prevRow][prevCol] == GameScene.WALL);
            // Erase the previous monster position
            erase(x, y);

            // Update the position if moving in that direction is valid
            x = newX;
            y = newY;

            // Redraw the food if the previous cell was food
            if (prevCellWasFoodWall) {
                gameScene.drawCell(prevRow, prevCol); // Call drawFood on gameScene instance
            }
        }
    }

    // Helper method to get the index based on the stored color
    private int getIndexByColor(String color) {
        for (int i = 0; i < monsterColors.length; i++) {
            if (monsterColors[i].equalsIgnoreCase(color)) {
                return i;
            }
        }
        // Default to blue if the color is not found
        return 0;
    }

    // Method to erase the previous position of the monster
    public void erase(double prevX, double prevY) {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledSquare(prevX + 0.5, prevY + 0.5, 0.5); // Draw a filled square over the previous position
    }

    // Check if the monster can move to the specified position
    private boolean canMove(double newX, double newY, int[][] environment) {
        int col = (int) Math.floor(newX);
        int row = environment.length - 1 - (int) Math.floor(newY);

        // Check if the target position is within the bounds of the environment array
        if (row < 0 || row >= environment.length || col < 0 || col >= environment[0].length) {
            return false; // Out of bounds, cannot move
        }

        // Check if the target position contains a wall
        return environment[row][col] != GameScene.WALL;
    }

    // Method to erase the monster from the maze
    public void eraseMonster() {
        // Erase the monster's position from the canvas
        erase(x, y);

        // Reset the monster's position to be outside the maze
        x = -1000;
        y = -1000;
    }
}