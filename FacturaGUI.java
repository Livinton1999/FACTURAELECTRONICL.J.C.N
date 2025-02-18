import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Factura implements Serializable {
    private String numero;
    private String cliente;
    private double monto;

    public Factura(String numero, String cliente, double monto) {
        this.numero = numero;
        this.cliente = cliente;
        this.monto = monto;
    }

    public String getNumero() {
        return numero;
    }

    public String getCliente() {
        return cliente;
    }

    public double getMonto() {
        return monto;
    }

    @Override
    public String toString() {
        return "Factura N°: " + numero + ", Cliente: " + cliente + ", Monto: $" + monto;
    }
}

public class FacturaGUI extends JFrame {
    private List<Factura> facturas = new ArrayList<>();

    public FacturaGUI() {
        setTitle("Gestión de Facturas");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        JButton btnRegistro = new JButton("Registrar Factura");
        JButton btnConsulta = new JButton("Consultar Factura");
        JButton btnMostrarArchivo = new JButton("Guardar en Archivo");
        JButton btnSalir = new JButton("Salir");

        panel.add(btnRegistro);
        panel.add(btnConsulta);
        panel.add(btnMostrarArchivo);
        panel.add(btnSalir);

        btnRegistro.addActionListener(e -> registrarFactura());
        btnConsulta.addActionListener(e -> consultarFactura());
        btnMostrarArchivo.addActionListener(e -> guardarEnArchivo());
        btnSalir.addActionListener(e -> System.exit(0));

        add(panel);
    }

    private void registrarFactura() {
        try {
            String numero = JOptionPane.showInputDialog("Ingrese número de factura:");
            if (numero == null || numero.trim().isEmpty()) return;

            for (Factura f : facturas) {
                if (f.getNumero().equalsIgnoreCase(numero)) {
                    JOptionPane.showMessageDialog(this, "Número de factura ya registrado.");
                    return;
                }
            }

            String cliente = JOptionPane.showInputDialog("Ingrese nombre del cliente:");
            if (cliente == null || cliente.trim().isEmpty()) return;

            double monto;
            try {
                monto = Double.parseDouble(JOptionPane.showInputDialog("Ingrese monto de la factura:"));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Error: Ingrese un monto válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            facturas.add(new Factura(numero, cliente, monto));
            JOptionPane.showMessageDialog(this, "Factura registrada correctamente.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al registrar factura.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void consultarFactura() {
        if (facturas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay facturas registradas.");
            return;
        }

        String numero = JOptionPane.showInputDialog("Ingrese número de factura a buscar:");
        if (numero == null || numero.trim().isEmpty()) return;

        for (Factura f : facturas) {
            if (f.getNumero().equalsIgnoreCase(numero)) {
                JOptionPane.showMessageDialog(this, f.toString());
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Factura no encontrada.");
    }

    private void guardarEnArchivo() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("facturas.txt"))) {
            for (Factura f : facturas) {
                writer.println(f.toString());
            }
            JOptionPane.showMessageDialog(this, "Facturas guardadas correctamente.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar en archivo.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FacturaGUI frame = new FacturaGUI();
            frame.setVisible(true);
        });
    }
}
