package VISTA;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectionView extends JFrame {
    private JButton empleadoButton;
    private JButton ventaButton;

    public SelectionView() {
        setTitle("Seleccionar Opción");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        // Configuración del fondo de la ventana en azul suave
        getContentPane().setBackground(new Color(173, 216, 230));

        // Crear fuente personalizada
        Font buttonFont = new Font("Arial", Font.BOLD, 18);
        Font headerFont = new Font("Arial", Font.BOLD, 24); // Fuente para el texto de "HOME MARKET"

        // Cargar las imágenes
        ImageIcon empleadoIcon = new ImageIcon("C:\\Users\\pc\\Pictures\\imgSuperMercado\\IconoAdmin.png");
        ImageIcon ventaIcon = new ImageIcon("C:\\Users\\pc\\Pictures\\imgSuperMercado\\IconoRopa.png");

        // Redimensionar imágenes
        Image empleadoImg = empleadoIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        Image ventaImg = ventaIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);

        // Crear el JLabel para el título "HOME MARKET"
        JLabel headerLabel = new JLabel("HOME MARKET");
        headerLabel.setFont(headerFont);
        headerLabel.setForeground(new Color(0, 0, 0)); // Color del texto en azul petróleo
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER); // Centrar el texto

        // Botón EMPLEADO con imagen y estilo personalizado
        empleadoButton = crearBoton("ADMINISTRADOR", new ImageIcon(empleadoImg), buttonFont);
        empleadoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminView().mostrar(); // Llama al método mostrar de AdminView
                dispose(); // Cierra la ventana actual
            }
        });

        // Botón VENTA con imagen y estilo personalizado
        ventaButton = crearBoton("VENTA", new ImageIcon(ventaImg), buttonFont);
        ventaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VistaVenta().mostrar(); // Llama al método mostrar de VistaVenta
                dispose(); // Cierra la ventana actual
            }
        });

        // Configuración de diseño para centrar los elementos
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;  // Hacer que el texto ocupe el ancho completo
        add(headerLabel, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;  // Botones deben ocupar solo una columna
        add(empleadoButton, gbc);

        gbc.gridy = 2;
        add(ventaButton, gbc);
    }

    // Método para crear un botón con estilo
    private JButton crearBoton(String texto, ImageIcon icono, Font fuente) {
        JButton boton = new JButton(texto, icono);
        boton.setFont(fuente);
        boton.setBackground(new Color(0, 102, 153));  // Azul petróleo
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createLineBorder(new Color(0, 76, 102), 2, true)); // Borde oscuro y redondeado
        boton.setPreferredSize(new Dimension(200, 50));
        boton.setHorizontalTextPosition(SwingConstants.RIGHT);
        boton.setVerticalTextPosition(SwingConstants.CENTER);
        return boton;
    }

    // Método para hacer visible la ventana
    public void mostrar() {
        setVisible(true);
    }
}
