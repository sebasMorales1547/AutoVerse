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
        switch (plataforma.toUpperCase()) {
            case "STRIPE":
                new PagosStripe().abrirNavegador();
                break;
            case "EPAYCO":
                new PagoEPayco().abrirNavegador();
                break;
            case "WOMPI":
                new PagoWompi().abrirPasarela();
                break;
            default:
                System.out.println("Plataforma no reconocida.");
        }
    }
}