package CapaUtilidades;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL =
            "jdbc:oracle:thin:@localhost:1521/xepdb1";

    private static final String USER = "autoverse";

    private static final String PASS = "1234";

   public static Connection getConexion() throws SQLException {
    try {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        return DriverManager.getConnection(URL, USER, PASS);
    } catch (ClassNotFoundException e) {
        throw new SQLException("Driver Oracle no encontrado", e);
    }
    }
}