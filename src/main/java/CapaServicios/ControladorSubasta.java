package CapaServicios;

import CapaDao.Dao;
import CapaExcepciones.Excepciones;
import CapaModelo.Subasta;
import CapaUtilidades.SesionActual;
import java.sql.SQLException;


public class ControladorSubasta implements SubastaServicio {
    private final Dao dao = new Dao();
    private final NotificacionServicio notificacion = new NotificacionServicio();

    @Override
    public void iniciarSubasta(int idAuto, double precioBase) throws Excepciones, SQLException {
        if (!SesionActual.esVendedor() && !SesionActual.esAdmin())
            throw new Excepciones("Solo un vendedor puede iniciar una subasta.");

        Subasta nueva = new Subasta();
        nueva.setIdPublicacion(idAuto);
        nueva.setMonto(precioBase);
        dao.crearSubasta(nueva);
    }

    @Override
    public void realizarPuja(int idAuto, double monto, String usuario) throws Excepciones, SQLException {
        if (!SesionActual.haySesionActiva())
            throw new Excepciones("Debes iniciar sesión para pujar.");

        long cedulaUsuario;
        try {
            cedulaUsuario = Long.parseLong(usuario);
        } catch (NumberFormatException e) {
            throw new Excepciones("El identificador de usuario no es válido.");
        }

        double precioActual = dao.obtenerMontoActual(idAuto);

        if (monto <= precioActual)
            throw new Excepciones("La puja debe ser mayor al precio actual: $" + precioActual);

        dao.registrarPuja(idAuto, monto, cedulaUsuario);
        System.out.println("Puja registrada por CC: " + cedulaUsuario + " por $" + monto);
    }

    public void realizarPujaConNotificacion(int idAuto, double monto, String usuario,
                                             String correoAnterior, String nombreAnterior,
                                             String tituloVehiculo) throws Excepciones, SQLException {
        double precioActual = dao.obtenerMontoActual(idAuto);
        realizarPuja(idAuto, monto, usuario);

        if (correoAnterior != null && !correoAnterior.isEmpty()) {
            final double montoAnteriorFinal = precioActual;
            new Thread(() -> notificacion.notificarPujaSuperada(
                correoAnterior, nombreAnterior, tituloVehiculo, montoAnteriorFinal, monto
            )).start();
        }
    }
}