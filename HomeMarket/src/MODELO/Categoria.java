/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Categoria {
    private String id;
    private String nombre;
    private String descripcion;

    // Constructor
    public Categoria(String id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
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

    // Método para obtener todas las categorías de la base de datos
    public static List<Categoria> obtenerTodas(Connection conexion) throws SQLException {
        List<Categoria> categorias = new ArrayList<>();
        String query = "SELECT * FROM Categoria"; // La consulta a la base de datos

        try (PreparedStatement ps = conexion.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String id = rs.getString("id");
                String nombre = rs.getString("nombre");
                String descripcion = rs.getString("descripcion");
                Categoria categoria = new Categoria(id, nombre, descripcion);
                categorias.add(categoria);
            }
        }

        return categorias;
    }
    
    public static int contarCategoriasRecursivo(Connection conexion) throws SQLException {
        return contarCategoriasRecursivo(conexion, 0);
    }

    // Método recursivo
    private static int contarCategoriasRecursivo(Connection conexion, int contador) throws SQLException {
        String query = "SELECT COUNT(*) FROM Categoria";
        try (PreparedStatement ps = conexion.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                int totalCategorias = rs.getInt(1);
                // Si el total es mayor que 0, seguimos recursivamente
                if (totalCategorias > 0) {
                    return contador + totalCategorias;  // Agregar al contador actual
                }
            }
        }
        return contador;  // Retorna el número total de categorías
    }
    
}





