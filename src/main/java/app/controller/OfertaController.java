package app.controller;

import CapaServicios.OfertaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/ofertas")
@CrossOrigin(origins = "*")
public class OfertaController {

    private final OfertaService ofertaService = new OfertaService();

    @PostMapping("/pujar")
    public ResponseEntity<String> pujar(@RequestBody Map<String, Object> body) {
        try {
            int idPublicacion = (int) body.get("idPublicacion");
            double monto      = ((Number) body.get("monto")).doubleValue();
            long cedula       = ((Number) body.get("cedula")).longValue();

            ofertaService.pujar(idPublicacion, monto, cedula);
            return ResponseEntity.ok("Puja registrada con éxito ✔");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al procesar la puja");
        }
    }

    @PostMapping("/comprar")
    public ResponseEntity<String> comprar(@RequestBody Map<String, Object> body) {
        try {
            int idPublicacion = (int) body.get("idPublicacion");
            long cedula       = ((Number) body.get("cedula")).longValue();

            ofertaService.comprar(idPublicacion, cedula);
            return ResponseEntity.ok("Compra realizada con éxito ✔");

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al procesar la compra");
        }
    }
}