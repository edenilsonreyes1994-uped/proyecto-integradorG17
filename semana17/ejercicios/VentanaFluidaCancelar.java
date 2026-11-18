package semana17.ejercicios;

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

public class VentanaFluidaCancelar extends JFrame {
    private JButton btnReporte = new JButton("Generar reporte");
    private JButton btnCancelar = new JButton("Cancelar");
    private JProgressBar barra = new JProgressBar(0, 10);
    private JLabel lblEstado = new JLabel("Esperando...", JLabel.CENTER);
    private Thread trabajador;

    public VentanaFluidaCancelar() {
        super("Reporte - con cancelar");
        JPanel panel = new JPanel(new GridLayout(4, 1, 8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        barra.setStringPainted(true);
        barra.setForeground(new Color(0x1E7A46));
        lblEstado.setFont(new Font("SansSerif", Font.BOLD, 15));
        btnCancelar.setEnabled(false);

        panel.add(btnReporte);
        panel.add(btnCancelar);
        panel.add(barra);
        panel.add(lblEstado);

        add(panel, BorderLayout.CENTER);
        btnReporte.addActionListener(e -> generar());
        btnCancelar.addActionListener(e -> {
            if (trabajador != null) trabajador.interrupt();
        });

        setSize(360, 240);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void generar() {
        btnReporte.setEnabled(false);
        btnCancelar.setEnabled(true);
        barra.setValue(0);
        lblEstado.setText("Procesando...");

        trabajador = new Thread(() -> {
            for (int paso = 1; paso <= 10; paso++) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                    SwingUtilities.invokeLater(() -> {
                        lblEstado.setText("Cancelado");
                        btnReporte.setEnabled(true);
                        btnCancelar.setEnabled(false);
                    });
                    return;
                }
                int avance = paso;
                SwingUtilities.invokeLater(() -> barra.setValue(avance));
            }
            SwingUtilities.invokeLater(() -> {
                lblEstado.setText("Reporte listo");
                btnReporte.setEnabled(true);
                btnCancelar.setEnabled(false);
            });
        });
        trabajador.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaFluidaCancelar().setVisible(true));
    }
}