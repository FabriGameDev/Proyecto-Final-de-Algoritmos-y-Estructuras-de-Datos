package VISTA;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VistaPago extends JFrame {
    private JRadioButton receiptRadio;
    private JRadioButton invoiceRadio;
    private JButton generateButton;
    private DefaultTableModel selectedItemsModel;

    public VistaPago(DefaultTableModel selectedItemsModel) {
        this.selectedItemsModel = selectedItemsModel;

        setTitle("Pago - Emitir Boleta o Factura");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Etiqueta superior
        JLabel titleLabel = new JLabel("Seleccione el tipo de comprobante a emitir", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(titleLabel, BorderLayout.NORTH);

        // Panel de opciones de pago
        receiptRadio = new JRadioButton("Boleta");
        invoiceRadio = new JRadioButton("Factura");
        ButtonGroup group = new ButtonGroup();
        group.add(receiptRadio);
        group.add(invoiceRadio);
        receiptRadio.setSelected(true);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setBorder(BorderFactory.createTitledBorder("Opciones de Pago"));
        optionsPanel.add(receiptRadio);
        optionsPanel.add(invoiceRadio);
        add(optionsPanel, BorderLayout.CENTER);

        // Panel de botones
        generateButton = new JButton("Siguiente");
        generateButton.setFont(new Font("Arial", Font.BOLD, 14));
        generateButton.setBackground(new Color(34, 139, 34));
        generateButton.setForeground(Color.WHITE);
        generateButton.setFocusPainted(false);
        generateButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (receiptRadio.isSelected()) {
                    openReceiptWindow();
                } else if (invoiceRadio.isSelected()) {
                    openInvoiceWindow();
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(generateButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void openReceiptWindow() {
        JFrame receiptFrame = new JFrame("Emitir Boleta");
        receiptFrame.setSize(300, 200);
        receiptFrame.setLocationRelativeTo(null);
        receiptFrame.setLayout(new BorderLayout(10, 10));

        JLabel nameLabel = new JLabel("Ingrese el nombre del cliente:", JLabel.CENTER);
        JTextField nameField = new JTextField();

        JButton payButton = new JButton("Pagar y Generar Boleta");
        payButton.addActionListener(e -> {
            String clientName = nameField.getText().trim();
            if (!clientName.isEmpty()) {
                generateReceiptFile(clientName);
                receiptFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(receiptFrame, "Por favor, ingrese el nombre del cliente.");
            }
        });

        receiptFrame.add(nameLabel, BorderLayout.NORTH);
        receiptFrame.add(nameField, BorderLayout.CENTER);
        receiptFrame.add(payButton, BorderLayout.SOUTH);
        receiptFrame.setVisible(true);
    }

    private void openInvoiceWindow() {
        JFrame invoiceFrame = new JFrame("Emitir Factura");
        invoiceFrame.setSize(400, 300);
        invoiceFrame.setLocationRelativeTo(null);
        invoiceFrame.setLayout(new GridLayout(5, 2, 10, 10));

        JLabel rucLabel = new JLabel("RUC:");
        JTextField rucField = new JTextField();
        JLabel razonLabel = new JLabel("Razón Social:");
        JTextField razonField = new JTextField();
        JLabel direccionLabel = new JLabel("Dirección:");
        JTextField direccionField = new JTextField();

        JButton payButton = new JButton("Pagar y Generar Factura");
        payButton.addActionListener(e -> {
            String ruc = rucField.getText().trim();
            String razonSocial = razonField.getText().trim();
            String direccion = direccionField.getText().trim();

            if (!ruc.isEmpty() && !razonSocial.isEmpty() && !direccion.isEmpty()) {
                generateInvoiceFile(ruc, razonSocial, direccion);
                invoiceFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(invoiceFrame, "Por favor, complete todos los campos.");
            }
        });

        invoiceFrame.add(rucLabel);
        invoiceFrame.add(rucField);
        invoiceFrame.add(razonLabel);
        invoiceFrame.add(razonField);
        invoiceFrame.add(direccionLabel);
        invoiceFrame.add(direccionField);
        invoiceFrame.add(new JLabel()); // Espacio vacío
        invoiceFrame.add(payButton);

        invoiceFrame.setVisible(true);
    }

    private void generateReceiptFile(String clientName) {
        String fileType = "Boleta";
        int fileNumber = getNextFileNumber(fileType);
        String fileName = fileType + " N°" + fileNumber + " - " + clientName + ".txt";
        writeToFile(fileName, fileType, "Cliente: " + clientName);
    }

    private void generateInvoiceFile(String ruc, String razonSocial, String direccion) {
        String fileType = "Factura";
        int fileNumber = getNextFileNumber(fileType);
        String fileName = fileType + " N°" + fileNumber + " - " + razonSocial + ".txt";
        writeToFile(fileName, fileType, "RUC: " + ruc + "\nRazón Social: " + razonSocial + "\nDirección: " + direccion);
    }

    private int getNextFileNumber(String fileType) {
        int number = 1;
        while (new File(fileType + " N°" + number + ".txt").exists()) {
            number++;
        }
        return number;
    }

    private void writeToFile(String fileName, String fileType, String clientInfo) {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime now = LocalDateTime.now();
    String date = dtf.format(now);

    try (FileWriter writer = new FileWriter(fileName)) {
        // Encabezado de la empresa
        writer.write("========================================\n");
        writer.write("                HOME MARKET              \n");
        writer.write("       ¡Tu tienda de confianza!         \n");
        writer.write("========================================\n");
        writer.write("Fecha y Hora: " + date + "\n");
        writer.write("Tipo de Comprobante: " + fileType + "\n");
        writer.write("========================================\n");

        // Datos del cliente
        writer.write(clientInfo + "\n");
        writer.write("========================================\n");

        // Tabla de productos
        writer.write("ID\tNombre\tCantidad\tP.Unit\tP.Neto\n");
        writer.write("----------------------------------------\n");

        double subtotal = 0.0;
        for (int i = 0; i < selectedItemsModel.getRowCount(); i++) {
            String id = selectedItemsModel.getValueAt(i, 0).toString();
            String name = selectedItemsModel.getValueAt(i, 1).toString();
            int quantity = Integer.parseInt(selectedItemsModel.getValueAt(i, 2).toString());
            double unitPrice = Double.parseDouble(selectedItemsModel.getValueAt(i, 3).toString());
            double netPrice = quantity * unitPrice;

            subtotal += netPrice;

            // Escribir fila
            writer.write(id + "\t" + name + "\t" + quantity + "\t" + unitPrice + "\t" + netPrice + "\n");
        }

        // Resumen de totales
        double igv = subtotal * 0.18; // IGV del 18%
        double total = subtotal + igv;

        writer.write("----------------------------------------\n");
        writer.write(String.format("Subtotal: %.2f\n", subtotal));
        writer.write(String.format("IGV (18%%): %.2f\n", igv));
        writer.write(String.format("Total: %.2f\n", total));
        writer.write("========================================\n");

        // Pie de página
        writer.write("Gracias por su compra en HOME MARKET\n");
        writer.write("¡Vuelva pronto!\n");

        JOptionPane.showMessageDialog(this, fileType + " generado exitosamente como " + fileName);
    } catch (IOException ex) {
        JOptionPane.showMessageDialog(this, "Error al generar el archivo: " + ex.getMessage());
    }
}

}
