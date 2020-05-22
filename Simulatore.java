import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Simulatore extends JPanel {
    double numeroPopolazione; double r; double c; double v; double i; double s; double l; double d;
    ArrayList<Persona> listaPopolazione = new ArrayList<Persona>();
    int y = 0;
    Persona p;

    public Simulatore(double p, double r, double c, double v, double i, double s, double l, double d) {
        timer.start();
        this.numeroPopolazione = p; this.r = r; this.c = c; this.v = v; this.i = i; this.s = s; this.l = l; this.d = d;
        generaPopolazione();
    }

    ActionListener listener = e -> {
        for (Persona p: listaPopolazione) {

            if (p.getX()+p.getSize() >= 1800) {
                p.setVelX(-p.getVelX());
            }
            if (p.getX() <= 0) {
                p.setVelX(-p.getVelX());
            }
            if (p.getY()+p.getSize() >= 900) {
                p.setVelY(-p.getVelY());
            }
            if (p.getY() <= 0) {
                p.setVelY(-p.getVelY());
            }


            for (Persona s: listaPopolazione) {
                if (p != s) {
                    if (p.collideWith(s)) {
                        p.colore = Color.WHITE;
                        s.colore = Color.WHITE;
                        System.out.println("Collisione avvenuta!");
                    }
                }
            }

            p.moveX();
            p.moveY();
        }
        repaint();
    };


    Timer timer = new Timer(20, listener);

    public void generaPopolazione() {
        for (int i = 0; i < numeroPopolazione; i++) {
            int x = ThreadLocalRandom.current().nextInt(0, 1800-100);
            int y = ThreadLocalRandom.current().nextInt(0, 900-100);

            if (i!=0) {
                for (int j = 0; j < listaPopolazione.size(); j++) {
                    Persona p = listaPopolazione.get(j);
                    if (p.distance(x, y) - (p.getSize()/2)*2 < 0) {
                        x = ThreadLocalRandom.current().nextInt(0, 1800-100);
                        y = ThreadLocalRandom.current().nextInt(0, 900-100);
                    }
                }
            }
            listaPopolazione.add(new Persona(x, y));
        }
    }


    // Stampa le particelle a schermo
    public void paintComponent(Graphics g) { // Stampa le particelle a schermo
        g.setColor(new Color(152, 203, 190));
        g.fillRect(0, 0, 1800, 900);

        for (Persona p: listaPopolazione) {
            g.setColor(p.colore);
            g.fillOval(p.getX(), p.getY(), p.getSize(), p.getSize());
        }
    }


}