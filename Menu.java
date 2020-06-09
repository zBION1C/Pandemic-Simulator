import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Menu extends JFrame {
    static public int popolazione;
    public Menu() {
        JFrame menu = new JFrame();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Simulatore");

        setTitle("Menu Parametri Simulazione");
        setSize(500, 500);

        //creo il mainPanel
        JPanel mainPanel = new JPanel(new BorderLayout());
        setContentPane(mainPanel);
        //aggiungo il pulsante di start sotto
        JPanel buttonPanel = new JPanel();                        // pannello del bottone
        JButton start = new JButton("Start Simulation");     // bottone
        buttonPanel.add(start);                                   // aggiungo bottone al pannello del bottone
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);           // metto il pannelo a sud nel mainPanel
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

        start.addActionListener(e -> {
            double n1=0; double n2=0; double n3=0; int n4=0; double n5=0; double n6=0; double n7=0; double n8=0;
            boolean b1; boolean b2; boolean b3; boolean b4; boolean b5; boolean b6; boolean b7; boolean b8;

            try { n1 = Double.parseDouble(p.getText()); p.setBorder(new LineBorder(Color.GRAY)); b1 = true;
            } catch (NumberFormatException f) {p.setBorder(new LineBorder(Color.RED)); b1 = false;}

            try { n2 = Double.parseDouble(r.getText()); r.setBorder(new LineBorder(Color.GRAY)); b2 = true;
            } catch (NumberFormatException f) {r.setBorder(new LineBorder(Color.RED)); b2 = false;}

            try { n3 = Double.parseDouble(c.getText()); c.setBorder(new LineBorder(Color.GRAY)); b3 = true;
            } catch (NumberFormatException f) {c.setBorder(new LineBorder(Color.RED)); b3 = false;}

            try { n4 = Integer.parseInt(v.getText());   v.setBorder(new LineBorder(Color.GRAY)); b4 = true;
            } catch (NumberFormatException f) {v.setBorder(new LineBorder(Color.RED)); b4 = false;}

            try { n5 = Double.parseDouble(i.getText()); i.setBorder(new LineBorder(Color.GRAY)); b5 = true;
            } catch (NumberFormatException f) {i.setBorder(new LineBorder(Color.RED)); b5 = false;}

            try { n6 = Double.parseDouble(s.getText()); s.setBorder(new LineBorder(Color.GRAY)); b6 = true;
            } catch (NumberFormatException f) {s.setBorder(new LineBorder(Color.RED)); b6 = false;}

            try { n7 = Double.parseDouble(l.getText()); l.setBorder(new LineBorder(Color.GRAY)); b7 = true;
            } catch (NumberFormatException f) {l.setBorder(new LineBorder(Color.RED)); b7 = false;}

            try { n8 = Double.parseDouble(d.getText()); d.setBorder(new LineBorder(Color.GRAY)); b8 = true;
            } catch (NumberFormatException f) {d.setBorder(new LineBorder(Color.RED)); b8 = false;}

            if (!(n2 < 10 * n1 * n3)) {
                p.setBorder(new LineBorder(Color.RED)); b1 = false;
                r.setBorder(new LineBorder(Color.RED)); b2 = false;
                c.setBorder(new LineBorder(Color.RED)); b3 = false;
            }
            if ((n2 < 10 * n1 * n3) && n1 != 0 && n2 != 0 && n3 != 0) {
                p.setBorder(new LineBorder(Color.GRAY)); b1 = true;
                r.setBorder(new LineBorder(Color.GRAY)); b2 = true;
                c.setBorder(new LineBorder(Color.GRAY)); b3 = true;
            }

            if (!(n2 < n1 * n8)) {
                p.setBorder(new LineBorder(Color.RED)); b1 = false;
                r.setBorder(new LineBorder(Color.RED)); b2 = false;
                d.setBorder(new LineBorder(Color.RED)); b8 = false;
            }
            if ((n2 < n1 * n8) && n1 != 0 && n2 != 0 && n8 != 0) {
                p.setBorder(new LineBorder(Color.GRAY)); b1 = true;
                r.setBorder(new LineBorder(Color.GRAY)); b2 = true;
                d.setBorder(new LineBorder(Color.GRAY)); b8 = true;
            }

            if (b1 && b2 && b3 && b4 && b5 && b6 && b7 && b8) {
                new Frame(n1, n2, n3, n4, n5, n6, n7, n8);
                dispose();
            }
        });

        setVisible(true);
    }

    private JPanel newParameter (String s, JTextField t) {
        JPanel parametro = new JPanel();
        parametro.add(new JLabel(s));
        parametro.add(t);
        return parametro;
    }

    public static void main(String[] args) { new Menu();}
}