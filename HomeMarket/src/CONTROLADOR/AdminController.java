/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CONTROLADOR;

import MODELO.AdminModel;
import javax.swing.JOptionPane;

public class AdminController {
    private AdminModel model;

    public AdminController() {
        model = new AdminModel();
    }

    public boolean login(String username, String password) {
        try {
            // Verifica si los campos no están vacíos
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, complete ambos campos: Usuario y Contraseña.", "Error de Login", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            // Intenta autenticar el usuario
            if (model.authenticate(username, password)) {
                return true; // Autenticación exitosa
            } else {
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos. Intente nuevamente.", "Error de Login", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (Exception e) {
            // Captura cualquier excepción y muestra un mensaje de error genérico
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error inesperado. Por favor, intente nuevamente más tarde.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Opcional: imprime el stack trace en la consola para depuración
            return false;
        }
    }
}


