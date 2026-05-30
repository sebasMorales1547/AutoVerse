package CapaServicios;

import CapaDao.Dao;
import CapaExcepciones.Excepciones;
import java.sql.SQLException;
import CapaModelo.Publicacion;

public class VentaServicio {
    private final Dao dao = new Dao(); 

    public void realizarCompra(Publicacion publicacion, String tipoPago) throws Exception, SQLException {
        MetodoPago metodo;
        switch (tipoPago.toUpperCase()) {
            case "EFECTIVO": metodo = new PagoEfectivo(); break;
            case "DEBITO": metodo = new PagoDebito(); break;
            case "CREDITO": metodo = new PagoCredito(); break;
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