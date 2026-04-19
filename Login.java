import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Login extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public Login() {
        setTitle("Login");
        setSize(800, 500); // MÁS GRANDE
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // PANEL IZQUIERDO (DECORACIÓN)
        JPanel leftPanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(0, 120, 215),
                        getWidth(), getHeight(), new Color(0, 200, 255)
                );
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        leftPanel.setPreferredSize(new Dimension(350, 0));
        leftPanel.setLayout(new GridBagLayout());

        JLabel branding = new JLabel("MI SISTEMA");
        branding.setForeground(Color.WHITE);
        branding.setFont(new Font("Arial", Font.BOLD, 26));
        leftPanel.add(branding);

        // PANEL DERECHO (LOGIN)
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(245, 245, 245));
        rightPanel.setLayout(new GridBagLayout());

        JPanel box = new JPanel();
        box.setBackground(Color.WHITE);
        box.setLayout(new GridLayout(6,1,10,10));
        box.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // TÍTULO
        JLabel titulo = new JLabel("Iniciar Sesión", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(new Color(50,50,50));

        // USUARIO
        txtUsuario = new JTextField();
        txtUsuario.setBorder(BorderFactory.createTitledBorder("Usuario"));
        txtUsuario.setFont(new Font("Arial", Font.PLAIN, 14));

        // PASSWORD
        txtPassword = new JPasswordField();
        txtPassword.setBorder(BorderFactory.createTitledBorder("Contraseña"));
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 14));

        // BOTÓN
        btnLogin = new JButton("Ingresar");
        btnLogin.setFocusPainted(false);
        btnLogin.setBackground(new Color(0, 120, 215));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // HOVER
        btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogin.setBackground(new Color(0, 150, 255));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLogin.setBackground(new Color(0, 120, 215));
            }
        });

        btnLogin.addActionListener(e -> login());

        // ARMAR BOX
        box.add(titulo);
        box.add(new JLabel());
        box.add(txtUsuario);
        box.add(txtPassword);
        box.add(new JLabel());
        box.add(btnLogin);

        rightPanel.add(box);

        // AGREGAR A FRAME
        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void login() {
        String user = txtUsuario.getText();
        String pass = new String(txtPassword.getPassword());

        try {
            Connection con = Conexion.conectar();

            String sql = "SELECT * FROM UserN WHERE Nombre = ? AND Contrasena = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Bienvenido");

                int userId = rs.getInt("id_UserN");

                new Dashboard(userId);
                dispose();

            } else {
                JOptionPane.showMessageDialog(this, "Datos incorrectos");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}