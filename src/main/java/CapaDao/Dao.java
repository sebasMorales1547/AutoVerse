package CapaDao;

import CapaModelo.*;
import CapaUtilidades.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Dao {

    public void crearSubasta(Subasta subasta) throws SQLException {
        String sql = "INSERT INTO OFERTAS (monto, estado, fecha, cedula, id_publicacion) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDouble(1, subasta.getMonto());
            ps.setString(2, "ACTIVA");
            ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            ps.setLong(4, subasta.getCedula());
            ps.setInt(5, subasta.getIdPublicacion());
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

    public boolean confirmarPagoManual(Ventas v, int idPublicacion) throws SQLException {
        String sqlVentas = "INSERT INTO VENTAS (fecha_venta, monto_final, metodo_pago, id_oferta, num_transaccion, referencia, comprobante) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlEstado = "UPDATE PUBLICACIONES SET ESTADO = 'VENDIDO' WHERE id_publicacion = ?";

        try (Connection con = Conexion.getConexion()) {
            con.setAutoCommit(false);

            try (PreparedStatement psV = con.prepareStatement(sqlVentas);
                 PreparedStatement psE = con.prepareStatement(sqlEstado)) {

                psV.setTimestamp(1, new Timestamp(v.getFechaVenta().getTime()));
                psV.setDouble(2, v.getMontoFinal());
                psV.setString(3, v.getMetodoPago());
                psV.setInt(4, v.getIdItem());
                psV.setString(5, v.getNumTransaccion());
                psV.setString(6, v.getReferencia());
                psV.setString(7, v.getComprobanteRuta());
                psV.executeUpdate();

                psE.setInt(1, idPublicacion);
                psE.executeUpdate();

                con.commit();
                return true;

            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        }
    }

    public List<HistorialCompra> obtenerHistorialCompras(int cedula) throws SQLException {
        List<HistorialCompra> lista = new ArrayList<>();
        String sql =
            "SELECT v.id_venta, p.titulo, vh.marca, vh.modelo, " +
            "       v.monto_final, v.metodo_pago, v.fecha_venta " +
            "FROM VENTAS v " +
            "JOIN OFERTAS o  ON v.id_oferta = o.id_ofertas " +
            "JOIN PUBLICACIONES p ON o.id_publicacion = p.id_publicacion " +
            "JOIN VEHICULOS vh ON p.id_publicacion = vh.id_publicacion " +
            "WHERE o.cedula = ? " +
            "ORDER BY v.fecha_venta DESC";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cedula);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    HistorialCompra h = new HistorialCompra();
                    h.setIdVenta(rs.getInt("id_venta"));
                    h.setTituloPublicacion(rs.getString("titulo"));
                    h.setMarcaVehiculo(rs.getString("marca"));
                    h.setModeloVehiculo(rs.getString("modelo"));
                    h.setMontoFinal(rs.getDouble("monto_final"));
                    h.setMetodoPago(rs.getString("metodo_pago"));
                    h.setFechaVenta(rs.getDate("fecha_venta"));
                    h.setEstadoVenta("COMPLETADA");
                    lista.add(h);
                }
            }
        }
        return lista;
    }

    
}