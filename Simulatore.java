import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Simulatore extends JPanel {
    static double P, R, C, I, S, L, D;
    static int V;
    static int borderX;
    static int borderY;
    static public ArrayList<Persona> listaPopolazione = new ArrayList<Persona>();
    public CollisionChecker collisionChecker;
    Quarantena quarantena;
    int giorni;
    static boolean tracciamento = false;

    public Simulatore(double P, double R, double C, int V, double I, double S, double L, double D, int borderX, int borderY, Quarantena quarantena) {
        this.borderX = borderX;
        this.borderY = borderY;
        setPreferredSize(new Dimension(borderX,borderY));
        setBorder(new LineBorder(Color.BLACK));
        collisionChecker = new CollisionChecker(listaPopolazione, I, V, P);
        timer.start();
        this.P = P; this.R = R; this.C = C; this.V = V; this.I = I; this.S = S; this.L = L; this.D = D;
        generaPopolazione();
        collisionChecker.start();
        this.quarantena = quarantena;
    }

    public void nextDay() {
        ++giorni;
        collisionChecker.incontriGiornata = V * P;
        for (int i = 0; i < listaPopolazione.size(); i++) {
            int rand = ThreadLocalRandom.current().nextInt(1,100);
            int rand1 = ThreadLocalRandom.current().nextInt(1,100);
            Persona p = listaPopolazione.get(i);

            if (p.getVelX() == 0 && p.getVelY() == 0) {
                this.R -= 1;
            }

            if (p.isInfected()){
                p.giorniTrascorsi++;
                if (p.giorniTrascorsi >= D/6 && p.colore != Color.RED) {
                    p.colore = Color.ORANGE;
                    p.setContagioso(true);
                }
                if (p.giorniTrascorsi >= D) {
                    p.setInfection(false);
                    p.setContagioso(false);
                    p.colore = Color.BLUE;
                }
                if (p.colore == Color.RED && rand1 <= L) {
                    p.setInfection(false);
                    p.setContagioso(false);
                    p.colore = Color.BLACK;
                    p.setVelX(0);
                    p.setVelY(0);
                }
                if (p.giorniTrascorsi >= D/3 && rand <= S) {
                    R = R - 3 * C;
                    p.colore = Color.RED;
                    if (p.tracker.getArrayIncontri().size() > 0)
                        for (Persona s : p.tracker.getArrayIncontri()) {
                            if (s.tampone()) {
                                //quarantena.putToQuarantine(s);
                            }
                        }
                    //quarantena.putToQuarantine(p);
                    tracciamento = true;
                    p.setVelX(0);
                    p.setVelY(0);
                }
            }

            p.maxIncontri = V;
            if (p.colore != Color.RED && p.colore != Color.BLACK)
                if (p.getX() > 10 && p.getX() + p.getSize() < borderX-10 && p.getX() > 10 && p.getY()+p.getSize() < borderY-10)
                    p.randomizeStatus();
        }

        System.out.println(collisionChecker.incontriGiornata);
        System.out.println("Risorse: " + this.R);
    }

    public Timer timer = new Timer(30, e -> {

        if (collisionChecker.incontriGiornata <= 0) {
            nextDay();
        }

        for (int i = 0; i < listaPopolazione.size(); i++) {
            Persona p = listaPopolazione.get(i);
            if (p!= null) {
                if (p.getX() + p.getSize() >= borderX) {
                    p.setVelX(-p.getVelX());
                }
                if (p.getX() <= 0) {
                    p.setVelX(-p.getVelX());
                }
                if (p.getY() + p.getSize() >= borderY) {
                    p.setVelY(-p.getVelY());
                }
                if (p.getY() <= 0) {
                    p.setVelY(-p.getVelY());
                }
                p.moveX();
                p.moveY();
            }
        }

        if (R <= 0) {
            //risorse finite, che si fa?
        }

        repaint();
    });

    public void generaPopolazione() {
        for (int i = 0; i < P; i++) {
            int x = ThreadLocalRandom.current().nextInt(0, borderX);
            int y = ThreadLocalRandom.current().nextInt(0, borderY);

            if (i!=0) {
                for (int j = 1; j < listaPopolazione.size(); j++) {
                    Persona p = listaPopolazione.get(j);
                    if (p.distance(x, y) - (p.getSize() / 2) * 2 < 0 || x <= 10 || x >= borderX - 10 || y <= 10 || y >= borderY - 10) {
                        x = ThreadLocalRandom.current().nextInt(0, borderX);
                        y = ThreadLocalRandom.current().nextInt(0, borderX);
                    }
                }
            }
            listaPopolazione.add(new Persona(x, y, V));
        }
        if (listaPopolazione.size() > 0) {
            listaPopolazione.get(0).colore = Color.ORANGE;
            listaPopolazione.get(0).setInfection(true);
            listaPopolazione.get(0).setContagioso(true);
        }
    }

    // Stampa le particelle a schermo
    public void paintComponent(Graphics g) { // Stampa le particelle a schermo
        g.setColor(new Color(59, 68, 76));
        g.fillRect(0, 0, borderX, borderY);
        for (int i = 0; i < listaPopolazione.size(); i++) {
            Persona p = listaPopolazione.get(i);
            g.setColor(p.colore);
            g.fillOval(p.getX(), p.getY(), p.getSize(), p.getSize());
        }
    }
}
