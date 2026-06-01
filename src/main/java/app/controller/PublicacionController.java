package app.controller;

import CapaDao.PublicacionDao;
import CapaDao.VehiculoDao;
import CapaModelo.Publicaciones;
import CapaModelo.Vehiculos;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/publicaciones")
public class PublicacionController {

    private final PublicacionDao dao = new PublicacionDao();
    private final VehiculoDao vehiculoDao = new VehiculoDao();

    @PostMapping("/crear")
public String crear(@RequestBody Map<String, Object> body) {
    try {
        Publicaciones pub = new Publicaciones();
        pub.setTitulo((String) body.get("titulo"));
        pub.setDescripcion((String) body.get("descripcion"));
        pub.setPrecio(Float.parseFloat(body.get("precio").toString()));
        pub.setCedula(1067603644);

        System.out.println("=== CEDULA QUE SE INSERTA: " + pub.getCedula()); // ← agrega esto

        Vehiculos v = new Vehiculos();
        v.setPlaca((String) body.get("placa"));
        v.setMarca((String) body.get("marca"));
        v.setModelo((String) body.get("modelo"));
        v.setAño(Integer.parseInt(body.get("anio").toString()));
        v.setKilometraje(Float.parseFloat(body.get("kilometraje").toString()));
        v.setColor((String) body.get("color"));
        v.setCombustible((String) body.get("combustible"));

        int idPublicacion = dao.crearPublicacion(pub);
        vehiculoDao.insertarVehiculo(v, idPublicacion);

        return "Publicación creada correctamente";

    } catch (Exception e) {
        e.printStackTrace();
        return e.getMessage();
    }
}

    @GetMapping("/disponibles")
    public List<Publicaciones> listar() {
        try {
            return dao.listarDisponibles();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }

    
}