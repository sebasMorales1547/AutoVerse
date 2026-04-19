package CapaServicios;
import CapaDao.Dao;
import CapaExcepciones.Excepciones;
import CapaModelo.Auto;
import java.sql.SQLException;

public class VentaServicio {
    private final Dao Dao = new Dao();

    public void realizarCompra(Auto auto, String tipoPago) throws Exception, SQLException {
        MetodoPago metodo;
        switch (tipoPago.toUpperCase()) {
            case "EFECTIVO": metodo = new PagoEfectivo(); break;
            case "DEBITO": metodo = new PagoDebito(); break;
            case "CREDITO": metodo = new PagoCredito(); break;
            default: throw new Excepciones("Método de pago no reconocido.");
        }

        metodo.procesarPago(auto.getPrecio());
        Dao.actualizarEstadoVenta(auto.getId(), true);
    }
}
