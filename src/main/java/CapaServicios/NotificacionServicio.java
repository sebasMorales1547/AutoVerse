/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaServicios;

import CapaUtilidades.EmailConfig;
import CapaUtilidades.EmailPlantillas;
import CapaUtilidades.EmailServicio;
import CapaUtilidades.RecuperacionContrasena;
import CapaExcepciones.Excepciones;
import jakarta.mail.MessagingException;


public class NotificacionServicio {
    private final EmailServicio email = new EmailServicio();

    public void notificarVentaCerrada(String correoComprador, String nombreComprador,
                                       String correoVendedor,  String nombreVendedor,
                                       String tituloVehiculo,  String marca, String modelo,
                                       double monto, String metodoPago, String numTransaccion) {
        try {
            // Correo al comprador
            String htmlComprador = EmailPlantillas.confirmacionCompra(
                nombreComprador, tituloVehiculo, marca, modelo, monto, metodoPago, numTransaccion
            );
            email.enviar(correoComprador, "✅ Compra confirmada — " + tituloVehiculo, htmlComprador);

            // Correo al vendedor
            String htmlVendedor = EmailPlantillas.confirmacionVenta(
                nombreVendedor, tituloVehiculo, monto, metodoPago
            );
            email.enviar(correoVendedor, "💰 Tu vehículo fue vendido — " + tituloVehiculo, htmlVendedor);

        } catch (MessagingException e) {
            // El fallo de correo no debe interrumpir la venta ya registrada en la base de datos
            System.err.println("Advertencia: no se pudo enviar correo de venta. " + e.getMessage());
        }
    }
    
    public void notificarPujaSuperada(String correoUsuario, String nombreUsuario,
                                       String tituloVehiculo,
                                       double montoAnterior, double montoNuevo) {
        try {
            String html = EmailPlantillas.pujasSuperada(
                nombreUsuario, tituloVehiculo, montoAnterior, montoNuevo
            );
            email.enviar(correoUsuario, "⚠️ Tu puja fue superada — " + tituloVehiculo, html);

        } catch (MessagingException e) {
            System.err.println("Advertencia: no se pudo notificar puja superada. " + e.getMessage());
        }
    }
    
    public void enviarCodigoRecuperacion(String correo, String nombreUsuario) throws Excepciones {
        String codigo = RecuperacionContrasena.generarCodigo(correo);
        try {
            String html = EmailPlantillas.recuperacionContrasena(nombreUsuario, codigo);
            email.enviar(correo, "🔐 Código de recuperación — Autoverse", html);
        } catch (MessagingException e) {
            throw new Excepciones("No se pudo enviar el correo de recuperación. Verifica tu conexión.");
        }
    }

    public boolean verificarCodigoRecuperacion(String correo, String codigoIngresado) {
        return RecuperacionContrasena.verificarCodigo(correo, codigoIngresado);
    }
}