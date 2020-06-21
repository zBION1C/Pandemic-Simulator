import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Frame extends JFrame {

    private final int frameborderX = 1920;
    private final int frameborderY = 1000;
    private final int borderX = 1250;
    private final int borderY = 1035;
    private static JPanel dati;
    private static JLabel giorno;
    private static JLabel incontri;
    private static JLabel lblPd;
    private static JLabel lblVd;
    private static JLabel lblsani;
    private static JLabel lblguariti;
    private static JLabel lblmorti;
    private static JLabel lblasintomatici;
    private static JLabel lblsintomatici;
    private static JLabel lblquarantena;
    private static JLabel lblcampo;
    private static JLabel lblrisorse;


    public Frame(int P, double R, double C, int V, double I, double S, double L, double D, boolean bt, boolean bc) {
        setSize(frameborderX,frameborderY);
        JFrame frame = new JFrame();

        JPanel rightSidePanel = new JPanel();
        rightSidePanel.setLayout(new BorderLayout());
        setLayout(new BorderLayout());
        dati = new JPanel();
        dati.setLayout(new BoxLayout(dati, BoxLayout.Y_AXIS));
        rightSidePanel.add(dati, BorderLayout.CENTER);
        giorno = new JLabel("Giorno: " + 0);
        incontri = new JLabel("Incontri rimasti: " + V*P);
        lblPd = new JLabel("Popolazione in movimento: " +  P);
        lblVd = new JLabel("Velocita dinamica: " +  V);
        lblsani = new JLabel("Sani: " +  (P - 1));
        lblguariti = new JLabel("Guariti: " +  0);
        lblmorti = new JLabel("Morti: " +  0);
        lblasintomatici = new JLabel("Asintomatici: " +  1);
        lblsintomatici = new JLabel("Sintomatici: " +  0);
        lblquarantena = new JLabel("Persone in quarantena: " +  0);
        lblcampo = new JLabel("Persone in campo: " +  P);
        lblrisorse = new JLabel("Risorse: " +  R);
        dati.add(giorno);
        dati.add(incontri);
        dati.add(lblPd);
        dati.add(lblVd);
        dati.add(lblsani);
        dati.add(lblguariti);
        dati.add(lblmorti);
        dati.add(lblasintomatici);
        dati.add(lblsintomatici);
        dati.add(lblquarantena);
        dati.add(lblcampo);
        dati.add(lblrisorse);
        Quarantena quarantena = new Quarantena();
        Simulatore simulator = new Simulatore(P, R, C, V, I, S, L, D, borderX, borderY, quarantena, bt, bc);
        add(simulator, BorderLayout.CENTER);
        rightSidePanel.add(quarantena, BorderLayout.NORTH);
        add(rightSidePanel, BorderLayout.EAST);


        setVisible(true);
        setTitle("SIMULAZIONE");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        pack();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                simulator.timer.stop();
                simulator.listaPopolazione.clear();
                simulator.collisionChecker.interrupt();
                exit();
            }
        });
    }

    public static void updateData(int g, int incontriRimasti, int Pd, int Vd, int sani, int guariti, int morti, int asintomatici, int sintomatici, int quarantena, double risorse, int campo) {
        giorno.setText("Giorno: " + g);
        incontri.setText("Incontri rimasti: " + incontriRimasti);
        lblPd.setText("Popolazione in movimento: " + Pd);
        lblVd.setText("Velocita dinamica: " + Vd);
        lblsani.setText("Sani: " + sani);
        lblguariti.setText("Guariti: " + guariti);
        lblmorti.setText("Morti: " + morti);
        lblasintomatici.setText("Asintomatici: " + asintomatici);
        lblsintomatici.setText("Sintomatici: " + sintomatici);
        lblquarantena.setText("Persona in quarantena: " + quarantena);
        lblcampo.setText("Persona in campo: " + campo);
        lblrisorse.setText("Risorse: " + risorse);
    }


    public void exit () {
        dispose();
        new ExitFrame();
    }
}