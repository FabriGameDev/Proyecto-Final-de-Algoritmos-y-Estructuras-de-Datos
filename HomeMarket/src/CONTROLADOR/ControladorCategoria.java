package CONTROLADOR;

import MODELO.Categoria;
import VISTA.VistaCategoria;
import java.sql.*;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import MODELO.Producto;
import javax.swing.JOptionPane;

public class ControladorCategoria {
    private VistaCategoria vista;
    private Connection conexion;

    public ControladorCategoria(VistaCategoria vista, Connection conexion)  {
        this.vista = vista;
        this.conexion = conexion;
        // Llamar al método para cargar las categorías en el árbol
        cargarCategoriasYProductosEnArbol();

        // Acción para agregar una nueva categoría
        vista.getBtnAgregar().addActionListener(e -> agregarCategoria());

        // Acción para modificar una categoría
        vista.getBtnModificar().addActionListener(e -> modificarCategoria());

        // Acción para eliminar una categoría
        vista.getBtnEliminar().addActionListener(e -> eliminarCategoria());

        // Acción para buscar una categoría
        vista.getBtnBuscar().addActionListener(e -> buscarCategoria());

        // Acción para ordenar las categorías por nombre
        vista.getBtnOrdenarNombre().addActionListener(e -> ordenarCategoriasPorNombre());
        
        // Acción para contar las categorías
        vista.getBtnContarCategorias().addActionListener(e -> contarCategorias());

        // Cargar categorías al iniciar la vista
        cargarCategorias();
        
        // Agregar un listener de selección de fila
        vista.getTablaCategorias().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int filaSeleccionada = vista.getTablaCategorias().getSelectedRow();
                    if (filaSeleccionada != -1) {
                        String id = (String) vista.getModeloTabla().getValueAt(filaSeleccionada, 0);
                        String nombre = (String) vista.getModeloTabla().getValueAt(filaSeleccionada, 1);
                        String descripcion = (String) vista.getModeloTabla().getValueAt(filaSeleccionada, 2);

                        vista.getTxtId().setText(id);
                        vista.getTxtNombre().setText(nombre);
                        vista.getTxtDescripcion().setText(descripcion);
                    }
                }
            }
        });
    }

    private void cargarCategorias() {
        try {
            List<Categoria> categorias = Categoria.obtenerTodas(conexion);
            DefaultTableModel modelo = (DefaultTableModel) vista.getTablaCategorias().getModel();
            modelo.setRowCount(0);  // Limpiar la tabla antes de llenarla

            for (Categoria categoria : categorias) {
                modelo.addRow(new Object[]{categoria.getId(), categoria.getNombre(), categoria.getDescripcion()});
            }
        } catch (SQLException ex) {
            mostrarError("Error al cargar las categorías: " + ex.getMessage());
        }
    }

    private void agregarCategoria() {
        String id = vista.getTxtId().getText();
        String nombre = vista.getTxtNombre().getText();
        String descripcion = vista.getTxtDescripcion().getText();

        if (id.isEmpty() || nombre.isEmpty() || descripcion.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String query = "INSERT INTO Categoria (id, nombre, descripcion) VALUES (?, ?, ?)";
            try (PreparedStatement ps = conexion.prepareStatement(query)) {
                ps.setString(1, id);
                ps.setString(2, nombre);
                ps.setString(3, descripcion);
                ps.executeUpdate();
                cargarCategorias();
                vista.mostrarMensaje("Categoría agregada con éxito");
            }
        } catch (SQLException ex) {
            mostrarError("Error al agregar la categoría: " + ex.getMessage());
        }
    }

    private void modificarCategoria() {
        String id = vista.getTxtId().getText();
        String nombre = vista.getTxtNombre().getText();
        String descripcion = vista.getTxtDescripcion().getText();

        if (id.isEmpty() || nombre.isEmpty() || descripcion.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String query = "UPDATE Categoria SET nombre = ?, descripcion = ? WHERE id = ?";
            try (PreparedStatement ps = conexion.prepareStatement(query)) {
                ps.setString(1, nombre);
                ps.setString(2, descripcion);
                ps.setString(3, id);
                ps.executeUpdate();
                cargarCategorias();
                vista.mostrarMensaje("Categoría modificada con éxito");
            }
        } catch (SQLException ex) {
            mostrarError("Error al modificar la categoría: " + ex.getMessage());
        }
    }

    private void eliminarCategoria() {
        String id = vista.getTxtId().getText();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese un ID válido para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String query = "DELETE FROM Categoria WHERE id = ?";
            try (PreparedStatement ps = conexion.prepareStatement(query)) {
                ps.setString(1, id);
                ps.executeUpdate();
                cargarCategorias();
                vista.mostrarMensaje("Categoría eliminada con éxito");
            }
        } catch (SQLException ex) {
            mostrarError("Error al eliminar la categoría: " + ex.getMessage());
        }
    }

    private void buscarCategoria() {
        String id = vista.getTxtId().getText();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese un ID para buscar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String query = "SELECT * FROM Categoria WHERE id = ?";
            try (PreparedStatement ps = conexion.prepareStatement(query)) {
                ps.setString(1, id);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String descripcion = rs.getString("descripcion");

                    vista.getTxtNombre().setText(nombre);
                    vista.getTxtDescripcion().setText(descripcion);

                    // Seleccionar la fila correspondiente en la tabla
                    DefaultTableModel modelo = (DefaultTableModel) vista.getTablaCategorias().getModel();
                    for (int i = 0; i < modelo.getRowCount(); i++) {
                        if (modelo.getValueAt(i, 0).equals(id)) {
                            vista.getTablaCategorias().setRowSelectionInterval(i, i);
                            break;
                        }
                    }
                } else {
                    vista.mostrarMensaje("Categoría no encontrada");
                }
            }
        } catch (SQLException ex) {
            mostrarError("Error al buscar la categoría: " + ex.getMessage());
        }
    }

    private void ordenarCategoriasPorNombre() {
        try {
            String query = "SELECT * FROM Categoria ORDER BY nombre";
            try (PreparedStatement ps = conexion.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {

                DefaultTableModel modelo = (DefaultTableModel) vista.getTablaCategorias().getModel();
                modelo.setRowCount(0);

                while (rs.next()) {
                    String id = rs.getString("id");
                    String nombre = rs.getString("nombre");
                    String descripcion = rs.getString("descripcion");
                    modelo.addRow(new Object[]{id, nombre, descripcion});
                }
            }
        } catch (SQLException ex) {
            mostrarError("Error al ordenar las categorías: " + ex.getMessage());
        }
    }

    private void contarCategorias() {
        try {
            int totalCategorias = Categoria.contarCategoriasRecursivo(conexion);  // Llamar al método recursivo
            vista.mostrarMensaje("Total de categorías: " + totalCategorias);
        } catch (SQLException ex) {
            mostrarError("Error al contar las categorías: " + ex.getMessage());
        }
    }

    private void cargarCategoriasYProductosEnArbol() {
        try {
            DefaultMutableTreeNode raiz = new DefaultMutableTreeNode("Categorías");

            List<Categoria> categorias = Categoria.obtenerTodas(conexion);

            for (Categoria categoria : categorias) {
                DefaultMutableTreeNode nodoCategoria = new DefaultMutableTreeNode(categoria.getNombre());
                List<Producto> productos = Producto.obtenerProductosPorCategoria(conexion, categoria.getId());

                for (Producto producto : productos) {
                    DefaultMutableTreeNode nodoProducto = new DefaultMutableTreeNode(producto.getNombre());
                    nodoCategoria.add(nodoProducto);
                }

                raiz.add(nodoCategoria);
            }

            DefaultTreeModel modeloArbol = new DefaultTreeModel(raiz);
            vista.getTreeCategorias().setModel(modeloArbol);

        } catch (SQLException ex) {
            mostrarError("Error al cargar el árbol de categorías y productos: " + ex.getMessage());
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
