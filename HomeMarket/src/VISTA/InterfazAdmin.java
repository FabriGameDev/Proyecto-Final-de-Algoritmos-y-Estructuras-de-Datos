/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VISTA;

import CONEXION.DatabaseConnection;
import CONTROLADOR.ControladorCategoria;
import CONTROLADOR.ControladorEmpleado;
import CONTROLADOR.ControladorInventario;
import CONTROLADOR.ControladorProducto;
import CONTROLADOR.ControladorPromocion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class InterfazAdmin {
    private JFrame frame;
    private JButton btnGestionarEmpleado;
    private JButton btnGestionarProducto;
    private JButton btnGestionarCategoria;
    private JButton btnGestionarPromocion;
    private JButton btnGestionarInventario;

    public InterfazAdmin() {
        frame = new JFrame("Interfaz Administrador");

        // Crear botones
        btnGestionarEmpleado = createButton("Administrar Empleado", "C:\\Users\\pc\\Pictures\\imgSuperMercado\\Empleadooo.png");
        btnGestionarProducto = createButton("Administrar Producto", "C:\\Users\\pc\\Pictures\\imgSuperMercado\\Productoo.png");
        btnGestionarCategoria = createButton("Administrar Categoria", "C:\\Users\\pc\\Pictures\\imgSuperMercado\\Categoriaa.png");
        btnGestionarPromocion = createButton("Administrar Promocion", "C:\\Users\\pc\\Pictures\\imgSuperMercado\\Promocionn.png");
        btnGestionarInventario = createButton("Administrar Inventario", "C:\\Users\\pc\\Pictures\\imgSuperMercado\\Inventarioo.png");

        // Configurar el panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10)); // GridLayout para los botones
        panel.setBackground(new Color(173, 216, 230)); // Fondo azul claro
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30)); // Espacios alrededor del panel

        // Añadir los botones al panel
        panel.add(btnGestionarEmpleado);
        panel.add(btnGestionarProducto);
        panel.add(btnGestionarCategoria);
        panel.add(btnGestionarPromocion);
        panel.add(btnGestionarInventario);

        // Establecer la conexión
        Connection conexion = DatabaseConnection.getConnection();

        // Acciones para cada botón
        btnGestionarEmpleado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (conexion != null) {
                    VistaEmpleado vista = new VistaEmpleado();
                    new ControladorEmpleado(vista, conexion);
                    vista.setVisible(true);
                } else {
                    System.out.println("No se pudo establecer la conexión a la base de datos.");
                }
            }
        });

        btnGestionarProducto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (conexion != null) {
                    VistaProducto vista = new VistaProducto();
                    new ControladorProducto(vista, conexion);
                    vista.setVisible(true);
                } else {
                    System.out.println("No se pudo establecer la conexión a la base de datos.");
                }
            }
        });

        btnGestionarCategoria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (conexion != null) {
                    VistaCategoria vista = new VistaCategoria();
                    new ControladorCategoria(vista, conexion);
                    vista.setVisible(true);
                } else {
                    System.out.println("No se pudo establecer la conexión a la base de datos.");
                }
            }
        });

        btnGestionarPromocion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (conexion != null) {
                    VistaPromocion vista = new VistaPromocion();
                    new ControladorPromocion(vista, conexion);
                    vista.setVisible(true);
                } else {
                    System.out.println("No se pudo establecer la conexión a la base de datos.");
                }
            }
        });

        btnGestionarInventario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (conexion != null) {
                    VistaInventario vista = new VistaInventario(conexion);
                    new ControladorInventario(vista, conexion);
                    vista.setVisible(true);
                } else {
                    System.out.println("No se pudo establecer la conexión a la base de datos.");
                }
            }
        });

        // Configuración del JFrame
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400); // Tamaño adecuado para la interfaz
        frame.setLocationRelativeTo(null); // Centrar la ventana
        frame.setVisible(true);
    }

    private JButton createButton(String text, String imagePath) {
        JButton button = new JButton(text);
        button.setIcon(new ImageIcon(imagePath)); // Añadir imagen al botón
        button.setHorizontalAlignment(SwingConstants.LEFT); // Alinear el texto y la imagen
        button.setPreferredSize(new Dimension(300, 50)); // Tamaño preferido del botón
        button.setBackground(new Color(70, 130, 180)); // Color de fondo del botón
        button.setForeground(Color.WHITE); // Color del texto
        button.setFont(new Font("Arial", Font.BOLD, 14)); // Fuente del texto
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Añadir espacio interior
        return button;
    }
}



