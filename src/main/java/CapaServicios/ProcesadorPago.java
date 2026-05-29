/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaServicios;

public class ProcesadorPago {
    
    public String obtenerConfiguracionPago(String tipo, double monto, String producto) {
        MetodoPago pasarela;
        
        if (tipo.equalsIgnoreCase("STRIPE")) {
            pasarela = new PagosStripe();
        } else if (tipo.equalsIgnoreCase("EPAYCO")) {
            pasarela = new PagoEPayco();
        } else {
            return "ERROR: Pasarela no soportada";
        }
        
        return pasarela.prepararPasarela(monto, producto);
    }
}