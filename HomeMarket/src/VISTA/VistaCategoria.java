/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VISTA;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.event.*;
import java.util.ArrayList;
public class VistaCategoria extends JFrame {
    private JTextField txtId, txtNombre, txtDescripcion;
    private JButton btnAgregar, btnModificar, btnEliminar, btnBuscar, btnOrdenarNombre;
    private JTable tablaCategorias;
    private DefaultTableModel modeloTabla;
    private JButton BtnContarCategorias;
    private JTree treeCategorias;  // JTree para mostrar las categorías en forma de árbol

    public VistaCategoria() {
        setTitle("Gestión de Categorías");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        

        // Panel para los campos de texto
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new BorderLayout(5, 10));

        // Panel para los campos de entrada
        JPanel panelCampos = new JPanel(new GridLayout(4, 2, 5, 5));  
        // Cambiar color del texto "Detalles de la Categoría" a azul
        panelCampos.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK), 
            "Detalles de la Categoría", 
            0, 0, 
            new Font("Arial", Font.BOLD, 14), 
            new Color(0, 0, 255) // Azul
        ));

        panelCampos.add(new JLabel("ID:"));
        txtId = new JTextField();
        panelCampos.add(txtId);

        panelCampos.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelCampos.add(txtNombre);

        panelCampos.add(new JLabel("Descripción:"));
        txtDescripcion = new JTextField();
        panelCampos.add(txtDescripcion);

        // Panel para la imagen (opcional)
        JPanel panelImagen = new JPanel();
        JLabel lblImagen = new JLabel(new ImageIcon("C:\\\\Users\\\\pc\\\\Pictures\\\\imgSuperMercado\\\\IconoCategoria.png"));
        panelImagen.add(lblImagen);

        panelFormulario.add(panelCampos, BorderLayout.CENTER);
        panelFormulario.add(panelImagen, BorderLayout.EAST);
        add(panelFormulario, BorderLayout.NORTH);

        // Tabla
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Descripción");
        tablaCategorias = new JTable(modeloTabla);
        tablaCategorias.setFillsViewportHeight(true);
        tablaCategorias.setRowHeight(30);
        tablaCategorias.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tablaCategorias.getTableHeader().setBackground(new Color(135, 206, 250));
        tablaCategorias.setFont(new Font("Arial", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(tablaCategorias);

        // Panel para los botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        btnAgregar = new JButton("Agregar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnBuscar = new JButton("Buscar");
        btnOrdenarNombre = new JButton("Ordenar por Nombre");
        BtnContarCategorias = new JButton("Contar Categorías");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnOrdenarNombre);
        panelBotones.add(BtnContarCategorias);

        // JTree para mostrar categorías
        treeCategorias = new JTree();
        JScrollPane treeScroll = new JScrollPane(treeCategorias);
        treeScroll.setPreferredSize(new Dimension(300, 500));  // Ajuste el tamaño del árbol
        add(treeScroll, BorderLayout.WEST);  // Ubicar el árbol a la izquierda

        // Agregar componentes al JFrame
        add(panelBotones, BorderLayout.SOUTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Métodos para obtener datos de los campos y otras funciones
    public JTextField getTxtId() {
        return txtId;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public JTextField getTxtDescripcion() {
        return txtDescripcion;
    }

    public JButton getBtnAgregar() {
        return btnAgregar;
    }

    public JButton getBtnModificar() {
        return btnModificar;
    }

    public JButton getBtnEliminar() {
        return btnEliminar;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnOrdenarNombre() {
        return btnOrdenarNombre;
    }

    public JTable getTablaCategorias() {
        return tablaCategorias;
    }

    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }

    public JButton getBtnContarCategorias() {
        return BtnContarCategorias;
    }

    public JTree getTreeCategorias() {
        return treeCategorias;
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
    
    
}









