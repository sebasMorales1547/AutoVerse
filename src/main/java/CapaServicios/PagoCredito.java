/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaServicios;

/**
 *
 * @author Familia
 */

class PagoCredito implements MetodoPago {
    @Override
    public void procesarPago(double monto) { System.out.println("Cargo a tarjeta crédito: " + monto); }

    @Override
    public String prepararPasarela(double monto, String producto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}