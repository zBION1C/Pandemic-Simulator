import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

class CollisionChecker extends Thread {
    int cont = 0;
    ArrayList<Persona> listaPopolazione = new ArrayList<Persona>();
    double I;

    public CollisionChecker(ArrayList<Persona> l, double i) {
        this.listaPopolazione = l;
        this.I = i;
    }

    public int getRandom() {
        return ThreadLocalRandom.current().nextInt(1,100);
    }

    @Override
    public void run() {
        while (true) {
            for (int i = 0; i< listaPopolazione.size(); i++) {
                Persona p = listaPopolazione.get(i);
                for (int y = 0; y < listaPopolazione.size(); y++) {
                    Persona s = listaPopolazione.get(y);
                    if (p != s) {
                        if (p.collideWith(s) && p.maxIncontri > 0 && !p.isColliding() && !s.isColliding()) {
                            p.colliding = true;
                            s.colliding = true;
                            p.timer.start();
                            s.timer.start();
                            p.maxIncontri--;
                            System.out.println(++cont);
                            int rand = getRandom();
                            if (rand <= I && p.isContagioso() || s.isContagioso()) {
                                s.infect();
                                p.infect();
                            }
                            //listaPopolazione.remove(s);
                            //s.colore = Color.BLACK;
                            //quarantena.putToQuarantine(s);
                        }
                    }
                }
            }
        }
    }
}