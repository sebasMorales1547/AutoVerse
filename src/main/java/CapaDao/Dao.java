package CapaDao;

// Importaciones corregidas a CapaModelo
import CapaModelo.Publicacion;
import CapaModelo.Subasta;
import CapaModelo.Ventas;
import CapaUtilidades.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Dao {

    /**
     * @return 
     * @throws java.sql.SQLException
     */
    public List<Publicacion> listarDisponibles() throws SQLException {
        List<Publicacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM PUBLICACIONES WHERE ESTADO = 'DISPONIBLE'";
        
        try (Connection con = Conexion.getConexion(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Publicacion p = new Publicacion();
                // Usamos los nombres exactos de la base de datos de Sebastián
                p.setIdPublicacion(rs.getInt("id_publicacion"));
                p.setTitulo(rs.getString("titulo"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setPrecio(rs.getFloat("precio"));
                p.setEstado(rs.getString("estado"));
                p.setCedula(rs.getLong("cedula"));
                lista.add(p);
            }
        }
        return lista;
    }

    /**
     * @param id
     * @param nuevoEstado
     * @throws java.sql.SQLException
     */
    public void actualizarEstadoVenta(int id, String nuevoEstado) throws SQLException {
        String sql = "UPDATE PUBLICACIONES SET ESTADO = ? WHERE id_publicacion = ?";
        
        try (Connection con = Conexion.getConexion(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, nuevoEstado);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    /**
     * @param subasta
     * @throws java.sql.SQLException
     */
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

    /**
     * @param idOferta
     * @param nuevoMonto
     * @param nuevaCedula
     * @throws java.sql.SQLException
     */
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
     *
     * @param v
     * @param idPublicacion
     * @return
     * @throws SQLException
     */
    public boolean confirmarPagoManual(Ventas v, int idPublicacion) throws SQLException {
    String sqlVentas = "INSERT INTO VENTAS (fecha_venta, monto_final, metodo_pago, id_oferta, num_transaccion, referencia, comprobante) VALUES (?, ?, ?, ?, ?, ?, ?)";
    String sqlEstado = "UPDATE PUBLICACIONES SET ESTADO = 'VENDIDO' WHERE id_publicacion = ?";
    
    Connection con = null;
    try {
        con = Conexion.getConexion();
        con.setAutoCommit(false); // Iniciamos una transacción para que se hagan ambas cosas o ninguna

        // 1. Insertar la Venta
        try (PreparedStatement psV = con.prepareStatement(sqlVentas)) {
            psV.setTimestamp(1, new Timestamp(v.getFechaVenta().getTime()));
            psV.setDouble(2, v.getMontoFinal());
            psV.setString(3, v.getMetodoPago());
            psV.setInt(4, v.getIdItem());
            psV.setString(5, v.getNumTransaccion());
            psV.setString(6, v.getReferencia());
            psV.setString(7, v.getComprobanteRuta());
            psV.executeUpdate();
        }

        try (PreparedStatement psE = con.prepareStatement(sqlEstado)) {
            psE.setInt(1, idPublicacion);
            psE.executeUpdate();
        }

        con.commit();
        return true;
    } catch (SQLException e) {
        if (con != null) con.rollback();
        throw e;
    }
}
    }
