package CONTROLADOR;

import VISTA.VistaInventario;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ControladorInventario {
    private VistaInventario vista;
    private Connection conexion;

    public ControladorInventario(VistaInventario vista, Connection conexion) {
        this.vista = vista;
        this.conexion = conexion;

        // Set up button actions
        vista.getBtnBuscar().addActionListener(e -> buscarInventario());
        vista.getBtnOrdenar().addActionListener(e -> ordenarInventario());
        vista.getBtnSalir().addActionListener(e -> vista.dispose()); // Close window on 'Salir' button click

        cargarDatosTabla(); // Load data on initialization
    }

    // Method to search for inventory based on ID
    private void buscarInventario() {
        String idProducto = JOptionPane.showInputDialog("Ingrese el ID del Producto para buscar:");
        
        if (idProducto == null || idProducto.trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "ID del Producto no proporcionado.");
            return;
        }

        String sql = "SELECT prod.id, prod.nombre, prod.precio, prod.stock, prod.tipo, cat.nombre AS categoria, " +
                     "(prod.precio * prod.stock) AS total_productos " +
                     "FROM Producto prod " +
                     "LEFT JOIN Categoria cat ON prod.categoria_id = cat.id " +
                     "WHERE prod.id = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, idProducto);
            ResultSet rs = stmt.executeQuery();
            DefaultTableModel modelo = vista.getModeloTabla();
            modelo.setRowCount(0);

            // If inventory data is found, add it to the table
            if (rs.next()) {
                Object[] fila = new Object[7];
                fila[0] = rs.getString("id");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getDouble("precio");
                fila[3] = rs.getInt("stock");
                fila[4] = rs.getString("tipo");
                fila[5] = rs.getString("categoria");
                fila[6] = rs.getDouble("total_productos");
                modelo.addRow(fila);
            } else {
                JOptionPane.showMessageDialog(vista, "No se encontraron resultados para el ID Producto: " + idProducto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(vista, "Error al buscar inventario");
        }
    }

    // Method to order the inventory table by product name
    private void ordenarInventario() {
        String sql = "SELECT prod.id, prod.nombre, prod.precio, prod.stock, prod.tipo, cat.nombre AS categoria, " +
                     "(prod.precio * prod.stock) AS total_productos " +
                     "FROM Producto prod " +
                     "LEFT JOIN Categoria cat ON prod.categoria_id = cat.id " +
                     "ORDER BY prod.nombre";

        try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            DefaultTableModel modelo = vista.getModeloTabla();
            modelo.setRowCount(0);

            while (rs.next()) {
                Object[] fila = new Object[7];
                fila[0] = rs.getString("id");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getDouble("precio");
                fila[3] = rs.getInt("stock");
                fila[4] = rs.getString("tipo");
                fila[5] = rs.getString("categoria");
                fila[6] = rs.getDouble("total_productos");
                modelo.addRow(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(vista, "Error al ordenar inventario");
        }
    }

    // Method to load all inventory data into the table
    private void cargarDatosTabla() {
        String sql = "SELECT prod.id, prod.nombre, prod.precio, prod.stock, prod.tipo, cat.nombre AS categoria, " +
                     "(prod.precio * prod.stock) AS total_productos " +
                     "FROM Producto prod " +
                     "LEFT JOIN Categoria cat ON prod.categoria_id = cat.id";

        try (Statement stmt = conexion.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            DefaultTableModel modelo = vista.getModeloTabla();
            modelo.setRowCount(0);

            while (rs.next()) {
                Object[] fila = new Object[7];
                fila[0] = rs.getString("id");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getDouble("precio");
                fila[3] = rs.getInt("stock");
                fila[4] = rs.getString("tipo");
                fila[5] = rs.getString("categoria");
                fila[6] = rs.getDouble("total_productos");
                modelo.addRow(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(vista, "Error al cargar los datos de inventario");
        }
    }
}