import javax.swing.*;
import java.awt.*;

public class ExitFrame extends JFrame {

    public ExitFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("ExitFrame");
        setSize(400, 150);
        setVisible(true);

        JPanel mainPanel = new JPanel(null);
        setContentPane(mainPanel);

        JButton close = new JButton("Exit");
        close.setBounds(20, 40, 160, 30);
        JButton open = new JButton("Set New Parameters");
        open.setBounds(203, 40, 160, 30);
        mainPanel.add(close);
        mainPanel.add(open);

        close.addActionListener(e -> System.exit(0));
        open.addActionListener(e -> {
            new Menu();
            dispose();
        });

    }
}