/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaServicios;

import CapaDao.*;
import CapaExcepciones.Excepciones;
import CapaModelo.FiltroVehiculo;
import CapaModelo.Publicaciones;
import CapaModelo.Vehiculos;
import CapaUtilidades.SesionActual;
import CapaUtilidades.Validador;
import java.sql.SQLException;
import java.util.List;

public class PublicacionServicio {

    private final PublicacionDao dao = new PublicacionDao();
  
    public void publicarVehiculo(Publicaciones pub, Vehiculos vehiculo) throws Excepciones, SQLException {
        if (!SesionActual.haySesionActiva())
            throw new Excepciones("Debes iniciar sesion para publicar un vehiculo.");

        if (!SesionActual.esVendedor() && !SesionActual.esAdmin())
            throw new Excepciones("Solo los vendedores pueden publicar vehiculos.");

     
        if (pub.getTitulo() == null || pub.getTitulo().trim().isEmpty())
            throw new Excepciones("El titulo de la publicación es obligatorio.");

        Validador.validarPrecioAuto(pub.getPrecio());

        if (vehiculo.getMarca() == null || vehiculo.getMarca().trim().isEmpty())
            throw new Excepciones("La marca del vehiculo es obligatoria.");

        if (vehiculo.getPlaca() == null || vehiculo.getPlaca().trim().isEmpty())
            throw new Excepciones("La placa del vehiculo es obligatoria.");

        if (vehiculo.getAño() < 1900 || vehiculo.getAño() > 2025)
            throw new Excepciones("El año del vehiculo no es valido.");


        pub.setCedula(SesionActual.getUsuario().getCedula());

        dao.crearPublicacion(pub, vehiculo);
    }

   
    public List<Publicaciones> listarDisponibles() throws SQLException {
        return dao.listarDisponibles();
    }

    public List<Publicaciones> buscarVehiculos(FiltroVehiculo filtro) throws SQLException {
        return dao.buscarConFiltros(filtro);
    }

    public List<Publicaciones> misPublicaciones() throws Excepciones, SQLException {
        if (!SesionActual.haySesionActiva())
            throw new Excepciones("Debes iniciar sesión.");

        return dao.listarPorVendedor(SesionActual.getUsuario().getCedula());
    }

    public void desactivarPublicacion(int idPublicacion) throws Excepciones, SQLException {
        if (!SesionActual.esAdmin())
            throw new Excepciones("Solo un administrador puede desactivar publicaciones.");

        dao.actualizarEstadoVenta(idPublicacion, "INACTIVA");
    }
}
