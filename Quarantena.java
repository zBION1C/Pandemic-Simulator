import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

public class Quarantena extends JPanel {

    ArrayList<Persona> popolazione = new ArrayList<Persona>();

    public Quarantena() {
        setPreferredSize(new Dimension(400,400));
        setBorder(new LineBorder(Color.BLACK));
        setLayout(new BorderLayout());
    }

    public void paintComponent(Graphics g) { // Stampa le particelle a schermo
        g.setColor(new Color(255, 174, 108));
        g.fillRect(0, 0, 400, 400);
    }
}
