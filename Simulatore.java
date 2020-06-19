import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
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
    static int sani; static int sintomatici; static int asintomatici; static int guariti; static int morti;
    static public ArrayList<Persona> listaPopolazione = new ArrayList<Persona>();
    public CollisionChecker collisionChecker;
    Quarantena quarantena;
    int giorni;
    static boolean tracciamentoStarted = false;
    private boolean strategiaTracciamento;
    private boolean strategiaCampione;

    public Simulatore(int P, double R, double C, int V, double I, double S, double L, double D, int borderX, int borderY, Quarantena quarantena, boolean bt, boolean bc) {
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
        strategiaTracciamento = bt;
        strategiaCampione = bc;
    }

    public void nextDay() {
        collisionChecker.collisionEnabled = false;
        collisionChecker.incontriGiornata = 0;

        ++giorni;
        Pd = listaPopolazione.size();
        sani = 0; asintomatici = 0; sintomatici = 0; guariti = 0; morti = 0;
        for (int j = 0; j < listaPopolazione.size(); j++) {
            Persona p = listaPopolazione.get(j);
            if (p.fermo()) Pd--;
        }

        Vd = (V * Pd)/P;

        R0 = Vd * D * I;

        for (int y = 0; y < quarantena.quarantena.size(); y++) {
            int rand = ThreadLocalRandom.current().nextInt(1,100);
            int rand1 = ThreadLocalRandom.current().nextInt(1,100);
            Persona p = quarantena.quarantena.get(y);
            p.giorniTrascorsi++;
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
                R -= 3 * C;
                p.colore = Color.RED;
                p.setVelX(0);
                p.setVelY(0);
            }
        }

        for (int i = 0; i < listaPopolazione.size(); i++) {
            int rand = ThreadLocalRandom.current().nextInt(1,100);
            int rand1 = ThreadLocalRandom.current().nextInt(1,100);
            Persona p = listaPopolazione.get(i);
            p.maxIncontri = Vd;

            if (p.getVelX() == 0 && p.getVelY() == 0 && p.colore != Color.BLACK) {
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
                        if (p.tracker.getArrayIncontri().size() > 0) {
                            for (Persona s : p.tracker.getArrayIncontri()) {
                                if (s.tampone()) {
                                    quarantena.putToQuarantine(s);
                                    Pd--;
                                }
                            }
                        }
                        quarantena.putToQuarantine(p);
                        Pd--;
                        Simulatore.tracciamentoStarted = true;
                    }
                    if (strategiaCampione) {
                        for (int y = 0; y < listaPopolazione.size() / 100; y++){
                            int indice = ThreadLocalRandom.current().nextInt(0, listaPopolazione.size());
                            Persona t = listaPopolazione.get(indice);
                            if (t.tampone()) {
                                quarantena.putToQuarantine(t);
                                Pd--;
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


        for (Persona persona: listaPopolazione) {
            collisionChecker.incontriGiornata += persona.maxIncontri;
        }


        for (int j = 0; j < listaPopolazione.size(); j++) {
            Persona p = listaPopolazione.get(j);
            if (!p.isInfected()) sani++;
            if (p.colore == Color.ORANGE) asintomatici++;
            if (p.colore == Color.RED) sintomatici++;
            if (p.colore == Color.BLUE) guariti++;
            if (p.colore == Color.BLACK) morti++;
        }

        for (Persona q: quarantena.quarantena) {
            if (q.colore == Color.ORANGE) asintomatici++;
            if (q.colore == Color.RED) sintomatici++;
            if (q.colore == Color.BLUE) guariti++;
            if (q.colore == Color.BLACK) morti++;
        }

        System.out.println("GIORNO: " + giorni);
        System.out.println("Incontri da fare: " + collisionChecker.incontriGiornata);
        System.out.println("Pd: " + this.Pd);
        System.out.println("Vd: " + this.Vd);
        System.out.println("Sani: " + sani + " || " + "Guariti: " + guariti + " || " + "Morti: " + morti);
        System.out.println("Asintomatici: " + asintomatici + " || " + "Sintomatici: " + sintomatici);
        System.out.println("Quarantena: " + quarantena.quarantena.size());
        System.out.println("Risorse: " + this.R + " || " + "Persone in campo: " + listaPopolazione.size());

        System.out.println("---------------------------------------------------------------------");

        if (R <= 0) {
            //this.timer.stop();
            //quarantena.timer.stop();
            //collisionChecker.interrupt();
            //System.out.println("Risorse finite, simulazione terminata");
        }
        if (R0 < 1) {
            this.timer.stop();
            quarantena.timer.stop();
            collisionChecker.interrupt();
            System.out.println("Simulazione terminata");
        }
        if (listaPopolazione.size() == sani) {
            this.timer.stop();
            quarantena.timer.stop();
            collisionChecker.interrupt();
            System.out.println("La malattia e' stata contenuta, tutti i malati sono in isolamento");
        }

        collisionChecker.collisionEnabled = true;
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