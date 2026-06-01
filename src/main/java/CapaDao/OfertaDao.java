package CapaDao;

import CapaModelo.Ofertas;
import CapaUtilidades.Conexion;
import java.sql.*;

public class OfertaDao{

public Ofertas obtenerSubastaPorPublicacion(int idPublicacion) throws SQLException {
    String sql = "SELECT * FROM OFERTAS WHERE id_publicacion = ? AND tipo = 'SUBASTA' AND ROWNUM = 1";

    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, idPublicacion);
        ResultSet rs = ps.executeQuery();
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
            return o;
        }
    }
    return null;
}

public String obtenerEstadoPublicacion(int idPublicacion) throws SQLException {
    String sql = "SELECT estado FROM PUBLICACIONES WHERE id_publicacion = ?";

    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, idPublicacion);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) return rs.getString("estado");
    }
    return null;
}

public double obtenerPrecioPublicacion(int idPublicacion) throws SQLException {
    String sql = "SELECT precio FROM PUBLICACIONES WHERE id_publicacion = ?";

    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, idPublicacion);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) return rs.getDouble("precio");
    }
    return 0;
}

public boolean existeUsuario(long cedula) throws SQLException {
    String sql = "SELECT COUNT(*) FROM USUARIOS WHERE cedula = ?";

    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setLong(1, cedula);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) return rs.getInt(1) > 0;
    }
    return false;
}

public void marcarVendido(int idPublicacion) throws SQLException {
    String sql = "UPDATE PUBLICACIONES SET estado = 'Vendido' WHERE id_publicacion = ?";

    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, idPublicacion);
        ps.executeUpdate();
    }
}

public void insertarVenta(double monto, long cedula, int idOferta) throws SQLException {
    String sql = "INSERT INTO VENTAS (id_venta, fecha_venta, monto_final, metodo_pago, cedula, id_oferta) " +
                 "VALUES (seq_ventas.NEXTVAL, SYSDATE, ?, 'Pendiente', ?, ?)";

    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setDouble(1, monto);
        ps.setLong(2, cedula);
        ps.setInt(3, idOferta);
        ps.executeUpdate();
    }
}

public int obtenerUltimoIdOferta(int idPublicacion) throws SQLException {
    String sql = "SELECT MAX(id_ofertas) FROM OFERTAS WHERE id_publicacion = ?";

    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, idPublicacion);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) return rs.getInt(1);
    }
    return 0;
}

public void crearOferta(Ofertas o) throws SQLException {
    String sql = "INSERT INTO OFERTAS (id_ofertas, monto, estado, fecha, cedula, id_publicacion, precio_minimo, fecha_limite, tipo) " +
                 "VALUES (seq_ofertas.NEXTVAL, ?, ?, SYSDATE, ?, ?, ?, ?, ?)";

    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setDouble(1, o.getMonto());
        ps.setString(2, o.getEstado());
        ps.setLong(3, o.getCedula());
        ps.setInt(4, o.getIdPublicacion());
        ps.setDouble(5, o.getPrecioMinimo());
        ps.setTimestamp(6, o.getFechaLimite());
        ps.setString(7, o.getTipo());
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

}