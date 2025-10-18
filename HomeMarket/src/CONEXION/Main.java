/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package CONEXION;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        // Intenta obtener la conexión
        Connection connection = DatabaseConnection.getConnection();
        if (connection != null) {
            try {
                // Aquí puedes realizar operaciones en la base de datos, si es necesario
                System.out.println("Conexión probada exitosamente.");
                
                // Cierra la conexión después de terminar las operaciones
                connection.close(); 
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexion: " + e.getMessage());
            }
        } else {
            System.out.println("No se pudo establecer la conexion.");
        }
    }
}
