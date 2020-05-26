import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Simulatore extends JPanel {
    double P; double R; double C; int V; double I; double S; double L; double D;
    int borderX;
    int borderY;
    public ArrayList<Persona> listaPopolazione = new ArrayList<Persona>();
    public CollisionChecker collisionChecker;
    Quarantena quarantena;

    public Simulatore(double P, double R, double C, int V, double I, double S, double L, double D, int borderX, int borderY, Quarantena quarantena) {
        setPreferredSize(new Dimension(borderX,borderY));
        setBorder(new LineBorder(Color.BLACK));
        collisionChecker = new CollisionChecker();
        timer.start();
        this.P = P; this.R = R; this.C = C; this.V = V; this.I = I; this.S = S; this.L = L; this.D = D;
        this.borderX = borderX;
        this.borderY = borderY;
        this.quarantena = quarantena;
        generaPopolazione();
        collisionChecker.start();
    }

    public Timer timer = new Timer(20, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
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
            repaint();
        }
    });

    public void generaPopolazione() {
        for (int i = 0; i < P; i++) {
            int x = ThreadLocalRandom.current().nextInt(0, borderX);
            int y = ThreadLocalRandom.current().nextInt(0, borderY);

            if (i!=0) {
                for (int j = 0; j < listaPopolazione.size(); j++) {
                    Persona p = listaPopolazione.get(j);
                    if (p.distance(x, y) - (p.getSize()/2)*2 < 0 && x <= 1 || x >= borderX-10 || y <= 1 || y >= borderY-10) {
                        x = ThreadLocalRandom.current().nextInt(0, borderX);
                        y = ThreadLocalRandom.current().nextInt(0, borderX);
                    }
                }
            }
            listaPopolazione.add(new Persona(x, y, V));
        }
    }


    // Stampa le particelle a schermo
    public void paintComponent(Graphics g) { // Stampa le particelle a schermo
        g.setColor(new Color(192, 219, 255));
        g.fillRect(0, 0, borderX, borderY);
        for (int i = 0; i < listaPopolazione.size(); i++) {
            Persona p = listaPopolazione.get(i);
            g.setColor(p.colore);
            g.fillOval(p.getX(), p.getY(), p.size, p.size);
        }
    }

    class CollisionChecker extends Thread {
        long cont;

        @Override
        public synchronized void run() {
            while (true) {
                for (int i = 0; i< listaPopolazione.size(); i++) {
                    Persona p = listaPopolazione.get(i);
                    for (int y = 0; y < listaPopolazione.size(); y++) {
                        Persona s = listaPopolazione.get(y);
                        if (p != s) {
                            if (p.collideWith(s) && p.maxIncontri > 0) {
                                p.maxIncontri--;
                                System.out.println(p.maxIncontri);
                                listaPopolazione.remove(s);
                                s.colore = Color.BLACK;
                                quarantena.putToQuarantine(s);
                                System.out.println(cont++);
                                while (p.collideWith(s))
                                    System.out.println("diocane");
                            }
                        }
                    }
                }
            }
        }
    }

}
