package semana17.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class VentanaFluida extends JFrame {
    private JButton btnReporte = new JButton("Generar reporte");
    private JProgressBar barra = new JProgressBar(0, 10);
    private JLabel lblEstado = new JLabel("Esperando...", JLabel.CENTER);

    public VentanaFluida() {
        super("Reporte - version con hilo");
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        barra.setStringPainted(true);
        barra.setForeground(new Color(0x1E7A46));
        lblEstado.setFont(new Font("SansSerif", Font.BOLD, 15));

        panel.add(btnReporte);
        panel.add(barra);
        panel.add(lblEstado);

        add(panel, BorderLayout.CENTER);
        btnReporte.addActionListener(e -> generar());
        setSize(360, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void generar() {
        btnReporte.setEnabled(false);
        barra.setValue(0);
        lblEstado.setText("Procesando...");

        Thread trabajador = new Thread(() -> {
            for (int paso = 1; paso <= 10; paso++) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                    return;
                }
                int avance = paso;
                SwingUtilities.invokeLater(() -> barra.setValue(avance));
            }
            SwingUtilities.invokeLater(() -> {
                lblEstado.setText("Reporte listo");
                btnReporte.setEnabled(true);
            });
        });
        trabajador.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaFluida().setVisible(true));
    }
}