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
            SELECT * FROM usuarios
            WHERE correo = ? AND contrasena = ?
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
                try { u.setFotoRuta(rs.getString("foto_ruta")); } catch (Exception ignored) {}
                return u;
            }
        }
        return null;
    }
 
    public void actualizarRol(int cedula, RolUsuario nuevoRol) throws SQLException {
        String sql = "UPDATE usuarios SET rol = ? WHERE cedula = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nuevoRol.name());
            ps.setInt(2, cedula);
            ps.executeUpdate();
        }
    }
 
    public void actualizarContrasena(String correo, String nuevaContrasena) throws SQLException {
        String sql = "UPDATE usuarios SET contrasena = ? WHERE correo = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, hashContrasena(nuevaContrasena));
            ps.setString(2, correo);
            ps.executeUpdate();
        }
    }
 
    public boolean existeCorreo(String correo) throws SQLException {
        String sql = "SELECT 1 FROM usuarios WHERE correo = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, correo);
            try (ResultSet rs = ps.executeQuery()) { return rs.next(); }
        }
    }

    /**
     * @param cedula
     * @param rutaFoto
     * @throws java.sql.SQLException
     */
    public void actualizarFotoRuta(int cedula, String rutaFoto) throws SQLException {
        String sql = "UPDATE usuarios SET foto_ruta = ? WHERE cedula = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, rutaFoto);
            ps.setInt(2, cedula);
            ps.executeUpdate();
        }
    }
 
    /**
     * @param cedula
     * @return 
     * @throws java.sql.SQLException
     */
    public String obtenerFotoRuta(int cedula) throws SQLException {
        String sql = "SELECT foto_ruta FROM usuarios WHERE cedula = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cedula);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("foto_ruta");
        }
        return null;
    }
 
    /**
     * NUEVO: Guarda el descriptor biométrico (128 valores float separados por coma).
     * @param cedula
     * @param descriptor
     * @throws java.sql.SQLException
     */
    public void actualizarDescriptorBio(int cedula, String descriptor) throws SQLException {
        String sql = "UPDATE usuarios SET descriptor_bio = ? WHERE cedula = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, descriptor);
            ps.setInt(2, cedula);
            ps.executeUpdate();
        }
    }
 
    /**
     * @param cedula
     * @return 
     * @throws java.sql.SQLException
     */
    public String obtenerDescriptorBio(int cedula) throws SQLException {
        String sql = "SELECT descriptor_bio FROM usuarios WHERE cedula = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cedula);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("descriptor_bio");
        }
        return null;
    }
}