import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Menu extends JFrame {
    static private int N_popolazione;

    public Menu() {
        JFrame menu = new JFrame("Simulatore");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setSize(500, 500);

        //creo il mainPanel
        JPanel mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);

        //aggiungo il pulsante di start sotto
        JPanel buttonPanel = new JPanel(); // pannello del bottone
        JButton start = new JButton("Start Simulation"); // bottone
        buttonPanel.add(start); // aggiungo bottone al pannello del bottone
        mainPanel.add(buttonPanel, BorderLayout.SOUTH); //metto il pannelo a sud nel mainPanel

        JPanel textPanel = new JPanel();        // Text Field numero popolazione
        JLabel pop_label = new JLabel("Popolazione:");
        textPanel.add(pop_label);
        JTextField popolazione = new JTextField(10);
        textPanel.add(popolazione);
        mainPanel.add(textPanel, BorderLayout.WEST);



        setVisible(true);

        start.addActionListener(e -> new Frame());
    }

    public static void main(String[] args) { new Menu();}

}
