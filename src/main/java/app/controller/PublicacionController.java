package app.controller;

import CapaDao.FotoDao;
import CapaDao.OfertaDao;
import CapaDao.PublicacionDao;
import CapaDao.VehiculoDao;
import CapaModelo.Ofertas;
import CapaModelo.Publicaciones;
import CapaModelo.PublicacionDetalle;
import CapaModelo.Vehiculos;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/publicaciones")
public class PublicacionController {

    private final PublicacionDao dao       = new PublicacionDao();
    private final VehiculoDao vehiculoDao  = new VehiculoDao();
    private final FotoDao fotoDao          = new FotoDao();
    private final OfertaDao ofertaDao      = new OfertaDao();

    private static final String UPLOAD_DIR = "uploads/vehiculos/";

    @PostMapping("/crear")
    public String crear(
            @RequestParam("titulo")                          String titulo,
            @RequestParam("descripcion")                     String descripcion,
            @RequestParam("precio")                          float precio,
            @RequestParam("placa")                           String placa,
            @RequestParam("marca")                           String marca,
            @RequestParam("modelo")                          String modelo,
            @RequestParam("anio")                            int anio,
            @RequestParam("kilometraje")                     float kilometraje,
            @RequestParam("color")                           String color,
            @RequestParam("combustible")                     String combustible,
            @RequestParam("tipo")                            String tipo,
            @RequestParam(value = "precioMin",   required = false) Double precioMin,
            @RequestParam(value = "fechaLimite", required = false) String fechaLimite,
            @RequestParam(value = "imagen",      required = false) MultipartFile imagen) {
        try {
            Publicaciones pub = new Publicaciones();
            pub.setTitulo(titulo);
            pub.setDescripcion(descripcion);
            pub.setPrecio(precio);
            pub.setCedula(1067603644);
            pub.setTipo(tipo);

            Vehiculos v = new Vehiculos();
            v.setPlaca(placa);
            v.setMarca(marca);
            v.setModelo(modelo);
            v.setAño(anio);
            v.setKilometraje(kilometraje);
            v.setColor(color);
            v.setCombustible(combustible);

            int idPublicacion = dao.crearPublicacion(pub);
            vehiculoDao.insertarVehiculo(v, idPublicacion);

            if ("SUBASTA".equals(tipo) && precioMin != null && fechaLimite != null) {
                Ofertas oferta = new Ofertas();
                oferta.setMonto(precioMin);
                oferta.setPrecioMinimo(precioMin);
                oferta.setEstado("ACTIVA");
                oferta.setTipo("SUBASTA");
                oferta.setFechaLimite(Timestamp.valueOf(LocalDateTime.parse(fechaLimite.replace("T", "T").length() == 16 ? fechaLimite + ":00" : fechaLimite)));
                oferta.setCedula(1067603644L);
                oferta.setIdPublicacion(idPublicacion);
                OfertaDao.crearOferta(oferta);
            }

            if (imagen != null && !imagen.isEmpty()) {
                String dirAbsoluto  = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "vehiculos";
                Files.createDirectories(Paths.get(dirAbsoluto));
                String nombreArchivo = System.currentTimeMillis() + "_" + imagen.getOriginalFilename();
                String rutaCompleta  = dirAbsoluto + File.separator + nombreArchivo;
                imagen.transferTo(Paths.get(rutaCompleta));
                fotoDao.insertarFoto("/uploads/vehiculos/" + nombreArchivo, idPublicacion);
            }

            return "Publicación creada correctamente";

        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @GetMapping("/disponibles")
    public List<PublicacionDetalle> listar() {
        try {
            return dao.listarDisponiblesDetalle();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }
}