package CapaServicios;

import CapaDao.*;
import CapaExcepciones.Excepciones;
import CapaModelo.Publicaciones;
import CapaUtilidades.SesionActual;
import java.sql.SQLException;

public class VentaServicio {
    private final PublicacionDao dao = new PublicacionDao();

    public void realizarCompra(Publicaciones publicacion, String tipoPago) throws Excepciones, SQLException {
        if (!SesionActual.haySesionActiva())
            throw new Excepciones("Debes iniciar sesión para realizar una compra.");

        MetodoPago metodo;
        switch (tipoPago.toUpperCase()) {
            case "EFECTIVO": metodo = new PagoEfectivo(); break;
            case "DEBITO":   metodo = new PagoDebito();   break;
            case "CREDITO":  metodo = new PagoCredito();  break;
            default: throw new Excepciones("Método de pago no reconocido.");
        }

        metodo.procesarPago(publicacion.getPrecio());
        dao.actualizarEstadoVenta(publicacion.getIdPublicacion(), "VENDIDO");
    }

    public void validarSoporte(String numTransaccion, String referencia) throws Excepciones {
        if (numTransaccion == null || numTransaccion.trim().isEmpty() ||
            referencia == null || referencia.trim().isEmpty()) {
            throw new Excepciones("Debes ingresar los datos del soporte de pago.");
        }
    }
}