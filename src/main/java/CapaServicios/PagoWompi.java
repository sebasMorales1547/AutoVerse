/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaServicios;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class PagoWompi implements MetodoPago {
   
    private final String LINK_WOMPI = "https://checkout.wompi.co/l/VPOS_wRaEQ1";

    @Override
    public String prepararPasarela(double monto, String descripcion) {
       
        return LINK_WOMPI + "?amount=" + (int)(monto * 100) + "&reference=AUTO-" + System.currentTimeMillis();
    }

    public void abrirPasarela() {
        try {
            Desktop.getDesktop().browse(new URI(LINK_WOMPI));
        } catch (IOException | URISyntaxException e) {
            System.err.println("Error al conectar con Wompi: " + e.getMessage());
        }
    }

    @Override
    public void procesarPago(double monto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}