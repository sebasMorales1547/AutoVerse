/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaServicios;

/**
 *
 * @author Familia
 */
public class GestionPasarelas {
    
    public void procesarSeleccion(String plataforma) {
        if (plataforma.equalsIgnoreCase("STRIPE")) {
            new PagosStripe().abrirNavegador();
        } else if (plataforma.equalsIgnoreCase("EPAYCO")) {
            new PagoEPayco().abrirNavegador();
        }
    }
}