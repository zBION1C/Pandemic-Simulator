import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

class CollisionChecker extends Thread {
    ArrayList<Persona> listaPopolazione = new ArrayList<Persona>();
    double I;
    double incontriGiornata;
    Quarantena quarantena;

    public CollisionChecker(ArrayList<Persona> l, double i, double V, double P, Quarantena quarantena) {
        this.listaPopolazione = l;
        this.I = i;
        incontriGiornata = V * P;
        this.quarantena = quarantena;
    }

    public int getRandom() {
        return ThreadLocalRandom.current().nextInt(1,100);
    }

    @Override
    public void run() {
        long cont = 0;
        while (true) {
            for (int i = 0; i< listaPopolazione.size(); i++) {
                Persona p = listaPopolazione.get(i);
                for (int y = 0; y < listaPopolazione.size(); y++) {
                    Persona s = listaPopolazione.get(y);
                    if (p != s && p.maxIncontri > 0) {

                        if (p.last!=null) {
                            if (p.collideWith(p.last) && p.colliding && p.maxIncontri > 0) {
                                p.colliding = true;
                                p.maxIncontri--;
                                incontriGiornata--;
                            } else {
                                p.colliding = false;
                            }
                        }

                        if (p.collideWith(s) && !p.isColliding()) {
                            System.out.println(incontriGiornata);
                            p.colliding = true;
                            p.last = s;
                            p.maxIncontri--;
                            incontriGiornata--;
                            if (Simulatore.tracciamento)
                                p.tracker.add(s);
                                s.tracker.add(p);

                            int rand = getRandom();
                            if (rand <= I && p.isContagioso() || s.isContagioso()) {
                                if (p.colore != Color.BLUE && s.colore != Color.BLUE) {
                                    s.setInfection(true);
                                    p.setInfection(true);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}