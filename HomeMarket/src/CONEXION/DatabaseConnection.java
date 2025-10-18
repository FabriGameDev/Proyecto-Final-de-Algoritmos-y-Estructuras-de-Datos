/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONEXION;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=vamosaprobar;encrypt=true;trustServerCertificate=true";
    private static final String USER = "GerardoThoz";
    private static final String PASSWORD = "gerardo13xdd";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Cargar el driver JDBC
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            // Establecer la conexión
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión establecida correctamente.");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver no encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
        return connection;
    }
}

