
package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    // DATOS DE MYSQL
    private static final String URL =
            "jdbc:mysql://localhost:3306/economia_db";

    private static final String USER = "root";

    private static final String PASSWORD = "root";

    // MÉTODO DE CONEXIÓN
    public static Connection conectar() {

        Connection conn = null;

        try {

            // Cargar driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Conectar
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.println("Conexión exitosa a MySQL");

        } catch (ClassNotFoundException e) {

            System.out.println("Driver no encontrado");
            System.out.println(e.getMessage());

        } catch (SQLException e) {

            System.out.println("Error de conexión");
            System.out.println(e.getMessage());
        }

        return conn;
    }
}