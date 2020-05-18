import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Simulatore extends JPanel implements ActionListener {
    Timer timer = new Timer(15, this);
    private int popolazione;

    // Stampa le particelle a schermo
    public void paintComponent(Graphics g) { // Stampa le particelle a schermo
        g.setColor(Color.RED);
        g.fillOval(10,10,100,100);
    }

    @Override
    public void actionPerformed(ActionEvent e) { // Movimento delle particelle
    }
}
