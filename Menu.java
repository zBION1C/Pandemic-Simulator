import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Menu extends JFrame {
    static private int N_popolazione;

    public Menu() {
        JFrame menu = new JFrame();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Simulatore");

        setSize(500, 500);

        //creo il mainPanel
        JPanel mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);

        //aggiungo il pulsante di start sotto
        JPanel buttonPanel = new JPanel();                        // pannello del bottone
        JButton start = new JButton("Start Simulation");     // bottone
        buttonPanel.add(start);                                   // aggiungo bottone al pannello del bottone
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);           //metto il pannelo a sud nel mainPanel

        JPanel textPanel = new JPanel();                          // Text Field numero popolazione
        BoxLayout layout = new BoxLayout(textPanel, BoxLayout.Y_AXIS);
        textPanel.setLayout(layout);


        JTextField p = new JTextField(10);
        JTextField r = new JTextField(10);
        JTextField c = new JTextField(10);
        JTextField v = new JTextField(10);
        JTextField i = new JTextField(10);
        JTextField s = new JTextField(10);
        JTextField l = new JTextField(10);
        JTextField d = new JTextField(10);
        JPanel popolazione = new JPanel();
        popolazione.add(new JLabel("Popolazione iniziale (P):"));
        popolazione.add(p);
        JPanel risorse = new JPanel();
        risorse.add(new JLabel("Risorse iniziali (R):"));
        risorse.add(r);
        JPanel costo = new JPanel();
        costo.add(new JLabel("Costo delle cure (C):"));
        costo.add(c);
        JPanel velocitá = new JPanel();
        velocitá.add(new JLabel("Velocitá (V):"));
        velocitá.add(v);
        JPanel infettivitá = new JPanel();
        infettivitá.add(new JLabel("Infettivitá (I):"));
        infettivitá.add(i);
        JPanel sintomaticitá = new JPanel();
        sintomaticitá.add(new JLabel("Sintomaticitá (S):"));
        sintomaticitá.add(s);
        JPanel letalitá = new JPanel();
        letalitá.add(new JLabel("Letalitá (L):"));
        letalitá.add(l);
        JPanel durata = new JPanel();
        durata.add(new JLabel("Durata (D):"));
        durata.add(d);
        textPanel.add(popolazione);
        textPanel.add(risorse);
        textPanel.add(costo);
        textPanel.add(velocitá);
        textPanel.add(infettivitá);
        textPanel.add(sintomaticitá);
        textPanel.add(letalitá);
        textPanel.add(durata);
        mainPanel.add(textPanel, BorderLayout.WEST);



        setVisible(true);

        start.addActionListener(e -> new Frame());
    }

    public static void main(String[] args) { new Menu();}

}
