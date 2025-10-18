/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLADOR;

import CONEXION.DatabaseConnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import MODELO.Categoria;
import MODELO.Empleado;
import MODELO.Inventario;
import MODELO.Producto;
import MODELO.Promocion;
import MODELO.Venta;

public class ControladorVenta {

    // Método para obtener todas las categorías
    public List<Categoria> obtenerCategorias() {
        List<Categoria> listaCategorias = null;
        
        try (Connection conexion = DatabaseConnection.getConnection()) {
            // Llamamos al método estático del modelo Categoria para obtener la lista
            listaCategorias = Categoria.obtenerTodas(conexion);
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al obtener las categorías de la base de datos.");
        }
        
        return listaCategorias;
    }
    
    
    // Método para obtener todos los empleados
    public List<Empleado> obtenerEmpleados() {
        List<Empleado> listaEmpleados = null;
        
        try (Connection conexion = DatabaseConnection.getConnection()) {
            // Llamamos al método estático del modelo Empleado para obtener la lista
            listaEmpleados = Empleado.obtenerTodos(conexion);
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al obtener los empleados de la base de datos.");
        }
        
        return listaEmpleados;
    }
    
    
    // Método para obtener todos los inventarios
    public List<Inventario> obtenerInventarios() {
        List<Inventario> listaInventarios = null;
        
        try (Connection conexion = DatabaseConnection.getConnection()) {
            // Llamamos al método estático del modelo Inventario para obtener la lista
            listaInventarios = Inventario.obtenerTodos(conexion);
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al obtener los inventarios de la base de datos.");
        }
        
        return listaInventarios;
    }
    
    
    // Método para obtener todos los productos de la base de datos
    public List<Producto> obtenerProductos() {
        List<Producto> listaProductos = null;
        
        try (Connection conexion = DatabaseConnection.getConnection()) {
            // Utilizamos el método obtenerTodos de la clase Producto
            listaProductos = Producto.obtenerTodos(conexion);
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al obtener los productos de la base de datos.");
        }
        
        return listaProductos;
    }
    
    
    public List<Producto> obtenerProductosConDescuento() {
    List<Producto> listaProductos = null;

    try (Connection conexion = DatabaseConnection.getConnection()) {
        // Obtenemos la lista de productos
        listaProductos = Producto.obtenerTodos(conexion);

        // Recorremos los productos para verificar promociones
        List<Promocion> listaPromociones = Promocion.obtenerTodos(conexion);

        for (Producto producto : listaProductos) {
            boolean tienePromocion = false;
            double precioOriginal = producto.getPrecio();

            // Buscar si el producto tiene promoción
            for (Promocion promocion : listaPromociones) {
                if (promocion.getId() == producto.getId()) {
                    tienePromocion = true;
                    // Aplica el descuento
                    double descuento = promocion.getDescuento(); // Porcentaje de descuento
                    producto.setPrecioConDescuento(precioOriginal - (precioOriginal * descuento / 100));
                    break;
                }
            }

            // Si no tiene promoción, el precio con descuento es el mismo
            if (!tienePromocion) {
                producto.setPrecioConDescuento(precioOriginal);
            }

            // Establece si tiene promoción
            producto.setTienePromocion(tienePromocion);
        }

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Error al obtener los productos con descuento.");
    }

    return listaProductos;
}
}