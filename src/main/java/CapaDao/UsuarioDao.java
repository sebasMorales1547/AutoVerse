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
        String sql = "INSERT INTO usuarios (nombre_usuario, contrasena, email, rol) VALUES (?, ?, ?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, hashContrasena(usuario.getContrasena()));
            ps.setString(3, usuario.getCorreo());
            ps.setString(4, usuario.getRol() != null ? usuario.getRol().name() : RolUsuario.COMPRADOR.name());
            ps.executeUpdate();
        }
    }

    public Usuarios validarLogin(String user, String pass) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE nombre_usuario = ? AND contrasena = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user);
            ps.setString(2, hashContrasena(pass));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Usuarios u = new Usuarios();
                u.setCedula(rs.getInt("id"));
                u.setNombre(rs.getString("nombre_usuario"));
                u.setContrasena(rs.getString("contrasena"));
                u.setCorreo(rs.getString("email"));
                // Se carga el rol en la base de datos
                try {
                    u.setRol(RolUsuario.valueOf(rs.getString("rol")));
                } catch (Exception e) {
                    u.setRol(RolUsuario.COMPRADOR); 
                }
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
}