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

public class Promocion {
    private String id;
    private String descripcion;
    private double descuento;

    public Promocion(String id, String descripcion, double descuento) {
        this.id = id;
        this.descripcion = descripcion;
        this.descuento = descuento;
    }

    // Métodos getters y setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    // Método para agregar una promoción a la base de datos
    public static void agregarPromocion(Connection conexion, Promocion promocion) throws SQLException {
        String sql = "INSERT INTO Promocion (id, descripcion, descuento) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, promocion.getId());
            stmt.setString(2, promocion.getDescripcion());
            stmt.setDouble(3, promocion.getDescuento());

            stmt.executeUpdate();
        }
    }

    // Método para obtener todas las promociones (para mostrar en la tabla)
    public static List<Promocion> obtenerTodos(Connection conexion) throws SQLException {
        String sql = "SELECT * FROM Promocion";
        List<Promocion> promociones = new ArrayList<>();

        try (PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String id = rs.getString("id");
                String descripcion = rs.getString("descripcion");
                double descuento = rs.getDouble("descuento");

                Promocion promocion = new Promocion(id, descripcion, descuento);
                promociones.add(promocion);
            }
        }

        return promociones;
    }
    
    public static void eliminarPromocion(Connection conexion, String id) throws SQLException {
    String sql = "DELETE FROM Promocion WHERE id = ?";
    try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
        stmt.setString(1, id);
        stmt.executeUpdate();
    }
    }
    
    public static void modificarPromocion(Connection conexion, Promocion promocion) throws SQLException {
    String sql = "UPDATE Promocion SET descripcion = ?, descuento = ? = ? WHERE id = ?";
    try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
        stmt.setString(1, promocion.getDescripcion());
        stmt.setDouble(2, promocion.getDescuento());
        stmt.setString(3, promocion.getId());
        stmt.executeUpdate();
    }
}
    public static Promocion obtenerPorId(Connection conexion, String id) throws SQLException {
    String sql = "SELECT * FROM Promocion WHERE id = ?";
    try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
        stmt.setString(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new Promocion(rs.getString("id"), rs.getString("descripcion"), rs.getDouble("descuento"));
        }
        return null;
    }
}
}
