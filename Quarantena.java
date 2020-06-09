import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Quarantena extends JPanel {

    ArrayList<Persona> quarantena = new ArrayList<Persona>();
    public Quarantena() {
        setPreferredSize(new Dimension(400,400));
        setBorder(new LineBorder(Color.BLACK));
        setLayout(new BorderLayout());
        timer.start();
    }

    public Timer timer = new Timer(40, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i< quarantena.size(); i++) {
                Persona p = quarantena.get(i);
                if (p.getX()+p.getSize() >= 400) {
                    p.setVelX(-p.getVelX());
                }
                if (p.getX() <= 0) {
                    p.setVelX(-p.getVelX());
                }
                if (p.getY()+p.getSize() >= 400) {
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

    public void paintComponent(Graphics g) { // Stampa le particelle a schermo
        g.setColor(new Color(255, 174, 108));
        g.fillRect(0, 0, 400, 400);
        for (int i = 0; i<quarantena.size(); i++) {
            Persona p = quarantena.get(i);
            g.setColor(Color.BLACK);
            g.fillOval(p.getX(), p.getY(), p.getSize(), p.getSize());
        }
    }

    public void putToQuarantine(Persona s) {
        s.setX(200);
        s.setY(200);
        s.setX(ThreadLocalRandom.current().nextInt(10, 380));
        s.setY(ThreadLocalRandom.current().nextInt(10, 380));
        s.setVelX(ThreadLocalRandom.current().nextInt(-2,2));
        s.setVelY(ThreadLocalRandom.current().nextInt(-2,2));
        quarantena.add(s);
        repaint();
    }
}