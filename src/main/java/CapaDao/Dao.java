package CapaDao;

import CapaModelo.Publicaciones;
import CapaModelo.Subasta;
import CapaModelo.Ventas;
import CapaUtilidades.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Dao {

    public List<Publicaciones> listarDisponibles() throws SQLException {
        List<Publicaciones> lista = new ArrayList<>();
        String sql = "SELECT * FROM PUBLICACIONES WHERE ESTADO = 'DISPONIBLE'";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Publicaciones p = new Publicaciones();
                p.setIdPublicacion(rs.getInt("id_publicacion"));
                p.setTitulo(rs.getString("titulo"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecio(rs.getFloat("precio"));
                p.setEstado(rs.getString("estado"));
                p.setCedula(rs.getInt("cedula"));
                lista.add(p);
            }
        }
        return lista;
    }

    public void actualizarEstadoVenta(int id, String nuevoEstado) throws SQLException {
        String sql = "UPDATE PUBLICACIONES SET ESTADO = ? WHERE id_publicacion = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nuevoEstado);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

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

    /**
     * @param idPublicacion
     * @return 
     * @throws java.sql.SQLException
     */
    public double obtenerMontoActual(int idPublicacion) throws SQLException {
        String sql = "SELECT MAX(monto) FROM OFERTAS WHERE id_publicacion = ? AND estado = 'ACTIVA'";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPublicacion);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
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
}