/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VISTA;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class VistaEmpleado extends JFrame {
    private JTable tablaEmpleados;
    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtPuesto;
    private JTextField txtSalario;
    private JTextField txtFechaContratacion;
    private JTextField txtAdminUsername;
    private JButton btnAgregar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnBuscar;
    private JButton btnOrdenarSalario;
    private JButton btnOrdenarFecha;
    private JButton btnAñadirCola;
    private JButton btnRecuperarCola;
    private JButton btnListaCola;
    private JLabel lblImagen;

    private JList<String> listaCola;  // JList para mostrar empleados eliminados
    private DefaultListModel<String> colaModel; // Modelo de datos para la cola

    public VistaEmpleado() {
        setTitle("Administración de Empleados");
        setSize(1400, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Panel para el formulario de entrada y la imagen a la derecha
        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new BorderLayout(20, 20));  // Reducimos el espaciado entre el formulario y la imagen

        // Panel para los campos de entrada
        JPanel panelCampos = new JPanel(new GridLayout(7, 2, 5, 5));  // Menos espacio entre los campos
        panelCampos.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK), 
            "Detalles del Empleado", 
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

        panelCampos.add(new JLabel("Puesto:"));
        txtPuesto = new JTextField();
        panelCampos.add(txtPuesto);

        panelCampos.add(new JLabel("Salario:"));
        txtSalario = new JTextField();
        panelCampos.add(txtSalario);

        panelCampos.add(new JLabel("Fecha Contratación (YYYY-MM-DD):"));
        txtFechaContratacion = new JTextField();
        panelCampos.add(txtFechaContratacion);

        panelCampos.add(new JLabel("Admin Username:"));
        txtAdminUsername = new JTextField();
        panelCampos.add(txtAdminUsername);

        // Panel para la imagen
        JPanel panelImagen = new JPanel();
        lblImagen = new JLabel(new ImageIcon("C:\\\\Users\\\\pc\\\\Pictures\\\\imgSuperMercado\\\\IconoEmpleado.png"));  // Asegúrate de poner la ruta correcta
        panelImagen.add(lblImagen);

        panelFormulario.add(panelCampos, BorderLayout.CENTER);
        panelFormulario.add(panelImagen, BorderLayout.EAST);
        add(panelFormulario, BorderLayout.NORTH);

        // Crear la tabla de empleados con el modelo de datos
        String[] columnas = {"ID", "Nombre", "Puesto", "Salario", "Fecha Contratación", "Admin Username"};
        Object[][] datos = {};  // Inicialmente no hay datos
        tablaEmpleados = new JTable(datos, columnas) {
            public boolean isCellEditable(int row, int column) {
                return false;  // Hacer que la tabla no sea editable por defecto
            }
        };
        tablaEmpleados.setFillsViewportHeight(true);
        tablaEmpleados.setRowHeight(30);
        tablaEmpleados.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tablaEmpleados.getTableHeader().setBackground(new Color(135, 206, 250));
        tablaEmpleados.setFont(new Font("Arial", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(tablaEmpleados);
        add(scrollPane, BorderLayout.CENTER);

        // Panel para los botones debajo de la tabla
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        btnAgregar = new JButton("Agregar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnBuscar = new JButton("Buscar");
        btnOrdenarSalario = new JButton("Ordenar por Salario Ascendente");
        btnOrdenarFecha = new JButton("Ordenar por Fecha de Contratación Descendente");

        btnAñadirCola = new JButton("Añadir a Cola");
        btnRecuperarCola = new JButton("Recuperar de Cola");
        btnListaCola = new JButton("Lista Cola");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnOrdenarSalario);
        panelBotones.add(btnOrdenarFecha);
        panelBotones.add(btnAñadirCola);
        panelBotones.add(btnRecuperarCola);
        panelBotones.add(btnListaCola);

        add(panelBotones, BorderLayout.SOUTH);

        // Agregar el ListSelectionListener para la tabla
        tablaEmpleados.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = tablaEmpleados.getSelectedRow();
                    if (selectedRow != -1) {
                        // Obtener los datos de la fila seleccionada y mostrarlos en los campos de texto
                        txtId.setText(tablaEmpleados.getValueAt(selectedRow, 0).toString());
                        txtNombre.setText(tablaEmpleados.getValueAt(selectedRow, 1).toString());
                        txtPuesto.setText(tablaEmpleados.getValueAt(selectedRow, 2).toString());
                        txtSalario.setText(tablaEmpleados.getValueAt(selectedRow, 3).toString());
                        txtFechaContratacion.setText(tablaEmpleados.getValueAt(selectedRow, 4).toString());
                        txtAdminUsername.setText(tablaEmpleados.getValueAt(selectedRow, 5).toString());
                    }
                }
            }
        });
    }

    // Métodos para acceder a los campos de texto y botones
public String getId() { 
    return txtId.getText(); 
}

public String getNombre() { 
    return txtNombre.getText(); 
}

public String getPuesto() { 
    return txtPuesto.getText(); 
}

public String getSalario() { 
    return txtSalario.getText(); // Devuelve como String
}

public String getFechaContratacion() { 
    return txtFechaContratacion.getText(); 
}

public String getAdminUsername() { 
    return txtAdminUsername.getText(); 
}
    
    public void setId(String id) {
    txtId.setText(id);
}

public void setNombre(String nombre) {
    txtNombre.setText(nombre);
}

public void setPuesto(String puesto) {
    txtPuesto.setText(puesto);
}

public void setSalario(double salario) {
    txtSalario.setText(String.valueOf(salario));
}

public void setFechaContratacion(String fechaContratacion) {
    txtFechaContratacion.setText(fechaContratacion);
}

public void setAdminUsername(String adminUsername) {
    txtAdminUsername.setText(adminUsername);
}

    

    public JTable getTablaEmpleados() { return tablaEmpleados; }
    public void setTablaEmpleadosModel(javax.swing.table.TableModel model) {
        this.tablaEmpleados.setModel(model);
    }

    public JList<String> getListaCola() {
        return listaCola;
    }

    public DefaultListModel<String> getColaModel() {
        return colaModel;
    }

    // Métodos para añadir listeners a los botones
    public void addAgregarListener(ActionListener listener) { btnAgregar.addActionListener(listener); }
    public void addModificarListener(ActionListener listener) { btnModificar.addActionListener(listener); }
    public void addEliminarListener(ActionListener listener) { btnEliminar.addActionListener(listener); }
    public void addBuscarListener(ActionListener listener) { btnBuscar.addActionListener(listener); }
    public void addOrdenarSalarioListener(ActionListener listener) { btnOrdenarSalario.addActionListener(listener); }
    public void addOrdenarFechaListener(ActionListener listener) { btnOrdenarFecha.addActionListener(listener); }
    public void addAñadirAColaListener(ActionListener listener) { btnAñadirCola.addActionListener(listener); }
    public void addRecuperarDeColaListener(ActionListener listener) { btnRecuperarCola.addActionListener(listener); }
    public void addListaColaListener(ActionListener listener) { btnListaCola.addActionListener(listener); }

    // Métodos para limpiar los campos de entrada
    public void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtPuesto.setText("");
        txtSalario.setText("");
        txtFechaContratacion.setText("");
        txtAdminUsername.setText("");
    }

    public void seleccionarFila(int rowIndex) {
        tablaEmpleados.setRowSelectionInterval(rowIndex, rowIndex);
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }
}
