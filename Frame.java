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
    private static JLabel lblR0;
    private static JLabel lblfermi;
    private static JLabel lbldurata;
    private static JLabel lblI;
    private static JLabel lblS;
    private static JLabel lblL;
    private static JLabel lblC;
    private static JLabel lblseparator;




    public Frame(int P, double R, double C, int V, double I, double S, double L, int D, boolean bt, boolean bc) {
        setSize(frameborderX,frameborderY);

        JPanel rightSidePanel = new JPanel();
        rightSidePanel.setLayout(new BorderLayout());
        setLayout(new BorderLayout());
        dati = new JPanel();
        dati.setLayout(new BoxLayout(dati, BoxLayout.Y_AXIS));
        rightSidePanel.add(dati, BorderLayout.CENTER);

        lblI = new JLabel("Infettivita: " + I + "%");
        lblS = new JLabel("Sintomaticita: " + S+ "%");
        lblL = new JLabel("Letalita: " + L+ "%");
        lblC = new JLabel("Costo Tampone: " + C);
        lbldurata = new JLabel("Durata: " + D + " giorni");

        lblseparator = new JLabel("===========================");

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
        lblR0 = new JLabel("Fattore R0: " + V * D * (I/100));
        lblfermi = new JLabel("Popolazione ferma: " + 0);

        dati.add(lblI);
        dati.add(lblS);
        dati.add(lblL);
        dati.add(lblC);
        dati.add(lbldurata);

        dati.add(lblseparator);

        dati.add(giorno);
        dati.add(incontri);
        dati.add(lblPd);
        dati.add(lblfermi);
        dati.add(lblVd);
        dati.add(lblsani);
        dati.add(lblguariti);
        dati.add(lblmorti);
        dati.add(lblasintomatici);
        dati.add(lblsintomatici);
        dati.add(lblquarantena);
        dati.add(lblcampo);
        dati.add(lblrisorse);
        dati.add(lblR0);
        dati.add(lblfermi);
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

    public static void updateData(int g, int incontriRimasti, int Pd, int Vd, int sani, int guariti, int morti, int asintomatici, int sintomatici, int quarantena, double risorse, int campo, double R0, int fermi) {
        giorno.setText("Giorno: " + g);
        incontri.setText("Incontri rimasti: " + incontriRimasti);
        lblPd.setText("Popolazione in movimento: " + Pd);
        lblVd.setText("Velocita dinamica: " + Vd);
        lblsani.setText("Sani: " + sani);
        lblguariti.setText("Guariti: " + guariti);
        lblmorti.setText("Morti: " + morti);
        lblasintomatici.setText("Asintomatici: " + asintomatici);
        lblsintomatici.setText("Sintomatici: " + sintomatici);
        lblquarantena.setText("Persone in quarantena: " + quarantena);
        lblcampo.setText("Persone in campo: " + campo);
        lblrisorse.setText("Risorse: " + risorse);
        lblR0.setText("Fattore R0: " + R0);
        lblfermi.setText("Popolazione ferma: " + fermi);
    }


    public void exit () {
        dispose();
        new ExitFrame();
    }
}