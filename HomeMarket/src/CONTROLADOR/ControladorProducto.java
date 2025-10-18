/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLADOR;

import MODELO.Producto;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack; // Importar Stack para la pila
import VISTA.VistaProducto;

public class ControladorProducto {
    private VistaProducto vista;
    private Connection conexion;
    private List<Producto> listaProductos; // Lista para almacenar los productos
    private Stack<Producto> pilaProductosEliminados; // Pila para almacenar los productos eliminados

    public ControladorProducto(VistaProducto vista, Connection conexion) {
        this.vista = vista;
        this.conexion = conexion;
        this.listaProductos = new ArrayList<>(); // Inicializar lista de productos
        this.pilaProductosEliminados = new Stack<>(); // Inicializar la pila de productos eliminados

        cargarProductos();

        // Listeners para los botones
        vista.addAgregarListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarProducto();
            }
        });

        vista.addModificarListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modificarProducto();
            }
        });

        vista.addEliminarListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProducto();
            }
        });

        vista.addBuscarListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProducto();
            }
        });

        vista.addOrdenarPrecioListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ordenarPorPrecio();
            }
        });

        vista.addOrdenarNombreListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ordenarPorNombre();
            }
        });

        // Listener para restaurar un producto eliminado
        vista.addRestaurarListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restaurarProducto();
            }
        });
    }

    private void cargarProductos() {
        try {
            listaProductos = Producto.obtenerTodos(conexion); // Cargar productos una sola vez
            mostrarProductosEnTabla(listaProductos);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   private void agregarProducto() {
    Producto nuevoProducto = new Producto(
            vista.getId(),
            vista.getNombre(),
            vista.getDescripcion(),
            vista.getPrecio(),
            vista.getStock(),
            vista.getTipo(),
            vista.getAdminUsername(),
            vista.getCategoriaId(),
            vista.getPromocionId(),  // Este campo es importante para el trigger
            vista.getPorcentaje_descuento() // Puedes mantener este valor si es necesario
    );

    try {
        // Guardar el nuevo producto en la base de datos
        if (nuevoProducto.guardar(conexion)) {
            listaProductos.add(nuevoProducto); // Agregar el nuevo producto a la lista
            mostrarProductosEnTabla(listaProductos);
            vista.limpiarCampos();
        } else {
            vista.mostrarMensaje("Error al agregar el producto.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        vista.mostrarMensaje("Error en la base de datos al agregar el producto.");
    }
}


    private void modificarProducto() {
        String id = vista.getId();
        for (Producto producto : listaProductos) {
            if (producto.getId().equals(id)) {
                producto.setNombre(vista.getNombre());
                producto.setDescripcion(vista.getDescripcion());
                producto.setPrecio(vista.getPrecio());
                producto.setStock(vista.getStock());
                producto.setTipo(vista.getTipo());
                producto.setAdminUsername(vista.getAdminUsername());
                producto.setCategoriaId(vista.getCategoriaId());
                producto.setPromocionId(vista.getPromocionId());
                producto.setPorcentaje_descuento(vista.getPorcentaje_descuento());
                
                try {
                    // Actualizar el producto en la base de datos
                    if (producto.actualizar(conexion)) {
                        mostrarProductosEnTabla(listaProductos);
                        vista.limpiarCampos();
                    } else {
                        vista.mostrarMensaje("Error al modificar el producto.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    vista.mostrarMensaje("Error en la base de datos al modificar el producto.");
                }
                break;
            }
        }
    }

    private void eliminarProducto() {
        String id = vista.getId();
        for (Producto producto : listaProductos) {
            if (producto.getId().equals(id)) {
                try {
                    // Eliminar el producto de la base de datos
                    if (producto.eliminar(conexion)) {
                        // Agregar el producto a la pila antes de eliminarlo de la lista
                        pilaProductosEliminados.push(producto);
                        listaProductos.remove(producto); // Eliminar de la lista
                        mostrarProductosEnTabla(listaProductos);
                        vista.limpiarCampos();
                        return;
                    } else {
                        vista.mostrarMensaje("Error al eliminar el producto.");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    vista.mostrarMensaje("Error en la base de datos al eliminar el producto.");
                }
                break;
            }
        }
    }

    private void restaurarProducto() {
        if (!pilaProductosEliminados.isEmpty()) {
            Producto productoRestaurado = pilaProductosEliminados.pop(); // Obtener el último producto eliminado
            try {
                // Restaurar el producto en la base de datos
                if (productoRestaurado.guardar(conexion)) {
                    listaProductos.add(productoRestaurado); // Agregar el producto restaurado a la lista
                    mostrarProductosEnTabla(listaProductos);
                } else {
                    vista.mostrarMensaje("Error al restaurar el producto.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                vista.mostrarMensaje("Error en la base de datos al restaurar el producto.");
            }
        } else {
            vista.mostrarMensaje("No hay productos eliminados para restaurar.");
        }
    }

    private void buscarProducto() {
        String id = vista.getId();
        for (Producto producto : listaProductos) {
            if (producto.getId().equals(id)) {
                vista.setNombre(producto.getNombre());
                vista.setDescripcion(producto.getDescripcion());
                vista.setPrecio(producto.getPrecio());
                vista.setStock(producto.getStock());
                vista.setTipo(producto.getTipo());
                vista.setAdminUsername(producto.getAdminUsername());
                vista.setCategoriaId(producto.getCategoriaId());
                vista.setPromocionId(producto.getPromocionId());
                vista.setPorcentaje_descuento(producto.getPorcentaje_descuento());
                return;
            }
        }
        vista.mostrarMensaje("Producto no encontrado.");
    }

    private void ordenarPorPrecio() {
        Producto.ordenarPorPrecioAscendente(listaProductos);
        mostrarProductosEnTabla(listaProductos);
    }

    private void ordenarPorNombre() {
        Producto.ordenarPorNombreDescendente(listaProductos);
        mostrarProductosEnTabla(listaProductos);
    }

    private void mostrarProductosEnTabla(List<Producto> productos) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Descripción");
        modelo.addColumn("Precio");
        modelo.addColumn("Stock");
        modelo.addColumn("Tipo");
        modelo.addColumn("Admin");
        modelo.addColumn("Categoría");
        modelo.addColumn("Promocion");
        modelo.addColumn("Descuento");

        for (Producto producto : productos) {
            modelo.addRow(new Object[]{
                    producto.getId(),
                    producto.getNombre(),
                    producto.getDescripcion(),
                    producto.getPrecio(),
                    producto.getStock(),
                    producto.getTipo(),
                    producto.getAdminUsername(),
                    producto.getCategoriaId(),
                    producto.getPromocionId(),
                    producto.getPorcentaje_descuento()
            });
        }
        vista.setModeloTabla(modelo);
    }
}


