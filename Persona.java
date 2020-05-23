import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class Persona {
    private int y;
    private int x;
    private int velX = ThreadLocalRandom.current().nextInt(-3, 3);
    private int velY = ThreadLocalRandom.current().nextInt(-3, 3);;
    static final private int size = 10;
    static final private int radius = size/2;
    public Color colore = Color.RED;
    int maxCollisioni;


    public Persona (int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX () { return this.x; }

    public int getY() { return this.y; }

    public int getSize() { return this.size; }

    public int getVelX() { return this.velX; }

    public int getVelY() {return this.velY; }

    public void setVelX(int valore) {
        this.velX = valore;
    }

    public void setVelY(int valore) {
        this.velY = valore;
    }

    public void setX(int valore) {
        this.x = valore;
    }

    public void setY(int valore) {
        this.y = valore;
    }

    public double distance(int x1, int y1) {
        int xDistance = this.x - x1;
        int yDistance = this.y - y1;
        return Math.sqrt((xDistance*xDistance)+(yDistance*yDistance));
    }

    public boolean collideWith(Persona s) {
        if (this.distance(s.getX(), s.getY()) - (this.getSize()/2)*2 < 0) {
            return true;
        }
        return false;
    }

    public void moveX() {
        this.x += this.velX;
    }

    public void moveY() {
        this.y += this.velY;
    }

}
