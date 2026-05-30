/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaUtilidades;

/**
 * Plantillas HTML para cada tipo de correo del sistema.
 * Centraliza el diseño para que sea fácil de modificar.
 */
public class EmailPlantillas {

    public static String confirmacionCompra(String nombreComprador, String tituloVehiculo,
                                             String marca, String modelo,
                                             double monto, String metodoPago,
                                             String numTransaccion) {
        return "<!DOCTYPE html><html><body style='font-family:Arial,sans-serif;background:#f4f4f4;padding:20px'>"
            + "<div style='max-width:600px;margin:auto;background:white;border-radius:8px;padding:30px'>"
            + "<h2 style='color:#2c3e50'>✅ Compra confirmada — Autoverse</h2>"
            + "<p>Hola <strong>" + nombreComprador + "</strong>, tu compra fue procesada exitosamente.</p>"
            + "<hr style='border:1px solid #eee'>"
            + "<h3 style='color:#2980b9'>Detalle del vehículo</h3>"
            + "<table style='width:100%;border-collapse:collapse'>"
            + fila("Publicación",    tituloVehiculo)
            + fila("Vehículo",       marca + " " + modelo)
            + fila("Monto pagado",   "$" + String.format("%,.2f", monto))
            + fila("Método de pago", metodoPago)
            + fila("N° Transacción", numTransaccion)
            + "</table>"
            + "<br><p style='color:#7f8c8d;font-size:12px'>Este es un correo automático de Autoverse. "
            + "No respondas a este mensaje.</p>"
            + "</div></body></html>";
    }

    public static String confirmacionVenta(String nombreVendedor, String tituloVehiculo,
                                            double monto, String metodoPago) {
        return "<!DOCTYPE html><html><body style='font-family:Arial,sans-serif;background:#f4f4f4;padding:20px'>"
            + "<div style='max-width:600px;margin:auto;background:white;border-radius:8px;padding:30px'>"
            + "<h2 style='color:#27ae60'>💰 Tu vehículo fue vendido — Autoverse</h2>"
            + "<p>Hola <strong>" + nombreVendedor + "</strong>, tu publicación acaba de concretarse.</p>"
            + "<hr style='border:1px solid #eee'>"
            + "<table style='width:100%;border-collapse:collapse'>"
            + fila("Vehículo vendido", tituloVehiculo)
            + fila("Monto recibido",   "$" + String.format("%,.2f", monto))
            + fila("Método de pago",   metodoPago)
            + "</table>"
            + "<br><p style='color:#7f8c8d;font-size:12px'>Gracias por vender en Autoverse.</p>"
            + "</div></body></html>";
    }

    public static String pujasSuperada(String nombreUsuario, String tituloVehiculo,
                                        double montoAnterior, double montoNuevo) {
        return "<!DOCTYPE html><html><body style='font-family:Arial,sans-serif;background:#f4f4f4;padding:20px'>"
            + "<div style='max-width:600px;margin:auto;background:white;border-radius:8px;padding:30px'>"
            + "<h2 style='color:#e67e22'>⚠️ Tu puja fue superada — Autoverse</h2>"
            + "<p>Hola <strong>" + nombreUsuario + "</strong>, alguien ha hecho una oferta mayor a la tuya.</p>"
            + "<hr style='border:1px solid #eee'>"
            + "<table style='width:100%;border-collapse:collapse'>"
            + fila("Vehículo",        tituloVehiculo)
            + fila("Tu puja",         "$" + String.format("%,.2f", montoAnterior))
            + fila("Nueva puja",      "$" + String.format("%,.2f", montoNuevo))
            + "</table>"
            + "<br><p>¿Quieres volver a pujar? Ingresa a Autoverse antes de que termine la subasta.</p>"
            + "<p style='color:#7f8c8d;font-size:12px'>Este es un correo automático de Autoverse.</p>"
            + "</div></body></html>";
    }
    
    public static String recuperacionContrasena(String nombreUsuario, String codigo) {
        return "<!DOCTYPE html><html><body style='font-family:Arial,sans-serif;background:#f4f4f4;padding:20px'>"
            + "<div style='max-width:600px;margin:auto;background:white;border-radius:8px;padding:30px'>"
            + "<h2 style='color:#8e44ad'>🔐 Recuperación de contraseña — Autoverse</h2>"
            + "<p>Hola <strong>" + nombreUsuario + "</strong>, recibimos una solicitud para restablecer tu contraseña.</p>"
            + "<p>Usa el siguiente código de verificación. <strong>Expira en 15 minutos.</strong></p>"
            + "<div style='text-align:center;margin:30px 0'>"
            + "<span style='font-size:36px;font-weight:bold;letter-spacing:8px;"
            +              "color:#8e44ad;background:#f0e6ff;padding:15px 30px;border-radius:8px'>"
            + codigo
            + "</span></div>"
            + "<p>Si no solicitaste este cambio, ignora este correo.</p>"
            + "<p style='color:#7f8c8d;font-size:12px'>Este es un correo automático de Autoverse.</p>"
            + "</div></body></html>";
    }
    private static String fila(String etiqueta, String valor) {
        return "<tr>"
            + "<td style='padding:8px;border-bottom:1px solid #eee;color:#7f8c8d;width:40%'>" + etiqueta + "</td>"
            + "<td style='padding:8px;border-bottom:1px solid #eee;font-weight:bold'>" + valor + "</td>"
            + "</tr>";
    }
}