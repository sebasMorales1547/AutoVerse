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

        try {

            usuarioServicio.registrarNuevoUsuario(usuario);

            return "Usuario registrado correctamente";

        } catch (Exception e) {

        e.printStackTrace();
        return e.getMessage();
        }
    }

    @PostMapping("/login")
    public String login(@RequestBody Usuarios usuario) {

        try {

            usuarioServicio.iniciarSesion(
            usuario.getCorreo(),
            usuario.getContrasena()
        );

        return "OK";

         } catch (Exception e) {

        return e.getMessage();
        }
    }
}