/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaServicios;

import CapaDao.UsuarioDao;
import CapaExcepciones.Excepciones;
import CapaModelo.RolUsuario;
import CapaModelo.Usuarios;
import CapaUtilidades.SesionActual;
import java.sql.SQLException;

public class UsuarioServicio {
    private final UsuarioDao usuarioDao = new UsuarioDao();
    private final NotificacionServicio notificacion = new NotificacionServicio();

    public void registrarNuevoUsuario(Usuarios usuario) throws Excepciones, SQLException {

        if (usuario.getNombre().isEmpty() || usuario.getContrasena().isEmpty() || usuario.getCorreo().isEmpty()) {

            throw new Excepciones("Todos los campos son obligatorios.");
        }

        if (usuario.getNombre().length() < 4)
            throw new Excepciones("El nombre debe tener al menos 4 caracteres.");

        if (usuario.getContrasena().length() < 8)
            throw new Excepciones("La contraseña debe tener al menos 8 caracteres.");

        if (!usuario.getCorreo().contains("@"))
            throw new Excepciones("El correo electrónico no es válido.");

        usuarioDao.registrarUsuario(usuario);
    }

    public Usuarios iniciarSesion(String user, String pass) throws Excepciones, SQLException {
        if (user.isEmpty() || pass.isEmpty())
            throw new Excepciones("Por favor, ingrese sus credenciales.");

        Usuarios usuario = usuarioDao.validarLogin(user, pass);
        if (usuario == null)
            throw new Excepciones("Credenciales incorrectas. Intente de nuevo.");

        SesionActual.iniciarSesion(usuario);
        return usuario;
    }

    public void cerrarSesion() {
        SesionActual.cerrarSesion();
    }

    public void cambiarRolUsuario(int cedulaObjetivo, RolUsuario nuevoRol) throws Excepciones, SQLException {
        if (!SesionActual.esAdmin())
            throw new Excepciones("No tienes permisos para cambiar roles de usuario.");
        usuarioDao.actualizarRol(cedulaObjetivo, nuevoRol);
    }

    public void solicitarRecuperacion(String correo)
            throws Excepciones, SQLException {

        if (!usuarioDao.existeCorreo(correo))
            throw new Excepciones("No existe una cuenta registrada con ese correo.");

        notificacion.enviarCodigoRecuperacion(correo, "Usuario");
    }

    /**
     * @param correo
     * @param codigo
     * @return
     */
    public boolean verificarCodigoRecuperacion(String correo, String codigo) {
        return notificacion.verificarCodigoRecuperacion(correo, codigo);
    }

    public void cambiarContrasena(String correo, String codigo, String nuevaContrasena)
            throws Excepciones, SQLException {
        if (nuevaContrasena.length() < 8)
            throw new Excepciones("La nueva contraseña debe tener al menos 8 caracteres.");

        if (!notificacion.verificarCodigoRecuperacion(correo, codigo))
            throw new Excepciones("El código es incorrecto o ya expiró.");

        usuarioDao.actualizarContrasena(correo, nuevaContrasena);
    }
}