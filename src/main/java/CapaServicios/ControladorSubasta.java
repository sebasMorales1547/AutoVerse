package CapaServicios;

import CapaDao.Dao;
import CapaExcepciones.Excepciones;
import CapaModelo.Subasta;
import java.sql.SQLException;

public class ControladorSubasta implements SubastaServicio {
    private final Dao dao = new Dao();

    @Override
    public void iniciarSubasta(int idAuto, double precioBase) throws SQLException {
        
        Subasta nueva = new Subasta();
        nueva.setIdPublicacion(idAuto);
        nueva.setMonto(precioBase);
        dao.crearSubasta(nueva);
    }

    @Override
    public void realizarPuja(int idAuto, double monto, String usuario) throws Excepciones, SQLException {
        long cedulaUsuario = Long.parseLong(usuario); 
        double precioActual = 10000; 
        if (monto <= precioActual) {
            throw new Excepciones("La puja debe ser mayor al precio actual.");
        }

  
        dao.registrarPuja(idAuto, monto, cedulaUsuario);
        
        System.out.println("Puja registrada por CC: " + cedulaUsuario + " por un valor de: " + monto);
    }
}