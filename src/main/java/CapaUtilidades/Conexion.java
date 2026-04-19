package CapaUtilidades;
import java.sql.*;

public class Conexion {
    private static final String URL = "jdbc:mysql://localhost:3306/autoverse_db";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}