import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Simulatore extends JPanel {
    static double R, C, I, S, L, D;
    static int P;
    static int Pd;
    static int V;
    static int Vd;
    static double R0;
    static int borderX;
    static int borderY;
    static int sani; static int malati; static int guariti; static int morti;
    static public ArrayList<Persona> listaPopolazione = new ArrayList<Persona>();
    public CollisionChecker collisionChecker;
    Quarantena quarantena;
    int giorni;
    static boolean tracciamentoStarted = false;
    private boolean strategiaTracciamento = false;
    private boolean strategiaCampione = false;

    public Simulatore(int P, double R, double C, int V, double I, double S, double L, double D, int borderX, int borderY, Quarantena quarantena) {
        this.borderX = borderX;
        this.borderY = borderY;
        setPreferredSize(new Dimension(borderX,borderY));
        setBorder(new LineBorder(Color.BLACK));
        collisionChecker = new CollisionChecker(listaPopolazione, I, V, P);
        timer.start();
        this.P = P; this.Pd = P; this.R = R; this.C = C; this.V = V; this.Vd = V; this.I = I; this.S = S; this.L = L; this.D = D;
        generaPopolazione();
        collisionChecker.start();
        this.quarantena = quarantena;
    }

    public void nextDay() {
        ++giorni;
        Pd = P;
        sani = 0; malati = 0; guariti = 0; morti = 0;
        for (int j = 0; j < listaPopolazione.size(); j++) {
            Persona p = listaPopolazione.get(j);
            if (p.fermo()) Pd--;
            if (!p.isInfected()) sani++;
            else malati++;
            if (p.colore == Color.BLUE) guariti++;
            if (p.colore == Color.BLACK) morti++;
        }
        Vd = (V * Pd)/P;
        collisionChecker.incontriGiornata = Vd * P;

        R0 = Vd * D * I;
        if (R0 < 1) this.timer.stop();

        for (int i = 0; i < listaPopolazione.size(); i++) {
            int rand = ThreadLocalRandom.current().nextInt(1,100);
            int rand1 = ThreadLocalRandom.current().nextInt(1,100);
            Persona p = listaPopolazione.get(i);
            p.maxIncontri = Vd;

            if (p.getVelX() == 0 && p.getVelY() == 0) {
                this.R -= 1;
            }
            if (p.isInfected()){
                p.giorniTrascorsi++;
                if (p.giorniTrascorsi >= D/6 && p.colore == Color.GREEN) {
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
                if (p.giorniTrascorsi >= D/3 && rand <= S && p.colore == Color.ORANGE) {
                    R = R - 3 * C;
                    p.colore = Color.RED;
                    if (strategiaTracciamento) {
                        if (p.tracker.getArrayIncontri().size() > 0)
                            for (Persona s : p.tracker.getArrayIncontri()) {
                                if (s.tampone()) {
                                    quarantena.putToQuarantine(s);
                                }
                            }
                        quarantena.putToQuarantine(p);
                        Simulatore.tracciamentoStarted = true;
                    }
                    if (strategiaCampione) {
                        for (int y = 0; i < listaPopolazione.size() / 100; i++){
                            int indice = ThreadLocalRandom.current().nextInt(0, listaPopolazione.size());
                            Persona t = listaPopolazione.get(indice);
                            if (t.tampone()) {
                                quarantena.putToQuarantine(t);
                            }
                        }
                    }
                    p.setVelX(0);
                    p.setVelY(0);
                }
            }
            if (p.colore != Color.RED && p.colore != Color.BLACK)
                if (p.getX() > 10 && p.getX() + p.getSize() < borderX-10 && p.getX() > 10 && p.getY()+p.getSize() < borderY-10)
                    p.randomizeStatus();
        }
        System.out.println("GIORNO: " + giorni);
        System.out.println("Incontri: " + collisionChecker.incontriGiornata);
        System.out.println("Pd: " + this.Pd);
        System.out.println("Vd: " + this.Vd);
        System.out.println("Sani: " + sani + " || " + "Guariti: " + guariti);
        System.out.println("Malati: " + malati + " || " + "Morti: " + morti);
        System.out.println("----------------------------------------");
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