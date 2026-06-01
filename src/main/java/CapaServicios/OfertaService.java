package CapaServicios;

import CapaDao.OfertaDao;
import CapaModelo.Ofertas;
import java.sql.SQLException;
import java.sql.Timestamp;

public class OfertaService {

    private final OfertaDao ofertaDao = new OfertaDao();

    public void pujar(int idPublicacion, double monto, long cedula) throws SQLException {

        String estado = ofertaDao.obtenerEstadoPublicacion(idPublicacion);
        if (estado == null)
            throw new IllegalArgumentException("Publicación no encontrada");
        if (!"DISPONIBLE".equals(estado))
            throw new IllegalArgumentException("Esta publicación ya no está disponible");

        Ofertas subasta = ofertaDao.obtenerSubastaPorPublicacion(idPublicacion);
        if (subasta == null)
            throw new IllegalArgumentException("No es una subasta activa");

        Timestamp fechaLimite = subasta.getFechaLimite();
        if (fechaLimite != null && fechaLimite.before(new Timestamp(System.currentTimeMillis())))
            throw new IllegalArgumentException("La subasta ya cerró");

        double montoActual = ofertaDao.obtenerMontoActual(idPublicacion);
        if (monto <= montoActual)
            throw new IllegalArgumentException("La puja debe superar el monto actual");

        if (!ofertaDao.existeUsuario(cedula))
            throw new IllegalArgumentException("Usuario no encontrado");

        ofertaDao.registrarPuja(subasta.getIdOfertas(), monto, cedula);
    }

    public void comprar(int idPublicacion, long cedula) throws SQLException {

        String estado = ofertaDao.obtenerEstadoPublicacion(idPublicacion);
        if (estado == null)
            throw new IllegalArgumentException("Publicación no encontrada");
        if (!"DISPONIBLE".equals(estado))
            throw new IllegalArgumentException("Este vehículo ya no está disponible");

        if (!ofertaDao.existeUsuario(cedula))
            throw new IllegalArgumentException("Usuario no encontrado");

        double precio = ofertaDao.obtenerPrecioPublicacion(idPublicacion);

        Ofertas oferta = new Ofertas();
        oferta.setMonto(precio);
        oferta.setEstado("ACEPTADA");
        oferta.setCedula(cedula);
        oferta.setIdPublicacion(idPublicacion);
        oferta.setTipo("DIRECTA");
        oferta.setPrecioMinimo(0);
        oferta.setFechaLimite(null);
        ofertaDao.crearOferta(oferta);

        int idOferta = ofertaDao.obtenerUltimoIdOferta(idPublicacion);
        ofertaDao.insertarVenta(precio, cedula, idOferta);
        ofertaDao.marcarVendido(idPublicacion);
    }
}