/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaServicios;

import CapaDao.Dao;
import CapaExcepciones.Excepciones;
import CapaModelo.Usuario;
import java.sql.SQLException;

public class UsuarioServicio {
    private final Dao dao = new Dao();

    public void registrarNuevoUsuario(String user, String pass, String email) throws Excepciones, SQLException {
        if (user.length() < 4) throw new Excepciones("El nombre de usuario es muy corto.");
        if (pass.length() < 6) throw new Excepciones("La contraseña debe tener al menos 6 caracteres.");
        
        Usuario nuevo = new Usuario(user, pass, email);
        dao.registrarUsuario(nuevo);
    }
    public Usuario iniciarSesion(String user, String pass) throws Excepciones, SQLException {
        Usuario usuario = dao.validarLogin(user, pass);
        if (usuario == null) {
            throw new Excepciones("Usuario o contraseña incorrectos.");
        }
        return usuario; 
    }
}