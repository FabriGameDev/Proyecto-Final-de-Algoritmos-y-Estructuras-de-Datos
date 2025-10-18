package MODELO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Inventario {
    private String id;
    private String idProducto;
    private String productoNombre;
    private double precioUnitario;
    private int stock;
    private InventarioProducto cabeza;  // Head of the linked list for products

    // Constructor without calculating totalProducts
    public Inventario(String id, String idProducto, String productoNombre, double precioUnitario, int stock) {
        this.id = id;
        this.idProducto = idProducto;
        this.productoNombre = productoNombre;
        this.precioUnitario = precioUnitario;
        this.stock = stock;
        this.cabeza = null;  // Initially, the list is empty
    }

    // Method to add a product to the linked list
    public void agregarProducto(Producto producto) {
        InventarioProducto nuevoNodo = new InventarioProducto(producto);
        if (cabeza == null) {
            cabeza = nuevoNodo;  // If the list is empty, the new product is the first node
        } else {
            InventarioProducto actual = cabeza;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();  // Traverse to the last node
            }
            actual.setSiguiente(nuevoNodo);  // Add the new node at the end
        }
    }

    // Method to remove a product from the linked list by ID
    public boolean eliminarProducto(String idProducto) {
        InventarioProducto actual = cabeza;
        InventarioProducto anterior = null;

        while (actual != null && !actual.getProducto().getId().equals(idProducto)) {
            anterior = actual;
            actual = actual.getSiguiente();
        }

        if (actual == null) {
            return false; // Product not found
        }

        if (anterior == null) {
            cabeza = actual.getSiguiente();  // The product to remove is the first one
        } else {
            anterior.setSiguiente(actual.getSiguiente());  // Remove the node
        }

        return true;
    }

    // Method to update a product in the list
    public boolean actualizarProducto(Producto producto) {
        InventarioProducto actual = cabeza;

        while (actual != null) {
            if (actual.getProducto().getId().equals(producto.getId())) {
                actual.getProducto().modificarProducto(producto.getNombre(), producto.getDescripcion(),
                        producto.getPrecio(), producto.getStock(), producto.getTipo(),
                        producto.getAdminUsername(), producto.getCategoriaId(), producto.getPromocionId(),producto.getPorcentaje_descuento());
                return true;
            }
            actual = actual.getSiguiente();
        }

        return false;  // Product not found
    }

    // Method to calculate the total value of products in inventory
    public double calcularTotalInventario() {
        double total = 0;
        InventarioProducto actual = cabeza;
        while (actual != null) {
            total += actual.getProducto().getPrecio() * actual.getProducto().getStock();
            actual = actual.getSiguiente();
        }
        return total;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getIdProducto() { return idProducto; }
    public void setIdProducto(String idProducto) { this.idProducto = idProducto; }
    public String getProductoNombre() { return productoNombre; }
    public void setProductoNombre(String productoNombre) { this.productoNombre = productoNombre; }
    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public InventarioProducto getCabeza() { return cabeza; }
    public void setCabeza(InventarioProducto cabeza) { this.cabeza = cabeza; }

    // Static method to fetch all inventory items with product details from the database
    public static List<Inventario> obtenerTodos(Connection conexion) throws SQLException {
        List<Inventario> inventarios = new ArrayList<>();
        String sql = "SELECT inv.id, inv.id_producto, prod.nombre AS producto_nombre, "
                   + "prod.precio AS precio_unitario, prod.stock "
                   + "FROM Inventario inv "
                   + "JOIN Producto prod ON inv.id_producto = prod.id";

        try (PreparedStatement ps = conexion.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String id = rs.getString("id");
                String idProducto = rs.getString("id_producto");
                String productoNombre = rs.getString("producto_nombre");
                double precioUnitario = rs.getDouble("precio_unitario");
                int stock = rs.getInt("stock");

                Inventario inventario = new Inventario(id, idProducto, productoNombre, precioUnitario, stock);
                inventarios.add(inventario);
            }
        }

        return inventarios;
    }

    // Save a new inventory record to the database
    public boolean guardar(Connection conexion) throws SQLException {
        String sql = "INSERT INTO Inventario (id, id_producto) VALUES (?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, idProducto);
            return ps.executeUpdate() > 0;
        }
    }

    // Update an inventory record in the database
    public boolean actualizar(Connection conexion) throws SQLException {
        String sql = "UPDATE Inventario SET id_producto = ? WHERE id = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, idProducto);
            ps.setString(2, id);
            return ps.executeUpdate() > 0;
        }
    }

    // Delete an inventory record from the database
    public boolean eliminar(Connection conexion) throws SQLException {
        String sql = "DELETE FROM Inventario WHERE id = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}