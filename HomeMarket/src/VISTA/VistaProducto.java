package VISTA;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class VistaProducto extends JFrame {
    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtDescripcion;
    private JTextField txtPrecio;
    private JTextField txtStock;
    private JTextField txtTipo;
    private JTextField txtAdminUsername;
    private JTextField txtCategoriaId;
    private JTextField txtPromocionId;
    private JTextField txtporcentaje_descuento;

    private JButton btnAgregar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnBuscar;
    private JButton btnOrdenarPorPrecio;
    private JButton btnOrdenarPorNombre;
    private JButton btnRestaurar;

    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;

    public VistaProducto() {
        setTitle("Administración de Productos");
        setSize(1200, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setLayout(new BorderLayout(10, 10));

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout(20, 20));

        JPanel panelFormulario = new JPanel();
        panelFormulario.setLayout(new GridLayout(9, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK), 
            "Detalles del Producto", 
            0, 0, 
            new Font("Arial", Font.BOLD, 14), 
            new Color(0, 0, 255)
        ));

        panelFormulario.add(new JLabel("ID:"));
        txtId = new JTextField();
        panelFormulario.add(txtId);

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Descripción:"));
        txtDescripcion = new JTextField();
        panelFormulario.add(txtDescripcion);

        panelFormulario.add(new JLabel("Precio:"));
        txtPrecio = new JTextField();
        panelFormulario.add(txtPrecio);

        panelFormulario.add(new JLabel("Stock:"));
        txtStock = new JTextField();
        panelFormulario.add(txtStock);

        panelFormulario.add(new JLabel("Tipo:"));
        txtTipo = new JTextField();
        panelFormulario.add(txtTipo);

        panelFormulario.add(new JLabel("Admin:"));
        txtAdminUsername = new JTextField();
        panelFormulario.add(txtAdminUsername);

        panelFormulario.add(new JLabel("Categoría:"));
        txtCategoriaId = new JTextField();
        panelFormulario.add(txtCategoriaId);
        
        panelFormulario.add(new JLabel("Promocion:"));
        txtPromocionId = new JTextField();
        panelFormulario.add(txtPromocionId);
        
        panelFormulario.add(new JLabel("Descuento:"));
        txtporcentaje_descuento = new JTextField();
        txtporcentaje_descuento.setEditable(false);  
        panelFormulario.add(txtporcentaje_descuento);

        btnAgregar = new JButton("Agregar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnBuscar = new JButton("Buscar");
        btnOrdenarPorPrecio = new JButton("Ordenar por Precio");
        btnOrdenarPorNombre = new JButton("Ordenar por Nombre");
        btnRestaurar = new JButton("Restaurar");

        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(1, 7, 10, 10)); 
        panelBotones.add(btnAgregar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnOrdenarPorPrecio);
        panelBotones.add(btnOrdenarPorNombre);
        panelBotones.add(btnRestaurar);

        modeloTabla = new DefaultTableModel();
        tablaProductos = new JTable(modeloTabla);
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Descripción");
        modeloTabla.addColumn("Precio");
        modeloTabla.addColumn("Stock");
        modeloTabla.addColumn("Tipo");
        modeloTabla.addColumn("Admin");
        modeloTabla.addColumn("Categoría");
        modeloTabla.addColumn("Promocion");
        modeloTabla.addColumn("Descuento");

        tablaProductos.setFillsViewportHeight(true);
        tablaProductos.setRowHeight(30);
        tablaProductos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tablaProductos.getTableHeader().setBackground(new Color(135, 206, 250));
        tablaProductos.setFont(new Font("Arial", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(tablaProductos);

        ImageIcon imagenIcon = new ImageIcon("C:\\Users\\pc\\Pictures\\imgSuperMercado\\ProductoI.png");
        JLabel imagenLabel = new JLabel(imagenIcon);

        panelPrincipal.add(panelFormulario, BorderLayout.CENTER); 
        panelPrincipal.add(imagenLabel, BorderLayout.EAST); 

        add(panelPrincipal, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        tablaProductos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tablaProductos.getSelectedRow();
                if (row != -1) {
                    txtId.setText(tablaProductos.getValueAt(row, 0).toString());
                    txtNombre.setText(tablaProductos.getValueAt(row, 1).toString());
                    txtDescripcion.setText(tablaProductos.getValueAt(row, 2).toString());
                    txtPrecio.setText(tablaProductos.getValueAt(row, 3).toString());
                    txtStock.setText(tablaProductos.getValueAt(row, 4).toString());
                    txtTipo.setText(tablaProductos.getValueAt(row, 5).toString());
                    txtAdminUsername.setText(tablaProductos.getValueAt(row, 6).toString());
                    txtCategoriaId.setText(tablaProductos.getValueAt(row, 7).toString());
                    txtPromocionId.setText(tablaProductos.getValueAt(row, 8).toString());
                    txtporcentaje_descuento.setText(tablaProductos.getValueAt(row, 9).toString());
                }
            }
        });

        btnAgregar.addActionListener(e -> agregarProducto());
    }

    private void agregarProducto() {
        String promocionId = getPromocionId();
        double descuento = obtenerDescuento(promocionId);
        setPorcentaje_descuento(descuento);

        // Aquí se agregarían los demás campos de producto a la base de datos
    }

    private double obtenerDescuento(String promocionId) {
    double descuento = 0.0;
    try (Connection conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=inmobiliaria", "usuario", "contraseña")) {
        String sql = "SELECT descuento FROM Promociones WHERE promocion_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, promocionId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    descuento = rs.getDouble("descuento");
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        mostrarMensaje("Error al obtener el descuento");
    }
    return descuento;
}
    
    public void setPorcentaje_descuento(double descuento) {
    String descuentoTexto = String.valueOf(descuento);
    if (descuentoTexto.isEmpty()) {
        txtporcentaje_descuento.setText("0.0"); // o algún valor por defecto
    } else {
        txtporcentaje_descuento.setText(descuentoTexto);
    }
}
    
    
    // Getters y Setters
    public String getId() {
        return txtId.getText();
    }

    public String getNombre() {
        return txtNombre.getText();
    }

    public String getDescripcion() {
        return txtDescripcion.getText();
    }

    public double getPrecio() {
        return Double.parseDouble(txtPrecio.getText());
    }

    public int getStock() {
        return Integer.parseInt(txtStock.getText());
    }

    public String getTipo() {
        return txtTipo.getText();
    }

    public String getAdminUsername() {
        return txtAdminUsername.getText();
    }

    public String getCategoriaId() {
        return txtCategoriaId.getText();
    }
    
    public String getPromocionId() {
        return txtPromocionId.getText();
    }
    
    public double getPorcentaje_descuento() {
    String descuentoTexto = txtporcentaje_descuento.getText();
    if (descuentoTexto.isEmpty()) {
        // Si el campo está vacío, puedes retornar un valor por defecto (0.0) o manejarlo de otra manera
        return 0.0;
    } else {
        try {
            return Double.parseDouble(descuentoTexto);
        } catch (NumberFormatException e) {
            mostrarMensaje("El porcentaje de descuento debe ser un número válido.");
            return 0.0;  // Retorna un valor por defecto en caso de error
        }
    }
}

    public void setNombre(String nombre) {
        txtNombre.setText(nombre);
    }

    public void setDescripcion(String descripcion) {
        txtDescripcion.setText(descripcion);
    }

    public void setPrecio(double precio) {
        txtPrecio.setText(String.valueOf(precio));
    }

    public void setStock(int stock) {
        txtStock.setText(String.valueOf(stock));
    }

    public void setTipo(String tipo) {
        txtTipo.setText(tipo);
    }

    public void setAdminUsername(String adminUsername) {
        txtAdminUsername.setText(adminUsername);
    }

    public void setCategoriaId(String categoriaId) {
        txtCategoriaId.setText(categoriaId);
    }
    
    public void setPromocionId(String promocionId) {
        txtPromocionId.setText(promocionId);
    }
    
    //public void setPorcentaje_descuento(double porcentaje_descuento) {
        //txtporcentaje_descuento.setText(String.valueOf(porcentaje_descuento));
    //}

    public void setModeloTabla(DefaultTableModel modelo) {
        tablaProductos.setModel(modelo);
    }

    public void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtPrecio.setText("");
        txtStock.setText("");
        txtTipo.setText("");
        txtAdminUsername.setText("");
        txtCategoriaId.setText("");
        txtPromocionId.setText("");
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    // Métodos para añadir los listeners a los botones
    public void addAgregarListener(ActionListener listener) {
        btnAgregar.addActionListener(listener);
    }

    public void addModificarListener(ActionListener listener) {
        btnModificar.addActionListener(listener);
    }

    public void addEliminarListener(ActionListener listener) {
        btnEliminar.addActionListener(listener);
    }

    public void addBuscarListener(ActionListener listener) {
        btnBuscar.addActionListener(listener);
    }

    public void addOrdenarPrecioListener(ActionListener listener) {
        btnOrdenarPorPrecio.addActionListener(listener);
    }

    public void addOrdenarNombreListener(ActionListener listener) {
        btnOrdenarPorNombre.addActionListener(listener);
    }

    // Método para añadir el listener al botón Restaurar
    public void addRestaurarListener(ActionListener listener) {
        btnRestaurar.addActionListener(listener);
    }
    
    
    
}



