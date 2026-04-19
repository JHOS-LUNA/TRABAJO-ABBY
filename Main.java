public class Main {
    public static void main(String[] args) {

        // Buena práctica en Swing (evita bugs gráficos)
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Login();
            }
        });

    }
}