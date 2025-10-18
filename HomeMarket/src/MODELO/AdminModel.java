/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MODELO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminModel {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=SuperrMercadoo;encrypt=true;trustServerCertificate=true";
    private static final String USER = "GerardoThoz";
    private static final String PASSWORD = "gerardo13xdd";

    public boolean authenticate(String username, String password) {
        boolean isValid = false;
        String query = "SELECT * FROM Administrador WHERE username = ? AND password = ?";
        
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
             
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                isValid = true; // Credenciales válidas
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isValid;
    }
}

