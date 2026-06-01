package CapaDao;

import CapaModelo.Ofertas;
import CapaUtilidades.Conexion;
import java.sql.*;

public class OfertaDao{

    public void crearOferta(Ofertas o) throws SQLException {
        String sql = "INSERT INTO OFERTAS (id_ofertas, monto, estado, fecha, cedula, id_publicacion, precio_minimo, fecha_limite, tipo) " +
                     "VALUES (seq_ofertas.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDouble(1, o.getMonto());
            ps.setString(2, o.getEstado());
            ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            ps.setLong(4, o.getCedula());
            ps.setInt(5, o.getIdPublicacion());
            ps.setDouble(6, o.getPrecioMinimo());
            ps.setTimestamp(7, o.getFechaLimite());
            ps.setString(8, o.getTipo());
            ps.executeUpdate();
        }
    }

    public void registrarPuja(int idOferta, double nuevoMonto, long nuevaCedula) throws SQLException {
        String sql = "UPDATE OFERTAS SET monto = ?, cedula = ? WHERE id_ofertas = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDouble(1, nuevoMonto);
            ps.setLong(2, nuevaCedula);
            ps.setInt(3, idOferta);
            ps.executeUpdate();
        }
    }

    public double obtenerMontoActual(int idPublicacion) throws SQLException {
        String sql = "SELECT MAX(monto) FROM OFERTAS WHERE id_publicacion = ? AND estado = 'ACTIVA'";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPublicacion);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble(1);
        }
        return 0;
    }

    public Ofertas obtenerSubastaActiva() throws SQLException {
        String sql = "SELECT o.*, p.titulo FROM OFERTAS o " +
                     "JOIN PUBLICACIONES p ON o.id_publicacion = p.id_publicacion " +
                     "WHERE o.tipo = 'SUBASTA' AND o.estado = 'ACTIVA' " +
                     "AND o.fecha_limite > SYSDATE AND ROWNUM = 1";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                Ofertas o = new Ofertas();
                o.setIdOfertas(rs.getInt("id_ofertas"));
                o.setMonto(rs.getDouble("monto"));
                o.setPrecioMinimo(rs.getDouble("precio_minimo"));
                o.setEstado(rs.getString("estado"));
                o.setFechaLimite(rs.getTimestamp("fecha_limite"));
                o.setCedula(rs.getLong("cedula"));
                o.setIdPublicacion(rs.getInt("id_publicacion"));
                o.setTipo(rs.getString("tipo"));
                o.setTituloPublicacion(rs.getString("titulo"));
                return o;
            }
        }
        return null;
    }
}