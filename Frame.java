import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Frame extends JFrame {

    private final int frameborderX = 1920;
    private final int frameborderY = 1000;
    private final int borderX = 1100;
    private final int borderY = 1000;

    public Frame(double p, double r, double c, double v, double i, double s, double l, double d) {
        setSize(frameborderX,frameborderY);
        JFrame frame = new JFrame();

        JPanel rightSidePanel = new JPanel();
        rightSidePanel.setLayout(new BorderLayout());

        setLayout(new BorderLayout());
        Quarantena quarantena = new Quarantena();
        Simulatore simulator = new Simulatore(p, r, c, v, i, s, l, d, borderX, borderY, quarantena);
        add(simulator, BorderLayout.WEST);
        rightSidePanel.add(quarantena, BorderLayout.NORTH);
        add(rightSidePanel, BorderLayout.EAST);
        add(simulator, BorderLayout.WEST);
        setVisible(true);
        setTitle("Simulazione");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        pack();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                simulator.timer.stop();
                exit();
            }
        });
    }

    public void exit () {
        dispose();
        new ExitFrame();
    }
}