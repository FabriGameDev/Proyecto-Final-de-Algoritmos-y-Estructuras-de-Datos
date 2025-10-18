/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VISTA;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class VistaPromocion extends JFrame {
    private JTable tablaPromociones;
    private JButton btnAgregar, btnEliminar, btnModificar, btnBuscar,btnOrdenar;
    private JTextField txtId, txtDescripcion, txtDescuento;
    private DefaultTableModel modeloTabla;

    public VistaPromocion() {
        setTitle("Gestión de Promociones");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        inicializarComponentes();
        

    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));

        // Panel principal que contendrá el formulario y la imagen
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout(20, 20));

        // Panel para el formulario de promoción
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(5, 2, 5, 5)); // Reducido el espaciado
        panelFormulario.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK), 
            "Detalles de la Promocion", 
            0, 0, 
            new Font("Arial", Font.BOLD, 14), 
            new Color(0, 0, 255) // Azul
        ));

        // Añadimos los campos al panel
        panelFormulario.add(new JLabel("ID:"));
        txtId = new JTextField();
        panelFormulario.add(txtId);

        panelFormulario.add(new JLabel("Descripción:"));
        txtDescripcion = new JTextField();
        panelFormulario.add(txtDescripcion);

        panelFormulario.add(new JLabel("Descuento:"));
        txtDescuento = new JTextField();
        panelFormulario.add(txtDescuento);

        // Crear los botones
        btnAgregar = new JButton("Agregar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnBuscar = new JButton("Buscar");
        btnOrdenar = new JButton("Ordenar Descuento");

        // Crear el panel de botones con GridBagLayout para alinearlos de forma más flexible
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(1, 4, 10, 10)); // Botones alineados en fila
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnOrdenar);

        // Tabla de promociones
        String[] columnNames = {"ID", "Descripción", "Descuento"};
        modeloTabla = new DefaultTableModel(columnNames, 0);
        tablaPromociones = new JTable(modeloTabla);

        // Ajustamos la tabla para que se vea más limpia
        tablaPromociones.setFillsViewportHeight(true);
        tablaPromociones.setRowHeight(30);
        tablaPromociones.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tablaPromociones.getTableHeader().setBackground(new Color(135, 206, 250));
        tablaPromociones.setFont(new Font("Arial", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(tablaPromociones);

        // Cargar una imagen si es necesario (opcional)
        ImageIcon imagenIcon = new ImageIcon("C:\\Users\\pc\\Pictures\\imgSuperMercado\\IconoPromocion.png"); // Ajusta la ruta de la imagen
        JLabel imagenLabel = new JLabel(imagenIcon);

        // Añadir los componentes al panel principal
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER); // Formulario en el centro
        panelPrincipal.add(imagenLabel, BorderLayout.EAST); // Imagen en el lado derecho

        // Añadir la tabla y los botones
        add(panelPrincipal, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    // Método para personalizar los botones (estilo y tamaño)
    private void customizeButton(JButton button) {
        button.setPreferredSize(new Dimension(140, 40)); // Tamaño de los botones
        button.setFont(new Font("Arial", Font.PLAIN, 12));
        button.setBackground(new Color(50, 150, 255));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        button.setFocusPainted(false);
    }

    // Métodos para obtener los botones, la tabla y los campos de texto
    public JButton getBtnAgregar() {
        return btnAgregar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public JButton getBtnModificar() {
        return btnModificar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }
    
    public JButton getBtnOrdenar() {
        return btnOrdenar;
    }

    public JTable getTablaPromociones() {
        return tablaPromociones;
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }

    public JTextField getTxtId() {
        return txtId;
    }

    public JTextField getTxtDescripcion() {
        return txtDescripcion;
    }

    public JTextField getTxtDescuento() {
        return txtDescuento;
    }
}


