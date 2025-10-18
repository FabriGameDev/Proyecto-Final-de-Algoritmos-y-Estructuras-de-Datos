package VISTA;

import CONEXION.DatabaseConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.sql.*;

public class VistaVenta extends JFrame {
    private JTable catalogTable;
    private JTable selectedItemsTable;
    private DefaultTableModel catalogModel;
    private DefaultTableModel selectedItemsModel;
    private JButton payButton;
    private JButton clearButton; // Nuevo botón para limpiar la tabla
    private JButton backButton;
    private final DecimalFormat solesFormat = new DecimalFormat ("S/ #,##0.00");
    private boolean isProcessingSelection = false;
    
    private String formatCurrency(double value){
        return solesFormat.format(value);
    }
    
    public VistaVenta() {
        setTitle("Venta - Catálogo de Productos");
        setSize(1250, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // Configuración del fondo general de la ventana
        getContentPane().setBackground(new Color(240, 248, 255));

        // Panel Izquierdo - Catálogo
        catalogModel = new DefaultTableModel(new String[]{"ID", "Nombre", "Stock", "Precio Unitario"}, 0);
        catalogTable = new JTable(catalogModel);
        catalogTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        catalogTable.setRowHeight(25);
        catalogTable.setSelectionBackground(new Color(135, 206, 250));
        catalogTable.setSelectionForeground(Color.WHITE);

        loadCatalogData(); // Carga los productos con stock desde la base de datos

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Catálogo de Productos"));
        leftPanel.add(new JScrollPane(catalogTable), BorderLayout.CENTER);
        leftPanel.setBackground(new Color(240, 248, 255));
        add(leftPanel, BorderLayout.WEST);

        // Panel Derecho - Artículos Seleccionados
        selectedItemsModel = new DefaultTableModel(new String[]{"ID", "Nombre", "Cantidad", "Precio Unitario", "Precio Neto", "Descuento"}, 0);
        selectedItemsTable = new JTable(selectedItemsModel);
        selectedItemsTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        selectedItemsTable.setRowHeight(25);
        selectedItemsTable.setSelectionBackground(new Color(135, 206, 250));
        selectedItemsTable.setSelectionForeground(Color.WHITE);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Artículos Seleccionados"));
        rightPanel.add(new JScrollPane(selectedItemsTable), BorderLayout.CENTER);
        rightPanel.setBackground(new Color(240, 248, 255));
        add(rightPanel, BorderLayout.CENTER);

        // Panel de Botones
        JPanel buttonPanel = new JPanel();

        // Botón de Pago
        payButton = new JButton("Pagar");
        payButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        payButton.setBackground(new Color(0, 123, 167)); // Azul marino
        payButton.setForeground(Color.WHITE);
        payButton.setFocusPainted(false);
        payButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 76, 102), 2, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        ActionListener payButtonListener = new PayButtonListener();
        payButton.addActionListener(payButtonListener);

        // Botón Limpiar
        clearButton = new JButton("Limpiar");
        clearButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        clearButton.setBackground(new Color(220, 53, 69)); // Rojo para limpiar
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);
        clearButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(139, 0, 0), 2, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        // ActionListener para limpiar la tabla de artículos seleccionados
        clearButton.addActionListener(e -> {
            selectedItemsModel.setRowCount(0); // Elimina todas las filas de la tabla
        });
       
        backButton = new JButton("Regresar");
        backButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        backButton.setBackground(new Color(255, 193, 7)); // Amarillo para el botón de regresar
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(204, 145, 0), 2, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        backButton.addActionListener(e -> {
            dispose(); 
            new SelectionView().setVisible(true);
        });


        // Añadir botones al panel de botones
        buttonPanel.add(payButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(backButton);
        // Acción para cerrar la ventana actual al presionar "Regresar"

        buttonPanel.setBackground(new Color(240, 248, 255)); // Color de fondo
        add(buttonPanel, BorderLayout.SOUTH);

        // Catalog selection
        // Variable para controlar que solo se procese un evento a la vez

        catalogTable.getSelectionModel().addListSelectionListener(event -> {
    if (!event.getValueIsAdjusting() && !isProcessingSelection) {
        try {
            isProcessingSelection = true;

            int selectedRow = catalogTable.getSelectedRow();
            if (selectedRow != -1) {
                String id = catalogModel.getValueAt(selectedRow, 0).toString();
                String name = catalogModel.getValueAt(selectedRow, 1).toString();
                double price = Double.parseDouble(catalogModel.getValueAt(selectedRow, 3).toString());

                boolean itemExists = false;

                // Verificar si el producto ya está en la tabla de artículos seleccionados
for (int i = 0; i < selectedItemsModel.getRowCount(); i++) {
    if (selectedItemsModel.getValueAt(i, 0).toString().equals(id)) {
        int currentQuantity = (int) selectedItemsModel.getValueAt(i, 2);
        double discount = obtenerDescuento(id) / 100;  // Para convertirlo a un valor decimal
        double priceWithDiscount = price * (1 - discount);  // Precio con descuento
        selectedItemsModel.setValueAt(currentQuantity + 1, i, 2);
        selectedItemsModel.setValueAt(formatCurrency((currentQuantity + 1) * priceWithDiscount), i, 4);
        itemExists = true;
        break;
    }
}

                // Si el producto no está en la lista de seleccionados, agregarlo
if (!itemExists) {
    int quantity = 1;
    double discount = obtenerDescuento(id) / 100;  // Obtener descuento y convertirlo en decimal
    double priceWithDiscount = price * (1 - discount);  // Precio con descuento
    double discountAmount = price - priceWithDiscount;  // Calcular cuánto se está descontando
    double netPrice = quantity * priceWithDiscount;  // Precio neto (sin aplicar más descuentos)
    
    // Añadir la fila correctamente
    selectedItemsModel.addRow(new Object[]{
        id, name, quantity, price, netPrice, formatCurrency(discountAmount) // Mostrar descuento en lugar de precio con descuento
    });
}
            }
        } finally {
            isProcessingSelection = false;
        }
    }
});


    }

    private void loadCatalogData() {
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            String query = "SELECT id, nombre, precio, stock FROM Producto WHERE stock > 0";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String idProducto = resultSet.getString("id");
                String nombreProducto = resultSet.getString("nombre");
                int stock = resultSet.getInt("stock");
                double precioUnitario = resultSet.getDouble("precio");

                catalogModel.addRow(new Object[]{idProducto, nombreProducto, stock, precioUnitario});
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar el catálogo desde la base de datos: " + e.getMessage());
        }
    }

    private void updateStockAfterPurchase() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String updateQuery = "UPDATE Producto SET stock = stock - ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);

            // Recorre cada fila en la tabla de artículos seleccionados
            for (int i = 0; i < selectedItemsModel.getRowCount(); i++) {
                String idProducto = selectedItemsModel.getValueAt(i, 0).toString();
                int cantidadComprada = Integer.parseInt(selectedItemsModel.getValueAt(i, 2).toString());

                // Configura los parámetros para el prepared statement
                preparedStatement.setInt(1, cantidadComprada);
                preparedStatement.setString(2, idProducto);

                // Ejecuta la actualización para cada producto en la lista de compra
                preparedStatement.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al actualizar el stock en la base de datos: " + e.getMessage());
        }
    }

    // Método mostrar() para hacer visible el frame
    public void mostrar() {
        setVisible(true);
    }

    // Clase interna para manejar el evento del botón de pago
    private class PayButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Validar que haya productos seleccionados
            if (selectedItemsModel.getRowCount() > 0) {
                // Actualiza el stock después de la compra
                updateStockAfterPurchase();

                // Abre la ventana de pago para procesar el pago
                VistaPago pagoWindow = new VistaPago(selectedItemsModel);
                pagoWindow.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(VistaVenta.this, "No hay productos seleccionados para pagar.");
            }
        }
    }
    
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=vamosaprobar;encrypt=true;trustServerCertificate=true";
private static final String USER = "GerardoThoz";
private static final String PASSWORD = "gerardo13xdd";

// Método para obtener el descuento del producto
private double obtenerDescuento(String productId) {
    double descuento = 0.0;

    // Consulta SQL para obtener el descuento basado en el ID del producto
    String query = "SELECT porcentaje_descuento FROM Producto WHERE id = ?";

    // Usamos el try-with-resources para gestionar la conexión y el statement
    try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setString(1, productId);  // Asumiendo que productId es de tipo String
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            // Obtener el valor de la columna 'porcentaje_descuento'
            descuento = rs.getDouble("porcentaje_descuento");
        } else {
            // Si no se encuentra el producto o no tiene descuento, devolver 0
            System.out.println("Producto no encontrado o sin descuento");
        }

    } catch (SQLException e) {
        // Manejo de excepciones
        System.out.println("Error al obtener el descuento del producto: " + e.getMessage());
    }

    return descuento;
}

    
    
    
}