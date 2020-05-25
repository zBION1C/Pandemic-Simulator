import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ErrorFrame extends JFrame {

    public ErrorFrame() {
        JFrame frame = new JFrame();
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setTitle("ATTENZIONE: ERRORE!");
        setSize(300, 100);
        setVisible(true);

        JPanel mainPanel = new JPanel();
        setContentPane(mainPanel);

        JButton ok = new JButton("OK");
        JLabel errore = new JLabel("Ricontrollare che i valori inseriti siano numerici!");
        errore.setForeground(Color.RED);
        mainPanel.add(errore);
        mainPanel.add(ok);
        ok.addActionListener(e -> dispose());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }
}
