/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaDao;

import CapaModelo.RolUsuario;
import CapaModelo.Usuarios;
import CapaUtilidades.Conexion;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class UsuarioDao {

    private String hashContrasena(String contrasena) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(contrasena.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al hashear la contraseña.", e);
        }
    }

    public void registrarUsuario(Usuarios usuario) throws SQLException {

    String sql = """
        INSERT INTO usuarios
        (cedula, nombre, apellido, correo, contrasena, telefono)
        VALUES (?, ?, ?, ?, ?, ?)
    """;

    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, usuario.getCedula());
        ps.setString(2, usuario.getNombre());
        ps.setString(3, usuario.getApellido());
        ps.setString(4, usuario.getCorreo());
        ps.setString(5, hashContrasena(usuario.getContrasena()));
        ps.setString(6, usuario.getTelefono());

        ps.executeUpdate();
    }
}

    public Usuarios validarLogin(String correo, String pass) throws SQLException {

    String sql = """
        SELECT *
        FROM usuarios
        WHERE correo = ?
        AND contrasena = ?
    """;

    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, correo);
        ps.setString(2, hashContrasena(pass));

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            Usuarios u = new Usuarios();

            u.setCedula(rs.getInt("cedula"));
            u.setNombre(rs.getString("nombre"));
            u.setApellido(rs.getString("apellido"));
            u.setCorreo(rs.getString("correo"));
            u.setTelefono(rs.getString("telefono"));

            return u;
        }
    }

    return null;
}

    public void actualizarRol(int cedula, RolUsuario nuevoRol) throws SQLException {
        String sql = "UPDATE usuarios SET rol = ? WHERE id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nuevoRol.name());
            ps.setInt(2, cedula);
            ps.executeUpdate();
        }
    }
    /**
     * @param correo
     * @param nuevaContrasena
     * @throws java.sql.SQLException
     */
    public void actualizarContrasena(String correo, String nuevaContrasena) throws SQLException {
        String sql = "UPDATE usuarios SET contrasena = ? WHERE email = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, hashContrasena(nuevaContrasena));
            ps.setString(2, correo);
            ps.executeUpdate();
        }
    }
}