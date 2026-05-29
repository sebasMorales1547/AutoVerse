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

public class PagoEPayco implements MetodoPago {
  
    private final String LINK_COBRO = "https://payco.link/fee1ce6e-c699-4307-8a3b-e9f93ad24423";

    @Override
    public String prepararPasarela(double monto, String descripcion) {
        return LINK_COBRO;
    }

    public void abrirPasarela() {
        try {
            Desktop.getDesktop().browse(new URI(LINK_COBRO));
        } catch (IOException | URISyntaxException e) {
            System.err.println("Error al abrir ePayco: " + e.getMessage());
        }
    }

    @Override
    public void procesarPago(double monto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    void abrirNavegador() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}