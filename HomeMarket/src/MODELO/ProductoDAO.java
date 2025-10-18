/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductoDAO {
    private Connection conexion;

    public ProductoDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // Método para obtener el descuento por código de promoción
    public String obtenerDescuentoPorCodigo(String codigoPromocion) {
        String descuento = null; // Variable para almacenar el descuento
        String sql = "SELECT descuento FROM Promocion WHERE id = ?"; // SQL para obtener el descuento por código de promoción

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, codigoPromocion); // Establecer el valor del parámetro en la consulta

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    descuento = rs.getString("descuento"); // Obtener el descuento desde el ResultSet
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return descuento; // Retornar el descuento encontrado
    }

    // Otros métodos para manejar productos (guardar, actualizar, eliminar, etc.) van aquí
}


