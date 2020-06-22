import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Persona extends Thread {
    private int y; private int x; private int velX; private int velY; int size = 4; private int radius = size / 2;
    public Color colore = Color.GREEN;
    public double maxIncontri;
    public int giorniTrascorsi;
    private boolean infected = false;
    private boolean contagioso = false;
    boolean colliding = false;
    Persona last;
    Tracker tracker = new Tracker();

    public Persona(int x, int y, int V) {
        initStatus();
        this.x = x;
        this.y = y;
        this.maxIncontri = V;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getSize() {
        return this.size;
    }

    public int getVelX() {
        return this.velX;
    }

    public int getVelY() {
        return this.velY;
    }

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
        return Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));

    }

    public boolean collideWith(Persona s) {
        double distance = this.distance(s.getX(), s.getY()) - ((this.getSize() / 2) * 2);
        if (distance <= 0) {
            return true;
        }
        return false;
    }

    public boolean isColliding() { return colliding; }

    public void moveX() {
        this.x += this.velX;
    }

    public void moveY() {
        this.y += this.velY;
    }

    public boolean fermo() {
        if (this.velX == 0 && this.velY == 0) return true;
        else return false;
    }

    public void randomizeStatus() {
        this.velX = ThreadLocalRandom.current().nextInt(-2, 3);
        this.velY = ThreadLocalRandom.current().nextInt(-2, 3);
    }

    public void initStatus() {
        while (this.velX == 0 || this.velY == 0) {
            this.velX = ThreadLocalRandom.current().nextInt(-2, 3);
            this.velY = ThreadLocalRandom.current().nextInt(-2, 3);
        }
    }

    public void setInfection(boolean valore) { this.infected = valore; }

    public boolean isInfected() { return infected; }

    public boolean isContagioso() { return this.contagioso; }

    public void setContagioso(boolean valore) { this.contagioso = valore; }

    public boolean tampone () {
        Simulatore.R = Simulatore.R - Simulatore.C;
        return contagioso;
    }
}