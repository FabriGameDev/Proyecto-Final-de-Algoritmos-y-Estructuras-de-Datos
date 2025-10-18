package VISTA;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VistaInventario extends JFrame {

    private JTable tablaInventario;
    private DefaultTableModel modeloTabla;
    private JButton btnBuscar, btnOrdenar, btnSalir;

    public VistaInventario(Connection conexion) {
        setTitle("Visualización de Stock de Inventarios");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        inicializarComponentes();
        cargarDatosTabla(conexion);  // Load inventory data into the table
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));

        // Initialize table with read-only model and columns
        modeloTabla = new DefaultTableModel(new String[]{
                "ID", "Nombre", "Precio", "Stock", "Tipo", "Categoria", "Total Productos"}, 0);
        tablaInventario = new JTable(modeloTabla);
        tablaInventario.setEnabled(false);  // Make table read-only

        tablaInventario.setFillsViewportHeight(true);
        tablaInventario.setRowHeight(30);
        tablaInventario.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tablaInventario.getTableHeader().setBackground(new Color(135, 206, 250));
        tablaInventario.setFont(new Font("Arial", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(tablaInventario);

        // Panel for buttons
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnBuscar = new JButton("Buscar");
        btnOrdenar = new JButton("Ordenar");
        btnSalir = new JButton("Salir");

        panelBotones.add(btnBuscar);
        panelBotones.add(btnOrdenar);
        panelBotones.add(btnSalir);

        // Add components to frame
        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // Action for 'Salir' button to close the window
        btnSalir.addActionListener(e -> dispose());
    }

    // Method to load data into the inventory table
    private void cargarDatosTabla(Connection conexion) {
        String sql = "SELECT prod.id, prod.nombre, prod.precio, prod.stock, prod.tipo, cat.nombre AS categoria, " +
                     "(prod.precio * prod.stock) AS total_productos " +
                     "FROM Producto prod " +
                     "LEFT JOIN Categoria cat ON prod.categoria_id = cat.id";
        
        try (PreparedStatement stmt = conexion.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            modeloTabla.setRowCount(0); // Clear the table

            while (rs.next()) {
                Object[] fila = new Object[7];
                fila[0] = rs.getString("id");                   // ID
                fila[1] = rs.getString("nombre");               // Nombre
                fila[2] = rs.getDouble("precio");               // Precio
                fila[3] = rs.getInt("stock");                   // Stock
                fila[4] = rs.getString("tipo");                 // Tipo
                fila[5] = rs.getString("categoria");            // Categoria
                fila[6] = rs.getDouble("total_productos");      // Total Productos (Precio * Stock)
                modeloTabla.addRow(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los datos de inventario");
        }
    }

    // Getters for accessing components in the controller
    public JButton getBtnBuscar() { return btnBuscar; }
    public JButton getBtnOrdenar() { return btnOrdenar; }
    public JButton getBtnSalir() { return btnSalir; }
    public DefaultTableModel getModeloTabla() { return modeloTabla; }
}