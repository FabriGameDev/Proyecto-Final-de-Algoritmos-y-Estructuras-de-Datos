/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLADOR;

import VISTA.VistaPromocion;
import MODELO.Promocion;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Collections;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

public class ControladorPromocion {
    private VistaPromocion vista;
    private Connection conexion;

    public ControladorPromocion(VistaPromocion vista, Connection conexion) {
        this.vista = vista;
        this.conexion = conexion;

        // Llamar a los métodos de inicialización
        cargarPromociones();
        agregarEventos();
    }

    private void cargarPromociones() {
        try {
            List<Promocion> promociones = Promocion.obtenerTodos(conexion);
            DefaultTableModel modelo = vista.getModeloTabla();

            // Limpiar la tabla antes de llenarla
            modelo.setRowCount(0);

            // Agregar las promociones a la tabla
            for (Promocion promo : promociones) {
                Object[] row = {
                    promo.getId(),
                    promo.getDescripcion(),
                    promo.getDescuento(),
                };
                modelo.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void agregarEventos() {
        // Evento de la tabla para la selección de filas
        vista.getTablaPromociones().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = vista.getTablaPromociones().getSelectedRow();
                if (row != -1) {
                    // Mostrar los datos de la fila seleccionada en los cuadros de texto
                    vista.getTxtId().setText(vista.getModeloTabla().getValueAt(row, 0).toString());
                    vista.getTxtDescripcion().setText(vista.getModeloTabla().getValueAt(row, 1).toString());
                    vista.getTxtDescuento().setText(vista.getModeloTabla().getValueAt(row, 2).toString());
                }
            }
        });

        // Evento para el botón de Agregar
        vista.getBtnAgregar().addActionListener(e -> {
            String id = vista.getTxtId().getText();
            String descripcion = vista.getTxtDescripcion().getText();
            String descuentoStr = vista.getTxtDescuento().getText();

            // Validar que los campos no estén vacíos
            if (id.isEmpty() || descripcion.isEmpty() || descuentoStr.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Todos los campos deben ser completados.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                double descuento = Double.parseDouble(descuentoStr);

                // Crear la nueva promoción
                Promocion nuevaPromocion = new Promocion(id, descripcion, descuento);

                // Agregarla a la base de datos
                Promocion.agregarPromocion(conexion, nuevaPromocion);

                // Actualizar la tabla
                cargarPromociones();

                // Limpiar los campos de texto
                vista.getTxtId().setText("");
                vista.getTxtDescripcion().setText("");
                vista.getTxtDescuento().setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "El descuento debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(vista, "Hubo un error al agregar la promoción.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Evento para el botón de Eliminar
        vista.getBtnEliminar().addActionListener(e -> {
            String id = vista.getTxtId().getText();

            // Validar que el campo ID no esté vacío
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Debe ingresar un ID para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                // Eliminar la promoción de la base de datos
                Promocion.eliminarPromocion(conexion, id);

                // Actualizar la tabla
                cargarPromociones();

                // Limpiar los campos de texto
                vista.getTxtId().setText("");
                vista.getTxtDescripcion().setText("");
                vista.getTxtDescuento().setText("");

                JOptionPane.showMessageDialog(vista, "Promoción eliminada correctamente.");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(vista, "Hubo un error al eliminar la promoción.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Evento para el botón de Modificar
        vista.getBtnModificar().addActionListener(e -> {
            String id = vista.getTxtId().getText();
            String descripcion = vista.getTxtDescripcion().getText();
            String descuentoStr = vista.getTxtDescuento().getText();

            // Validar que los campos no estén vacíos
            if (id.isEmpty() || descripcion.isEmpty() || descuentoStr.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Todos los campos deben ser completados.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                double descuento = Double.parseDouble(descuentoStr);

                // Crear el objeto de promoción modificado
                Promocion promocionModificada = new Promocion(id, descripcion, descuento);

                // Modificar la promoción en la base de datos
                Promocion.modificarPromocion(conexion, promocionModificada);

                // Actualizar la tabla
                cargarPromociones();

                // Limpiar los campos de texto
                vista.getTxtId().setText("");
                vista.getTxtDescripcion().setText("");
                vista.getTxtDescuento().setText("");

                JOptionPane.showMessageDialog(vista, "Promoción modificada correctamente.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "El descuento debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(vista, "Hubo un error al modificar la promoción.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Evento para el botón de Buscar
        vista.getBtnBuscar().addActionListener(e -> {
            String id = vista.getTxtId().getText();

            // Validar que el campo ID no esté vacío
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Debe ingresar un ID para buscar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                // Buscar la promoción en la base de datos
                Promocion promocion = Promocion.obtenerPorId(conexion, id);

                if (promocion != null) {
                    // Mostrar los datos en los campos de texto
                    vista.getTxtDescripcion().setText(promocion.getDescripcion());
                    vista.getTxtDescuento().setText(String.valueOf(promocion.getDescuento()));

                    // Buscar la fila que corresponde al ID y seleccionarla
                    DefaultTableModel modelo = vista.getModeloTabla();
                    for (int i = 0; i < modelo.getRowCount(); i++) {
                        if (modelo.getValueAt(i, 0).equals(id)) {
                            // Seleccionar la fila correspondiente
                            vista.getTablaPromociones().setRowSelectionInterval(i, i);
                            break;
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(vista, "No se encontró la promoción con ese ID.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(vista, "Hubo un error al buscar la promoción.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Evento para el botón de Ordenar por descuento
        vista.getBtnOrdenar().addActionListener(e -> {
            try {
                // Obtener todas las promociones
                List<Promocion> promociones = Promocion.obtenerTodos(conexion);
                
                // Ordenar las promociones por descuento de manera ascendente
                Collections.sort(promociones, (p1, p2) -> Double.compare(p1.getDescuento(), p2.getDescuento()));

                // Actualizar la tabla con las promociones ordenadas
                DefaultTableModel modelo = vista.getModeloTabla();
                modelo.setRowCount(0);  // Limpiar la tabla antes de agregar las promociones ordenadas

                // Agregar las promociones ordenadas
                for (Promocion promo : promociones) {
                    Object[] row = {
                        promo.getId(),
                        promo.getDescripcion(),
                        promo.getDescuento(),
                    };
                    modelo.addRow(row);
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(vista, "Hubo un error al ordenar las promociones.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}




