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
        long cedulaUsuario;
        try {
            cedulaUsuario = Long.parseLong(usuario);
        } catch (NumberFormatException e) {
            throw new Excepciones("El identificador de usuario no es válido.");
        }
        
        double precioActual = dao.obtenerMontoActual(idAuto);

        if (monto <= precioActual) {
            throw new Excepciones("La puja debe ser mayor al precio actual: $" + precioActual);
        }

        dao.registrarPuja(idAuto, monto, cedulaUsuario);
        System.out.println("Puja registrada por CC: " + cedulaUsuario + " por un valor de: $" + monto);
    }
}