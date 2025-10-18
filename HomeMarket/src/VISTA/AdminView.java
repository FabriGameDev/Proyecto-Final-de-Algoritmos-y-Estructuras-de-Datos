package VISTA;

import CONTROLADOR.AdminController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminView {
    private AdminController controller;
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel messageLabel;

    public AdminView() {
        controller = new AdminController();
        frame = new JFrame("Admin Login");
        
        // Crear campos de texto con tamaños personalizados
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        messageLabel = new JLabel("");

        // Establecer tamaño preferido, máximo y mínimo para los cuadros de texto
        usernameField.setPreferredSize(new Dimension(150, 30));
        usernameField.setMaximumSize(new Dimension(150, 30));
        usernameField.setMinimumSize(new Dimension(150, 30));

        passwordField.setPreferredSize(new Dimension(150, 30));
        passwordField.setMaximumSize(new Dimension(150, 30));
        passwordField.setMinimumSize(new Dimension(150, 30));

        // Panel para los campos de texto y botones
        JPanel panelCampos = new JPanel();
        panelCampos.setLayout(new BoxLayout(panelCampos, BoxLayout.Y_AXIS));
        panelCampos.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelCampos.setOpaque(false); // Panel transparente

        // Añadir los componentes al panel
        panelCampos.add(Box.createVerticalStrut(50)); // Espacio superior
        panelCampos.add(new JLabel("Username:"));
        panelCampos.add(Box.createVerticalStrut(10)); // Espacio entre campo y texto
        panelCampos.add(usernameField);
        panelCampos.add(Box.createVerticalStrut(20)); // Espacio entre campos
        panelCampos.add(new JLabel("Password:"));
        panelCampos.add(Box.createVerticalStrut(10)); // Espacio entre campo y texto
        panelCampos.add(passwordField);
        panelCampos.add(Box.createVerticalStrut(30)); // Espacio entre campos y botón
        panelCampos.add(loginButton);
        panelCampos.add(Box.createVerticalStrut(20)); // Espacio entre botón y mensaje
        panelCampos.add(messageLabel);

        // Panel para la imagen
        JLabel backgroundLabel = new JLabel(new ImageIcon("C:\\Users\\pc\\Pictures\\imgSuperMercado\\adminn.png"));
        backgroundLabel.setPreferredSize(new Dimension(150, 150)); // Ajusta el tamaño de la imagen

        // Panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(173, 216, 230)); // Color de fondo azul claro
        mainPanel.add(backgroundLabel, BorderLayout.WEST); // Coloca la imagen al costado
        mainPanel.add(panelCampos, BorderLayout.CENTER); // Centra los campos

        // Configuración del frame
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300); // Tamaño adecuado para el login
        frame.setLocationRelativeTo(null); // Centra la ventana en la pantalla

        // Acción de login
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (controller.login(username, password)) {
                    messageLabel.setText("Acceso concedido.");
                    // Abrir la interfaz de administración después de login exitoso
                    new InterfazAdmin();
                    frame.setVisible(false); // Cerrar el frame de login
                } else {
                    messageLabel.setText("Acceso denegado. Credenciales incorrectas.");
                }
            }
        });
    }

    // Método mostrar() para hacer visible el frame
    public void mostrar() {
        frame.setVisible(true);
    }
}