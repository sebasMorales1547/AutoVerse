package CapaDao;

import CapaUtilidades.Conexion;
import CapaModelo.Vehiculos;

import java.sql.*;

public class VehiculoDao {

    public void insertarVehiculo(Vehiculos v, int idPublicacion) throws SQLException {

    String sql = "INSERT INTO Vehiculos " +
                 "(placa, marca, modelo, \"año\", kilometraje, color, id_publicacion) " +
                 "VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, v.getPlaca());
        ps.setString(2, v.getMarca());
        ps.setString(3, v.getModelo());
        ps.setInt(4, v.getAño());      
        ps.setDouble(5, v.getKilometraje());
        ps.setString(6, v.getColor());
        ps.setInt(7, idPublicacion);

        ps.executeUpdate();
    }
}

}