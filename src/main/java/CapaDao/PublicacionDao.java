package CapaDao;

import CapaModelo.*;
import CapaUtilidades.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PublicacionDao{

    public List<Publicaciones> listarDisponibles() throws SQLException {
        List<Publicaciones> lista = new ArrayList<>();
        String sql = "SELECT * FROM PUBLICACIONES WHERE ESTADO = 'DISPONIBLE'";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearPublicacion(rs));
            }
        }
        return lista;
    }

    public List<Publicaciones> buscarConFiltros(FiltroVehiculo filtro) throws SQLException {
        List<Publicaciones> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT p.* FROM PUBLICACIONES p " +
            "JOIN VEHICULOS v ON p.id_publicacion = v.id_publicacion " +
            "WHERE p.ESTADO = 'DISPONIBLE'"
        );

        if (filtro.getMarca() != null)          sql.append(" AND v.marca = ?");
        if (filtro.getModelo() != null)         sql.append(" AND v.modelo = ?");
        if (filtro.getAñoMin() != null)         sql.append(" AND v.año >= ?");
        if (filtro.getAñoMax() != null)         sql.append(" AND v.año <= ?");
        if (filtro.getPrecioMin() != null)      sql.append(" AND p.precio >= ?");
        if (filtro.getPrecioMax() != null)      sql.append(" AND p.precio <= ?");
        if (filtro.getGama() != null)           sql.append(" AND v.gama = ?");
        if (filtro.getKilometrajeMax() != null) sql.append(" AND v.kilometraje <= ?");

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            int i = 1;
            if (filtro.getMarca() != null)          ps.setString(i++, filtro.getMarca());
            if (filtro.getModelo() != null)         ps.setString(i++, filtro.getModelo());
            if (filtro.getAñoMin() != null)         ps.setInt(i++, filtro.getAñoMin());
            if (filtro.getAñoMax() != null)         ps.setInt(i++, filtro.getAñoMax());
            if (filtro.getPrecioMin() != null)      ps.setDouble(i++, filtro.getPrecioMin());
            if (filtro.getPrecioMax() != null)      ps.setDouble(i++, filtro.getPrecioMax());
            if (filtro.getGama() != null)           ps.setString(i++, filtro.getGama());
            if (filtro.getKilometrajeMax() != null) ps.setFloat(i++, filtro.getKilometrajeMax());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapearPublicacion(rs));
            }
        }
        return lista;
    }

    public void crearPublicacion(Publicaciones pub, Vehiculos vehiculo) throws SQLException {
        String sqlPub = "INSERT INTO PUBLICACIONES (titulo, descripcion, precio, estado, cedula) " +
                        "VALUES (?, ?, ?, 'DISPONIBLE', ?)";

        try (Connection con = Conexion.getConexion()) {
            con.setAutoCommit(false);

            try {
                int idGenerado;

                try (PreparedStatement ps = con.prepareStatement(sqlPub, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, pub.getTitulo());
                    ps.setString(2, pub.getDescripcion());
                    ps.setFloat(3, pub.getPrecio());
                    ps.setInt(4, (int) pub.getCedula());
                    ps.executeUpdate();

                    ResultSet keys = ps.getGeneratedKeys();
                    if (!keys.next()) throw new SQLException("No se pudo obtener el ID de la publicación.");
                    idGenerado = keys.getInt(1);
                }

                VehiculoDao vehiculoDao = new VehiculoDao();
                vehiculoDao.insertarVehiculo(vehiculo, idGenerado);

                con.commit();

            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        }
    }

    public List<Publicaciones> listarPorVendedor(int cedula) throws SQLException {
        List<Publicaciones> lista = new ArrayList<>();
        String sql = "SELECT * FROM PUBLICACIONES WHERE cedula = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cedula);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapearPublicacion(rs));
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

    private Publicaciones mapearPublicacion(ResultSet rs) throws SQLException {
        Publicaciones p = new Publicaciones();
        p.setIdPublicacion(rs.getInt("id_publicacion"));
        p.setTitulo(rs.getString("titulo"));
        p.setDescripcion(rs.getString("descripcion"));
        p.setPrecio(rs.getFloat("precio"));
        p.setEstado(rs.getString("estado"));
        p.setCedula(rs.getInt("cedula"));
        return p;
    }
}