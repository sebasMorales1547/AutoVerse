package app.controller;

import CapaModelo.Usuarios;
import CapaServicios.UsuarioServicio;
import java.util.Map;
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
            Usuarios u = usuarioServicio.iniciarSesion(
                    usuario.getCorreo(),
                    usuario.getContrasena());

            return "OK:" + u.getCedula() + "|" + u.getNombre();

        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @PostMapping("/recuperar")
    public String recuperar(@RequestBody Map<String, String> body) {

        try {

            usuarioServicio.solicitarRecuperacion(
                    body.get("correo"));

            return "Código enviado al correo";

        } catch (Exception e) {

            return e.getMessage();
        }
    }

    @PostMapping("/cambiar-contrasena")
    public String cambiarContrasena(
            @RequestBody Map<String, String> body) {

        try {

            usuarioServicio.cambiarContrasena(
                    body.get("correo"),
                    body.get("codigo"),
                    body.get("nuevaContrasena")
            );

            return "Contraseña actualizada correctamente";

        } catch (Exception e) {

            return e.getMessage();
        }
    }
}