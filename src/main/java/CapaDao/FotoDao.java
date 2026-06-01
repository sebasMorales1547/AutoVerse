package CapaDao;

import CapaUtilidades.Conexion;
import java.sql.*;

public class FotoDao {

    public void insertarFoto(String url, int idPublicacion) throws SQLException {
        String sql = "INSERT INTO FOTOS (id_fotos, url, id_publicacion) " +
                     "VALUES (seq_fotos.NEXTVAL, ?, ?)";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, url);
            ps.setInt(2, idPublicacion);
            ps.executeUpdate();
        }
    }

    public String obtenerFotoPrincipal(int idPublicacion) throws SQLException {
        String sql = "SELECT url FROM FOTOS WHERE id_publicacion = ? AND ROWNUM = 1";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPublicacion);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("url");
        }
        return null;
    }
}