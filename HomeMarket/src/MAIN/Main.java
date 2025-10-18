package MAIN;

import CONEXION.DatabaseConnection;
import VISTA.SelectionView;
import java.sql.Connection;


public class Main {
    public static void main(String[] args) {
        Connection conexion = DatabaseConnection.getConnection();

        // Si la conexión es exitosa, muestra la vista de selección
        if (conexion != null) {
            SelectionView selectionView = new SelectionView();
            selectionView.mostrar();
        } else {
            System.out.println("No se pudo establecer la conexión a la base de datos.");
        }
    }
}