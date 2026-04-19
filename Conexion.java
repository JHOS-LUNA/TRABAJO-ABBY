import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    public static Connection conectar() {
        try {
            String url = "jdbc:postgresql://localhost:5432/cocteles";
            String user = "postgres";
            String password = "postgres";

            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Conectado");

            return conn;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}