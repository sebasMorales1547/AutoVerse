package CapaDao;
import CapaModelo.Auto;
import CapaModelo.Subasta;
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

public void crearSubasta(Subasta subasta) throws SQLException {
    String sql = "INSERT INTO subastas (id_auto, precio_actual, ultimo_postor, fecha_fin) VALUES (?, ?, ?, ?)";
    try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, subasta.getIdAuto());
        ps.setDouble(2, subasta.getPrecioActual());
        ps.setString(3, subasta.getUltimoPostor());
        ps.setTimestamp(4, Timestamp.valueOf(subasta.getFechaFin()));
        ps.executeUpdate();
    }
}

public void registrarPuja(int idAuto, double nuevoPrecio, String usuario) throws SQLException {
    String sql = "UPDATE subastas SET precio_actual = ?, ultimo_postor = ? WHERE id_auto = ?";
    try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setDouble(1, nuevoPrecio);
        ps.setString(2, usuario);
        ps.setInt(3, idAuto);
        ps.executeUpdate();
    }
}
}