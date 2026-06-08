/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.controller;

import CapaDao.UsuarioDao;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/biometria")
@CrossOrigin(origins = "*")
public class BiometriaController {

    private final UsuarioDao usuarioDao = new UsuarioDao();
    private static final String FOTO_DIR = "uploads/fotos/";
    private static final double UMBRAL_SIMILITUD = 0.85;

    @PostMapping("/registrar-foto")
    public ResponseEntity<String> registrarFoto(
            @RequestParam("cedula") int cedula,
            @RequestParam("foto")   MultipartFile foto) {
        try {
            // Crear carpeta si no existe
            String dirAbsoluto = System.getProperty("user.dir") + File.separator +
                                 "uploads" + File.separator + "fotos";
            Files.createDirectories(Paths.get(dirAbsoluto));

            // Guardar foto con nombre = cedula.jpg
            String nombreArchivo = cedula + ".jpg";
            String rutaCompleta  = dirAbsoluto + File.separator + nombreArchivo;
            foto.transferTo(Paths.get(rutaCompleta));

            // Guardar ruta en BD
            String rutaBD = "/uploads/fotos/" + nombreArchivo;
            usuarioDao.actualizarFotoRuta(cedula, rutaBD);

            return ResponseEntity.ok("Foto registrada correctamente");
        } catch (IOException | IllegalStateException | SQLException e) {
            return ResponseEntity.internalServerError().body("Error al guardar la foto: " + e.getMessage());
        }
    }

    @PostMapping("/registrar-descriptor")
    public ResponseEntity<String> registrarDescriptor(@RequestBody Map<String, Object> body) {
        try {
            int cedula = ((Number) body.get("cedula")).intValue();
            List<Double> descriptor = (List<Double>) body.get("descriptor");

            // Convierte los 128 valores a String separado por comas
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < descriptor.size(); i++) {
                sb.append(descriptor.get(i));
                if (i < descriptor.size() - 1) sb.append(",");
            }

            usuarioDao.actualizarDescriptorBio(cedula, sb.toString());
            return ResponseEntity.ok("Descriptor biométrico guardado");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/verificar")
    public ResponseEntity<Map<String, Object>> verificar(@RequestBody Map<String, Object> body) {
        Map<String, Object> respuesta = new HashMap<>();
        try {
            int cedula = ((Number) body.get("cedula")).intValue();
            List<Double> descriptorCapturado = (List<Double>) body.get("descriptor");

            // Obtiene el descriptor guardado del usuario
            String descriptorGuardadoStr = usuarioDao.obtenerDescriptorBio(cedula);

            if (descriptorGuardadoStr == null || descriptorGuardadoStr.isEmpty()) {
                respuesta.put("similitud", 0.0);
                respuesta.put("mensaje", "No hay descriptor biométrico registrado para este usuario.");
                return ResponseEntity.ok(respuesta);
            }

            // Parsea el descriptor guardado
            String[] partes = descriptorGuardadoStr.split(",");
            double[] descriptorGuardado = new double[partes.length];
            for (int i = 0; i < partes.length; i++) {
                descriptorGuardado[i] = Double.parseDouble(partes[i].trim());
            }

            // Calcula similitud euclidiana (igual que BiometriaServicio.java)
            double similitud = calcularSimilitudEuclidiana(descriptorCapturado, descriptorGuardado);

            respuesta.put("similitud", similitud);
            respuesta.put("verificado", similitud >= UMBRAL_SIMILITUD);
            respuesta.put("mensaje", similitud >= UMBRAL_SIMILITUD
                ? "Identidad verificada correctamente."
                : "Rostro no reconocido.");

            return ResponseEntity.ok(respuesta);

        } catch (NumberFormatException | SQLException e) {
            respuesta.put("similitud", 0.0);
            respuesta.put("mensaje", "Error al verificar: " + e.getMessage());
            return ResponseEntity.internalServerError().body(respuesta);
        }
    }

    private double calcularSimilitudEuclidiana(List<Double> capturado, double[] guardado) {
        if (capturado.size() != guardado.length) return 0.0;

        double suma = 0.0;
        for (int i = 0; i < guardado.length; i++) {
            double diff = capturado.get(i) - guardado[i];
            suma += diff * diff;
        }
        double distancia = Math.sqrt(suma);
        return Math.max(0.0, 1.0 - distancia);
    }
}
