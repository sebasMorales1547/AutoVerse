/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaServicios;

import CapaDao.UsuarioDao; 
import CapaExcepciones.Excepciones;
import CapaModelo.Usuarios;
import java.sql.SQLException;

public class UsuarioServicio {
    private final UsuarioDao usuarioDao = new UsuarioDao();

    public void registrarNuevoUsuario(String user, String pass, String email) throws Excepciones, SQLException {
        if (user.isEmpty() || pass.isEmpty() || email.isEmpty()) {
            throw new Excepciones("Todos los campos son obligatorios.");
        }
        if (user.length() < 4) {
            throw new Excepciones("El nombre de usuario debe tener al menos 4 caracteres.");
        }
        if (pass.length() < 8) {
            throw new Excepciones("La contraseña debe tener al menos 8 caracteres.");
        }
        
        Usuarios nuevo = new Usuarios(user, pass, email);
        usuarioDao.registrarUsuario(nuevo);
    }

    public Usuarios iniciarSesion(String user, String pass) throws Excepciones, SQLException {
        if (user.isEmpty() || pass.isEmpty()) {
            throw new Excepciones("Por favor, ingrese sus credenciales.");
        }
        Usuarios usuario = usuarioDao.validarLogin(user, pass);
        
        if (usuario == null) {
            throw new Excepciones("Credenciales incorrectas. Intente de nuevo.");
        }
        return usuario; 
    }
}
