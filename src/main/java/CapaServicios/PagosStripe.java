/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaServicios;

/**
 *
 * @author Familia
 */
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class PagosStripe implements MetodoPago {
  
    private final String LINK_STRIPE = "https://buy.stripe.com/test_6oU28t0yW3ed281axDbo400";

    @Override
    public String prepararPasarela(double monto, String descripcion) {
        return LINK_STRIPE;
    }

    public void abrirNavegador() {
        try {
            Desktop.getDesktop().browse(new URI(LINK_STRIPE));
        } catch (IOException | URISyntaxException e) {
            System.err.println("No se pudo abrir Stripe: " + e.getMessage());
        }
    }

    @Override
    public void procesarPago(double monto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
