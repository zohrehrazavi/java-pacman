import java.awt.event.KeyEvent;
import princeton.StdDraw;

public class Pacman extends GameCharacter {
    private static final String PACMAN_IMAGE_RIGHT = "PNG/pacman_right.png";
    private static final String PACMAN_IMAGE_LEFT = "PNG/pacman_left.png";
    private String currentImage = PACMAN_IMAGE_RIGHT;
    private double currentAngle = 0;
    private int dotsEaten;
    private double prevX;
    private double prevY;
    private boolean hasEatenBigFood;
    private boolean hasSpecialPower; // Indicates whether Pacman has special power after eating a big food

    public Pacman(double x, double y, double size) {
        super(x, y, null, size);
        this.dotsEaten = 0;
        this.hasEatenBigFood = false;
        this.hasSpecialPower = false;
        this.currentAngle = 0;
    }

    @Override
    public void draw() {
        StdDraw.picture(x, y, currentImage, size * 0.7, size * 0.7, currentAngle);
    }

    @Override
    public void handleInput(int[][] environment) {
        double newX = x;
        double newY = y;
        double newAngle = currentAngle;
        String newImage = currentImage;

        if (StdDraw.isKeyPressed(KeyEvent.VK_UP)) {
            newY += 1;
            newImage = PACMAN_IMAGE_RIGHT;
            newAngle = 90;
        }
        if (StdDraw.isKeyPressed(KeyEvent.VK_DOWN)) {
            newY -= 1;
            newImage = PACMAN_IMAGE_RIGHT;
            newAngle = 270;
        }
        if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT)) {
            newX -= 1;
            newImage = PACMAN_IMAGE_LEFT;
            newAngle = 0;
            if (newX < 0) {
                if (environment[0][0] != GameScene.WALL) {
                    newX = environment[0].length - 1;
                    newX = newX + (x - Math.floor(x));
                }
            }
        }
        if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT)) {
            newX += 1;
            newImage = PACMAN_IMAGE_RIGHT;
            newAngle = 0;
            int lastColumnIndex = environment[0].length - 1;
            if (Math.round(x) == lastColumnIndex && environment[(int) (environment.length - Math.round(y) - 1)][0] == 0) {
                newX = 0;
                for (int col = 0; col < environment[0].length; col++) {
                    if (environment[(int) (environment.length - Math.round(y) - 1)][col] != GameScene.WALL) {
                        newX = col;
                        break;
                    }
                }
                newX = newX + (x - Math.floor(x));
            }
        }

        if (canMove(newX, newY, environment)) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.filledSquare(prevX, prevY, size * 0.5);
            x = newX;
            y = newY;
            currentAngle = newAngle;
            currentImage = newImage;
            draw();
            int col = (int) Math.floor(x);
            int row = environment.length - 1 - (int) Math.floor(y);
            if (environment[row][col] == GameScene.WALL) {
                return;
            } if (environment[row][col] == GameScene.FOOD || environment[row][col] == GameScene.BIG_FOOD) {
                dotsEaten++;
                if (environment[row][col] == GameScene.BIG_FOOD) {
                    hasEatenBigFood = true;
                    setSpecialPower(true);
                    System.out.println("BigFood");
                    System.out.println("Pacman gained special power!");
                }
                environment[row][col] = 0;
            }
        }
        prevX = x;
        prevY = y;
    }

    public int getDotsEaten() {
        return dotsEaten;
    }

    public boolean hasSpecialPower() {
        return hasSpecialPower;
    }

    public void setSpecialPower(boolean specialPower) {
        if (specialPower && !hasSpecialPower) {
            System.out.println("Pacman gained special power!");
        } else if (!specialPower && hasSpecialPower) {
            System.out.println("Pacman lost special power!");
        }
        this.hasSpecialPower = specialPower;
    }

    // Check if Pacman can move to the specified position
    private boolean canMove(double newX, double newY, int[][] environment) {
        int col = (int) Math.floor(newX);
        int row = environment.length - 1 - (int) Math.floor(newY);

        // Check if the target position is within the bounds of the environment array
        if (row < 0 || row >= environment.length || col < 0 || col >= environment[0].length) {
            return false; // Out of bounds, cannot move
        }

        // Check if the target position contains a wall (represented by 1 in the environment array)
        if (environment[row][col] == GameScene.WALL) {
            return false; // Wall, cannot move
        }

        // Pacman can move to any other type of cell
        return true;
    }
}