package Program.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Lenovo
 */
public class ConexionDB {private static final String SERVIDOR = "localhost";
    private static final String PUERTO = "3306";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "38688610";  // Tu contraseña
    private static final String BASE_DE_DATOS = "titos_restobar";  // Tu nombre de BD

    public static Connection Conectar() throws SQLException {
        String cadenaConexion = "jdbc:mysql://" + SERVIDOR + ":" + PUERTO + "/" + BASE_DE_DATOS;
        try {
            Connection conexion = DriverManager.getConnection(cadenaConexion, USUARIO, PASSWORD);
            System.out.println("¡Conexión exitosa a la BD!");  // Mensaje de confirmación
            return conexion;
        } catch (SQLException e) {
            System.err.println("Error al conectar: " + e.getMessage());
            throw e;  // Relanzamos la excepción para manejo externo
        }
    }
}
    

