import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Frame extends JFrame {

    private int borderX = 1800;
    private int borderY = 900;

    public Frame(double p, double r, double c, double v, double i, double s, double l, double d) {
        setSize(borderX,borderY);
        JFrame frame = new JFrame();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(new Simulatore(p, r, c, v, i, s, l, d));
        setVisible(true);
        frame.setBackground(new Color(101,101,101));
    }
}
