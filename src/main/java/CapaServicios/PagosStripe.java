/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaServicios;

/**
 *
 * @author Familia
 */

public class PagosStripe implements MetodoPago {
    private final String PUBLIC_KEY = "pk_test_tu_llave_stripe";
    public String prepararPasarela(double monto, String descripcion) {
        
        return "STRIPE_CONFIG:{key:'" + PUBLIC_KEY + "', amount:" + (monto * 100) + "}";
    }

    @Override
    public void procesarPago(double monto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
