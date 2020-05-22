import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Frame extends JFrame {

    private int borderX = 1800;
    private int borderY = 900;

    public Frame(double p, double r, double c, double v, double i, double s, double l, double d) {
        setSize(borderX,borderY);
        JFrame frame = new JFrame();
        add(new Simulatore(p, r, c, v, i, s, l, d));
        setVisible(true);
        setTitle("Simulazione");
        frame.setBackground(new Color(101,101,101));

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });
    }
    
    public void exit () {
        dispose();
        new ExitFrame();
    }
}