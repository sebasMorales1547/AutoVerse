package CapaDao;
import CapaModelo.Auto;
import CapaUtilidades.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Dao {
    public List<Auto> listarDisponibles() throws SQLException {
        List<Auto> lista = new ArrayList<>();
        String sql = "SELECT * FROM autos WHERE vendido = false";
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(new Auto(rs.getInt("id"), rs.getString("marca"), 
                        rs.getString("modelo"), rs.getDouble("precio"), 
                        rs.getString("gama"), rs.getBoolean("vendido")));
            }
        }
        return lista;
    }

    public void actualizarEstadoVenta(int id, boolean estado) throws SQLException {
        String sql = "UPDATE autos SET vendido = ? WHERE id = ?";
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setBoolean(1, estado);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }
}