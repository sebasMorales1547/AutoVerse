/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaServicios;
import CapaDao.Dao;
import CapaExcepciones.Excepciones;
import CapaModelo.Subasta;
import java.sql.SQLException;

public class ControladorSubasta implements SubastaServicio {
    private final Dao dao = new Dao();

    @Override
    public void iniciarSubasta(int idAuto, double precioBase) throws SQLException {
        Subasta nueva = new Subasta(idAuto, precioBase);
        dao.crearSubasta(nueva);
    }

    @Override
    public void realizarPuja(int idAuto, double monto, String usuario) throws Excepciones, SQLException {
        double precioActual = 10000;
        
        if (monto <= precioActual) {
            throw new Excepciones("La puja debe ser mayor al precio actual.");
        }
        dao.registrarPuja(idAuto, monto, usuario);
        System.out.println("Puja registrada por " + usuario + " por un valor de: " + monto);
    }
}
