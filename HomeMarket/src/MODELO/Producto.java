/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Producto {

    public double getPrecioConDescuento() {
        return precioConDescuento;
    }

    public void setPrecioConDescuento(double precioConDescuento) {
        this.precioConDescuento = precioConDescuento;
    }

    public boolean isTienePromocion() {
        return tienePromocion;
    }

    public void setTienePromocion(boolean tienePromocion) {
        this.tienePromocion = tienePromocion;
    }
    
    private double precioConDescuento;
    private boolean tienePromocion;
    
    private String id;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;
    private String tipo;
    private String adminUsername; // Asumiendo que cada producto tiene un administrador asociado
    private String categoriaId; // Asumiendo que los productos tienen una categoría
    private String promocionId; // Asumiendo que los productos tienen una promocion
    private double porcentaje_descuento;
    
    public void disminuirStock(int cantidad) {
        this.stock -= cantidad;
    }
    // Pila para guardar el historial de cambios
    private Stack<Producto> historialCambios;
    
    // Constructor
    public Producto(String id, String nombre, String descripcion, double precio, int stock, String tipo, String adminUsername, String categoriaId,String promocionId,double porcentaje_descuento) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.tipo = tipo;
        this.adminUsername = adminUsername;
        this.categoriaId = categoriaId;
        this.promocionId = promocionId;
        this.porcentaje_descuento = porcentaje_descuento;
        this.historialCambios = new Stack<>();
    }
    
    
    
    // Métodos para realizar cambios en el producto
    public void modificarProducto(String nombre, String descripcion, double precio, int stock, String tipo, String adminUsername, String categoriaId,String promocionId, double porcentaje_descuento) {
        // Guardamos el estado actual antes de modificarlo
        historialCambios.push(this.clone());

        // Realizamos los cambios
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.tipo = tipo;
        this.adminUsername = adminUsername;
        this.categoriaId = categoriaId;
        this.promocionId = promocionId;
        this.porcentaje_descuento = porcentaje_descuento;
    }

    // Método para revertir al último estado guardado
    public void revertirCambio() {
        if (!historialCambios.isEmpty()) {
            Producto estadoAnterior = historialCambios.pop();
            this.id = estadoAnterior.id;
            this.nombre = estadoAnterior.nombre;
            this.descripcion = estadoAnterior.descripcion;
            this.precio = estadoAnterior.precio;
            this.stock = estadoAnterior.stock;
            this.tipo = estadoAnterior.tipo;
            this.adminUsername = estadoAnterior.adminUsername;
            this.categoriaId = estadoAnterior.categoriaId;
            this.promocionId = estadoAnterior.promocionId;
            this.porcentaje_descuento = estadoAnterior.porcentaje_descuento;
        }
    }

    // Método para clonar el producto actual (para usar en la pila)
    protected Producto clone() {
        return new Producto(this.id, this.nombre, this.descripcion, this.precio, this.stock, this.tipo, this.adminUsername, this.categoriaId,this.promocionId, this.porcentaje_descuento);
    }
    
    
    
    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    public String getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(String categoriaId) {
        this.categoriaId = categoriaId;
    }
    
    public String getPromocionId() {
        return promocionId;
    }

    public void setPromocionId(String promocionId) {
        this.promocionId = promocionId;
    }
    
    public double getPorcentaje_descuento() {
        return porcentaje_descuento;
    }

    public void setPorcentaje_descuento(double porcentaje_descuento) {
        this.porcentaje_descuento = porcentaje_descuento;
    }
    
    

    // Método estático para obtener todos los productos
    public static List<Producto> obtenerTodos(Connection conexion) throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT id, nombre, descripcion, precio, stock, tipo, admin_username, categoria_id, promocion_id, porcentaje_descuento FROM Producto";

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String id = rs.getString("id");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");
                double precio = rs.getDouble("precio");
                int stock = rs.getInt("stock");
                String tipo = rs.getString("tipo");
                String adminUsername = rs.getString("admin_username");
                String categoriaId = rs.getString("categoria_id");
                String promocionId = rs.getString("promocion_id");
                double porcentaje_descuento = rs.getDouble("porcentaje_descuento");

                Producto producto = new Producto(id, nombre, descripcion, precio, stock, tipo, adminUsername, categoriaId, promocionId, porcentaje_descuento);
                productos.add(producto);
            }
        }

        return productos;
    }

    // Guardar un nuevo producto en la base de datos
    public boolean guardar(Connection conexion) throws SQLException {
        String sql = "INSERT INTO Producto (id, nombre, descripcion, precio, stock, tipo, admin_username, categoria_id, promocion_id, porcentaje_descuento) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, nombre);
            ps.setString(3, descripcion);
            ps.setDouble(4, precio);
            ps.setInt(5, stock);
            ps.setString(6, tipo);
            ps.setString(7, adminUsername);
            ps.setString(8, categoriaId);
            ps.setString(9, promocionId);
            ps.setDouble(10, porcentaje_descuento);
            return ps.executeUpdate() > 0;
        }
    }

    // Actualizar un producto en la base de datos
    public boolean actualizar(Connection conexion) throws SQLException {
        String sql = "UPDATE Producto SET nombre = ?, descripcion = ?, precio = ?, stock = ?, tipo = ?, admin_username = ?, categoria_id = ?, promocion_id = ?, porcentaje_descuento = ? WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, descripcion);
            ps.setDouble(3, precio);
            ps.setInt(4, stock);
            ps.setString(5, tipo);
            ps.setString(6, adminUsername);
            ps.setString(7, categoriaId);
            ps.setString(8, promocionId);
            ps.setDouble(9, porcentaje_descuento);
            ps.setString(10, id);
            return ps.executeUpdate() > 0;
        }
    }

    // Eliminar un producto de la base de datos
    public boolean eliminar(Connection conexion) throws SQLException {
        String sql = "DELETE FROM Producto WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    // Ordenar los productos por precio de forma ascendente
    public static void ordenarPorPrecioAscendente(List<Producto> listaProductos) {
        listaProductos.sort((p1, p2) -> Double.compare(p1.getPrecio(), p2.getPrecio()));
    }

    // Ordenar los productos por nombre de forma descendente
    public static void ordenarPorNombreDescendente(List<Producto> listaProductos) {
        listaProductos.sort((p1, p2) -> p2.getNombre().compareTo(p1.getNombre()));
    }
    
    public static List<Producto> obtenerProductosPorCategoria(Connection conexion, String categoriaId) throws SQLException {
    List<Producto> productos = new ArrayList<>();
    String query = "SELECT * FROM Producto WHERE categoria_id = ?";

    try (PreparedStatement ps = conexion.prepareStatement(query)) {
        ps.setString(1, categoriaId); // Establecer el id de la categoría
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String id = rs.getString("id");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");
                double precio = rs.getDouble("precio");
                int stock = rs.getInt("stock");
                String tipo = rs.getString("tipo");
                String adminUsername = rs.getString("admin_username");
                String promocionId = rs.getString("promocion_id");
                double porcentaje_descuento = rs.getDouble("porcentaje_descuento");
                
                Producto producto = new Producto(id, nombre, descripcion, precio, stock, tipo, adminUsername, categoriaId, promocionId, porcentaje_descuento);
                productos.add(producto);
            }
        }
    }

    return productos;
    }
    
    
}
