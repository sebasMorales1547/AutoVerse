package app.controller;

import CapaModelo.Usuarios;
import CapaServicios.UsuarioServicio;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private UsuarioServicio usuarioServicio = new UsuarioServicio();

    @PostMapping("/registro")
    public String registrar(@RequestBody Usuarios usuario) {

    System.out.println("Solicitud recibida");
    System.out.println(usuario.getNombre());

    try {
        usuarioServicio.registrarNuevoUsuario(
            usuario.getNombre(),
            usuario.getContrasena(),
            usuario.getCorreo()
        );

        return "Usuario registrado correctamente";

    } catch (Exception e) {
        e.printStackTrace();
        return e.getMessage();
    }
}
}