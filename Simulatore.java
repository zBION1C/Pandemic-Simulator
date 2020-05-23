import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Simulatore extends JPanel {
    double numeroPopolazione; double r; double c; double v; double i; double s; double l; double d;
    int borderX;
    int borderY;
    public ArrayList<Persona> listaPopolazione = new ArrayList<Persona>();
    public CollisionChecker collisionChecker;
    Quarantena quarantena;

    public Simulatore(double p, double r, double c, double v, double i, double s, double l, double d, int borderX, int borderY, Quarantena quarantena) {
        setPreferredSize(new Dimension(borderX,borderY));
        setBorder(new LineBorder(Color.BLACK));
        collisionChecker = new CollisionChecker();
        timer.start();
        this.numeroPopolazione = p; this.r = r; this.c = c; this.v = v; this.i = i; this.s = s; this.l = l; this.d = d;
        this.borderX = borderX;
        this.borderY = borderY;
        this.quarantena = quarantena;
        generaPopolazione();
        collisionChecker.start();
    }

    public Timer timer = new Timer(7, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < listaPopolazione.size(); i++) {
                Persona p = listaPopolazione.get(i);
                if (p.getX()+p.getSize() >= borderX) {
                    p.setVelX(-p.getVelX());
                }
                if (p.getX() <= 0) {
                    p.setVelX(-p.getVelX());
                }
                if (p.getY()+p.getSize() >= borderY) {
                    p.setVelY(-p.getVelY());
                }
                if (p.getY() <= 0) {
                    p.setVelY(-p.getVelY());
                }
                p.moveX();
                p.moveY();
            }
            repaint();
        }
    });

    public void generaPopolazione() {
        for (int i = 0; i < numeroPopolazione; i++) {
            int x = ThreadLocalRandom.current().nextInt(0, borderX-7);
            int y = ThreadLocalRandom.current().nextInt(0, borderY-7);

            if (i!=0) {
                for (int j = 0; j < listaPopolazione.size(); j++) {
                    Persona p = listaPopolazione.get(j);
                    if (p.distance(x, y) - (p.getSize()/2)*2 < 0) {
                        x = ThreadLocalRandom.current().nextInt(0, borderX-7);
                        y = ThreadLocalRandom.current().nextInt(0, borderX-7);
                    }
                }
            }
            listaPopolazione.add(new Persona(x, y));
        }
    }


    // Stampa le particelle a schermo
    public void paintComponent(Graphics g) { // Stampa le particelle a schermo
        g.setColor(new Color(152, 203, 190));
        g.fillRect(0, 0, borderX, borderY);
        for (int i = 0; i < listaPopolazione.size(); i++) {
            Persona p = listaPopolazione.get(i);
            g.setColor(p.colore);
            g.fillOval(p.getX(), p.getY(), p.getSize(), p.getSize());
        }
    }

    class CollisionChecker extends Thread {
        int cont;

        @Override
        public void run() {
            while (true) {
                for (int i = 0; i< listaPopolazione.size(); i++) {
                    Persona p = listaPopolazione.get(i);
                    for (int y = 0; y < listaPopolazione.size(); y++) {
                        Persona s = listaPopolazione.get(y);
                        if (p != s && p.colore != Color.BLACK && s.colore != Color.BLACK) {
                            if (p.collideWith(s)) {
                                p.colore = Color.BLACK;
                                s.colore = Color.BLACK;
                                quarantena.putToQuarantine(s);
                                listaPopolazione.remove(s);
                                System.out.println(cont++);
                                while (p.collideWith(s))
                                    ;
                            }
                        }
                    }
                }
            }
        }
    }

}
