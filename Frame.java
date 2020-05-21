import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Frame extends JFrame {

    private int borderX = 800;
    private int borderY = 800;

    public Frame(double p, double r, double c, double v, double i, double s, double l, double d) {
        setSize(borderX,borderY);
        JFrame frame = new JFrame();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(new Simulatore());
        setVisible(true);
        frame.setBackground(new Color(101,101,101));
    }
}
