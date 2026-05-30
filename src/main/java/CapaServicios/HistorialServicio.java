/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaServicios;

import CapaDao.Dao;
import CapaExcepciones.Excepciones;
import CapaModelo.HistorialCompra;
import CapaUtilidades.SesionActual;
import java.sql.SQLException;
import java.util.List;


public class HistorialServicio {
    private final Dao dao = new Dao();

    public List<HistorialCompra> obtenerMisCompras() throws Excepciones, SQLException {
        if (!SesionActual.haySesionActiva())
            throw new Excepciones("Debes iniciar sesion para ver tu historial.");

        return dao.obtenerHistorialCompras(SesionActual.getUsuario().getCedula());
    }

    
    public List<HistorialCompra> obtenerHistorialDeUsuario(int cedula) throws Excepciones, SQLException {
        if (!SesionActual.esAdmin())
            throw new Excepciones("No tienes permisos para ver el historial de otros usuarios.");

        return dao.obtenerHistorialCompras(cedula);
    }
}