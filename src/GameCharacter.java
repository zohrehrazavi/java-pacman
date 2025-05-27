import java.awt.Image;

public abstract class GameCharacter {
    protected double x;
    protected double y;
    protected Image image;
    protected double size;

    public GameCharacter(double x, double y, Image image, double size) {
        this.x = x;
        this.y = y;
        this.image = image;
        this.size = size;
    }

    public abstract void draw();

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public abstract void handleInput(int[][] map);
    
}
