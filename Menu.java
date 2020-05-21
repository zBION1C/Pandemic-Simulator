import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Menu extends JFrame {
    static public int popolazione;

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

        JPanel textPanel = new JPanel();                          // Text Field numero p_panel
        BoxLayout layout = new BoxLayout(textPanel, BoxLayout.Y_AXIS);
        textPanel.setLayout(layout);
        JPanel parametri = new JPanel();
        parametri.add(new JLabel("PARAMETRI INIZIALI"));
        parametri.setBackground(Color.GRAY);


        JTextField p = new JTextField("0", 10);
        JTextField r = new JTextField("0", 10);
        JTextField c = new JTextField("0", 10);
        JTextField v = new JTextField("0", 10);
        JTextField i = new JTextField("0", 10);
        JTextField s = new JTextField("0", 10);
        JTextField l = new JTextField("0", 10);
        JTextField d = new JTextField("0", 10);
        textPanel.add(newParameter("Popolazione iniziale (P):", p));
        textPanel.add(newParameter("Risorse iniziali (R):", r));
        textPanel.add(newParameter("Costo delle cure (C):", c));
        textPanel.add(newParameter("Velocitá (V):", v));
        textPanel.add(newParameter("Infettivitá (I):", i));
        textPanel.add(newParameter("Sintomaticitá (S):", s));
        textPanel.add(newParameter("Letalitá (L):", l));
        textPanel.add(newParameter("Durata (D):", d));
        mainPanel.add(parametri, BorderLayout.NORTH);
        mainPanel.add(textPanel, BorderLayout.CENTER);
        buttonPanel.setBackground(Color.WHITE);



        setVisible(true);

        start.addActionListener(e -> {
            double n1 = Double.parseDouble(p.getText());
            double n2 = Double.parseDouble(r.getText());
            double n3 = Double.parseDouble(c.getText());
            double n4 = Double.parseDouble(v.getText());
            double n5 = Double.parseDouble(i.getText());
            double n6 = Double.parseDouble(s.getText());
            double n7 = Double.parseDouble(l.getText());
            double n8 = Double.parseDouble(d.getText());
            new Frame(n1, n2, n3, n4, n5, n6, n7, n8);
            this.dispose();
        });
    }

    private JPanel newParameter (String s, JTextField t) {
        JPanel parametro = new JPanel();
        parametro.add(new JLabel(s));
        parametro.add(t);
        return parametro;
    }

    public static void main(String[] args) { new Menu();}

}
