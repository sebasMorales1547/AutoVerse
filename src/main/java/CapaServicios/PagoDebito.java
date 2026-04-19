/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaServicios;

/**
 *
 * @author Familia
 */

class PagoDebito implements MetodoPago {
    @Override
    public void procesarPago(double monto) { System.out.println("Cargo a cuenta débito: " + monto); }
}
