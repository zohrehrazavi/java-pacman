import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import princeton.StdDraw;

public class GameScene {
    protected static final int WIDTH = 21; // Number of columns
    protected static final int HEIGHT = 18; // Number of rows
    private static final int CELL_SIZE = 50; // Size of each cell

    // Constants for cell types
    static final int WALL = 1;
    protected static final int FOOD = 2;
    protected static final int BIG_FOOD = 3;

    private final int[][] environment;
    private int totalFood; // Total number of food cells

    public GameScene() {
        environment = new int[HEIGHT][WIDTH];
        totalFood = 0;
    }

    public int[][] getEnvironment() {
        return environment;
    }

    public int getTotalFood() {
        return totalFood;
    }

    public void loadMap(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            int row = 0;
            while ((line = reader.readLine()) != null && row < HEIGHT) {
                String[] values = line.trim().split("\\s+");
                for (int col = 0; col < WIDTH && col < values.length; col++) {
                    int cell = Integer.parseInt(values[col]);
                    environment[row][col] = cell;
                    if (cell == FOOD || cell == BIG_FOOD) {
                        totalFood++; // Increment totalFood for each food cell
                    }
                }
                row++;
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Error reading map file: " + e.getMessage());
        }
    }

    public void drawScene() {
        StdDraw.setCanvasSize(WIDTH * CELL_SIZE, HEIGHT * CELL_SIZE);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);

        // Set the background to black
        StdDraw.clear(StdDraw.BLACK);

        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < WIDTH; col++) {
                drawCell(row, col);
            }
        }
    }

    public void drawCell(int row, int col) {
        switch (environment[row][col]) {
            case WALL:
                // Draw the outer square with the original blue color
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.filledSquare(col + 0.5, HEIGHT - row - 0.5, 0.5); // Wall

                // Draw the inner square with a different shade of blue
                StdDraw.setPenColor(StdDraw.BOOK_BLUE); // Adjust the alpha value for transparency
                StdDraw.filledSquare(col + 0.5, HEIGHT - row - 0.5, 0.3); // Inner square

                // Draw the small square inside two squares
                StdDraw.setPenColor(StdDraw.BLUE); // Adjust the alpha value for transparency
                StdDraw.filledSquare(col + 0.5, HEIGHT - row - 0.5, 0.1); // Inner square
                break;
            case FOOD:
                StdDraw.setPenColor(StdDraw.YELLOW);
                StdDraw.filledCircle(col + 0.5, HEIGHT - row - 0.5, 0.1); // Food
                break;
            case BIG_FOOD:
                StdDraw.setPenColor(StdDraw.YELLOW);
                StdDraw.filledCircle(col + 0.5, HEIGHT - row - 0.5, 0.2); // Big food
                break;
            default:
                // No need to draw anything for empty cells
                break;
        }
    }
    public void displayText(String text) {
        StdDraw.setPenColor(StdDraw.WHITE); // Set pen color to white
        StdDraw.setFont(new Font("Jogan", Font.BOLD, 80)); // Set the font
        StdDraw.text(10.5, 10, text);
    }
}