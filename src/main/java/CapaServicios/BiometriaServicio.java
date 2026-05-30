/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaServicios;

import CapaDao.Dao;
import CapaModelo.Ventas;
import CapaExcepciones.Excepciones;

public class BiometriaServicio {
    private final Dao dao = new Dao();

    public void procesarVentaSegura(Ventas nuevaVenta, int idPublicacion, double similitudRostro) throws Exception {
        
        if (similitudRostro >= 0.85) {
            System.out.println("Biometría aceptada. Procediendo con la actualización en Oracle...");
           
            boolean exito = dao.confirmarPagoManual(nuevaVenta, idPublicacion);
            
            if (!exito) {
                throw new Excepciones("Error al actualizar la base de datos.");
            }
        } else {
            throw new Excepciones("Acceso denegado: El rostro no coincide con el titular de la cuenta.");
        }
    }
}