import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.HashSet;

public class Dashboard extends JFrame {

    private int userId;
    private JPanel contentPanel;

    public Dashboard(int userId) {
        this.userId = userId;

        setTitle("Sistema");
        setSize(1000, 600); // MÁS GRANDE
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 🔵 PANEL IZQUIERDO (MENÚ / ESTILO LOGIN)
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

        leftPanel.setPreferredSize(new Dimension(250, 0));
        leftPanel.setLayout(new BorderLayout());

        JLabel logo = new JLabel("MI SISTEMA", SwingConstants.CENTER);
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Arial", Font.BOLD, 20));
        logo.setBorder(BorderFactory.createEmptyBorder(20,10,20,10));

        leftPanel.add(logo, BorderLayout.NORTH);

        JPanel menu = new JPanel();
        menu.setOpaque(false);
        menu.setLayout(new GridLayout(10,1,10,10));
        menu.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        leftPanel.add(menu, BorderLayout.CENTER);

        // ⚪ PANEL DERECHO (CONTENIDO)
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(245,245,245));

        JLabel titulo = new JLabel("Dashboard", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setBorder(BorderFactory.createEmptyBorder(15,0,15,0));

        contentPanel.add(titulo, BorderLayout.NORTH);

        // PANEL CENTRAL DINÁMICO
        JPanel dynamicPanel = new JPanel(new CardLayout());
        dynamicPanel.setBackground(Color.WHITE);

        contentPanel.add(dynamicPanel, BorderLayout.CENTER);

        // CARGAR DESDE BD
        cargarAplicaciones(menu, dynamicPanel);

        add(leftPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void cargarAplicaciones(JPanel menu, JPanel dynamicPanel) {

        try {
            Connection con = Conexion.conectar();

            String sql = "SELECT Tipo_Aplicacion FROM Aplicacion";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            HashSet<String> tipos = new HashSet<>();

            while(rs.next()) {
                String tipo = rs.getString("Tipo_Aplicacion");

                if (!tipos.contains(tipo)) {
                    tipos.add(tipo);

                    // CREAR PANEL
                    JPanel panel = crearPanel(tipo);
                    dynamicPanel.add(panel, tipo);

                    // BOTÓN MENÚ
                    JButton btn = new JButton(tipo);
                    btn.setFocusPainted(false);
                    btn.setBackground(new Color(255,255,255,50));
                    btn.setForeground(Color.WHITE);
                    btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

                    btn.addActionListener(e -> {
                        CardLayout cl = (CardLayout)(dynamicPanel.getLayout());
                        cl.show(dynamicPanel, tipo);
                    });

                    menu.add(btn);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JPanel crearPanel(String tipo) {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel titulo = new JLabel(tipo, SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));

        panel.add(titulo, BorderLayout.NORTH);

        JPanel contenido = new JPanel();
        contenido.setBackground(new Color(245,245,245));
        contenido.setLayout(new GridLayout(2,2,20,20));
        contenido.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));

        for (int i = 1; i <= 4; i++) {
            JButton btn = new JButton(tipo + " " + i);
            btn.setFocusPainted(false);
            btn.setBackground(new Color(0,120,215));
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Arial", Font.BOLD, 12));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            contenido.add(btn);
        }

        panel.add(contenido, BorderLayout.CENTER);

        return panel;
    }
}