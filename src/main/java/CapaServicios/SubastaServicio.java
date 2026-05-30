/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaServicios;
import CapaExcepciones.Excepciones;
import java.sql.SQLException;

public interface SubastaServicio {
    void iniciarSubasta(int idAuto, double precioBase) throws Excepciones, SQLException;
    void realizarPuja(int idAuto, double monto, String usuario) throws Excepciones, SQLException;
}