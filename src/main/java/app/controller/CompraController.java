package app.controller;

import CapaServicios.OfertaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/compras")
public class CompraController {

    private final OfertaService ofertaService = new OfertaService();

    @PostMapping("/comprar")
    public String comprar(
            @RequestParam int idPublicacion,
            @RequestParam long cedula) {

        try {

            ofertaService.comprar(idPublicacion, cedula);

            return "Compra realizada correctamente";

        } catch (Exception e) {

            e.printStackTrace();
            return e.getMessage();
        }
    }
}