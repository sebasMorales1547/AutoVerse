/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaServicios;

/**
 *
 * @author Familia
 */
public class PagoEPayco implements MetodoPago {
    private final String PUBLIC_KEY = "tu_public_key_epayco";

    public String prepararPasarela(double monto, String descripcion) {

        return "EPAYCO_CONFIG:{key:'" + PUBLIC_KEY + "', name:'" + descripcion + "', currency:'cop'}";
    }

    @Override
    public void procesarPago(double monto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}